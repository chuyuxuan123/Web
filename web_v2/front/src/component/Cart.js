import React, { Component } from 'react';

import { Table, InputNumber, Statistic, Button } from 'antd';

import '../assets/css/cart.css';
import Axios from 'axios';

// fixed data
// const data = [{
//   key: '1',
//   bookname: '深入理解计算机系统',
//   price: 60,
//   amount: 1,
// }, {
//   key: '2',
//   bookname: '算法导论',
//   price: 40,
//   amount: 2,
// }, {
//   key: '3',
//   bookname: 'Web开发技术',
//   price: 50,
//   amount: 1,
// }, {
//   key: '4',
//   bookname: 'Mysql从删库到跑路',
//   price: 99,
//   amount: 1,
// }];

export default class Cart extends Component {

  constructor(props) {
    super(props);
    this.state = {
      totalPrice: 0,
      dataSource: [],
      selectedRowKeys: [],
      selectedRows: [],
    }
  }

  componentDidMount(){
    this.fetch();
  }

  fetch = ()=>{
    Axios.get("http://localhost:8080/cartItems/all")
    .then((response)=>{
      // console.log(response);
      var d = response.data;
      var n = new Array;
      for (let index = 0; index < d.length; index++) {
        const element = d[index];
        n.push({"key":index,"bookname":element.bookname,"price":element.price,"amount":element.amount})
      }
      this.setState({dataSource:n});
    })
    .catch((error)=>{
      console.log(error);
    })
  }

  handleAmount = (value, item) => {
    let oldData = [...this.state.dataSource];
    let selectedRows = this.state.selectedRows;
    let totPrice = this.state.totalPrice;
    // console.log(item);
    // console.log(value);

    for (let i = 0; i < selectedRows.length; ++i) {
      if (selectedRows[i].key == item.key) {
        let oldAmount = selectedRows[i].amount;
        totPrice -= oldAmount * selectedRows[i].price;
        // console.log(oldAmount);
        totPrice += value * selectedRows[i].price;
        // console.log(value);
        selectedRows[i].amount = value;
      }
    }
    for (let i = 0; i < oldData.length; ++i) {
      if (oldData[i].key == item.key) {
        oldData[i].amount = value;
      }
    }

    this.setState({
      dataSource: oldData,
      selectedRows: selectedRows,
      totalPrice: totPrice,
    });
  }

  render() {
    const columns = [{
      title: '书名',
      dataIndex: 'bookname',
      render: text => <a href="javascript:;">{text}</a>,
    }, {
      title: '单价',
      dataIndex: 'price',
    }, {
      title: '数量',
      dataIndex: 'amount',
      render: (key, item) => <InputNumber min={1} defaultValue={item.amount} onChange={(value) => { this.handleAmount(value, item) }} />
    }, {
      title: '操作',
      render: () => <a style={{ color: "#f81d22" }}>移出购物车</a>,
    }];




    // rowSelection object indicates the need for row selection
    const rowSelection = {
      onChange: (selectedRowKeys, selectedRows) => {
        console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
        let totalPrice = 0;
        for (let i = 0; i < selectedRows.length; ++i) {
          totalPrice += selectedRows[i].amount * selectedRows[i].price;
        }
        this.setState({
          totalPrice: totalPrice,
          selectedRowKeys: selectedRowKeys,
          selectedRows: selectedRows,
        })
      },
      getCheckboxProps: record => ({
        disabled: record.bookname === 'Disabled User', // Column configuration not to be checked
        name: record.bookname,
      }),
    };

    return (
      <div>
        <h2>我的购物车</h2>
        <Table rowSelection={rowSelection} columns={columns} dataSource={this.state.dataSource} pagination={{ hideOnSinglePage: true }} />
        <Button type="primary" size="large" id="buy">结算</Button>
        <div id="total">
          <Statistic title="合计" value={this.state.totalPrice} precision={2} suffix="￥" />
        </div>


      </div>
    )
  }
}
