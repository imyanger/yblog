/**
 * @author YangHao
 * @description 博客用户登录
 */
<template>
    <el-row>
        <div  id="user_info">
            <h3>用户信息 </h3>
            <div>
                <center>
                    <span id="header_msg">亲爱的游客，您还未登录</span><br/>
                    <span @click="openSignDialog" id="openSignDialog">点此快速登录</span><br/>
                    <span id="find_pwd" @click="findPwd">忘记密码，点击找回</span><br/>
                </center>
            </div>
			<el-dialog :visible.sync="sginData.dialogVisible" :close-on-click-modal="false" width="450px">
				<el-row id="sign">
					<center>
						<span>{{sginData.signTitle}}</span>
					</center>
				</el-row>
				<el-row id="login_row" v-if="sginData.isLogin">
                    <center>
                        <el-input placeholder="请输入登录账号" prefix-icon="el-icon-info"></el-input>
                        <br/><br/>
                        <el-input placeholder="请输入登录密码" prefix-icon="el-icon-sold-out"></el-input>
                        <br/><br/>
                        <drag v-on:dragOk = 'dealDragOk'></drag>
                    </center>
				</el-row>
                <el-row id="register_row" v-if="sginData.isLogin === false">
                    <center>
                        <el-input placeholder="请输入注册账号" prefix-icon="el-icon-info"></el-input>
                        <el-input placeholder="请输入注册密码" prefix-icon="el-icon-sold-out" type='password'></el-input>
                        <el-input placeholder="请再次输入密码" prefix-icon="el-icon-goods" type='password'></el-input>
                        <el-input placeholder="请输入用户昵称" prefix-icon="el-icon-service"></el-input>
                        <el-input placeholder="请输入邮箱地址" prefix-icon="el-icon-message"></el-input>
                        <el-input placeholder="请输入手机号码" prefix-icon="el-icon-phone-outline"></el-input>
                    </center>
				</el-row>
                <el-row>
                    <span>错误信息</span>
                </el-row>
				<div slot="footer" class="dialog-footer">
                    <el-row>
                        <div class="footer_left">
                            <span id="sign_change" @click="signChange(sginData.isLogin)">{{sginData.signTips}}</span>
                        </div>
                        <div class="footer_right">
                            <el-button type="primary" :loading="sginData.addLoading">{{sginData.signTitle}}</el-button>
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
    import drag from './Drag'; //列表文章底部部分
    export default {
		props: [],
		data() {
			return {
				sginData:{
					dialogVisible: false,     //模态框是否显示
                    addLoading: false,       //是否显示loading
                    signTips: '没有账号，快速注册',
                    signTitle: '登录',
                    isLogin: true, //是否加载登录区域
                    isDragOk: false // 登录滑块时候解锁
				}
			};
		},
		methods: {
			openSignDialog() {
                this.sginData.dialogVisible = true;
            },
            //登录注册切换
            signChange(type) {
                this.sginData.isLogin = !this.sginData.isLogin;
                if(this.sginData.isLogin){
                    this.sginData.signTips = '没有账号，快速注册';
                    this.sginData.signTitle = '登录';
                }else {
                    this.sginData.signTips = '已有账号，快速登录';
                    this.sginData.signTitle = '注册';
                    //切换后滑块解锁需要重新加载
                    this.isDragOk = false;
                }
            },
            //找回密码
            findPwd() {
                alert('找回密码')
            },
            //接受滑动解锁成功的回调
            dealDragOk(dragRes){
                this.isDragOk = dragRes;
            }
        },
        components: {
            drag
        }
    }
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
	#user_info #openSignDialog, #find_pwd {
		color: #72afd2;
		cursor: pointer;
		font-size: 15px;
    	line-height: 27px;
	}
    .dialog-footer {
        color: #72afd2;
		cursor: pointer;
		font-size: 15px;
    	line-height: 27px;
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
</style>