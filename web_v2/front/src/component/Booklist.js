import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import {
    Table, Input, Button, Icon, Divider, Popconfirm
} from 'antd';
import Highlighter from 'react-highlight-words';

import NewBookForm from './NewBookFrom';
import ModifyBookForm from './ModifyBookForm';

import '../assets/css/booklist.css';
import Axios from 'axios';

let GLOBALKEY = 8;

const data = [{
    key: 1,
    cover: '/img/img1.jpg',
    bookname: '三体',
    author: '刘慈欣',
    inventory: 200,
    ISBN: '0-452-28423-6',

}, {
    key: 2,
    cover: '/img/img2.jpg',
    bookname: '乡村教师',
    author: '刘慈欣',
    inventory: 120,
    ISBN: '0-06-080660-5',

}, {
    key: 3,
    cover: '/img/Tales_serial.jpg',
    bookname: "A Tale of Two Cities",
    author: "Charles Dickens",
    inventory: 34,
    ISBN: "978-0-452-01118-2",

}, {
    key: 4,
    cover: '/img/First_Single_Volume_Edition_of_The_Lord_of_the_Rings.gif',
    bookname: "The Lord of the Rings",
    author: "J. R. R. Tolkien",
    inventory: 56,
    ISBN: "978-0-517-22317-8",

}, {
    key: 5,
    cover: '/img/1984first.jpg',
    bookname: "Nineteen Eighty-Four",
    author: "George Orwell",
    inventory: 82,
    ISBN: "0-312-23841-X",

}, {
    key: 6,
    cover: '/img/Gone_with_the_Wind_cover.jpg',
    bookname: "Gone with the Wind",
    author: "Margaret Mitchell",
    inventory: 145,
    ISBN: "0-434-69517-3",

}, {
    key: 7,
    cover: '/img/Godfather-Novel-Cover.png',
    bookname: "The Godfather",
    author: "Mario Puzo",
    inventory: 13,
    ISBN: "0-472-87400-4",

}];


export default class Booklist extends Component {
    constructor(props) {
        super(props);
        this.state = {
            searchText: '',
            dataSource: [],
            loading: false
        }
    }

    componentDidMount() {
        this.fetch();
    }

    fetch = (params = {}) => {

        this.setState({ loading: true });
        Axios.get('http://localhost:8080/books/all')
            .then((response) => {
                // console.log(response);

                var d = response.data;
                var n = new Array();
                for (var i in d) {
                    var t = d[i];
                    n.push({"key":parseInt(i) + 1,"bookname":t.bookname,"author":t.author,"ISBN":t.isbn,"inventory":t.inventory,"cover":t.cover});
                }
                // console.log(n);
                // console.log(d);
                // console.log(p);
                // console.log(data);
                this.setState({loading:false,dataSource:n});
            })
            .catch(function (error) {
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

    handleCreate = (n) => {
        const dataSource = [...this.state.dataSource];
        let newItem = {
            key: GLOBALKEY,
            bookname: n.bookname,
            author: n.author,
            ISBN: n.ISBN,
            inventory: n.inventory,
            cover: null,
        };
        dataSource.push(newItem);

        this.setState({
            dataSource: dataSource,
        });
        GLOBALKEY++;
    }

    handleModify = (item, modified) => {
        // console.log(item);
        const dataSource = [...this.state.dataSource];
        let key = item.key;
        for (let i = 0; i < dataSource.length; ++i) {
            if (dataSource[i].key === key) {
                dataSource[i].bookname = modified.bookname;
                dataSource[i].author = modified.author;
                dataSource[i].ISBN = modified.ISBN;
                dataSource[i].inventory = modified.inventory;
            }
        }

        this.setState({ dataSource: dataSource });
    }

    handleRemove = (e, item) => {
        const dataSource = [...this.state.dataSource];
        let key = item.key;
        this.setState({ dataSource: dataSource.filter(item => item.key !== key) });
    }

    render() {
        const columns = [{
            title: '封面',
            dataIndex: 'cover',
            key: 'cover',
            render: (imgurl) => (
                <span>
                    <img src={"http://localhost:8080/image" + imgurl} alt="暂无封面" className="cover" style={{ maxWidth: "60px" }} />
                </span>
            ),
            width: '10%',
        }, {
            title: '书名',
            dataIndex: 'bookname',
            key: 'bookname',
            ...this.getColumnSearchProps('bookname'),
        }, {
            title: '作者',
            dataIndex: 'author',
            key: 'author',
            ...this.getColumnSearchProps('author'),
        }, {
            title: 'ISBN编号',
            dataIndex: 'ISBN',
            key: 'ISBN',
            sorter: (a, b) => a.ISBN.length - b.ISBN.length,
            ...this.getColumnSearchProps('ISBN'),
        }, {
            title: '库存量',
            dataIndex: 'inventory',
            key: 'inventory'
        }, {
            title: '操作',
            key: 'action',
            render: (key, item) => (
                <span>
                    {this.props.isAdmin ? (
                        <div>
                            <ModifyBookForm item={item} handleModify={this.handleModify} />
                            <Divider type="vertical" />
                            <Popconfirm title="确认删除?" okText="确认" cancelText="取消" onConfirm={(e) => { this.handleRemove(e, item) }}>
                                <a>删除</a>
                            </Popconfirm>
                        </div>) :
                        (<Link to={'/detail/' + item.ISBN} >详细信息</Link>)}
                </span>
            )
        }];

        return (
            <div>
                {this.props.isAdmin ? (
                    <div>
                        <NewBookForm handleCreate={this.handleCreate} />
                        <Table columns={columns} dataSource={this.state.dataSource} loading={this.state.loading} id="table" />
                    </div>
                ) : (<Table columns={columns} dataSource={this.state.dataSource} loading={this.state.loading} />)}
                {/* <Table columns={columns} dataSource={this.state.dataSource} id="table"  /> */}
            </div>
        );
    }
}
