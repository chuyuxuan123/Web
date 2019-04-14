import React, { Component } from 'react';

import {
    Form, Icon, Input, Button,DatePicker
  } from 'antd';

class StatisticForm extends Component {

    handleSubmit = () => {
        
    }

  render() {
    const {
        getFieldDecorator, getFieldsError, getFieldError, isFieldTouched,
      } = this.props.form;
    const { RangePicker } = DatePicker;
    return (
        <Form layout="inline" onSubmit={this.handleSubmit}>
        <Form.Item>
            <RangePicker />
        </Form.Item>
        {
        this.props.isAdmin && 
        <Form.Item>
            <Input prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="筛选用户名" />
        </Form.Item>
        }
        <Form.Item>
            <Input prefix={<Icon type="book" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="筛选书名" />
        </Form.Item>
        <Form.Item>
            <Button type="primary" >搜索订单</Button>
        </Form.Item>
        </Form>
    )
  }
}

export default Form.create()(StatisticForm);
