<template>
    <el-row id="content" class="row" style="margin: 0px;">
        <div id="content_left">
            <div id="Msg">
                <h2 id="">留言板</h2>
                <div class="editor-container">
                    <UE :defaultMsg='msg' :config='config' ref="ue"></UE>
                </div>
                <button @click="leaveMsg">发表</button>
                <h3>留言总数（{{msgData.msgPage.totalCount}}）</h3>
                <div id="Msg_content" v-for="(msg, index) in msgData.msgPage.data" :key="msg.index">
                    <el-row>
                        <div class="row each_Msg" style="margin: 0 auto;">
                            <div class="row each_Msg_right">
                                <p>
                                    <font>{{msg.updateTime | formatDate}}&nbsp;&nbsp;&nbsp;&nbsp;</font>{{msg.userNickName}}&nbsp;:
                                </p>
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
            </div>
            <div id="page_bar">
                <el-pagination @current-change="handleCurrentChange" :current-page.sync="msgData.msgPage.pageNo" :page-size="msgData.msgPage.pageSize" layout="total,jumper, prev, pager, next" :total="msgData.msgPage.totalCount">
                </el-pagination>
            </div>
        </div>
        <div id="content_right">
            <!-- 网站登录模块 -->
            <sign></sign>
            <!-- 关于我 -->
            <about-me></about-me>
        </div>
    </el-row>
</template>

<script>
import UE from '@/components/common/ueditor'; //ueditor富文本编辑器
import aboutMe from '@/components/blog/AboutMe';
import sign from '@/components/blog/Sign'; //用户信息展示
import { formatDate } from 'static/js/date'; //date格式化
import { mapGetters } from 'vuex'; //vuex组件
export default {
    components: {
        UE, aboutMe, sign
    },
    data() {
        return {
            msgData: {
                msgPage: {}
            },
            msg: '',
            config: {
                initialFrameWidth: 698,
                initialFrameHeight: 200,
                toolbars: [[
                    'fullscreen', 'source', '|', 'undo', 'redo', '|',
                    'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                    'rowspacingtop', 'rowspacingbottom', 'lineheight', 'fontfamily', '|',
                    'directionalityltr', 'directionalityrtl', 'indent', '|',
                    'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                    'link', 'unlink', 'emotion', 'horizontal', 'date', 'time', 'spechars'
                ]],
                maximumWords: 300,
                elementPathEnabled: false
            }
        }
    },
    filters: {
        formatDate(time) {
            var date = new Date(time);
            return formatDate(date, 'yyyy-MM-dd hh:mm');
        }
    },
    created() {
        //上下文的改变
        let _this = this;
        this.$get("/blog/boardInit")
            .then(function (response) {
                _this.msgData.msgPage = response.data.msgPage;
            })
            .catch(function (error) {
                console.log(error);
            });
    },
    methods: {
        ...mapGetters(['getUser']),
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
            return this.$refs.ue.getContentTxt();
        },
        setHide() {
            this.$refs.ue.setHide()
        },
        setShow() {
            this.$refs.ue.setShow()
        },
        handleCurrentChange(val) {
            let _this = this;
            this.$post("/blog/msgPage", {
                pageNo: val
            })
                .then(function (response) {
                    _this.msgData.msgPage = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        //发表留言
        leaveMsg() {
            let user = this.getUser();
            if (user.userId) {
                let msg = this.$refs.ue.getContent();
                if (msg) {
                    //发送留言信息
                    let _this = this;
                    this.$post("/blog/leaveMsg", {
                        type: '02',
                        content: msg
                    })
                        .then(function (response) {
                            _this.handleCurrentChange(1);
                            //清空内容
                            _this.$refs.ue.clear();
                        })
                        .catch(function (error) {
                            console.log(error);
                        });
                } else {
                    this.$alert("请输入留言内容", "提示");
                }
            } else {
                this.$alert("登录后可使用留言功能，请先登录", "提示");
            }
        }
    }
};
</script>

<style scoped>
#content {
    width: 1000px;
    margin: 0 auto;
    margin-top: 8px;
}
#content_left {
    width: 700px;
    height: 100%;
    float: left;
    margin-top: 3px;
    border-radius: 5px;
    box-shadow: 1px 2px 3px #adc2d7;
}
#Msg {
    width: 700px;
    background-color: #fefefe;
    border-radius: 4px;
}
#content_right {
    width: 280px;
    height: 100%;
    float: right;
    background-color: #b4f3dc;
    margin-top: 40px;
    border-radius: 5px;
    box-shadow: 1px 2px 3px #adc2d7;
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
#Msg button {
    width: 70px;
    height: 24px;
    border-style: none;
    border-radius: 3px;
    background-color: #eda347;
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
#Msg_content {
    width: 100%;
}
.each_Msg {
    width: 690px;
    margin: 0 auto;
}
.each_Msg_left {
    float: right;
    width: 70px;
}
.each_Msg_left img {
    height: 100%;
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
    margin-left: -92px;
    margin-right: 17px;
    margin-top: 10px;
    margin-bottom: 10px;
    height: 1px;
    background-color: #db6d4c;
}

/* ----------------上下页的导航菜单-------------- */
#page_bar {
    width: 100%;
    height: 37px;
    background-color: #fefefe;
    border-bottom: #bfab86 1px solid;
    border-top: #bfab86 1px solid;
}
</style>

<style>
/* ----------------分页组件------------- */
#article .el-pagination .btn-prev {
    margin-left: 10%;
}
#article .el-pagination .el-input__inner,
.btn-prev,
.btn-next {
    background-color: transparent !important;
}
#article .el-pagination .el-pager li {
    background-color: transparent;
}
</style>