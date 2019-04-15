import React, { Component } from 'react';

import {
    Table, Button, Icon, Switch,Input
} from 'antd';
import Highlighter from 'react-highlight-words';
import Axios from 'axios';

export default class AccountManage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data:[],
            searchText: '',
            loading:false,
        }
    }

    componentDidMount(){
        this.fetch();
    }

    // 返回的数据形式如下
    // {
    //     key:,
    //     user:,
    //     password:,
    //     email:,
    //     auth:,
    // }
    fetch=()=>{
        this.setState({loading:true});
        Axios.get('http://localhost:8080/users/all')
        .then((response)=>{
            // console.log(response.data);
            var d = response.data;
            var dataSource = new Array();
            for(var i in d){
                var tmp = d[i];
                dataSource.push({"key":tmp.userId,"user":tmp.username,"password":tmp.password,"email":tmp.email,"auth":tmp.enable});
            }
            this.setState({
                data:dataSource,
                loading:false,
            })
        })
        .catch(function(error){
            console.log(error);
        });
    }

    getColumnSearchProps = (dataIndex) => ({
        filterDropdown: ({
            setSelectedKeys, selectedKeys, confirm, clearFilters,
        }) => (
                <div style={{ padding: 8 }}>
                    <Input
                        ref={node => { this.searchInput = node; }}
                        placeholder={`Search ${dataIndex}`}
                        value={selectedKeys[0]}
                        onChange={e => setSelectedKeys(e.target.value ? [e.target.value] : [])}
                        onPressEnter={() => this.handleSearch(selectedKeys, confirm)}
                        style={{ width: 188, marginBottom: 8, display: 'block' }}
                    />
                    <Button
                        type="primary"
                        onClick={() => this.handleSearch(selectedKeys, confirm)}
                        icon="search"
                        size="small"
                        style={{ width: 90, marginRight: 8 }}
                    >
                        Search
            </Button>
                    <Button
                        onClick={() => this.handleReset(clearFilters)}
                        size="small"
                        style={{ width: 90 }}
                    >
                        Reset
            </Button>
                </div>
            ),
        filterIcon: filtered => <Icon type="search" style={{ color: filtered ? '#1890ff' : undefined }} />,
        onFilter: (value, record) => record[dataIndex].toString().toLowerCase().includes(value.toLowerCase()),
        onFilterDropdownVisibleChange: (visible) => {
            if (visible) {
                setTimeout(() => this.searchInput.select());
            }
        },
        render: (text) => (
            <Highlighter
                highlightStyle={{ backgroundColor: '#ffc069', padding: 0 }}
                searchWords={[this.state.searchText]}
                autoEscape
                textToHighlight={text.toString()}
            />
        ),
    })

    handleSearch = (selectedKeys, confirm) => {
        confirm();
        this.setState({ searchText: selectedKeys[0] });
    }

    handleReset = (clearFilters) => {
        clearFilters();
        this.setState({ searchText: '' });
    }

    handleAuth = (item) => {        
        console.log(item)
        var datasource = [...this.state.data];
        for(let i = 0;i<datasource.length;++i){
            if(datasource[i].key==item.key){
                datasource[i].auth = !datasource[i].auth;
            }
        }
        Axios.get("http://localhost:8080/users/auth",{params:{
            user:item.user,
            enable:item.auth
        }
    }).then(function(response){
        console.log(response);
    }).catch(function(error){
        console.log(error);
    })
        this.setState({data:datasource});
    }

    render() {
        const columns = [{
            title: 'Username',
            dataIndex: 'user',
            key: 'user',
            ...this.getColumnSearchProps('user'),
        }, {
            title: 'Password',
            dataIndex: 'password',
            key: 'password',
        }, {
            title: 'E-mail',
            dataIndex: 'email',
            key: 'email',
            ...this.getColumnSearchProps('email'),
        }, {
            title: 'Authority',
            key: 'auth',
            render: (key,item) => (<Switch checked={item.auth} onChange={()=>this.handleAuth(item)} />)
        }];

        return (
            <div>
                <Table columns={columns} dataSource={this.state.data} loading={this.state.loading} />
            </div>
        )
    }
}
