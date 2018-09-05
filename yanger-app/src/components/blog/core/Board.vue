<template>
    <el-row id="content" class="row" style="margin: 0px;">
		<div id="content_left">
			<div id="Msg">
				<h2 id="">留言板</h2>
                <div class="editor-container">
                    <UE :defaultMsg='msgData.defaultMsg' :config='msgData.config' ref="ue"></UE>
                </div>
				<button @click="leaveMsg">发表</button>
				<h3>留言总数（{{msgData.page.total}}）</h3>
				<div id="Msg_content" v-for="(msg, index) in msgData.data.msgs" :key="msg.index">
                    <el-row>
                        <div class="row each_Msg" style="margin: 0 auto;">
                            <div class="row each_Msg_right">
                                <p><font>{{msg.createDate}}&nbsp;&nbsp;&nbsp;&nbsp;</font>{{msg.userName}}&nbsp;:</p>
                                <div class="each_Msg_right_c">{{msg.content}}</div>
                                <hr>
                            </div>
                            <div class="each_Msg_left">
                                <img alt="" src="static/img/img.jpg">
                            </div>
                        </div>
                    </el-row>
				</div>
			</div>
			<div id="page_bar">
				<el-pagination @current-change="handleCurrentChange" 
                    :current-page.sync="msgData.page.currentPage" :page-size="msgData.page.size" 
                    layout="total,jumper, prev, pager, next" :total="msgData.page.total">
                </el-pagination>
			</div>
		</div>
		<div id="content_right">
            <about-me></about-me>
			<!-- 网站登录模块 -->
			<!-- 关于我 -->
		</div>
	</el-row>
</template>

<script>
    import UE from '../common/ueditor'; //ueditor富文本编辑器
    import aboutMe from './AboutMe';
    export default {
        components: {
            UE, aboutMe
        },
        data() {
            return {
                msgData: {
                    defaultMsg: '这里是UE测试',
                    config: {
                        initialFrameWidth: 700,
                        initialFrameHeight: 200
                    },
                    data: {
                        msgs: [
                            {
                                createDate:'2019-04-22',
                                userName: '张飒',
                                content: '测试谁数据'
                            },
                            {
                                createDate:'2019-04-22',
                                userName: '张飒',
                                content: '测试谁但是防守是放松放松放松放松的放松放松放松法放松方式方法如何让人个人得分躲躲\
                                测试谁但是防守是放松放松放松放松的放松放松放松法放松方式方法如何让人个人得分躲躲\
                                测试谁但是防守是放松放松放松放松的放松放松放松法放松方式方法如何让人个人得分躲躲\
                                测试谁但是防守是放松放松放松放松的放松放松放松法放松方式方法如何让人个人得分躲躲藏藏数据'
                            }
                        ]
                    },
                    page: {
                        currentPage: 1,
                        size: 5,
                        total: 20
                    },
                }
            }
        },
        methods: {
            getUEContent() {
                let content = this.$refs.ue.getContent();
                this.$notify({
                title: '获取成功，可在控制台查看！',
                message: content,
                type: 'success'
                });
                console.log(content)
            },
            getUEContentTxt() {
                alert(this.$refs.ue.getContentTxt());
            },
            setHide() {
                this.$refs.ue.setHide()
            },
            setShow() {
                this.$refs.ue.setShow()
            },
            handleCurrentChange(val) {
				console.log(`当前页: ${val}`);
            },
            leaveMsg() {
                /* this.$axios.get('/api/core/blog/homeInit')
                .then(function (response) {
                    console.log(response);
                })
                .catch(function (error) {
                    console.log(error);
                }); */
                this.$get("/blog/homeInit")
                .then(function (response) {
                    console.log(response);
                })
                .catch(function (error) {
                    console.log(error);
                });
            }
        }
    };
</script>

<style scoped>
    #content {
        width: 1000px;
        margin: 0 auto; 
        margin-top:8px;
    }
    #content_left {
        width: 700px;
        height: 100%;
        float: left;
        margin-top:3px;
    }
    #Msg {
        width: 700px;
        background-color: #fefefe;
    }
    #content_right {
        width: 280px;
        height: 100%;
        float: right;
        background-color: #b4f3dc;
        margin-top: 40px;
    }

    /*-------------- 留言板  --------------------*/
    #ueditorPart {
        margin: 0 auto;
        text-align: center;
    }
    #Msg h2 {
        width: 658px;
        color: #db6d4c;
        font-size: 17px;
        font-weight: 400;
        padding: 20px 0 10px 1px;
        margin: 0 0 20px 21px;
        border-bottom: #db6d4c 2px solid;
    }
    #Msg button{
        width: 70px;
        height: 24px;
        border-style: none;
        border-radius: 3px;
        background-color: #EDA347;
        float: right;
        margin-top: 20px;
        margin-right: 30px;
    }
    #Msg h3 {
        width: 658px;
        color: #db6d4c;
        font-size: 16px;
        font-weight: 400;
        padding: 20px 0 10px 1px;
        margin: 23px 0 10px 21px;
        border-bottom: #db6d4c 1px solid;
    }
    #Msg_content{
        width: 100%;
    }
    .each_Msg {
        width: 658px;
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

    /* ----------------上下页的导航菜单-------------- */
    #page_bar {
        width: 100%;
        height: 37px;
        background-color: #fefefe;
        border-bottom: #BFAB86 1px solid;
        border-top: #BFAB86 1px solid;
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