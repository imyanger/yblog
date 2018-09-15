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
                    <span @click="openSignDialog">亲爱的游客，您还未登录</span><br/>
                    <span @click="openSignDialog">点此快速登录</span>
                </center>
            </div>
			<el-dialog :visible.sync="sginData.dialogVisible" :close-on-click-modal="false" width="450px">
				<el-row id="sign">
					<center>
						<span>登录</span>
					</center>
				</el-row>
				<el-row>
					<el-input placeholder="请输入内容以搜索" prefix-icon="el-icon-search"></el-input>
					<el-input placeholder="请输入内容以搜索" prefix-icon="el-icon-search"></el-input>
				</el-row>
				<div slot="footer" class="dialog-footer">
					<el-button @click.native="sginData.dialogVisible = false">取消</el-button>
					<el-button type="primary" :loading="sginData.addLoading">提交</el-button>
				</div>
			</el-dialog>
        </div>
    </el-row>
</template>

<script>
    export default {
		props: [],
		data() {
			return {
				sginData:{
					dialogVisible: false,     //模态框是否显示
					addLoading: false,       //是否显示loading
				}
			};
		},
		methods: {
			openSignDialog() {
				this.sginData.dialogVisible = true;
			}
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
	#user_info span {
		color: #72afd2;
		cursor: pointer;
		font-size: 15px;
    	line-height: 27px;
	}
	#sign {
		margin-top: -20px;
	}
	#sign span {
		font-size: 18px;
	}
</style>