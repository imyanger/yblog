import axios from 'axios';
import { MessageBox } from 'element-ui';
import router from '@/router'

axios.defaults.timeout = 30000;
axios.defaults.baseURL = '/api/core';

//http request 拦截器
axios.interceptors.request.use(
    config => {
        // config.headers = {
        //     /* 'Content-Type': 'application/x-www-form-urlencoded' */
        //     'Content-Type': 'application/json'
        // }
        config.headers['Content-Type'] = 'application/json';
        let token;
        if(config.url.indexOf("/blog/") > -1){
            token = localStorage.getItem('$token');
        } else {
            token = localStorage.getItem('$back-token');
        }
        if(token){
            config.headers['token'] = token;
        }
        return config;
    },
    error => {
        return Promise.reject(err);
    }
);

//http response 拦截器
axios.interceptors.response.use(
    response => {
        //token失效的判断
        if (response.data.status === 2) {
            MessageBox.alert(response.data.msg, '提示', {
                confirmButtonText: '确定',
                callback: action => {
                    router.push({
                        path: "/back/login",
                        querry: {
                            // 从哪个页面跳转
                            redirect: router.currentRoute.fullPath
                        } 
                    })
                }
            });
        }
        // 后台请求失败
        else if(response.data.status === 1){
            MessageBox.alert(response.data.msg, '提示', {
                confirmButtonText: '确定',
                callback: action => { }
            });
            return Promise.reject(response.data.msg);
        }
        //有token则更新token
        else if(response.data.token) {
            if(response.config.url.indexOf("/blog/") > -1) {
                localStorage.setItem('$token', response.data.token);
            } else {
                localStorage.setItem('$back-token', response.data.token);
            }
        }
        console.log(response.data);
        return response;
    },
    error => {
        return Promise.reject(error)
    }
)

/**
 * 封装get方法
 * @param url
 * @param data
 * @returns {Promise}
 */
export function get(url, params = {}) {
    return new Promise((resolve, reject) => {
        axios.get(url, {
                params: params
            })
            .then(response => {
                resolve(response.data);
            })
            .catch(err => {
                reject(err)
            })
    })
}

/**
 * 封装post请求
 * @param url
 * @param data
 * @returns {Promise}
 */
export function post(url, data = {}) {
    return new Promise((resolve, reject) => {
        axios.post(url, data)
            .then(response => {
                resolve(response.data);
            }, err => {
                reject(err)
            })
    })
}

/**
 * 封装patch请求
 * @param url
 * @param data
 * @returns {Promise}
 */
export function patch(url, data = {}) {
    return new Promise((resolve, reject) => {
        axios.patch(url, data)
            .then(response => {
                resolve(response.data);
            }, err => {
                reject(err)
            })
    })
}

/**
 * 封装put请求
 * @param url
 * @param data
 * @returns {Promise}
 */
export function put(url, data = {}) {
    return new Promise((resolve, reject) => {
        axios.put(url, data)
            .then(response => {
                resolve(response.data);
            }, err => {
                reject(err)
            })
    })
}

/**
 * 封装delete请求
 * @param url
 * @param data
 * @returns {Promise}
 */
export function del(url, data = {}) {
    return new Promise((resolve, reject) => {
        axios.delete(url, data)
            .then(response => {
                resolve(response.data);
            }, err => {
                reject(err)
            })
    })
}