import React, { Component } from 'react';

import {
    Button, Modal, Form, Input, Upload, Icon, message
} from 'antd';
import Axios from 'axios';

const ModifyBookCreateForm = Form.create({ name: 'form_in_modal' })(
    // eslint-disable-next-line
    class extends Component {
        render() {
            const {
                visible, onCancel, onCreate, form,
            } = this.props;
            const { getFieldDecorator } = form;
            return (
                <Modal
                    visible={visible}
                    title="修改信息"
                    okText="确认"
                    cancelText="取消"
                    onCancel={onCancel}
                    onOk={onCreate}
                >
                    <Form layout="vertical">
                        <Form.Item label="书名">
                            {getFieldDecorator('bookname', {
                                rules: [{ required: true, message: "请输入书名" }],
                            })(
                                <Input />
                            )}
                        </Form.Item>
                        <Form.Item label="作者">
                            {getFieldDecorator('author', {
                                rules: [{ required: true, message: "请输入作者" }],
                            })(
                                <Input />
                            )}
                        </Form.Item>
                        <Form.Item label="ISBN">
                            {getFieldDecorator('ISBN', {
                                rules: [{ required: true, message: "请输入书的ISBN编号" }],
                            })(
                                <Input />
                            )}
                        </Form.Item>
                        <Form.Item label="库存">
                            {getFieldDecorator('inventory', {
                                rules: [{ required: true, message: "请输入库存量" }],
                            })(
                                <Input />
                            )}
                        </Form.Item>
                        <Form.Item label="单价">
                            {getFieldDecorator('price', {
                                rules: [{ required: true, message: "请输入单价" }],
                            })(
                                <Input />
                            )}
                        </Form.Item>
                        {/* <Form.Item label="封面">
              
              <Upload>
                <Button>
                  <Icon type="upload" /> Click to Upload
                </Button>
              </Upload>
              
            </Form.Item> */}
                    </Form>
                </Modal>
            );
        }
    }
);

class ModifyBookForm extends Component {
    state = {
        visible: false,
    };

    showModal = () => {
        this.setState({ visible: true });
        const form = this.formRef.props.form;
        form.setFieldsValue({
            bookname: this.props.item.bookname,
            author: this.props.item.author,
            ISBN: this.props.item.ISBN,
            inventory: this.props.item.inventory,
            price:this.props.item.price,
        })
    }

    handleCancel = () => {
        this.setState({ visible: false });
    }

    handleCreate = () => {
        const form = this.formRef.props.form;

        form.validateFields((err, values) => {
            if (err) {
                return;
            }
            values["bookId"] = this.props.item.key;
            console.log('Received values of form: ', values);

            Axios.put("http://localhost:8080/books",values).then((response)=>{
                console.log(response);
                this.props.handleModify(this.props.item, values);
                message.info("修改成功");
            })
            
            form.resetFields();
            this.setState({ visible: false });
        });

    }

    saveFormRef = (formRef) => {
        this.formRef = formRef;
    }

    render() {
        return (
            <span>
                <a onClick={this.showModal} >修改</a>
                <ModifyBookCreateForm
                    wrappedComponentRef={this.saveFormRef}
                    visible={this.state.visible}
                    onCancel={this.handleCancel}
                    onCreate={this.handleCreate}
                />
            </span>
        );
    }
}

export default ModifyBookForm;