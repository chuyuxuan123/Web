import React, { Component } from 'react'
import { Comment, Avatar, Form, Button, List, Input, Tooltip } from 'antd';
import moment from 'moment';

import Axios from 'axios';
const { TextArea } = Input;

const CommentList = ({ comments }) => (
    <List
        dataSource={comments}
        header={`${comments.length} ${comments.length > 1 ? 'replies' : 'reply'}`}
        itemLayout="horizontal"
        renderItem={props => <Comment {...props} />}
    />
);

const Editor = ({ onChange, onSubmit, submitting, value }) => (
    <div>
        <Form.Item>
            <TextArea rows={4} onChange={onChange} value={value} />
        </Form.Item>
        <Form.Item>
            <Button htmlType="submit" loading={submitting} onClick={onSubmit} type="primary">
                Add Comment
        </Button>
        </Form.Item>
    </div>
);

export default class BookComment extends Component {

    constructor(props) {
        super(props);
        this.state = {
            comments: [],
            submitting: false,
            value: '',
        };
        Axios.get(`http://localhost:8080/books/${this.props.bookId}/comment`)
            .then(Response => {
                console.log(Response);
                var rawData = Response.data;
                var comments = [];
                rawData.forEach(element => {
                    var comment = {};
                    comment["author"] = element.username;
                    comment["avatar"] = "http://localhost:8080/users/avatar?name=" + element.username;
                    comment["content"] = <p>{element.content}</p>;
                    comment["datetime"] = (
                        <Tooltip
                            title={moment(element.createDate)
                                .format('YYYY-MM-DD HH:mm:ss')}
                        >
                            <span>
                                {moment(element.createDate).fromNow()}
                            </span>
                        </Tooltip>
                    );
                    comments.push(comment);
                });
                this.setState({
                    comments: comments
                })
                console.log(comments)
            })

    }



    handleSubmit = () => {
        if (!this.state.value) {
            return;
        }

        this.setState({
            submitting: true,
        });
        Axios.post(`http://localhost:8080/books/${this.props.bookId}/comment`, this.state.value)
            .then(Response => {
                console.log(Response);
            });
        setTimeout(() => {
            this.setState({
                submitting: false,
                value: '',
                comments: [
                    {
                        author: this.props.username,
                        avatar: 'http://localhost:8080/users/my-avatar',
                        content: <p>{this.state.value}</p>,
                        datetime: moment().fromNow(),
                    },
                    ...this.state.comments,
                ],
            });
        }, 1000);
    };

    handleChange = e => {
        this.setState({
            value: e.target.value,
        });
    };



    render() {
        const { comments, submitting, value } = this.state;
        return (
            <div>
                {comments.length > 0 && <CommentList comments={comments} />}
                <Comment
                    avatar={
                        <Avatar
                            src="http://localhost:8080/users/my-avatar"
                            alt="Avatar"
                        />
                    }
                    content={
                        <Editor
                            onChange={this.handleChange}
                            onSubmit={this.handleSubmit}
                            submitting={submitting}
                            value={value}
                        />
                    }
                />
            </div>
        )
    }
}
