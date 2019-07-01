import React, { Component } from 'react';

import { Table, InputNumber, Statistic, Button, Popconfirm, Modal } from 'antd';

import '../assets/css/cart.css';
import Axios from 'axios';
import { message } from 'antd';

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
    Axios.get("http://localhost:8080/users/validate").then((response) => {
      if (response.data == 401) {
        message.warn("登录过期，请重新登录");
        window.location.href = "/";
        return;
      }
    }).catch((error) => {
      message.error("网络出错!");
      window.location.href = "/";
    });
    this.state = {
      totalPrice: 0,
      dataSource: [],
      buttonDisabled: true,
      selectedRowKeys: [],
      selectedRows: [],
    }
  }

  componentDidMount() {
    this.fetch();
  }

  fetch = () => {
    Axios.get("http://localhost:8080/cartItems/all")
      .then((response) => {
        // console.log(response);
        var d = response.data;
        var n = new Array();
        for (let index = 0; index < d.length; index++) {
          const element = d[index];
          n.push({ "key": element.bookId, "bookname": element.bookname, "price": element.price, "amount": element.amount })
        }
        this.setState({ dataSource: n });
      })
      .catch((error) => {
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

  handleRemove = (e, item) => {
    Axios.get("http://localhost:8080/cartItems/remove", {
      params: {
        bookId: item.key,
      }
    }).then((response) => {
      if (response.data == "200") {
        // very terrible; needn't call fetch to get data from backend
        this.fetch();
      }
    }).catch((error) => {

    });
  }

  handlePurchase = () => {
    var rawList = this.state.selectedRows;

    var data = new Array();
    for (let index = 0; index < rawList.length; index++) {
      const element = rawList[index];
      data.push({ "bookId": element.key, "amount": element.amount });
    }

    Axios.post("http://localhost:8080/books/inventory", data, {
      headers: { "Content-Type": "application/json" }
    }).then((response) => {
      console.log("validate inventory");
      console.log(response.data);
      if (response.data.length !== 0) {
        var warningMessage = "";
        (response.data).forEach(element => {
          var tmp = "您选择的图书: " + element.bookName + ", 目前仅剩 " + element.inventory + " 本\n";
          warningMessage += tmp;
        }); 
        Modal.warning({
          title: '库存不足',
          content: warningMessage,
        });
      } else {
        Axios.post("http://localhost:8080/orders/cart/buy", data, {
          'Content-Type': 'application/json',
        }).then((response) => {
          if (response.data == 200) {
            message.info("购买成功");
            let oldData = [...this.state.dataSource];
            // console.log(oldData);
            // console.log(data);
            for (let index = 0; index < oldData.length; index++) {
              for (let j = 0; j < data.length; j++) {
                if (oldData[index].key == data[j].bookId) {
                  oldData.splice(index, 1);
                }
              }
            }
            this.setState({ dataSource: oldData, totalPrice: 0 });

          }
          console.log(response);
        }).catch((error) => {
          if (error.response) {
            // The request was made and the server responded with a status code
            // that falls out of the range of 2xx
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
          } else if (error.request) {
            // The request was made but no response was received
            // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
            // http.ClientRequest in node.js
            console.log(error.request);
          } else {
            // Something happened in setting up the request that triggered an Error
            console.log('Error', error.message);
          }
          console.log(error.config);
        })
      }
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
      render: (item) => (
        <Popconfirm title="确认删除?" okText="确认" cancelText="取消" onConfirm={(e) => { this.handleRemove(e, item) }}>
          <a>移出购物车</a>
        </Popconfirm>),
    }];




    // rowSelection object indicates the need for row selection
    const rowSelection = {
      onChange: (selectedRowKeys, selectedRows) => {
        // console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
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
        {/* <Table columns={columns} dataSource={this.state.dataSource} pagination={{ hideOnSinglePage: true }} />
        <Button type="primary" size="large" id="buy" onClick={this.handlePurchase} disabled={this.state.dataSource<=0} >结算</Button>         */}

        <Table rowSelection={rowSelection} columns={columns} dataSource={this.state.dataSource} pagination={{ hideOnSinglePage: true }} />
        <Button type="primary" size="large" id="buy" onClick={this.handlePurchase} disabled={this.state.selectedRows.length <= 0} >结算</Button>
        <div id="total">
          <Statistic title="合计" value={this.state.totalPrice} precision={2} suffix="￥" />
        </div>


      </div>
    )
  }
}
