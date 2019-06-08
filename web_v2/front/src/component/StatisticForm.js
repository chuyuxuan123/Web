import React, { Component } from 'react';

import {
    Form, Icon, Input, Button, DatePicker
} from 'antd';
import Axios from 'axios';

let counter = 0;
function makeData(orderNum, time, username, bookname, booknum, ISBN) {
    counter += 1;
    return { key: counter, orderNum, time, username, bookname, booknum, ISBN };
}

class StatisticForm extends Component {

    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                // console.log('Received values of form: ', values);
                // console.log(values['dateRange'][0].format('YYYY-MM-DD'));
                // console.log(values['dateRange'][1].format('YYYY-MM-DD'));
                Axios.get("http://localhost:8080/orders/search", {
                    params: {
                        start: values['dateRange'][0].format('YYYY-MM-DD'),
                        end: values['dateRange'][1].format('YYYY-MM-DD'),
                        username: (values['username']?values['username']:"user"),
                    }
                }).then((response) => {
                    // console.log(response.data);
                    // var gotdata = response.data;
                    // var ret = new Array();
                    // for (let index = 0; index < gotdata.length; index++) {
                    //     const element = gotdata[index];
                    //     ret.push(makeData(element.orderId, element.createTime, element.username, element.bookname, element.amount, element.isbn));
                    // }
                    this.props.setDataSource(response.data);
                }).catch((error) => {
                    console.log(error);
                })
            }
        });
    }

    render() {
        const {
            getFieldDecorator, getFieldsError, getFieldError, isFieldTouched,
        } = this.props.form;
        const { RangePicker } = DatePicker;
        return (
            <Form layout="inline" onSubmit={this.handleSubmit}>
                <Form.Item>
                    {
                        getFieldDecorator("dateRange")
                            (<RangePicker format="YYYY-MM-DD" />)
                    }

                </Form.Item>
                {
                    this.props.isAdmin &&
                    <Form.Item>
                        {
                            getFieldDecorator("username")
                                (<Input prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="筛选用户名" />)
                        }
                    </Form.Item>
                }

                <Form.Item>
                    <Button type="primary" htmlType="submit" >搜索订单</Button>
                </Form.Item>
            </Form>
        )
    }
}

export default Form.create()(StatisticForm);
