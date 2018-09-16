<template>
    <div class="content_aboutMe">
        <h3>About Me</h3>
        <div class="aboutMe_left">
            <img alt="" src="static/img/img.jpg">
        </div>
        <div class="aboutMe_right">
            <p>姓名：杨号</p>
            <p>性别：男</p>
            <p>职业：java工程师</p>
            <p>现居：北京</p>
            <p>爱好：阅读，编程，写作</p>
        </div>
        <div id="about_me_follow">
            <img id="about_me_sinaweb" alt="" src="static/img/blog/sinaweb.png">
            <img id="about_me_weichat" alt="" src="static/img/blog/weichat.png">
            <img id="about_me_csdn" alt="" src="static/img/blog/csdn.png">
            <!-- 引入微信公众号关注的模态框 -->
           
        </div>
    </div>
</template>
<style scoped>
    .content_aboutMe {
        width: 100%;
        height: 250px;
        margin-bottom: 20px;
    }
    .aboutMe_left {
        width: 90px;
        height: 120px;
        float: left;
        margin: 5px 0 10px 10px;
    }
    .aboutMe_left img {
        width: 90px;
        height: 120px;
    }
    .content_aboutMe h3 {
        line-height: 40px;
        color: #38485a;
        font-size: 18px;
        border-bottom: 1px solid #ff3300;
        text-align: center;
        height: 40px;
        font-weight: 500;
    }
    .aboutMe_right {
        height: 120px;
        width: 160px;
        float: right;
        margin: 5px 5px 0 10px;
    }
    .aboutMe_right p {
        line-height: 16px;
        display: block;
        -webkit-margin-start: 0px;
        -webkit-margin-end: 0px;
        font-size: 14px;
        line-height: 24px;
    }
    #about_me_follow {
        width: 280px;
        height: 65px;
        margin-top: 150px;
    }
    #about_me_sinaweb, #about_me_weichat {
        width: 50px;
        height: 50px;
        float: left;
        margin-left: 33px;
        cursor: pointer;
    }
    #about_me_csdn {
        width: 42px;
        height: 42px;
        float: left;
        margin-left: 33px;
        cursor: pointer;
    }
</style>
