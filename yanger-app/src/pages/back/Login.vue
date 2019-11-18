<template>
    <div class="login-wrap">
        <div class="ms-title">博客后台管理系统</div>
        <div class="ms-login">
            <el-form :model="loginData" :rules="rules" ref="loginData" label-width="0px" class="demo-ruleForm">
                <el-form-item prop="userCode">
                    <el-input v-model="loginData.userCode" placeholder="请输入登录账号"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" placeholder="请输入登录密码" v-model="loginData.password" @keyup.enter.native="submitForm('ruleForm')"></el-input>
                </el-form-item>
                <el-form-item>
                    <p class="msg">{{errorMsg}}</p>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm('loginData')">登录</el-button>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script>
import { mapMutations } from 'vuex';
export default {
    data: function () {
        return {
            loginData: {
                userCode: '',
                password: ''
            },
            errorMsg: '',
            rules: {
                userCode: [
                    { required: true, message: '请输入登录账号', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入登录密码', trigger: 'blur' }
                ]
            }
        }
    },
    methods: {
        ...mapMutations(['setBuser']),
        submitForm(formName) {
            let _this = this;
            _this.errorMsg = '';
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    //进行登录
                    this.$post("/back/login", this.loginData)
                        .then(function (response) {
                            if (response.status === 0) {
                                //将登录信息放入vuex中
                                _this.setBuser(response.data);
                                _this.$router.push('/back/home');
                            } else {
                                _this.errorMsg = response.msg;
                            }
                        })
                        .catch(function (error) {
                            console.log(error);
                        });
                }
            });
        }
    }
}
</script>

<style scoped>
.login-wrap {
  position: relative;
  width: 100%;
  height: 100%;
  background-image: url(/static/img/login-bg.jpg);
  background-size: 100%;
}
.ms-title {
  position: absolute;
  top: 50%;
  width: 100%;
  margin-top: -230px;
  text-align: center;
  font-size: 30px;
  color: #534949;
}
.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 300px;
  height: 160px;
  margin: -150px 0 0 -190px;
  padding: 40px;
  border-radius: 5px;
  background: #fff;
}
.login-btn {
  text-align: center;
}
.login-btn button {
  width: 100%;
  height: 36px;
}
.msg {
  text-align: center;
  color: red;
  font-size: 15px;
  height: 20px;
}
</style>