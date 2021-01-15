<template>
    <el-row id="content" class="" style="margin: 0px;">
        <div class="content_left">
            <div class="content_study">
                <h2 class="title-h2">最新学习笔记</h2>
                <div class="content_study_note" v-for="(study, index) in homeData.studys" :key="index">
                    <div class="note_left">
                        <img alt="" :src="homeData.serverPath + '/file/thumbImg?wh=150&path=' + study.artImgPath" title="">
                    </div>
                    <div class="note_right">
                        <h4><a :href="'#/view/'+ study.articleId" title="" target="_blank">{{study.title}}</a></h4>
                        <p class="note_c">{{study.summary}}</p>
                        <autor :createDate='study.updateTime' :likes='study.likes' :commons='study.commons' :views='study.views' :id='study.articleId'></autor>
                    </div>
                </div>
            </div>
            <div class="content_journal">
                <h2 class="title-h2">随心情更新的日志</h2>
                <div class="content_journal_note" v-for="(essay, index) in homeData.essays" :key="index">
                    <div class="journal_left">
                        <img alt="" :src="homeData.serverPath + '/file/thumbImg?wh=150&path=' + essay.artImgPath" title="">
                    </div>
                    <div class="journal_right">
                        <h4><a :href="'#/view/'+ essay.articleId" title="" target="_blank">{{essay.title}}</a></h4>
                        <p class="note_c">{{essay.summary}}</p>
                        <autor :createDate='essay.updateTime' :likes='essay.likes' :commons='essay.commons' :views='essay.views' :id='essay.articleId'></autor>
                    </div>
                </div>
            </div>
        </div>
        <div class="content_right">
            <about-me></about-me>
            <!-- 关于我 -->
            <div id="content_message">
                <h3>最近留言</h3>
                <div id="con">
                    <ul>
                        <li v-for="(msg, index) in homeData.msgs" :key="index">
                            <div class="message_left">
                                <img alt="" src="static/img/img.jpg">
                            </div>
                            <div class="message_right">
                                <p>
                                    <a href="javascript:void(0)">{{msg.userNickName}}</a>
                                    &nbsp;&nbsp;&nbsp;{{msg.updateTime | formatDate}}&nbsp;:
                                </p>
                                <p v-html="msg.content"></p>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div id="content_link">
                <h3>友情链接 </h3>
                <div v-for="(shipLink, index) in homeData.shipLinks" :key="index">
                    <p><a :href="shipLink.link" target="_Blank">{{shipLink.depict}}</a></p>
                </div>
            </div>
        </div>
    </el-row>
</template>

<script>
import aboutMe from '@/components/blog/AboutMe'; //关于我模块
import autor from '@/components/blog/Autor'; //列表文章底部部分
import { formatDate } from 'static/js/date'; //date格式化

export default {
    data() {
        return {
            homeData: {
                msgs: []
            }
        }
    },
    created() {
        //上下文的改变
        let _this = this;
        this.$get("/blog/homeInit")
            .then(function (response) {
                _this.homeData = response.data;
            })
            .catch(function (error) {
                console.log(error);
            });
    },
    filters: {
        formatDate(time) {
            var date = new Date(time);
            return formatDate(date, 'yyyy-MM-dd hh:mm');
        }
    },
    components: {
        aboutMe,
        autor
    },
    methods: {
    },
    computed: {
        classOption: function () {
            return {
                step: 0.5,
                limitMoveNum: 5
            }
        }
    },
    mounted: function () {
    }
}
</script>

<style>
.content_left {
    width: 700px;
    height: 100%;
    float: left;
    margin-top: 3px;
}
.content_right {
    width: 280px;
    height: 100%;
    float: right;
    margin-top: 3px;
}
.autor span span {
    color: #72afd2;
}

/*   ------------------ 文章-学习笔记区域 ---------------------  */
.content_study {
    margin-top: 10px;
    width: 100%;
    height: 530px;
    margin-bottom: 30px;
}
.content_study .title-h2 {
    font-size: 17px;
    font-weight: 400;
    color: #333;
    padding: 2px 0 4px 10px;
    border-left: 3px solid #3472ef;
    margin: 0 0 0 0;
    border-bottom: #fb4912 2px solid;
}
.content_study_note {
    border-bottom: #bfab86 1px solid;
    overflow: hidden;
    background-color: #fefefe;
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
    border-radius: 4px;
}
.note_c {
    color: #888;
    height: 80px;
    margin-bottom: 20px;
    margin-right: 10px;
    overflow: hidden;
    font-size: 14px;
    margin-top: 10px;
    line-height: 20px;
}
.note_right {
    width: 75%;
    height: 136px;
    float: right;
    margin: 5px 0 10px 2px;
}
.note_right h4 {
    font-size: 16px;
}
.note_right h4 a {
    text-decoration: none;
    color: #72afd2;
    font-weight: 500;
}

/*   ------------------ 个人日志区域---------------------  */
.content_journal {
    width: 100%;
    height: 400px;
}
.content_journal_note {
    width: 100%;
    height: 170px;
    border-bottom: #bfab86 1px solid;
    overflow: hidden;
    background-color: #fcfbf1;
    border-radius: 5px;
    box-shadow: 1px -1px 3px #adc2d7;
}
.content_journal .title-h2 {
    font-size: 17px;
    font-weight: 400;
    color: #00cc00;
    padding: 2px 10px 4px 551px;
    border-right: 3px solid #3472ef;
    margin: 0 0 0 0;
    border-bottom: #db6d4c 2px solid;
    float: right;
}
.journal_right h4 {
    font-size: 16px;
    text-align: center;
}
.journal_right h4 a {
    text-decoration: none;
    color: #72afd2;
    font-weight: 500;
}
.journal_left {
    width: 150px;
    height: 136px;
    float: left;
    margin: 20px 0 10px 2px;
}
.journal_left img {
    width: 90%;
    height: 90%;
    border-radius: 4px;
}
.journal_right {
    width: 75%;
    height: 136px;
    float: right;
    margin: 5px 0 10px 2px;
}

/*   ------------------ 最新留言 ---------------------  */
#content_message {
    height: 512px;
    margin: 0px auto;
    position: relative;
    border-top: 1px solid #d6d6d6;
    background-color: #fff;
    overflow: hidden;
    word-break: break-all;
    border-radius: 4px;
    box-shadow: 1px 2px 3px #adc2d7;
}
#con {
    overflow: hidden;
    margin-top: -10px;
}
#content_message ul {
    position: relative;
    margin: 0px 10px 0px 10px;
    top: 0;
    left: 0;
    padding: 0;
}
#content_message ul li {
    width: 100%;
    border-top: 1px dotted #d6d6d6;
    padding: 10px 0;
    _padding: 8px 0;
    overflow: hidden;
    line-height: 1.5;
    height: 71px;
}
#content_message h3 {
    line-height: 40px;
    color: #38485a;
    font-size: 18px;
    border-bottom: 1px solid #ff3300;
    text-align: center;
    height: 40px;
    margin-bottom: 16px;
    margin-top: 3px;
    font-weight: 500;
}
.message {
    width: 99%;
    height: 60px;
    background-color: #eefcfb;
    margin: 3px 2px 2px 2px;
    border-radius: 6px;
}
.message_left {
    width: 60px;
    height: 60px;
    float: left;
}
.message_left img {
    margin: 5px 5px 5px 5px;
    width: 50px;
    height: 50px;
    border-radius: 50px;
    cursor: pointer;
}
.message_right {
    width: 200px;
    float: right;
    padding-top: 3px;
}
.message_right p {
    margin-left: 5px;
    margin-right: 5px;
    margin-bottom: 5px;
    margin-top: -1px;
    font-size: 12px;
    line-height: 15px;
    overflow: hidden;
}
.message_right a {
    cursor: pointer;
    font-size: 12px;
    color: #3472ef;
}

/*   ------------------ 友情链接 ---------------------  */
#content_link {
    width: 100%;
    height: 200px;
    margin-top: 15px;
}
#content_link h3 {
    line-height: 40px;
    color: #38485a;
    font-size: 18px;
    border-bottom: 1px solid #ff3300;
    text-align: center;
    height: 40px;
    margin-bottom: 5px;
    font-weight: 500;
}
#content_link p {
    margin: 0 auto;
    font-size: 15px;
    cursor: pointer;
    line-height: 22px;
    margin-left: 4px;
}
#content_link p a {
    color: #3472ef;
}
</style>