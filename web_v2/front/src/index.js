import React from 'react';
import ReactDOM from 'react-dom';
import App from './component/App';
import AOrder from './component/AOrder';
import * as serviceWorker from './serviceWorker';
import Axios from 'axios';

Axios.defaults.withCredentials = true;
ReactDOM.render(<App />, document.getElementById('root'));
// ReactDOM.render(<AOrder isAdmin={true} />, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
