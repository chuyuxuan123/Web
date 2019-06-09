import React, { Component } from 'react'

import { Upload, Icon, Avatar, message } from 'antd';

import '../assets/css/uploadButton.css';

function getBase64(img, callback) {
    const reader = new FileReader();
    reader.addEventListener('load', () => callback(reader.result));
    reader.readAsDataURL(img);
}

function beforeUpload(file) {
    const isJPG = file.type === 'image/jpeg';
    if (!isJPG) {
        message.error('You can only upload JPG file!');
    }
    const isLt2M = file.size / 1024 / 1024 < 2;
    if (!isLt2M) {
        message.error('Image must smaller than 2MB!');
    }
    return isJPG && isLt2M;
}

export default class Settings extends Component {
    state = {
        loading: false,
    };

    handleChange = info => {
        // if (info.file.status === 'uploading') {
        //     this.setState({ loading: true });
        //     return;
        // }
        // if (info.file.status === 'done') {
        //     // Get this url from response in real world.
        //     getBase64(info.file.originFileObj, imageUrl =>
        //         this.setState({
        //             imageUrl,
        //             loading: false,
        //         }),
        //     );
        // }
        if (info.file.status !== 'uploading') {
            console.log(info.file, info.fileList);
          }
          if (info.file.status === 'done') {
            message.success(`${info.file.name} file uploaded successfully`);
          } else if (info.file.status === 'error') {
            message.error(`${info.file.name} file upload failed.`);
          }
    };

    render() {
        const uploadButton = (
            <div>
                <Icon type={this.state.loading ? 'loading' : 'plus'} />
                <div className="ant-upload-text">Upload</div>
            </div>
        );
        const imageUrl = this.state.imageUrl;
        return (
            <div>
            <Avatar shape="square" size={102.4} src="http://localhost:8080/users/my-avatar" />
            <Upload
                name="avatar"
                listType="picture-card"
                className="avatar-uploader"
                showUploadList={false}
                action="http://localhost:8080/users/avatar"
                beforeUpload={beforeUpload}
                onChange={this.handleChange}
                withCredentials={true}
            >
                {imageUrl ? <img src={imageUrl} alt="avatar" /> : uploadButton}
            </Upload>
            </div>
        );
    }
}
