import Vue from 'vue';
import App from './App';
import router from './router';
import axios from 'axios';
import ELEMENT from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';    // 默认主题
import "babel-polyfill";
//axios的封装
import { post, get, patch, put, del } from '../static/js/http';
//vuex
import store from './store';

//lib-flexible屏幕适配
// import  'lib-flexible';

//定义全局变量
Vue.prototype.$post = post;
Vue.prototype.$get = get;
Vue.prototype.$patch = patch;
Vue.prototype.$put = put;
Vue.prototype.$del = del;

Vue.use(ELEMENT, { size: 'small' });
Vue.prototype.$axios = axios;

//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
    /* const role = localStorage.getItem('ms_username');
    if(!role && to.path !== '/blog'){
        next('/blog');
    }else if(to.meta.permission){
        // 如果是管理员权限则可进入，这里只是简单的模拟管理员权限而已
        role === 'admin' ? next() : next('/403');
    }else{
        // 简单的判断IE10及以下不进入富文本编辑器，该组件不兼容
        if(navigator.userAgent.indexOf('MSIE') > -1 && to.path === '/editor'){
            Vue.prototype.$alert('vue-quill-editor组件不兼容IE10及以下浏览器，请使用更高版本的浏览器查看', '浏览器不兼容通知', {
                confirmButtonText: '确定'
            });
        }else{
            next();
        }
    } */
    next();
})

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');