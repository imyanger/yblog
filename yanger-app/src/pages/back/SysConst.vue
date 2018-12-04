<template>
    <div class="table">
        <div class="container">
            <el-button type="primary" icon="search" calss="add" @click="addConst">新增常量</el-button>
            <div class="handle-box">
                <el-input v-model="constList.queryValue" placeholder="描述搜索" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
            </div>
            <el-table :data="constList.page.data" border style="width: 100%" :row-style="{height:'15px'}" 
                :header-row-style="{'height': '40px'}" ref="multipleTable" stripe>
                <el-table-column prop="depict" label="描述" sortable></el-table-column>
                 <el-table-column prop="code" label="常量代码" sortable></el-table-column>
                <el-table-column prop="val" label="常量值" sortable></el-table-column>
                <el-table-column prop="likes" label="插入时间" sortable :formatter="dateFormatter"></el-table-column>
                <el-table-column prop="views" label="更新时间" sortable :formatter="dateFormatter"></el-table-column>
                <el-table-column prop="status" label="状态" :formatter="statusFormatter"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <div class="operate">
                            <i class="el-icon-edit" title="修改" @click="editConst(scope.$index, scope.row)"></i>
                            <i class="el-icon-delete" title="删除" @click="delConst(scope.$index, scope.row)"></i>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination @current-change="handleCurrentChange" :page-sizes="[10, 20, 30, 50]" @size-change="handleSizeChange"
                    :current-page.sync="constList.page.pageNo" :page-size="constList.page.pageSize" 
                    layout="total, sizes, jumper, prev, pager, next" :total="constList.page.totalCount">
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog :title="title" :visible.sync="editVisible" width="30%">
            <el-form ref="form" :model="constData" label-width="30%">
                <el-form-item label="常量描述">
                    <el-input v-model="constData.depict" class="handle-input mr10"></el-input>
                </el-form-item>
                <el-form-item label="常量代码">
                    <el-input v-model="constData.code" class="handle-input mr10"></el-input>
                </el-form-item>
                <el-form-item label="常量值">
                    <el-input v-model="constData.val" class="handle-input mr10"></el-input>
                </el-form-item>
                <el-form-item label="状态">
                    <el-select placeholder="状态" v-model="constData.status"  class="handle-input mr10">
                        <el-option key="1" label="有效" value="1"></el-option>
                        <el-option key="2" label="无效" value="0"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="saveConst">提交</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import { formatDate } from 'static/js/date'; //date格式化
    export default {
        data() {
            return {
                constList: {
                    page: {},
                    queryValue: ''
                },
                editVisible: false,
                title: '',
                constData: {}
            }
        },
        created() {
            this.getPage(1);
        },
        computed: {
        },
        methods: {
            // 分页导航
            handleCurrentChange(val) {
                this.getPage(val, this.constList.page.pageSize);
            },
            handleSizeChange(val){
                this.getPage(1, val);
            },
           //获取分页数据
			getPage(pageNo, pageSize) {
				let _this = this;
				this.$post("/const/list", {
					pageNo: pageNo,
                    pageSize: pageSize,
					queryValue: _this.constList.queryValue
				})
				.then(function (response) {
					_this.constList.page = response.data;
				})
				.catch(function (error) {
					console.log(error);
				});
            },
            //搜索
            search() {
                this.getPage(1);
            },
            //时间格式化
            dateFormatter(row, column) {
                var date = new Date(row.updateTime);
                return formatDate(date, 'yyyy-MM-dd hh:mm');
            },
            statusFormatter(row, column){ 
                return row.status === "1" ? '有效' : '无效';
            },
            addConst(){
                this.title = '常量新增',
                this.constData = {};
                this.constData.status = "1";
                this.editVisible = true;
            },
            //编辑
            editConst(index, row){
                this.title = '常量修改',
                this.constData = row;
                this.editVisible = true;
            },
            //新增或者修改提交
            saveConst(){
                let _this = this;
                if(!_this.constData.depict || !_this.constData.code || !_this.constData.val){
                    _this.$alert("请填写完整", "提示");
                    return;
                }
				this.$put("/const/add", _this.constData)
				.then(function (response) {
                    _this.$alert("提交成功", "提示");
                    _this.editVisible = false;
                    //刷新列表
                    _this.getPage(1, _this.constList.page.pageSize);
				})
				.catch(function (error) {
					console.log(error);
				});
            },
            delConst(index, row){
                let _this = this;
				this.$del("/const/" + row.id)
				.then(function (response) {
                    _this.$alert("删除成功", "提示");
                    //刷新列表
                    _this.getPage(1, _this.constList.page.pageSize);
				})
				.catch(function (error) {
					console.log(error);
				});
            }
        }
    }
</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
        float: right;
    }
    .handle-select {
        width: 120px;
        margin-right: 10px;
    }
    .handle-input {
        width: 200px;
        display: inline-block;
        margin-right: 30px;
    }
    .del-dialog-cnt{
        font-size: 16px;
        text-align: center
    }
    .el-table .cell {
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        white-space: normal;
        word-break: break-all;
        line-height: 2px;
    }
    .summary {
        line-height: 20px;
    }
    .operate i {
        font-size: 14px;
        cursor: pointer;
        margin-left: 15px;
    }
</style>

<style>
    .el-table .cell {
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        white-space: normal;
        word-break: break-all;
        line-height: 16px;
    }
</style>
