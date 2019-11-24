<template>
   <el-row id="content" class="row" style="margin: 0px;">
		<div id="content_left">
			<div id="article">
				<h2 class="title-h2">路漫漫其修远兮&nbsp;&nbsp;&nbsp;吾将上下而求索</h2>
				<div class="content_study_note" v-for="(study, index) in studyData.studyPage.data" :key="index">
					<div class="note_left">
						<img alt="" :src="studyData.serverPath + '/file/thumbImg?wh=150&path=' + study.artImgPath">
					</div>
					<div class="note_right">
						<h4><a :href="'#/view/'+ study.articleId" title="文章标题" target="_blank">{{study.title}}</a><span>分类：{{study.classifyVal}}</span></h4>
						<p class="note_c">{{study.summary}}</p>
						<autor :createDate='study.updateTime' :likes='study.likes' :commons='study.commons' 
							:views='study.views' :id='study.articleId'></autor>
					</div>
				</div>
				<!-- 分页组件 -->
				<el-pagination @current-change="handleCurrentChange" @prev-click="handlePrevClick"
					@next-click="handleNextClick" :current-page.sync="studyData.studyPage.pageNo" :page-size="studyData.studyPage.pageSize" 
					layout="total,jumper, prev, pager, next" :total="studyData.studyPage.totalCount">
				</el-pagination>
			</div>
		</div>
		<div id="content_right">
			<!-- 用户信息 -->
			<sign></sign>
			<!-- 网站登录模块 -->
			<div id="search">
				<el-row>
					<el-input placeholder="请输入内容以搜索" prefix-icon="el-icon-search" v-model="queryValue"></el-input>
					<i class="el-icon-search" @click="queryArticle"></i>
				</el-row>
			</div>
			<div id="note_clazz">
				<h3>文章分类</h3>
				<el-tabs tab-position="left">
					<el-tab-pane :label="kind.typeVal" v-for="kind in studyData.kinds" :key="kind.index">
						<ul v-for="child in kind.children" :key="child.index">
							<li @click="queryClassify(kind.type, child.classify)">{{child.classifyVal}}（{{child.sum}}）</li>
						</ul>
					</el-tab-pane>
				</el-tabs>
			</div>
			<div  id="note_hot">
				<h3>热门文章 </h3>
				<ul  v-for="(hot, index) in studyData.hots" :key="index">
					<li>
						<a :href="'#/view/'+ hot.articleId" :title="hot.id" target="_blank">{{hot.title}}</a>
					</li>
				</ul>
			</div>
		</div>
	</el-row>
</template>

<script>
	import autor from '@/components/blog/Autor'; //文章列表底部展示
	import sign from '@/components/blog/Sign'; //用户信息展示
	export default {
		data() {
			return {
				studyData: {
					studyPage: {
						data: []
					},
					hots: [],
					kinds: []
				},
				queryValue: ''
			};
		},
		created() {
            //上下文的改变
            let _this = this;
            this.$get("/blog/studyInit")
            .then(function (response) {
                _this.studyData = response.data;
            })
            .catch(function (error) {
                console.log(error);
            });
        },
		methods: {
			//页码改变
			handleCurrentChange(val) {
				this.getPage(val);
			},
			handlePrevClick(val) {
				console.log("上一页: ${val}");
			},
			handleNextClick(val) {
				console.log("下一页: ${val}");
			},
			queryArticle() {
				//条件查询，点击查询出符合条件的第一页
				this.getPage(1);
			},
			queryClassify(type, classify) {
				this.getPage(1, type, classify);
			},
			//获取页码数据
			getPage(pageNo, type, classify) {
				let _this = this;
				this.$post("/blog/articlePage", {
					module: "wzlx01",
					pageNo: pageNo,
					queryValue: _this.queryValue,
					classify: classify,
					type: type
				})
				.then(function (response) {
					_this.studyData.studyPage = response.data;
				})
				.catch(function (error) {
					console.log(error);
				});
			}
		},
		components: {
			autor, sign
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
        border-radius: 5px;
	}
	#content_left {
		width: 700px;
		height: 100%;
		float: left;
        margin-top: 3px;
        border-radius: 5px;
	}
	#content_right {
		width: 280px;
		height: 100%;
		float: right;
		background-color: #fafacc;
        margin-top: 40px;
        border-radius: 5px;
        box-shadow: 1px 2px 3px #adc2d7
	}
	#content_left .title-h2 {
		width: 100%;
        height: 50px;
        background-color: #2875605c;
        font-size: 23px;
        text-align: center;
        padding-top: 11px;
        margin-bottom: -1px;
        font-weight: 500;
        border-radius: 5px;
        box-shadow: 1px 2px 3px #adc2d7;
	}

	/* ---------------文章列表 --------------- */
	.content_study_note {
		border-bottom: #bfab86 1px solid;
		overflow: hidden;
        background-color: white;
        border-radius: 5px;
        box-shadow: 1px -1px 3px #adc2d7;
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
    #article .el-pagination {
        border-radius: 4px;
        box-shadow: 1px 2px 3px #adc2d7;
    }
</style>
