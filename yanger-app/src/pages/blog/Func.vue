<template>
    <el-row id="content" class="row" style="margin: 0px;">
		<div id="content_left">
			<h3>在这里，也许你可以</h3>
			<div id="resource_table">
				<table>
					<tr>
						<td id="book">
							<a href="https://github.com/imyanger/yblog" target="_blank">
								<span class="resource_table_img">
									<img alt="" src="static/img/blog/code.jpg">
								</span>
								<br>
								<a>项目源码</a>
							</a>
						</td>
						<td><a href="#/back/home" target="_blank">
								<span class="resource_table_img">
									<img alt="" src="static/img/blog/back2.jpg">
								</span>
								<br>
								<a>网站后台</a>
							</a></td>
						<td><a>更多敬请期待</a></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
                        <td></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</table>
			</div>
		</div>
		<div id="content_right">
			<!-- 网站登录模块 -->
			<span id="goodweb">
				<h3>实用网站推荐</h3>
				<div v-for="(link,index) in links" :key="index">
					<p><a :href="link.link" target="_Blank">{{link.depict}}</a></p>
				</div>
			</span>
		</div>
	</el-row>
</template>
<script>
export default {
	data() {
		return {
			links: []
		}
	},
	created(){
		//上下文的改变
		let _this = this;
		this.$get("/blog/links")
		.then(function (response) {
			_this.links = response.data;
		})
		.catch(function (error) {
			console.log(error);
		});
	}
}
</script>

<style scoped>
    #content {
	width: 1000px;
	height: 450px;
	margin: 0 auto; 
	margin-top:8px;
	margin-bottom: 25px;
	}
	#content_left {
		width: 700px;
		height: 100%;
		float: left;
		margin-top: 3px;
        background-color: #fefefe;
        border-radius: 5px;
        box-shadow: 1px 2px 3px #adc2d7;
	}
	#content_right {
		width: 280px;
		height: 100%;
		float: right;
		margin-top: 3px;
        background-color: #c8dbf8;
        border-radius: 5px;
        box-shadow: 1px 2px 3px #adc2d7;
	}

	/*--------------------- 扩展功能表格 ---------------------------*/
	#content_left h3 {
		font-size: 20px;
		text-align: center;
		height: 40px;
		margin-top: 20px;
		color: #00EC00;
		font-weight: bold;
	}
	table {
		margin: 0 auto;
	}
	table tr td {
		border: 1px solid #d0d0d0;
		width: 160px;
		height: 120px;
		text-align: center;
        cursor: pointer;
        border-radius: 10px;
        box-shadow: 1px 2px 3px #adc2d7;
	}
	table tr td img {
		width: 50px;
		height: 50px;
        margin-bottom: 10px;
        border-radius: 4px;
        box-shadow: 1px 2px 3px #adc2d7;
	}

	table tr td a {
		color: #72afd2;
		font-size: 14px;
	}

	/*--------------------- 实用网站推荐 ---------------------------*/
	#goodweb h3 {
		line-height: 40px;
		color: #38485A;
		font-size: 18px;
		border-bottom: 1px solid #ff3300;
		text-align: center;
		height: 40px;
		margin-bottom: 16px;
		font-weight: 500;
	}
	#goodweb p{
		margin: 0 auto;
		text-align: center;
		font-size: 15px;
		cursor: pointer;
		line-height: 30px;
	}
	#goodweb p a {
		color: #40a73e;
	}
</style>