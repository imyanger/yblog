<template>
    <div class="wrapper">
        <header>
            <div class='header'>
                <img src="static/img/blog/header.jpg" alt="杨号个人网站-杨号个人博客-杨号"/>
            </div>
        </header>
		<div id="content">
			<el-row id="content_center">
				<div id="article">
					<h3>假装有标题</h3>
					<div id="article_describe">XX:个人日志 浏览：1010</div>
					<div id="article_html">
					11111

					</div>
				</div>
				<div id="msg">
					222
				</div>
			</el-row>
		</div >
		<b-footer></b-footer>
    </div>
</template>

<script>
	import bFooter from '../common/Footer';
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
		},
		components: {
            bFooter
        },
    }
</script>

<style scoped>
	.wrapper {
        overflow-y: auto;
        background-color: #efefef;
    }
    .header {
		width: 100%;
		height: 180px;
		text-align: center;
        background-color: #efefef;
	}
    .header img {
		width: 1000px;
		height: 144px;
		padding-top: 1px;
	}
   	#content {
		width: 100%;
		background-color: #efefef;
	}
	#content_center {
		width: 1000px;
		margin: 0 auto;
	}
	#article {
		width: 700px;
		float: left;
		margin-top: -20px;
		background-color: #fefefe;
		height: 1000px;
	}
	#msg {
		width: 280px;
		float: right;
		margin: 0 auto;
		margin-top: -20px;
		background-color: #fefefe;
		height: 2000px;
	}
	#article h3 {
		text-decoration: none;
		color: #333;
		font-weight: 500;
		text-align: center;
		margin-top: 20px;
		font-weight: normal;
		font-size: 22px;
	}
	#article_html {
		width: 96%;
		height: 100px;
		margin: 0 auto;
	}
</style>