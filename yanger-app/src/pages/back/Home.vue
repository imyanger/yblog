<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="8">
                <el-row id="user-info">
                    <el-col>
                        <el-card shadow="hover" >
                            <div class="user-info">
                                <img src="static/img/img.jpg" class="user-avator" alt="">
                                <div class="user-info-cont">
                                    <div class="user-info-name">{{name}}</div>
                                    <div>欢迎您</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
            </el-col>
            <el-col :span="16">
               <el-row :gutter="20" class="mgb20">
                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-1">
                                <i class="el-icon-view grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div>网站访问量</div>
                                    <div class="grid-num">1234</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-2">
                                <i class="el-icon-tickets grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div>文章数量</div>
                                    <div class="grid-num">321</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-3">
                                <i class="el-icon-user grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div>用户数量</div>
                                    <div class="grid-num">5000</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
                <el-row :gutter="20" class="mgb20">
                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-4">
                                <i class="el-icon-data-line grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-5">
                                <i class="el-icon-message grid-con-icon"></i>
                                <div class="grid-cont-right">

                                </div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-6">
                                <i class="el-icon-money grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import { mapGetters} from 'vuex'; //vuex组件
    export default {
        data() {
            return {

            }
        },
        computed: {
            name() {
                let user = this.getBuser();
                return user.userNickName ? user.userNickName : '游客';
            }
        },
        methods: {
            ...mapGetters(['getBuser']),
        }
    }
</script>

<style scoped>
    .el-row {
        margin-bottom: 20px;
    }
    .grid-content {
        display: flex;
        align-items: center;
        height: 80px;
    }
    .grid-cont-right {
        flex: 1;
        text-align: center;
        font-size: 12px;
        color: #999;
    }
    .grid-num {
        font-size: 30px;
        font-weight: bold;
    }
    .grid-con-icon {
        font-size: 50px;
        width: 100px;
        height: 100px;
        text-align: center;
        line-height: 100px;
        color: #fff;
    }
    .grid-con-1 .grid-con-icon {
        background: rgb(45, 140, 240);
    }
    .grid-con-1 .grid-num {
        color: rgb(45, 140, 240);
    }
    .grid-con-2 .grid-con-icon {
        background: rgb(100, 213, 114);
    }
    .grid-con-2 .grid-num {
        color: rgb(100, 213, 114);
    }
    .grid-con-3 .grid-con-icon {
        background: rgb(242, 94, 67);
    }
    .grid-con-3 .grid-num {
        color: rgb(242, 94, 67);
    }
    .grid-con-4 .grid-con-icon {
        background: rgba(145, 43, 171, 0.78)
    }
    .grid-con-4 .grid-num {
        color: rgba(145, 43, 171, 0.78)
    }
    .grid-con-5 .grid-con-icon {
        background: rgb(245, 83, 113);
    }
    .grid-con-5 .grid-num {
        color: rgb(245, 83, 113);
    }
    .grid-con-6 .grid-con-icon {
        background: rgb(73, 200, 239);
    }
    .grid-con-6 .grid-num {
        color: rgb(73, 200, 239);
    }
    .user-info {
        display: flex;
        align-items: center;
        padding-bottom: 20px;
        border-bottom: 2px solid #ccc;
        margin-bottom: 20px;
    }
    .user-avator {
        width: 120px;
        height: 120px;
        border-radius: 50%;
    }
    .user-info-cont {
        padding-left: 20px;
        flex: 1;
        font-size: 14px;
        color: #999;
        text-align: center;
        line-height: 40px;
    }
    .user-info-cont div:first-child {
        font-size: 23px;
        color: #222;
    }
    .mgb20 {
        margin-bottom: 20px;
    }
    .todo-item {
        font-size: 14px;
    }
    .todo-item-del {
        text-decoration: line-through;
        color: #999;
    }
    .operate i {
        font-size: 14px;
        cursor: pointer;
        margin-left: 15px;
    }
</style>
<style>
    #user-info .el-card__body {
        padding-top: 20px;
        padding-left: 20px;
        padding-right: 20px;
        padding-bottom: 0px;
    }
</style>
