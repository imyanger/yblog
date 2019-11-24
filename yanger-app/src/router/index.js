import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

const router = new Router({
    routes: [
        {
            path: '/',
            redirect: '/blog/home'
        },
        //blog前台展示部分
        {
            path: '/blog',
            component: resolve => require(['@/components/blog/Layout.vue'], resolve),
            children: [
                {
                    path: 'home',
                    component: resolve => require(['@/pages/blog/Home.vue'], resolve)
                },
                {
                    path: 'study',
                    component: resolve => require(['@/pages/blog/Study.vue'], resolve)
                },
                {
                    path: 'essay',
                    component: resolve => require(['@/pages/blog/Essay.vue'], resolve)
                },
                {
                    path: 'func',
                    component: resolve => require(['@/pages/blog/Func.vue'], resolve)
                },
                {
                    path: 'board',
                    component: resolve => require(['@/pages/blog/Board.vue'], resolve)
                },
                {
                    path: 'about',
                    component: resolve => require(['@/pages/blog/About.vue'], resolve)
                },
            ]
        },
        //文章浏览部分
        {
            path: '/view/:id',
            component: resolve => require(['@/pages/blog/View.vue'], resolve),
            meta: { title: '文章浏览' }
        },
        //后台管理系统
        {
            path: '/back/login',
            component: resolve => require(['@/pages/back/Login.vue'], resolve),
            meta: { title: '后台登录' }
        },
        {
            path: '/back',
            component: resolve => require(['@/components/back/Layout.vue'], resolve),
            meta: { title: '后台管理' },
            children:[
                {
                    path: 'home',
                    component: resolve => require(['@/pages/back/Home.vue'], resolve),
                    meta: { title: '系统首页' }
                },
                {
                    path: 'art/list',
                    component: resolve => require(['@/pages/back/ArtList.vue'], resolve),
                    meta: { title: '文章查询' }
                },
                {
                    path: 'art/add',
                    component: resolve => require(['@/pages/back/ArtAdd.vue'], resolve),
                    meta: { title: '文章新增' }
                },
                {
                    path: 'art/add/md',
                    component: resolve => require(['@/pages/back/ArtAddMd.vue'], resolve),
                    meta: { title: 'markdown' }
                },
                {
                    path: 'art/count',
                    component: resolve => require(['@/pages/back/ArtCount.vue'], resolve),
                    meta: { title: '文章统计' }
                },
                {
                    path: 'user/list',
                    component: resolve => require(['@/pages/back/UserList.vue'], resolve),
                    meta: { title: '用户查询' }
                },
                {
                    path: 'sys/const',
                    component: resolve => require(['@/pages/back/SysConst.vue'], resolve),
                    meta: { title: '文章类型维护' }
                },
                {
                    path: 'art/edit',
                    component: resolve => require(['@/pages/back/ArtEdit.vue'], resolve),
                    meta: { title: '文章编辑' }
                },
            ]
        },
        {
            path: '/404',
            component: resolve => require(['../components/common/404.vue'], resolve)
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
})

router.beforeEach((to, from, next) => {
    // 后台路径校验token
    if (to.path != "/back/login" && to.path.startsWith("/back/")) {
        let jwtToken = localStorage.getItem('$back-token');
        if(jwtToken){
            next();
        } else {
            next('back/login');
        }
    } else {
        next();
    }
});

export default router;