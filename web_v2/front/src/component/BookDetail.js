import React, { Component } from 'react';
import { Card, Statistic, InputNumber, Button } from 'antd';

import '../assets/css/bookdetail.css';
import Axios from 'axios';

const { Meta } = Card;

export default class BookDetail extends Component {
  constructor(props) {
    super(props);
    this.state = {
      bookname:'',
      author:'',
      price:'',
      inventory:'',
      cover:''


    }
  }

  componentDidMount(){
    // console.log(this.props.match.params.isbn);
    this.fetch(this.props.match.params.isbn);
  }

  fetch = (isbn)=>{
    Axios.get('http://localhost:8080/books/get',{
      params:{
        ISBN:this.props.match.params.isbn
      },
      headers: {
        'Content-Type': 'application/json'
      }
    })
         .then((response)=>{
          //  console.log(response);
           this.setState({
             bookname:response.data.bookname,
             author:response.data.author,
             price:response.data.price,
             inventory:response.data.inventory,
             cover:response.data.cover,
           });
         })
         .catch((error)=>{
           console.log(error);
         });

  }

  render() {
    return (
      <div id="detail">
        <Card
          hoverable
          style={{ width: 240 }}
          cover={<img src={process.env.PUBLIC_URL+this.state.cover} alt="暂无封面" />}
          id="cover"
        >
          <Meta
            title={this.state.bookname}
            description={this.state.bookname+' '+this.state.author}
          />
        </Card>
        <div id="info">
          <h2 style={{margin:"12px"}}>书名：{this.state.bookname}</h2>
          <h2 style={{margin:"10px"}}>作者：{this.state.author}</h2>
          <br/>
          <Statistic title="单价 (CNY)" value={this.state.price} precision={2} suffix="￥" style={{margin:"12px"}} />
          <Statistic title="库存" value={this.state.inventory} style={{margin:"12px"}} />
          <label>选择数量:&nbsp;</label>
          <InputNumber min={1} max={parseInt(this.state.inventory)} defaultValue={1} size="large" />
          <br/>
          <Button type="primary" size="large" style={{margin:"10px"}} >立即购买</Button>
          <Button type="default" size="large" style={{margin:"10px"}} >添加到购物车</Button>
        </div>
      </div>
    )
  }
}
