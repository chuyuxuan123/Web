import React, { Component } from 'react';

import { Table } from 'antd';

import StatisticForm from './StatisticForm';
import Axios from 'axios';



let counter = 0;
function makeData(orderNum, time, username, bookname, booknum, ISBN) {
  counter += 1;
  return { key: counter, orderNum, time, username, bookname, booknum, ISBN };
}

const data = [
  makeData("5170309101", "2017-03-09", "user1", "ICS:app1", "2", "1548484-54-x"),
  makeData("5170309102", "2017-03-12", "user1", "ICS:app2", "1", "1548484-54-3"),
  makeData("5170309103", "2017-04-14", "user2", "ICS:app3", "5", "1548484-54-45"),
  makeData("5170309104", "2017-04-15", "user1", "ICS:app4", "1", "1548484-54-12"),
  makeData("5170309105", "2017-05-20", "user3", "ICS:app5", "3", "1548484-54-8"),
  makeData("5170309106", "2017-05-21", "user2", "ICS:app6", "2", "1548484-54-0"),
]

export default class Orders extends Component {
  constructor(props) {
    super(props);
    this.state = {
      searchText: '',
      dataSource: [],
    };
  }

  componentDidMount() {
    this.fetch();
  }

  fetch = () => {
    Axios.get("http://localhost:8080/orders/unknown/all", {
      withCredentials: true
    }).then((response) => {
      console.log(response.data);
      var gotdata = response.data;
      var ret = new Array();
      for (let index = 0; index < gotdata.length; index++) {
        const element = gotdata[index];
        for (let item_i = 0; item_i < element.orderItems.length; item_i++) {
          const item = element.orderItems[item_i];
          ret.push(makeData(element.orderId, element.createTime, element.user.username, item.book.bookname, item.amount, item.book.isbn));
        }
      }
      console.log(ret);
      this.setState({ dataSource: ret });
    }).catch(function (error) {
      console.log(error);
    });
  }

  render() {
    const columnsAdmin = [{
      title: "订单号",
      dataIndex: "orderNum",
      key: "orderNum",

    }, {
      title: "时间",
      dataIndex: "time",
      key: "time",
    }, {
      title: "用户名",
      dataIndex: "username",
      key: "username",
    }, {
      title: "书名",
      dataIndex: "bookname",
      key: "bookname",
    }, {
      title: "书本数",
      dataIndex: "booknum",
      key: "booknum",
    }, {
      title: "ISBN",
      dataIndex: "ISBN",
      key: "ISBN",
    },];

    const columnsUser = [{
      title: "订单号",
      dataIndex: "orderNum",
      key: "orderNum",

    }, {
      title: "时间",
      dataIndex: "time",
      key: "time",
    }, {
      title: "书名",
      dataIndex: "bookname",
      key: "bookname",
    }, {
      title: "书本数",
      dataIndex: "booknum",
      key: "booknum",
    }, {
      title: "ISBN",
      dataIndex: "ISBN",
      key: "ISBN",
    },];


    return (
      <div>
        <StatisticForm isAdmin={this.props.isAdmin} />
        {this.props.isAdmin ?
          <Table columns={columnsAdmin} dataSource={this.state.dataSource} /> :
          <Table columns={columnsUser} dataSource={this.state.dataSource} />}
      </div>
    )
  }
}
