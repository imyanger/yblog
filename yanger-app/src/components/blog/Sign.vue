/**
 * @author YangHao
 * @description 博客用户登录
 */
<template>
    <el-row>
        <div  id="user_info">
            <h3>用户信息 </h3>
            <div>
                <center v-if="!sginData.user.userNickName">
                    <span id="header_msg">亲爱的游客，您还未登录</span><br/>
                    <span @click="openSignDialog" id="openSignDialog">点此快速登录</span><br/>
                    <span id="find_pwd" @click="findPwd">忘记密码，点击找回</span><br/>
                </center>
                <div v-if="sginData.user.userNickName">
                    <div id="info_left">
                        <!-- <img alt="" :src="sginData.user.userImgPath"> -->
                        <img alt="" src="static/img/img.jpg">
                    </div>
                    <div id="info_right">
                        <span id="header_msg">亲爱的{{sginData.user.userNickName}}，欢迎您</span><br/>
                        <span @click="clearUserInfo" id="openSignDialog">[退出]</span><br/>
                    </div>
                </div>
            </div>
			<el-dialog :visible.sync="sginData.dialogVisible" :close-on-click-modal="false" width="450px">
				<el-row id="sign">
					<center>
						<span>{{sginData.signTitle}}</span>
					</center>
				</el-row>
				<el-row id="login_row" v-if="sginData.isLogin">
                    <center>
                        <el-input placeholder="请输入登录账号|手机号|邮箱" prefix-icon="el-icon-info" 
                            v-model="sginData.loginData.userCode" maxlength="20">
                        </el-input>
                        <br/><br/>
                        <el-input placeholder="请输入登录密码" prefix-icon="el-icon-sold-out" type="password" maxlength="20" 
                            v-model="sginData.loginData.password">
                        </el-input>
                        <br/><br/>
                        <drag v-on:dragOk = 'dealDragOk'></drag>
                    </center>
				</el-row>
                <el-row id="register_row" v-if="sginData.isLogin === false">
                    <center>
                        <el-input placeholder="请输入注册账号    6-20字母或数字" prefix-icon="el-icon-info" 
                            v-model="sginData.registerData.userCode" maxlength="20" 
                            :suffix-icon="sginData.registerIconData.userCode" 
                            @change="reCgUserCode(sginData.registerData.userCode)">
                        </el-input>
                        <el-input placeholder="请输入注册密码    6-20字母或数字" prefix-icon="el-icon-sold-out" type='password' 
                            v-model="sginData.registerData.password" maxlength="20" 
                            :suffix-icon="sginData.registerIconData.password" 
                            @change="reCgPassword(sginData.registerData.password)">
                        </el-input>
                        <el-input placeholder="请输入确认密码    6-20字母或数字" prefix-icon="el-icon-goods" type='password' 
                            v-model="sginData.registerData.password2" maxlength="20" 
                            :suffix-icon="sginData.registerIconData.password2"
                            @change="reCgPassword2(sginData.registerData.password2)">
                        </el-input>
                        <el-input placeholder="请输入用户昵称    3-12非特殊字符" prefix-icon="el-icon-service" 
                            v-model="sginData.registerData.userNickName" maxlength="12" 
                            :suffix-icon="sginData.registerIconData.userNickName"
                            @change="reCgUserNickName(sginData.registerData.userNickName)">
                        </el-input>
                        <el-input placeholder="请输入邮箱地址" prefix-icon="el-icon-message" 
                            v-model="sginData.registerData.email" maxlength="30" 
                            :suffix-icon="sginData.registerIconData.email" 
                            @change="reCgEmail(sginData.registerData.email)">
                        </el-input>
                        <el-input placeholder="请输入手机号码" prefix-icon="el-icon-phone-outline" 
                            v-model="sginData.registerData.moblie" maxlength="11" 
                            :suffix-icon="sginData.registerIconData.moblie" 
                            @change="reCgMoblie(sginData.registerData.moblie)">
                        </el-input>
                    </center>
				</el-row>
                <el-row id="error_msg">
                    <span>{{sginData.errorMsg}}</span>
                </el-row>
				<div slot="footer" class="dialog-footer">
                    <el-row>
                        <div class="footer_left">
                            <span id="sign_change" @click="signChange(sginData.isLogin)">{{sginData.signTips}}</span>
                        </div>
                        <div class="footer_right">
                            <el-button type="primary" :loading="sginData.addLoading" @click="signSubmit(sginData.signTitle)">{{sginData.signTitle}}</el-button>
                        </div>
                    </el-row>
                    <center>
                    </center>
				</div>
			</el-dialog>
        </div>
    </el-row>
</template>

<script>
    import drag from "./Drag"; //列表文章底部部分
    import simpleValidate from 'static/js/simpleValidate'; //date格式化
    import { mapGetters, mapMutations } from 'vuex'; //vuex组件
    const el_icon_error = 'el-icon-warning';
    const el_icon_success = 'el-icon-success';
    export default {
        props: [],
        data() {
            return {
                sginData: {
                    dialogVisible: false, //模态框是否显示
                    addLoading: false, //是否显示loading
                    signTips: "没有账号，快速注册",
                    signTitle: "登录",
                    isLogin: true, //是否加载登录区域
                    isDragOk: false, // 登录滑块时候解锁
                    loginData: {},
                    registerData: {},
                    registerIconData: {
                        //动态绑定class必须初始化声明
                        userCode: '',
                        password: '',
                        password2: '',
                        userNickName: '',
                        email: '',
                        moblie: ''
                    },
                    user: {},
                    errorMsg: ""
                }
            };
        },
        created(){
            console.log(this.getUser())
            //获取登录信息
            let user = this.getUser();
            if(user.userNickName){
                this.sginData.user = user;
            }
        },
        methods: {
            ...mapGetters(['getUser']),
            ...mapMutations(['setUser', 'clearUser']),
            //打开模态框
            openSignDialog() {
                this.sginData.dialogVisible = true;
            },
            //登录注册切换
            signChange(type) {
                this.sginData.isLogin = !this.sginData.isLogin;
                if (this.sginData.isLogin) {
                    this.sginData.signTips = "没有账号，快速注册";
                    this.sginData.signTitle = "登录";
                } else {
                    this.sginData.signTips = "已有账号，快速登录";
                    this.sginData.signTitle = "注册";
                    //切换后滑块解锁需要重新加载
                    this.isDragOk = false;
                }
                //清空提示语
                this.sginData.errorMsg = "";
            },
            //找回密码
            findPwd() {
                alert("找回密码");
            },
            //注销用户
            clearUserInfo(){
                this.sginData.user = {};
                localStorage.removeItem('$token');
                this.clearUser();
            },
            //接受滑动解锁成功的回调
            dealDragOk(dragRes) {
                this.isDragOk = dragRes;
            },
            //用户登录
            signSubmit(title){
                let _this = this;
                if('登录' === title){
                    let loginMsg = "";
                    //校验录入
                    if(!this.sginData.loginData.userCode){
                        loginMsg = "请输入登录账号|手机号|邮箱";
                    }else if(!this.sginData.loginData.password){
                        loginMsg = "请输入登录密码";
                    }else if(!this.isDragOk){
                        loginMsg = "请拖动滑块验证";
                    }
                    if(loginMsg){
                        this.sginData.errorMsg = loginMsg;
                    }else{
                        this.sginData.errorMsg = "";
                        //进行登录
                        this.$post("/blog/login", this.sginData.loginData)
                        .then(function (response) {
                            if(response.status === 0){
                                //关闭模态框
                                _this.sginData.dialogVisible = false;
                                //渲染用户信息
                                _this.sginData.user.userNickName = response.data.userNickName;
                                _this.sginData.user.userImgPath = response.data.userImgPath;
                                _this.sginData.user.userId = response.data.userId;
                                _this.sginData.user.userCode = response.data.userCode;
                                //将登录信息放入vuex中
                                _this.setUser(_this.sginData.user);
                            }else {
                               _this.sginData.errorMsg = response.msg;
                            }
                        })
                        .catch(function (error) {
                            console.log(error);
                        });
                    }

                }else if('注册' === title){
                    let msg = _this.validate();
                    if(!msg){
                        //进行提交
                        this.$post("/blog/register", this.sginData.registerData)
                        .then(function (response) {
                            if(response.status === 0){
                                _this.$alert('注册成功', "提示", {
                                    confirmButtonText: '确定',
                                    callback: action => {
                                       //清空数据
                                       _this.sginData.registerData = {
                                           icon: {
                                                userCode: '',
                                                password: '',
                                                password2: '',
                                                userNickName: '',
                                                email: '',
                                                moblie: ''
                                            }
                                       };
                                       //切换到登录页面
                                       _this.signChange("登录");
                                    }
                                });
                            }else {
                                 _this.$alert(response.msg, "提示", {confirmButtonText: '确定',});
                            }
                        })
                        .catch(function (error) {
                            console.log(error);
                        });
                    }
                }
            },
            //注册数据校验
            validate(){
                let msg = "请输入";
                //校验是否全部必录
                if(!this.sginData.registerData.userCode){
                    msg += "注册账号";
                }else if(!this.sginData.registerData.password){
                    msg += "注册密码";
                }else if(!this.sginData.registerData.password2){
                    msg += "确认密码";
                }else if(!this.sginData.registerData.userNickName){
                    msg += "用户昵称";
                }else if(!this.sginData.registerData.email){
                    msg += "邮箱地址";
                }else if(!this.sginData.registerData.moblie){
                    msg += "手机号码";
                }
                if(msg.length === 3){
                    //检查是否全部录入正确
                    if(el_icon_success !== this.sginData.registerIconData.userCode){
                        msg += "正确的注册账号";
                    }else if(el_icon_success !== this.sginData.registerIconData.password){
                        msg += "正确的注册密码";
                    }else if(el_icon_success !== this.sginData.registerIconData.password2){
                        msg += "正确的确认密码";
                    }else if(el_icon_success !== this.sginData.registerIconData.userNickName){
                        msg += "正确的用户昵称";
                    }else if(el_icon_success !== this.sginData.registerIconData.email){
                        msg += "正确的邮箱地址";
                    }else if(el_icon_success !== this.sginData.registerIconData.moblie){
                        msg += "正确的手机号码";
                    }
                    if(msg.length === 3){
                        msg = "";
                    }else {
                        this.sginData.errorMsg = msg;
                    }
                }else {
                    this.sginData.errorMsg = msg;
                }
                return msg;
            },
            //注册账号校验
            reCgUserCode(val){
                let f = simpleValidate.isUsername(val);
                if(f){
                    this.sginData.registerIconData.userCode = el_icon_success;
                    this.sginData.errorMsg = "";
                }else {
                    this.sginData.registerIconData.userCode = el_icon_error;
                    this.sginData.errorMsg = "注册账号输入错误，请使用6-20字母或数字";
                }
            },
            //注册密码校验
            reCgPassword(val){
                let f = simpleValidate.isPassword(val);
                if(f){
                    //校验两次密码是否一致
                    if(this.sginData.registerData.password2 && val !== this.sginData.registerData.password2){
                        this.sginData.registerIconData.password = el_icon_error;
                        this.sginData.errorMsg = "注册密码输入错误，两次密码不一致";
                    }else{
                        this.sginData.registerIconData.password = el_icon_success;
                        this.sginData.errorMsg = "";
                        if(this.sginData.registerData.password2){
                            this.sginData.registerIconData.password2 = el_icon_success;
                        }
                    }
                }else {
                    this.sginData.registerIconData.password = el_icon_error;
                    this.sginData.errorMsg = "注册密码输入错误，请使用6-20字母或数字";
                }
            },
            //注册重复密码校验
            reCgPassword2(val){
                let f = simpleValidate.isPassword(val);
                if(f){
                    if(this.sginData.registerData.password && val !== this.sginData.registerData.password){
                        this.sginData.registerIconData.password2 = el_icon_error;
                        this.sginData.errorMsg = "确认密码输入错误，两次密码不一致";
                    }else {
                        this.sginData.registerIconData.password2 = el_icon_success;
                        this.sginData.errorMsg = "";
                        if(this.sginData.registerData.password){
                            this.sginData.registerIconData.password = el_icon_success;
                        }
                    }
                }else {
                    this.sginData.registerIconData.password2 = el_icon_error;
                    this.sginData.errorMsg = "确认密码输入错误，请使用6-20字母或数字";
                }
            },
            //注册昵称校验
            reCgUserNickName(val){
                let f = simpleValidate.isNickName(val);
                if(f){
                    this.sginData.registerIconData.userNickName = el_icon_success;
                    this.sginData.errorMsg = "";
                }else {
                    this.sginData.registerIconData.userNickName = el_icon_error;
                    this.sginData.errorMsg = "用户昵称输入错误，请使用3-12非特殊字符";
                }
            },
            //注册邮箱校验
            reCgEmail(val){
                let f = simpleValidate.isEmail(val);
                if(f){
                    this.sginData.registerIconData.email = el_icon_success;
                    this.sginData.errorMsg = "";
                }else {
                    this.sginData.registerIconData.email = el_icon_error;
                    this.sginData.errorMsg = "邮箱格式错误";
                }
            },
            //注册手机号校验
            reCgMoblie(val){
                let f = simpleValidate.isTelphone(val);
                if(f){
                    this.sginData.registerIconData.moblie = el_icon_success;
                    this.sginData.errorMsg = "";
                }else {
                    this.sginData.registerIconData.moblie = el_icon_error;
                    this.sginData.errorMsg = "手机号格式错误";
                }
            },
        },
        components: {
            drag
        },
    };
</script>

<style scoped>
    #user_info h3 {
        line-height: 40px;
        color: #38485a;
        font-size: 18px;
        border-bottom: 1px solid #ff3300;
        text-align: center;
        height: 40px;
        margin-bottom: 16px;
        font-weight: 500;
    }
    #header_msg {
        color: #ab6a8a;
        cursor: auto;
        font-size: 15px;
        line-height: 27px;
    }
    #user_info #openSignDialog,
    #find_pwd {
        color: #72afd2;
        cursor: pointer;
        font-size: 15px;
        line-height: 27px;
    }
    .dialog-footer {
        color: #72afd2;
        cursor: pointer;
        font-size: 14px;
        line-height: 30px;
    }
    #sign {
        margin-top: -20px;
        margin-bottom: 20px;
    }
    #sign span {
        font-size: 18px;
    }
    #login_row .el-input {
        width: 330px;
    }
    #register_row .el-input {
        width: 330px;
        line-height: 45px;
    }
    .footer_left {
        width: 200px;
        float: left;
    }
    .footer_right {
        width: 200px;
        float: right;
    }
    .footer_right button {
        margin-right: 80px;
    }
    #error_msg {
        text-align: center;
        padding-top: 15px;
        margin-bottom: -25px;
    }
    #error_msg span {
        color: #e03131;
    }
    #info_left {
        float: left;
        margin-left: 30px;
    }
    #info_left img {
        margin: 5px 5px 5px 5px;
        width: 50px;
        height: 50px;
        border-radius: 50px;
        cursor: pointer;
    }
    #info_right {
        float: right;
        width: 180px;
    }
</style>

<style>
    #register_row .el-icon-warning {
        color: red;
    }
    #register_row .el-icon-success {
        color: #25c523 !important;
    }
</style>
