import React, { Component } from 'react'
import { Table, Collapse, message, Button } from 'antd';

import StatisticForm from './StatisticForm';
import Axios from 'axios';


export default class AOrder extends Component {

    constructor(props) {
        super(props);
        Axios.get("http://localhost:8080/users/validate").then((response)=>{
          if(response.data==401){
            message.warn("登录过期，请重新登录");
            window.location.href="/";
            return;
          }
        }).catch((error)=>{
          message.error("网络出错!");
          window.location.href="/";
        });
        this.state = {
          searchText: '',
          dataSource: [],
        };
      }

      componentDidMount(){
          Axios.get("http://localhost:8080/orders/all")
            .then((Response)=>{
                this.setState({
                    dataSource:Response.data,
                });
            })
            .catch((error)=>{
                message.error("网络出错");
                window.location.href="/";
            })
      }

      setDataSource=(newData)=>{
        this.setState({
          dataSource:newData,
        });
      }

    render() {

    const columnsAdmin = [{
        title: "订单号",
        dataIndex: "orderId",
        key: "orderId",
        width: "8%"
      }, {
        title: "时间",
        dataIndex: "createTime",
        key: "createTime",
        width: "10%"
      }, {
        title: "用户名",
        dataIndex: "username",
        key: "username",
        width: "10%"
      }, {
        title: "总价格￥",
        dataIndex: "totalPrice",
        key: "totalPrice",
        width: "10%"
      }, {
        title: "详情",
        dataIndex: "detail",
        key: "detail",
        render: detail => (
            <div>
                <Collapse>
                <Collapse.Panel header="订单详情">
                    {detail.map((detail)=>{
                        return <div>书名：{detail.bookname}  数量：{detail.amount} 单价：{detail.price} ISBN：{detail.isbn} </div>;
                    })}                 
                </Collapse.Panel>
            </Collapse>
            
            </div>
        ),
      },];
  
      const columnsUser = [{
        title: "订单号",
        dataIndex: "orderId",
        key: "orderId",
        width: "8%"
  
      }, {
        title: "时间",
        dataIndex: "createTime",
        key: "createTime",
        width: "10%"
      },{
        title: "总价格￥",
        dataIndex: "totalPrice",
        key: "totalPrice",
        width: "10%"
      }, {
        title: "详情",
        dataIndex: "detail",
        key: "detail",
        render: (detail) => (            
            <Collapse>
                <Collapse.Panel header="订单详情">
                {detail.map((detail)=>{
                        return <div>书名：{detail.bookname}  数量：{detail.amount} 单价：{detail.price} ISBN：{detail.isbn} </div>;
                    })}   
                </Collapse.Panel>
            </Collapse>
        ),
      }];


    return (
        <div>
        <StatisticForm isAdmin={this.props.isAdmin} setDataSource={this.setDataSource} />
        {this.props.isAdmin ?
          <Table columns={columnsAdmin} dataSource={this.state.dataSource} /> :
          <Table columns={columnsUser} dataSource={this.state.dataSource} />}
      </div>
      
    )
  }
}
