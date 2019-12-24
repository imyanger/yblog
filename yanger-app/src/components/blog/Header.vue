<template>
    <div>
        <header>
            <div class='header'>
                <img src="static/img/blog/header_yh.jpg" alt="杨号个人网站-杨号个人博客-杨号"/>
                <div class='navbar-container'>
                    <ul>
                        <li class='navbar-container-home'>
                            <router-link to="/blog/home">首页</router-link>
                        </li>
                        <div class='h-sep'></div>
                        <li>
                            <router-link to="/blog/study">学习笔记</router-link>
                        </li>
                        <div class='h-sep'></div>
                        <li>
                            <router-link to="/blog/essay">心情随笔</router-link>
                        </li>
                        <div class='h-sep'></div>
                        <li>
                            <router-link to="/blog/func">扩展功能</router-link>
                        </li>
                        <div class='h-sep'></div>
                        <li>
                            <router-link to="/blog/board">留言板</router-link>
                        </li>
                        <div class='h-sep'></div>
                        <li>
                            <router-link to="/blog/about">关于我</router-link>
                        </li>
                    </ul>
                </div>
            </div>
        </header>
    </div>
</template>

<script>
    export default {
        data() {
            return {
            }
        },
        computed:{
        },
        methods:{
        },
        mounted(){
        }
    }
</script>

<style scoped>
    .header {
		width: 100%;
		height: 180px;
		text-align:center;
        background-color: #efefef;
	}
    .header img {
		width: 1000px;
		height: 144px;
		padding-top: 1px;
	}
    .navbar-container {
		background-color: #111111;
		-webkit-box-shadow: 0 1px 1px rgba(17, 17, 17, 0.6);
		box-shadow: 0 1px 1px rgba(17, 17, 17, 0.6);
		border-radius: 5px;
		width: 1000px;
		margin: 0 auto;
        margin-top: -3px;
	}
	.navbar-container ul {
		display: -webkit-box;
		display: -webkit-flex;
		display: -ms-flexbox;
		display: flex;
		-webkit-justify-content: space-around;
		-ms-flex-pack: distribute;
		justify-content: space-around;
		border: 2px solid #111111;
        font-size: 14px;
		border-radius: 5px;
		list-style: none;
		padding: 0;
		margin: 0;
	}
	.navbar-container ul li > a {
		width: 150px;
		display: block;
		padding: 5px 0px;
		margin: 0 5px;
		text-decoration: none;
		border-radius: 3px;
		font-weight: 300;
		color: antiquewhite;
		text-shadow: 0 1px 0 black;
		-webkit-transition: all ease .25s;
		transition: all ease .25s;
	}
	.navbar-container ul li > a:hover {
		font-weight:bold;
		background-color: rgb(228, 215, 198);
		color: #111111;
		text-shadow: none;
	}
	.navbar-container ul .h-sep {
		display: block;
		width: 2px;
        background: rgba(247, 247, 247, 0.1);
        padding: 1px;
	}
    .router-link-active {
        background-color: #6486a2;
    }
</style>