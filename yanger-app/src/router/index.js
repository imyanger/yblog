import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
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
                    path: '/blog/home',
                    component: resolve => require(['../pages/blog/Home.vue'], resolve)
                },
                {
                    path: '/blog/study',
                    component: resolve => require(['../pages/blog/Study.vue'], resolve)
                },
                {
                    path: '/blog/essay',
                    component: resolve => require(['../pages/blog/Essay.vue'], resolve)
                },
                {
                    path: '/blog/func',
                    component: resolve => require(['../pages/blog/Func.vue'], resolve)
                },
                {
                    path: '/blog/board',
                    component: resolve => require(['../pages/blog/Board.vue'], resolve)
                },
                {
                    path: '/blog/about',
                    component: resolve => require(['../pages/blog/About.vue'], resolve)
                },
            ]
        },
        //文章浏览部分
        {
            path: '/view/:id',
            component: resolve => require(['../pages/blog/View.vue'], resolve),
            meta: { title: '文章浏览' }
        },
        //后台管理系统
        {
            path: '/back',
            component: resolve => require(['@/components/back/Layout.vue'], resolve),
            meta: { title: '后台管理' },
            children:[
                {
                    path: '/back/home',
                    component: resolve => require(['@/pages/back/Home.vue'], resolve),
                    meta: { title: '系统首页' }
                },
                {
                    path: '/back/art/list',
                    component: resolve => require(['@/pages/back/ArtList.vue'], resolve),
                    meta: { title: '文章查询' }
                },
                {
                    path: '/back/art/add',
                    component: resolve => require(['@/pages/back/ArtAdd.vue'], resolve),
                    meta: { title: '文章新增' }
                },
                {
                    path: '/back/art/count',
                    component: resolve => require(['@/pages/back/ArtCount.vue'], resolve),
                    meta: { title: '文章统计' }
                },
                {
                    path: '/back/user/list',
                    component: resolve => require(['@/pages/back/UserList.vue'], resolve),
                    meta: { title: '用户查询' }
                },
                {
                    path: '/back/sys/const',
                    component: resolve => require(['@/pages/back/SysConst.vue'], resolve),
                    meta: { title: '常量查询' }
                },
            ]
        },


        {
            path: '/',
            component: resolve => require(['../components/page/Home.vue'], resolve),
            meta: { title: '自述文件' },
            children:[
                {
                    path: '/dashboard',
                    component: resolve => require(['../components/page/Dashboard.vue'], resolve),
                    meta: { title: '系统首页' }
                },
                {
                    path: '/table',
                    component: resolve => require(['../components/page/BaseTable.vue'], resolve),
                    meta: { title: '基础表格' }
                },
                {
                    path: '/tabs',
                    component: resolve => require(['../components/page/Tabs.vue'], resolve),
                    meta: { title: 'tab选项卡' }
                },
                {
                    path: '/form',
                    component: resolve => require(['../components/page/BaseForm.vue'], resolve),
                    meta: { title: '基本表单' }
                },
                {
                    // 富文本编辑器组件
                    path: '/editor',
                    component: resolve => require(['../components/page/VueEditor.vue'], resolve),
                    meta: { title: '富文本编辑器' }
                },
                {
                    // markdown组件
                    path: '/markdown',
                    component: resolve => require(['../components/page/Markdown.vue'], resolve),
                    meta: { title: 'markdown编辑器' }    
                },
                {
                    // 图片上传组件
                    path: '/upload',
                    component: resolve => require(['../components/page/Upload.vue'], resolve),
                    meta: { title: '文件上传' }   
                },
                // {
                //     // vue-schart组件
                //     path: '/charts',
                //     component: resolve => require(['../components/page/BaseCharts.vue'], resolve),
                //     meta: { title: 'schart图表' }
                // },
                {
                    // 拖拽列表组件
                    path: '/drag',
                    component: resolve => require(['../components/page/DragList.vue'], resolve),
                    meta: { title: '拖拽列表' }
                },
                {
                    // 权限页面
                    path: '/permission',
                    component: resolve => require(['../components/page/Permission.vue'], resolve),
                    meta: { title: '权限测试', permission: true }
                }
            ]
        },
        {
            path: '/login',
            component: resolve => require(['../components/page/Login.vue'], resolve)
        },
        {
            path: '/403',
            component: resolve => require(['../components/page/403.vue'], resolve)
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
