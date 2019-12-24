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
					<h3>{{viewData.article.title}}</h3>
					<div id="article_describe">
						<div id="article_describe_left">
							<span>
								<i class="el-icon-document"></i>
								<span>{{viewData.article.typeVal}} - {{viewData.article.classifyVal}}</span>
							</span>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<span>
								<i class="el-icon-date"></i>
								<span>{{viewData.article.updateTime | formatDate}}</span>
							</span>
						</div>
						<div id="article_describe_right">
							<span>
								<i class="el-icon-star-off"></i>
								<span>{{viewData.article.likes}}</span>
							</span>
							<span>
								<i class="el-icon-edit"></i>
								<span>{{viewData.article.commons}}</span>
							</span>
							<span>
								<i class="el-icon-view"></i>
								<span>{{viewData.article.views}}</span>
							</span>
						</div>
					</div>
					<el-row>
						<div id="article_html" v-html="viewData.article.content"></div>
					</el-row>
					<div id="Msg" ref="msgArea">
						
						<div id="Msg_le">
							<span>想说点什么，留下你的想法吧</span>
							<button @click="leaveMsg">发表评论</button>
						</div>
						<div class="editor-container">
							<UE :defaultMsg='msg' :config='config' ref="ue"></UE>
						</div>
						<div id="Msg_content" v-for="(msg, index) in viewData.msgPage.data" :key="index">
							<el-row>
								<div class="row each_Msg" style="margin: 0 auto;">
									<div class="row each_Msg_right">
										<p><font>{{msg.updateTime | formatDate}}&nbsp;&nbsp;&nbsp;&nbsp;</font>{{msg.userNickName}}&nbsp;:</p>
										<div class="each_Msg_right_c">
											<div v-html="msg.content"></div>
										</div>
										<hr>
									</div>
									<div class="each_Msg_left">
										<img alt="" src="static/img/img.jpg">
									</div>
								</div>
							</el-row>
						</div>
						<div id="page_bar">
							<el-pagination @current-change="handleCurrentChange" 
								:current-page.sync="viewData.msgPage.pageNo" :page-size="viewData.msgPage.pageSize" 
								layout="total,jumper, prev, pager, next" :total="viewData.msgPage.totalCount">
							</el-pagination>
						</div>
					</div>
				</div>
				<div id="info">
					<!-- 网站登录模块 -->
					<sign></sign>
					<!-- 关于我 -->
					<about-me></about-me>
					<!-- 推荐文章 -->
					<div  id="note_hot">
					<h3>推荐阅读</h3>
					<ul  v-for="(hot, index) in viewData.hots" :key="index">
						<li>
							<a href="" :title="hot.id" target="_blank">{{hot.title}}</a>
						</li>
					</ul>
				</div>
				</div>
			</el-row>
		</div >
		<b-footer></b-footer>
    </div>
</template>

<script>
	import bFooter from '@/components/blog/Footer';
	import UE from '@/components/common/ueditor'; //ueditor富文本编辑器
	import { formatDate } from 'static/js/date'; //date格式化
	import aboutMe from '@/components/blog/AboutMe';
	import sign from '@/components/blog/Sign'; //用户信息展示
	import { mapGetters } from 'vuex'; //vuex组件
    export default {
        data() {
            return {
                viewData: {
					msgPage: {},
					hots: [],
					article: {}
                },
                msg: '',
                config: {
                    initialFrameWidth: 698,
                    initialFrameHeight: 120,
                    toolbars: [[
                        'fullscreen', 'source', '|', 'undo', 'redo', '|',
                        'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                        'rowspacingtop', 'rowspacingbottom', 'lineheight', 'fontfamily', '|', 
                        'directionalityltr', 'directionalityrtl', 'indent', '|',
                        'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                        'link', 'unlink', 'emotion','horizontal', 'date', 'time', 'spechars'
                    ]],
                    maximumWords: 300,
                    elementPathEnabled: false
                }
            }
        },
        computed:{
        },
        methods:{
			...mapGetters(['getUser']),
			//发表评论
			leaveMsg(){
				let user = this.getUser();
                if(user.userId){
                    let msg = this.$refs.ue.getContent();
                    if(msg){
                        //发送留言信息
                        let _this = this;
                        this.$post("/blog/leaveMsg", {
                            type: '01',
							content: msg,
							articleId: _this.viewData.article.articleId,
							articleTitle: _this.viewData.article.title,
							artImgPath: _this.viewData.article.artImgPath
                        })
                        .then(function (response) {
                            // 加载第一页
                           _this.handleCurrentChange(1);
                            //清空内容
                            _this.$refs.ue.clear();
                        })
                        .catch(function (error) {
                            console.log(error);
                        });
                    }else {
                        this.$alert("请输入留言内容", "提示");
                    }
                }else {
                    this.$alert("登录后可使用留言功能，请先登录", "提示");
                }
			},
			//页码改变
			handleCurrentChange(val) {
				let _this = this;
				this.$post("/blog/artMsgPage", {
					articleId: _this.viewData.article.articleId,
					pageNo: val
				})
				.then(function (response) {
                    _this.viewData.msgPage = response.data;
				})
				.catch(function (error) {
					console.log(error);
				});
			},
			
        },
        mounted(){
		},
		created(){
			//获取url传递参数id
			let id = this.$route.params.id;
			//上下文的改变
            let _this = this;
            this.$get("/blog/view",{
				id: id
			})
            .then(function (response) {
				_this.viewData.msgPage = response.data.msgPage;
				_this.viewData.hots = response.data.hots;
				_this.viewData.article = response.data.article;
				let type = _this.$route.query.type;
				if('commons' === type){
					//_this.$refs.msgArea.focus();
					_this.$nextTick(() => {
						document.getElementById("Msg_le").scrollIntoView();
					})
				}
            })
            .catch(function (error) {
                console.log(error);
            });
		},
		components: {
            bFooter, UE, aboutMe, sign
		},
		filters: {
             formatDate(time) {
                var date = new Date(time);
                return formatDate(date, 'yyyy-MM-dd hh:mm');
            }
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
		width: 93%;
		height: auto;
		margin: 0 auto;
		font-size: 15px;
    	line-height: 25px;
	}
	#article_describe {
	    font-size: 14px;
		height: 20px;
		margin-top: 15px;
		margin-bottom: 20px;
    	padding-bottom: 10px;
    	border-bottom: #bfab86 1px solid;
	}
	#article_describe_left {
		width: 400px;
		float: left;
    	margin-left: 20px;
	}
	#article_describe_right {
		width: 250px;
		float: right;
		margin-right: 20px;
	}
	#article_describe_right span {
		float: right;
		margin-right: 10px;
	}

	/* ----------------留言信息上下页-------------- */
    #page_bar {
        width: 100%;
        height: 37px;
        background-color: #fefefe;
        border-bottom: #BFAB86 1px solid;
        border-top: #BFAB86 1px solid;
    }

	/*-------------- 留言板  --------------------*/
	#Msg {
		border-top: #bfab86 1px solid;
	}
    .editor-container {
		margin-bottom: 10px;
    }
	#Msg_le {
		height: 60px;
	}
    #Msg_le button{
        width: 70px;
        height: 24px;
        border-style: none;
        border-radius: 3px;
        background-color: #EDA347;
        float: right;
        margin-top: 20px;
        margin-right: 40px;
    }
    #Msg_le span {
        width: 250px;
		color: #db6d4c;
		font-size: 16px;
		font-weight: 400;
		margin: 20px 0 20px 200px;
		float: left;
    }
    #Msg_content{
        width: 100%;
    }
    .each_Msg {
        width: 680px;
        margin: 0 auto;
    }
    .each_Msg_left {
        float: right;
        width: 70px;
    }
    .each_Msg_left img {
        height:100%;
        margin-left: -18px;
        width: 50px;
        height: 50px;
        border-radius: 50px;
    }
    .each_Msg_right {
        margin-top: 5px;
        float: right;
        width: 580px;
    }
    .each_Msg_right font {
        color: #888;
        font-size: 15px;
    }
    .each_Msg_right p {
        font-size: 14px;
    }
    .each_Msg_right_c {
        margin-left: 10px;
        line-height: 25px;
        font-size: 13px;
        margin-right: 20px;
    }
    .each_Msg_right hr {
        margin-left:-92px;
        margin-right: 17px;
        margin-top: 10px;
        margin-bottom: 10px;
        height:1px;
        background-color: #db6d4c;
    }

	/*-------------- 右侧部分  --------------------*/
	#info {
		width: 280px;
		float: right;
		margin: 0 auto;
		margin-top: -20px;
		background-color: #fefefe;
	}

	/*------------------ 推荐日志------------------*/
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
		line-height: 26px;
		list-style: square;
		font-size: 15px;
		margin-left: 25px;
		font-size: 14px;
	}
	#note_hot ul li a {
		color: #72afd2;
	}
	#article_html img {
		max-width: 100%;
	}
</style>
<style>
	#article_html img {
		max-width: 100% !important;
	}
</style>
