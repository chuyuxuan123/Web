import React, { Component } from 'react';

import { Router, Route, Link } from 'react-router-dom';

import {
    Form, Icon, Input, Button, Checkbox,
} from 'antd';

import '../assets/css/signin.css';

class Signin extends Component {
    //temporal signin handler
    //TODOS: back end
    handleSubmit = (e) => {
        e.preventDefault();
        console.log(e);
        this.props.form.validateFields((err, values) => {
            if (!err) {
                console.log('Received values of form: ', values);
            }
        });
        var value = this.props.form.getFieldsValue();
        if (value.userName == "admin") {
            this.props.handleLogin(0);
        } else if (value.userName == "banned") {
            this.props.handleLogin(-1);
        } else {
            this.props.handleLogin(1);
        }
        
    }

    render() {
        const { getFieldDecorator } = this.props.form;
        return (
            <div>
                <h1 id="title">请登录</h1>
                <Form onSubmit={this.handleSubmit} className="login-form">
                    <Form.Item>
                        {getFieldDecorator('userName', {
                            rules: [{ required: true, message: '请输入用户名!' }],
                        })(
                            <Input prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="用户名" />
                        )}
                    </Form.Item>
                    <Form.Item>
                        {getFieldDecorator('password', {
                            rules: [{ required: true, message: '请输入密码!' }],
                        })(
                            <Input prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />} type="password" placeholder="密码" />
                        )}
                    </Form.Item>
                    <Form.Item>
                        {getFieldDecorator('remember', {
                            valuePropName: 'checked',
                            initialValue: true,
                        })(
                            <Checkbox>记住账号密码</Checkbox>
                        )}
                        <a className="login-form-forgot" >忘记密码</a>
                        <Button type="primary" htmlType="submit" className="login-form-button">
                            登&nbsp;录
              </Button>
                        没有账号？ <Link to="/register">注册新账号</Link>
                    </Form.Item>
                </Form>
            </div>
        );
    }
}

export default Form.create()(Signin);
