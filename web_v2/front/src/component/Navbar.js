import React, { Component } from 'react'
import { BrowserRouter as Router, Route, Link, Redirect } from "react-router-dom";
import { Menu, Layout, Icon } from 'antd';

import '../assets/css/navbar.css';

const {
    Sider,
} = Layout;
const SubMenu = Menu.SubMenu;

export default class Navbar extends Component {

    constructor(props) {
        super(props);

        this.state = {
            login: true,
            isAdmin: false,
            collapsed: false
        }
    }

    onCollapse = (collapsed) => {
        this.setState({ collapsed });
    }

    handleMenuSelect = (e) => {
        // console.log(e);
    }

    render() {
        return (<Sider
            collapsible
            collapsed={this.state.collapsed}
            onCollapse={this.onCollapse}
        >
            <div className="logo" />
            {this.props.login ? (this.props.isAdmin ?
                (<Menu theme="dark" defaultSelectedKeys={['1']} mode="inline" onClick={this.handleMenuSelect}>
                    <Menu.Item key="1">
                        <Link to="/booklist">
                            <Icon type="read" />
                            <span>图书管理</span>
                        </Link>
                    </Menu.Item>
                    <Menu.Item key="2">
                        <Link to="/orders">
                            <Icon type="bars" />
                            <span>订单管理</span>
                        </Link>
                    </Menu.Item>
                    <Menu.Item key="3">
                        <Link to="/account">
                            <Icon type="team" />
                            <span>账号管理</span>
                        </Link>
                    </Menu.Item>
                    <Menu.Item key="4">
                        <Link to="/settings">
                            <Icon type="setting" />
                            <span>设置</span>
                        </Link>
                    </Menu.Item>
                    <Menu.Item key="5" onClick={this.props.handleLogout} >
                        <Icon type="logout" />
                        <span>注销</span>
                    </Menu.Item>
                </Menu>) :
                (<Menu theme="dark" defaultSelectedKeys={['1']} mode="inline" onClick={this.handleMenuSelect}>
                    <Menu.Item key="1">
                        <Link to="/booklist">
                            <Icon type="read" />
                            <span>图书列表</span>
                        </Link>
                    </Menu.Item>
                    <Menu.Item key="2">
                        <Link to="/cart">
                            <Icon type="shopping-cart" />
                            <span>购物车</span>
                        </Link>
                    </Menu.Item>
                    <Menu.Item key="3">
                        <Link to="/orders">
                            <Icon type="bars" />
                            <span>我的订单</span>
                        </Link>
                    </Menu.Item>
                    <Menu.Item key="4">
                        <Link to="/settings">
                            <Icon type="setting" />
                            <span>设置</span>
                        </Link>
                    </Menu.Item>
                    <Menu.Item key="5" onClick={this.props.handleLogout} >

                        <Icon type="logout" />
                        <span>注销</span>

                    </Menu.Item>
                </Menu>)) :
                (<Menu theme="dark" defaultSelectedKeys={['1']} mode="inline">
                    <Menu.Item key="1">
                        <Link to="/booklist">
                            <Icon type="read" />
                            <span>图书列表</span>
                        </Link>
                    </Menu.Item>
                    <Menu.Item key="2">
                        <Link to="/">
                            <Icon type="login" />
                            <span>登录</span>
                        </Link>
                    </Menu.Item>
                </Menu>)}

        </Sider>);
    }
}
