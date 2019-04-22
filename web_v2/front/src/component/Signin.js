import React, { Component } from 'react';

import { Router, Route, Link } from 'react-router-dom';

import {
    Form, Icon, Input, Button, Checkbox, message
} from 'antd';

import '../assets/css/signin.css';
import Axios from 'axios';

class Signin extends Component {

    constructor(props){
        super(props);
        this.state = {
            loading:false,
        }
    }

    //temporal signin handler
    //TODO: handle signin
    handleSubmit = (e) => {
        e.preventDefault();
        // console.log(e);
        this.setState({loading:true});
        this.props.form.validateFields((err, values) => {
            if (!err) {
                // console.log('Received values of form: ', values);
                Axios.get("http://localhost:8080/users/sign",{
                    params:{
                        username:values.userName,
                        password:values.password,
                    }
                }).then((response)=>{
                    // console.log(response);
                    this.setState({loading:false});
                    if(response.data==="ADMIN"){
                        this.props.handleLogin(0);
                        this.props.setUsername(values.userName);
                        message.info("登录成功");
                    }else if(response.data==="USER"){
                        this.props.handleLogin(1);
                        this.props.setUsername(values.userName);
                        message.info("登录成功");
                    }
                    else if(response.data==="BAN"){
                        this.props.handleLogin(-1);
                        message.warning("你已被管理员封禁");
                    }
                    else if(response.data==="WRONG"){
                        message.error("用户名或密码错误");
                    }
                    else{
                        message.error("登录出错");
                    }
                }).catch((error)=>{
                    this.setState({loading:false});
                    message.error("登录出错，请稍后重试");
                });
            }
        });
        
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
                        <Button type="primary" loading={this.state.loading} htmlType="submit" className="login-form-button">
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
