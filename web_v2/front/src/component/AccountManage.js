import React, { Component } from 'react';

import {
    Table, Button, Icon, Switch,Input
} from 'antd';
import Highlighter from 'react-highlight-words';

export default class AccountManage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            searchText: '',
        }
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
            render: () => (<Switch defaultChecked />)
        }];

        const data = [{
            key: 1,
            user: 'admin',
            password: '1234',
            email: 'admin@qq.com'
        }, {
            key: 2,
            user: 'Alice',
            password: '2345',
            email: 'alice@gmail.com'
        }, {
            key: 3,
            user: 'Bob',
            password: '3456',
            email: 'bob@126.com'
        }]
        return (
            <div>
                <Table columns={columns} dataSource={data} />
            </div>
        )
    }
}
