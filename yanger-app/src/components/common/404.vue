<template>
    <div class="error-page">
        <el-row>
            <div class="error-desc">您访问的页面消失在遥远的二次元</div>
        </el-row>
        <el-row>
            <div><img src="static/img/blog/404.jpg"></div>
        </el-row>
        <div class="error-handle">
            <router-link to="/">
                <el-button type="primary" size="large">返回首页</el-button>
            </router-link>
            <el-button class="error-btn" type="primary" size="large" @click="goBack">返回上一页</el-button>
        </div>
    </div>
</template>

<script>
    export default {
        methods: {
            goBack(){
                this.$router.go(-1);
            }
        }
    }
</script>


<style scoped>
    .error-page{
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        width: 100%;
        height: 100%;
        background: #f3f3f3;
        box-sizing: border-box;
    }
    img {
        margin-top: 30px;
        height: 350px;
    }
    .error-code{
        line-height: 1;
        font-size: 250px;
        font-weight: bolder;
        color: #2d8cf0;
    }
    .error-code span{
        color: #00a854;
    }
    .error-desc{
        margin-top: 150px;
        font-size: 26px;
        color: #777;
    }
    .error-handle{
        margin-top: 30px;
        padding-bottom: 200px;
    }
    .error-btn{
        margin-left: 100px;
    }
</style>
