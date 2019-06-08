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
// import Orders from './Orders';
import AOrder from './AOrder';
import Statistic from './Statistic';

import Axios from 'axios';
import { message } from 'antd';


const {
  Header, Content, Footer
} = Layout;


class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: '',
      login: false,
      isAdmin: false,
    };
  }

  componentDidMount(){
    Axios.get("http://localhost:8080/users/validate")
    .then((response)=>{
      if(response.data==200){
        
        this.setState({
          username: '',
          login: true,
          isAdmin: false,
        });
      }else if(response.data==202){
        this.setState({
          username: '',
          login: true,
          isAdmin: true,
        });
      }else{
        this.setState({
          username: '',
          login: false,
          isAdmin: false,
        });
      };
    })
  }
  // call setState in componentWillUpdate() will cause infinite loop
  // call componentDidUpdate is inproperty here because each time react component update this function will be invoked
  // componentDidUpdate(prevState) {
  //   Axios.get("http://localhost:8080/users/validate")
  //     .then((response) => {
  //       if (response.data == 200) {
  //         if (!prevState.login) {
  //           this.setState({ login: true });
  //         }
  //       } else if (response.data == 202) {
  //         if (!prevState.login || !prevState.isAdmin) {
  //           this.setState({ login: true, isAdmin: true });
  //         }
  //       } else if (response.data == 401) {
  //         if (prevState.login) {
  //           message.info("请先登录");
  //           this.setState({ login: false, isAdmin: false });
  //           window.location.href = "/";
  //         }
  //       }
  //     })
  //     .catch((error) => { })
  // }

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
      })
    } else if (flag == -1) {

    };
  }
  setUsername = (username) => {
    this.setState({ username: username });
  }

  handleLogout = () => {
    Axios.get("http://localhost:8080/users/logout")
      .then((response) => {

        this.setState({
          username: '',
          login: false,
          isAdmin: false,
        });


      }).catch((error) => { });

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
                    <Route exact path="/booklist" render={props => <Booklist isAdmin={this.state.isAdmin} />} />
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
                    <Route exact path="/orders" render={props => <AOrder isAdmin={this.state.isAdmin} />} />
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
                    <Route exact path="/orders" render={props => <AOrder isAdmin={this.state.isAdmin} />} />
                    <Route exact path="/statistic" render={props => <Statistic /> } />
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
