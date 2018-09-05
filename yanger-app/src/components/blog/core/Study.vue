<template>
   <el-row id="content" class="row" style="margin: 0px;">
		<div id="content_left">
			<div id="article">
				<h2 class="title-h2">路漫漫其修远兮&nbsp;&nbsp;&nbsp;吾将上下而求索</h2>
				<div class="content_study_note" v-for="(study, index) in studyData.data.studys" :key="study.index">
					<div class="note_left">
						<img alt="" src="static/img/img.jpg">
					</div>
					<div class="note_right">
						<h4><a href="" title="文章标题">{{study.title}}</a><span>分类：{{study.classify}}</span></h4>
						<p class="note_c">{{study.summary}}</p>
						<autor :createDate='study.createDate' :likes='study.likes' :commons='study.commons' :views='study.views'></autor>
					</div>
				</div>
				<!-- 分页组件 -->
				<el-pagination @current-change="handleCurrentChange" @prev-click="handlePrevClick"
					@next-click="handleNextClick" :current-page.sync="studyData.page.currentPage" :page-size="studyData.page.size" 
					layout="total,jumper, prev, pager, next" :total="studyData.page.total">
				</el-pagination>
			</div>
		</div>
		<div id="content_right">
			<!-- 网站登录模块 -->
			<div id="search">
				<el-row>
					<el-input placeholder="请输入内容以搜索" prefix-icon="el-icon-search" v-model="studyData.queryValue"></el-input>
					<i class="el-icon-search"></i>
				</el-row>
			</div>
			<div id="note_clazz">
				<h3>文章分类 </h3>
				<el-tabs tab-position="left">
					<el-tab-pane label="java">
						<ul>
							<li>源码解析（3）</li>
							<li>JVM分析</li>
						</ul>
					</el-tab-pane>
					<el-tab-pane label="web前端">
						<ul>
							<li>vue</li>
							<li>html</li>
						</ul>
					</el-tab-pane>
					<el-tab-pane label="数据库">
						<ul>
							<li>mysql</li>
							<li>分布式数据库</li>
						</ul>
					</el-tab-pane>
					<el-tab-pane label="其他">
						<ul>
							<li>linux</li>
							<li>阿里云部署</li>
						</ul>
					</el-tab-pane>
				</el-tabs>
			</div>
			<div  id="note_hot">
				<h3>热门文章 </h3>
				<ul  v-for="(hot, index) in studyData.data.hots" :key="hot.index">
					<li>
						<a href="" :title="hot.id" target="_blank">{{hot.title}}</a>
					</li>
				</ul>
			</div>
		</div>
	</el-row>
</template>

<script>
	import autor from '../common/Autor';
	export default {
		data() {
			return {
				studyData: {
					data: {
						studys: [
							{
								createDate: "1028-09-09",
								title: "2二十大二恶大的撒",
								classify: 'java',
								summary:
									"魔力数据dsds的四大四大阿魔力数据dsds的四大四大魔力数据dsds的四大四大魔力数据dsds的四大四大魔力数据dsds的四大四大达阿斯顿撒旦撒旦阿达",
								likes: 18,
								commons: 299,
								views: 333
							},
							{
								createDate: "1028-09-09",
								title: "恶风大大的撒",
								classify: 'java',
								summary: "魔力数据斯顿撒旦撒旦阿达",
								likes: 18,
								commons: 299,
								views: 333
							},
							{
								createDate: "1028-09-09",
								title: "十大大的撒",
								summary:
								"魔力数据dsds的四大四大阿魔力数据dsds的四大四大魔力数据sds的四大四大阿魔力数据ds\
											s的四大四大魔力数据dsds的sds的四大四大阿魔力数据dsds的四大四大魔力数据dsds的sds的四大四\
											数据dsds的四大四大魔力数数据dsds的四大四大魔力数数据dsds的四大四大魔力数大阿魔力数据dsds的四大四大魔力数据dsds\
											的dsds的四大四大魔力数据dsds的四大四大魔力数据dsds的四大四大达阿斯顿数据dsds的四大四大魔力数数据dsds的四大四大魔力数数据dsds的四大四大魔力数撒旦撒旦阿达",
								likes: 18,
								commons: 299,
								views: 333
							},
							{
								createDate: "1028-09-09",
								title: "十大大的撒",
								summary:
								"魔力数据dsds的四大四大阿魔力数据dsds的四大四大魔力数据sds的四大四大阿魔力数据ds\
											s的四大四大魔力数据dsds的sds的四大四大阿魔力数据dsds的四大四大魔力数据dsds的sds的四大四\
											数据dsds的四大四大魔力数数据dsds的四大四大魔力数数据dsds的四大四大魔力数大阿魔力数据dsds的四大四大魔力数据dsds\
											的dsds的四大四大魔力数据dsds的四大四大魔力数据dsds的四大四大达阿斯顿数据dsds的四大四大魔力数数据dsds的四大四大魔力数数据dsds的四大四大魔力数撒旦撒旦阿达",
								likes: 18,
								commons: 299,
								views: 333
							},
							{
								createDate: "1028-09-09",
								title: "十大大的撒",
								summary:
								"魔力数据dsds的四大四大阿魔力数据dsds的四大四大魔力数据sds的四大四大阿魔力数据ds\
											s的四大四大魔力数据dsds的sds的四大四大阿魔力数据dsds的四大四大魔力数据dsds的sds的四大四\
											数据dsds的四大四大魔力数数据dsds的四大四大魔力数数据dsds的四大四大魔力数大阿魔力数据dsds的四大四大魔力数据dsds\
											的dsds的四大四大魔力数据dsds的四大四大魔力数据dsds的四大四大达阿斯顿数据dsds的四大四大魔力数数据dsds的四大四大魔力数数据dsds的四大四大魔力数撒旦撒旦阿达",
								likes: 18,
								commons: 299,
								views: 333
							}
						],
						hots: [
							{
								id: 1,
								title: '山东省地方'
							},
							{
								id: 1,
								title: '多的是矫情的人'
							},
							{
								id: 1,
								title: '一个人的夜我的心应该放在哪里'
							},{
								id: 1,
								title: '礼物不需要太贵'
							},{
								id: 1,
								title: '胜多负少都很开放'
							}
						]
					},
					page: {
						currentPage: 1,
						size: 5,
						total: 20
					},
					queryValue: ''
				}
			};
		},
		methods: {
			handleCurrentChange(val) {
				console.log(`当前页: ${val}`);
			},
			handlePrevClick(val) {
				console.log("上一页: ${val}");
			},
			handleNextClick(val) {
				console.log("下一页: ${val}");
			}
		},
		components: {
			autor
		}
	};
</script>

<style scoped>
	#content {
		width: 1000px;
		margin: 0 auto;
		margin-top: 8px;
	}
	#article {
		width: 700px;
		max-height: 1130px;
		min-height: 750px;
		background-color: #fefefe;
	}
	#content_left {
		width: 700px;
		height: 100%;
		float: left;
		margin-top: 3px;
	}
	#content_right {
		width: 280px;
		height: 100%;
		float: right;
		background-color: #fafacc;
		margin-top: 40px;
	}
	#content_left .title-h2 {
		width: 100%;
		height: 50px;
		background-color: #efefef;
		font-size: 23px;
		text-align: center;
		padding-top: 11px;
		margin-bottom: -1px;
		border-top: 2px dashed #ccc;
		border-left: 2px dashed #ccc;
		border-right: 2px dashed #ccc;
		font-weight: 500;
		margin-left: -2px;
	}

	/* ---------------文章列表 --------------- */
	.content_study_note {
		border-bottom: #bfab86 1px solid;
		overflow: hidden;
		background-color: white;
	}
	.note_left {
		width: 150px;
		height: 136px;
		float: left;
		margin: 20px 0 10px 2px;
	}
	.note_left img {
		width: 90%;
		height: 90%;
	}
	.note_c {
		color: #888;
		height: 80px;
		margin-bottom: 15px;
		margin-right: 10px;
		overflow: hidden;
		font-size: 14px;
		margin-top: 10px;
		line-height: 21px;
	}
	.note_right {
		width: 75%;
		height: 136px;
		float: right;
		margin: 5px 0 10px 2px;
	}
	.note_right h4 {
		font-size: 16px;
		font-weight: 500;
	}
	.note_right h4 a {
		color: #72afd2;
	}
	.note_right h4 span {
		float: right;
		margin-right: 25px;
		font-size: 14px;
		color: #888;
	}

	/*------------------ 搜索功能------------------*/
	#search {
		width: 100%;
		height: 50px;
		text-align: center;
		padding-top: 7px;
		margin-top: 10px;
	}
	#search .el-input {
		border: none;
		width: 80%;
		margin-right: 10px;
	}
	#search .el-icon-search {
		cursor: pointer;
	}

	/*------------------ 分类-----------------*/
	#note_clazz h3 {
		line-height: 40px;
		color: #38485a;
		font-size: 18px;
		border-bottom: 1px solid #ff3300;
		text-align: center;
		height: 40px;
		margin-bottom: 16px;
		font-weight: 500;
	}
	#note_clazz .el-tabs {
		height: 300px;
	}
	#note_clazz li {
		font-size: 13px;
		line-height: 25px;
		margin-left: 30px;
	}
	#note_clazz li:hover {
		font-size: 13px;
		line-height: 25px;
		margin-left: 30px;
		color: #409EFF;
		cursor: pointer;
	}

	/*------------------ 热门文章------------------*/
	#note_hot h3 {
		line-height: 40px;
		color: #38485a;
		font-size: 18px;
		border-bottom: 1px solid #ff3300;
		text-align: center;
		height: 40px;
		margin-bottom: 16px;
		font-weight: 500;
	}
	#note_hot ul li {
		line-height: 30px;
		list-style-type: none;
		font-size: 15px;
		margin-left: 10px;
		font-size: 15px;
	}
	#note_hot ul li a {
		color: #72afd2;
	}
</style>

<style>
	/* ----------------分页组件------------- */
	#article .el-pagination .btn-prev {
		margin-left: 10%;
	}
	#article .el-pagination .el-input__inner, .btn-prev, .btn-next {
		background-color: transparent !important;
	}
	#article .el-pagination .el-pager li {
		background-color: transparent;
	}
</style>
