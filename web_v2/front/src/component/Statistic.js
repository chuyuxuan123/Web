import React, { Component } from 'react';

import { Tabs } from 'antd';

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

export default class Statistic extends Component {

    constructor(props) {
        super(props);
        this.state = {
            bookSales: [],
            userPays: [],
        }
    }

    componentWillMount() {
        this.fetch();
    }

    fetch = () => {
        Axios.get("http://localhost:8080/orders/sales").then(response => {
            // console.log(response);
            this.setState({
                bookSales: response.data,
            });
        });
        Axios.get("http://localhost:8080/orders/pay").then(response => {
            // console.log(response);
            this.setState({
                userPays: response.data,
            })
        });
    }

    render() {
        const cols = {
            sales: {
                tickInterval: 20
            }
        };

        return (
            <div>
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
                        </div>
                    </TabPane>
                </Tabs>
            </div>
        )
    }
}
