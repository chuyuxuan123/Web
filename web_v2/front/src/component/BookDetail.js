import React, { Component } from 'react';
import { Card, Statistic, InputNumber, Button, message, Modal } from 'antd';

import './BookComment';
import '../assets/css/bookdetail.css';
import Axios from 'axios';
import BookComment from './BookComment';

const { Meta } = Card;

export default class BookDetail extends Component {
  constructor(props) {
    super(props);
    console.log(props);
    this.state = {
      bookId: '',
      bookname: '',
      author: '',
      price: '',
      inventory: '',
      cover: '',
      amount: 1,
    }
  }

  componentWillMount() {
    // console.log(this.props.match.params.isbn);
    this.fetch(this.props.match.params.bookId);
  }

  fetch = (isbn) => {
    Axios.get("http://localhost:8080/users/validate")
      .then((response) => {
        if (response.data == 401) {
          message.warn("登录过期，请重新登录");
          window.location.href = "/";
          return;
        }
        Axios.get('http://localhost:8080/books', {
          params: {
            bookId: this.props.match.params.bookId
          },
          headers: {
            'Content-Type': 'application/json'
          }
        })
          .then((response) => {
            //  console.log(response);
            this.setState({
              bookId: response.data.bookId,
              bookname: response.data.bookname,
              author: response.data.author,
              price: response.data.price,
              inventory: response.data.inventory,
              cover: response.data.cover,
            });
          })
          .catch((error) => {
            console.log("get book detail infomation error");
            console.log(error);
          });
      })
      .catch((error) => {
        console.log("bookdetail构造函数中获取用户权限出错");
        console.log(error);
      });

  }

  handlePurchase = () => {
    var data = [{
      "bookId": this.state.bookId,
      "amount": this.state.amount
    }];

    Axios.post("http://localhost:8080/books/inventory", data, {
      headers: { "Content-Type": "application/json" }
    }).then((response) => {
      console.log("validate inventory");
      console.log(response.data);
      if (response.data.length !== 0) {
        Modal.warning({
          title: '库存不足',
          content: '您选择的图书' + response.data[0].bookName + ", 目前仅剩" + response.data[0].inventory + "本",
        });
      } else {
        Axios.post("http://localhost:8080/orders/buy", data, {
          headers: { "Content-Type": "application/json" }
        }).then((response) => {
          if (response.data == 200) {
            message.info("购买成功");
            window.history.go(-1);
          }
        });
      }
    });
  }

  handleAddToCart = () => {
    var data = {
      "bookId": this.state.bookId,
      "amount": this.state.amount,
      "price": this.state.price,
      "bookname": this.state.bookname
    };

    Axios.post("http://localhost:8080/cartItems/add/", data, {
      headers: { "Content-Type": "application/json" }
    }).then((response) => {
      if (response.data == 200) {
        message.info("添加成功");
        window.history.go(-1);
      }
    })
  }

  handleAmount = (e) => {
    console.log(e);
    this.setState({ amount: e });
  }

  render() {
    return (
      <div>
        <div id="detail">
          <Card
            hoverable
            style={{ width: 240 }}
            cover={<img src={"http://localhost:8080/image" + this.state.cover} alt="暂无封面" />}
            id="cover"
          >
            <Meta
              title={this.state.bookname}
              description={this.state.bookname + ' ' + this.state.author}
            />
          </Card>
          <div id="info">
            <h2 style={{ margin: "12px" }}>书名：{this.state.bookname}</h2>
            <h2 style={{ margin: "10px" }}>作者：{this.state.author}</h2>
            <br />
            <Statistic title="单价 (CNY)" value={this.state.price} precision={2} suffix="￥" style={{ margin: "12px" }} />
            <Statistic title="库存" value={this.state.inventory} style={{ margin: "12px" }} />
            <label>选择数量:&nbsp;</label>
            <InputNumber min={1} max={parseInt(this.state.inventory)} value={this.state.amount} size="large" onChange={this.handleAmount} />
            {this.state.inventory == 0 && <p style={{ 'color': 'red' }} >目前库存不足</p>}
            <br />

            <Button type="primary" size="large" style={{ margin: "10px" }} onClick={this.handlePurchase} disabled={this.state.inventory == 0} >立即购买</Button>
            <Button type="default" size="large" style={{ margin: "10px" }} onClick={this.handleAddToCart} disabled={this.state.inventory == 0} >添加到购物车</Button>
          </div>

        </div>
        <div style={{ float: "none", width: "70%", paddingLeft: "100px" }} >
          <BookComment bookId={this.props.match.params.bookId} username={this.props.username} />
        </div>
      </div>
    )
  }
}
