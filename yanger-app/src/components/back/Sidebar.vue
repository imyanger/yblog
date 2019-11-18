<template>
    <div class="sidebar">
        <el-menu class="sidebar-el-menu" :default-active="onRoutes" :collapse="collapse" background-color="#324157"
            text-color="#bfcbd9" active-text-color="#20a0ff" unique-opened router>
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i><span slot="title">{{ item.title }}</span>
                        </template>
                        <el-menu-item v-for="(subItem,i) in item.subs" :key="i" :index="subItem.index">
                            &nbsp;&nbsp;&nbsp;{{ subItem.title }}
                        </el-menu-item>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i><span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
    import bus from '../common/bus';
    export default {
        data() {
            return {
                collapse: false,
                items: [
                    {
                        icon: 'el-icon-star-on',
                        index: '/back/home',
                        title: '系统首页'
                    },
                    {
                        icon: 'el-icon-date',
                        index: '1',
                        title: '文章管理',
                        subs: [
                            {
                                index: '/back/art/list',
                                title: '文章查询'
                            },
                            {
                                index: '/back/art/add',
                                title: '文章新增'
                            },
                            {
                                index: '/back/art/add/md',
                                title: 'markdown'
                            },
                            {
                                index: '/back/art/count',
                                title: '文章统计'
                            }
                        ]
                    },
                    {
                        icon: 'el-icon-mobile-phone',
                        index: '2',
                        title: '用户管理',
                        subs: [
                            {
                                index: '/back/user/list',
                                title: '用户查询'
                            },
                        ]
                    },
                    {
                        icon: 'el-icon-setting',
                        index: '3',
                        title: '系统管理',
                        subs: [
                            {
                                index: '/back/sys/const',
                                title: '文章类型维护'
                            },
                        ]
                    }
                ]
            }
        },
        computed:{
            onRoutes(){
                //路由变化时重定位路由
                return this.$route.path;
            }
        },
        created(){
            // 通过 Event Bus 进行组件间通信，来折叠侧边栏
            bus.$on('collapse', msg => {
                this.collapse = msg;
            })
        }
    }
</script>

<style scoped>
    .sidebar{
        display: block;
        position: absolute;
        left: 0;
        top: 70px;
        bottom:0;
        overflow-y: scroll;
    }
    .sidebar::-webkit-scrollbar{
        width: 0;
    }
    .sidebar-el-menu:not(.el-menu--collapse){
        width: 220px;
    }
    .sidebar > ul {
        height:100%;
    }
</style>
