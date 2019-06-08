import React, { Component } from 'react';

import {
  Button, Modal, Form, Input, Upload, Icon, message
} from 'antd';
import Axios from 'axios';

const NewBookCreateForm = Form.create({ name: 'form_in_modal' })(
  // eslint-disable-next-line
  class extends Component {
    constructor(props) {
      super(props);
      this.state = {
        loading:false,
        file: '',
      }
    }

    render() {
      const {
        visible, onCancel, onCreate, form,
      } = this.props;

      const { getFieldDecorator } = form;

      const uploadProps = {
        action: 'http://localhost:8080/image/upload',
        onChange(info) {
          if (info.file.status !== 'uploading') {
            console.log(info.file, info.fileList);
          }
          if (info.file.status === 'done') {
            message.success(`${info.file.name} file uploaded successfully`);
          } else if (info.file.status === 'error') {
            message.error(`${info.file.name} file upload failed.`);
          }
        },
        // beforeUpload: file => {
        //   this.setState(state => ({
        //     file: [...state.file, file],
        //   }));
        //   return false;
        // }
      }
      return (
        <Modal
          visible={visible}
          title="创建一个书目"
          okText="新建"
          cancelText="取消"
          loading={this.state.loading}
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
              {getFieldDecorator('isbn', {
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

            <Form.Item label="封面">
              {getFieldDecorator('file', {
                rules: [{ required: true, message: "请上传封面" }],
              })(
                <Upload {...uploadProps}>
                  <Button>
                    <Icon type="upload" /> 点击上传封面
                    </Button>
                </Upload>
              )}
            </Form.Item>
          </Form>
        </Modal>
      );
    }
  }
);

class NewBookForm extends Component {
  state = {
    visible: false,
  };

  showModal = () => {
    this.setState({ visible: true });
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
      this.setState({loading:true})
      
      values["cover"] = values.file.file.name;
      console.log('Received values of form: ', values);
      
      Axios.post("http://localhost:8080/books/add", values).then(response => {
        this.setState({loading:false})
        console.log(response);
        this.props.handleCreate(values);
        message.info("添加成功");
      });
      
      form.resetFields();
      this.setState({ visible: false });
    });
  }

  saveFormRef = (formRef) => {
    this.formRef = formRef;
  }

  render() {
    return (
      <div id="button">
        {/* <Button type="primary" onClick={this.showModal}>New Collection</Button> */}
        <Button type="primary" onClick={this.showModal} size="large" shape="circle" icon="plus" ></Button>
        <NewBookCreateForm
          wrappedComponentRef={this.saveFormRef}
          visible={this.state.visible}
          onCancel={this.handleCancel}
          onCreate={this.handleCreate}
        />
      </div>
    );
  }
}

export default NewBookForm;