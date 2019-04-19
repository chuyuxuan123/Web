// src/App.js
import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Redirect, Switch } from 'react-router-dom';
import {
  Layout,
} from 'antd';

import Navbar from './Navbar';
import Signin from './Signin';
import Register from './Register';
import Booklist from './Booklist';
import AccountManage from './AccountManage';
import BookDetail from './BookDetail';
import Cart from './Cart';
import Orders from './Orders';

const {
  Header, Content, Footer
} = Layout;


class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username:'',
      login: false,
      isAdmin: false,
    };
  }

  handleLogin = (flag) => {
    if (flag == 1) {             //flag=1 user
      this.setState({
        login: true,
        isAdmin: false
      });
    } else if (flag == 0) {      //flag=0 admin
      this.setState({
        login: true,
        isAdmin: true,
      })} else if (flag == -1) {
        
      };
  }
  setUsername=(username)=>{
      this.setState({username:username});
  }

handleLogout = () => {
  this.setState({
    username:'',
    login: false,
    isAdmin: false,
  })
}


render() {
  return (
    <Router>
      <Layout style={{ minHeight: '100vh' }}>
        <Navbar login={this.state.login} isAdmin={this.state.isAdmin} handleLogout={this.handleLogout} />
        <Layout>
          <Header style={{ background: '#fff', padding: 0 }} />
          <Content style={{ margin: '0 16px' }}>
            {/* put test component here */}
            {!this.state.login && (
              <div>
                <Switch>
                  <Route exact path="/" render={props => <Redirect to="/login" />} />
                  <Route exact path="/login" render={props => <Signin handleLogin={this.handleLogin} setUsername={this.setUsername} />} />
                  <Route exact path="/register" render={props => <Register />} />
                  <Route render={props => <Redirect to="/login" />} />
                </Switch>
              </div>
            )}
            {this.state.login && !this.state.isAdmin && (
              <div>
                <Switch>
                  <Route exact path="/" render={props => <Redirect to="/booklist" />} />
                  <Route exact path="/booklist" render={props => <Booklist isAdmin={this.state.isAdmin} />} />
                  <Route exact path="/cart" render={props => <Cart />} />
                  <Route exact path="/orders" render={props => <Orders isAdmin={this.state.isAdmin} />} />
                  <Route path="/detail/:isbn" render={props => <BookDetail {...props} username={this.state.username} />} />
                  <Route exact path="/settings" />
                  <Route render={props => <Redirect to="/booklist" />} />
                </Switch>
              </div>
            )}
            {this.state.login && this.state.isAdmin && (
              <div>
                <Switch>
                  <Route exact path="/" render={props => <Redirect to="/booklist" />} />
                  <Route exact path="/booklist" render={props => <Booklist isAdmin={this.state.isAdmin} />} />
                  <Route exact path="/account" render={props => <AccountManage />} />
                  <Route exact path="/orders" render={props => <Orders isAdmin={this.state.isAdmin} />} />
                  <Route exact path="/settings" />
                  <Route render={props => <Redirect to="/booklist" />} />
                </Switch>
              </div>
            )}


          </Content>
          <Footer style={{ textAlign: 'center' }}>
            Copyright &copy; 2019 SJTU.cyx. All rights reserved.
          </Footer>
        </Layout>
      </Layout>
    </Router>
  );
}
}

export default App;
