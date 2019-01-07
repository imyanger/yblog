import axios from 'axios';
import { Message } from 'element-ui';

axios.defaults.timeout = 30000;
axios.defaults.baseURL = '/api/core';

//http request 拦截器
axios.interceptors.request.use(
    config => {
        const token = localStorage.getItem('$token');
        //config.data = JSON.stringify(config.data);
        config.headers = {
            /* 'Content-Type': 'application/x-www-form-urlencoded' */
            'Content-Type': 'application/json'
        }
        if(token){
            if(config.params){
                config.params.token = token;
            }else {
                config.params = {'token': token};
            }
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
        if (response.data.status === '2') {
            router.push({
                path: "/login",
                querry: {
                    redirect: router.currentRoute.fullPath
                } //从哪个页面跳转
            })
        }else if(response.data.token){ //有token则更新token
            localStorage.setItem('$token', response.data.token);
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