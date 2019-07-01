import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Tabs, DatePicker, Form, Input, Icon, Button, message, Table } from 'antd';

import {
    G2,
    Chart,
    Geom,
    Axis,
    Tooltip,
    Coord,
    Label,
    Legend,
    View,
    Guide,
    Shape,
    Facet,
    Util
} from "bizcharts";
import Axios from 'axios';

const TabPane = Tabs.TabPane;

class Statistic extends Component {

    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            bookSales: [],
            userPays: [],
        }
    }

    componentWillMount() {
        this.fetch();
    }

    fetch = () => {
        this.setState({loading:true});
        Axios.all([Axios.get("http://localhost:8080/orders/sales"), 
                    Axios.get("http://localhost:8080/orders/pay")])
            .then(Axios.spread((sales, pay)=>{
                this.setState({
                    userPays:pay.data,
                    bookSales:sales.data,
                    loading:false
                });
            })).catch((error)=>{
                this.setState({loading:false});
                message.warn("获取数据出错");
                console.log(error);
            })

    }

    handleSubmit = (e) => {
        e.preventDefault();
        this.setState({loading: true});
        this.props.form.validateFields((err, values) => {
            if (!err) {
                Axios.all([Axios.get("http://localhost:8080/orders/salesBetween", {
                    params: {
                        start: values['dateRange'][0].format('YYYY-MM-DD'),
                        end: values['dateRange'][1].format('YYYY-MM-DD')
                    }
                }), Axios.get("http://localhost:8080/orders/payBetween", {
                    params: {
                        start: values['dateRange'][0].format('YYYY-MM-DD'),
                        end: values['dateRange'][1].format('YYYY-MM-DD')
                    }
                })]).then(Axios.spread((sales, pay)=>{
                    this.setState({
                        userPays:pay.data,
                        bookSales:sales.data,
                        loading:false
                    });
                })).catch((error)=>{
                    this.setState({loading:false});
                    message.warn("获取数据出错");
                    console.log(error);
                });
            }
        });

    }

    render() {
        const cols = {
            sales: {
                tickInterval: 20
            }
        };

        const {
            getFieldDecorator, getFieldsError, getFieldError, isFieldTouched,
        } = this.props.form;
        const { RangePicker } = DatePicker;

        const bookSallesColumns = [
            {
              title: '书名',
              dataIndex: 'bookName',
              key: 'bookName',
            },
            {
              title: '销量(本)',
              dataIndex: 'sales',
              key: 'sales',
            },
            {
              title: '操作',
              render: (item) => (<Link to={'/detail/' + item.key} >详细信息</Link>)
            },
          ];

        const userPayColumns = [
            {
              title: '用户名',
              dataIndex: 'username',
              key: 'username',
            },
            {
              title: '总金额(rmb)',
              dataIndex: 'pay',
              key: 'pay',
            }
          ];

        return (
            <div>
                <Form layout="inline" onSubmit={this.handleSubmit}>
                <Form.Item label="时间范围">
                    {
                        getFieldDecorator("dateRange")
                            (<RangePicker format="YYYY-MM-DD" />)
                    }

                </Form.Item>

                <Form.Item>
                    <Button type="primary" htmlType="submit" >统计数据</Button>
                </Form.Item>
                </Form>
                <Tabs defaultActiveKey="1">
                    <TabPane tab="图书销量统计" key="1">
                        <div>
                            <Chart height={600} data={this.state.bookSales} scale={cols} forceFit>
                                <Axis name="bookName" />
                                <Axis name="sales" />
                                <Tooltip
                                    crosshairs={{
                                        type: "y"
                                    }}
                                />
                                <Geom type="interval" position="bookName*sales" />
                            </Chart>
                            <Table dataSource={this.state.bookSales} columns={bookSallesColumns} />
                        </div>
                    </TabPane>
                    <TabPane tab="用户消费统计" key="2">
                        <div>
                            <Chart height={600} data={this.state.userPays} scale={cols} forceFit>
                                <Axis name="username" />
                                <Axis name="pay" />
                                <Tooltip
                                    crosshairs={{
                                        type: "y"
                                    }}
                                />
                                <Geom type="interval" position="username*pay" />
                            </Chart>
                            <Table dataSource={this.state.userPays} columns={userPayColumns} />
                        </div>
                    </TabPane>
                </Tabs>
            </div>
        )
    }
}

export default Form.create()(Statistic);
