<template>
    <div class="table">
        <div class="container">
            <el-button type="primary" icon="search" calss="add" @click="addConst">新增文章类型</el-button>
            <div class="handle-box">
                <el-input v-model="constList.queryValue" placeholder="描述搜索" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
            </div>
            <el-table :data="constList.page.data" border style="width: 100%" :row-style="{height:'15px'}" 
                :header-row-style="{'height': '40px'}" ref="multipleTable" stripe>
                <el-table-column prop="code" label="文章类型代码" sortable align='center'></el-table-column>
                <el-table-column prop="val" label="文章类型名称" align='center'></el-table-column>
                <el-table-column prop="module" label="所属模块" align='center'></el-table-column>
                <el-table-column prop="depict" label="文章类型描述" align='center'></el-table-column>
                <el-table-column prop="num" label="分类数量" sortable align='center'></el-table-column>
                <el-table-column prop="status" label="状态" :formatter="statusFormatter" align='center'></el-table-column>
                <el-table-column label="操作" align='center'>
                    <template slot-scope="scope">
                        <div class="operate">
                            <i class="el-icon-edit" title="修改" @click="editConst(scope.$index, scope.row)"></i>
                            <i class="el-icon-delete" title="删除" @click="delConst(scope.$index, scope.row)"></i>
                            <i class="el-icon-s-tools" title="编辑子集" @click="editSub(scope.$index, scope.row)"></i>
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
        <el-dialog :title="title" :visible.sync="editVisible" width="30%" :close-on-click-modal="false">
            <el-form ref="form" :model="constData" label-width="30%" :rules="rules">
                <el-form-item label="文章模块" prop="upperCode">
                    <el-select placeholder="文章模块" v-model="constData.upperCode"  class="handle-input mr10">
                        <el-option v-for="(module, index) in constList.modules" :key="index" :label="module.code + '-' + module.val" :value="module.code"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="文章类型代码" prop="code">
                    <el-input v-model="constData.code" class="handle-input mr10" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="文章类型名称" prop="val">
                    <el-input v-model="constData.val" class="handle-input mr10"></el-input>
                </el-form-item>
                <el-form-item label="文章类型描述" prop="depict">
                    <el-input v-model="constData.depict" class="handle-input mr10"></el-input>
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

        <!-- 文章分类处理 -->
        <el-dialog :title="'文章分类编辑（' + sub.oneData.code + '-' + sub.oneData.val + '）'" 
            :visible.sync="subVisible" width="60%"  :close-on-click-modal="false">
            <el-form ref="subForm" id="subForm" :model="constData" label-width="0px">
                <el-table :data="sub.subConst" border style="width: 100%" :row-style="{height:'10px'}" 
                    :header-row-style="{'height': '40px'}" ref="multipleTable">
                    <el-table-column label="文章分类代码">
                        <template slot-scope="scope">
                             <el-form-item>
                                <el-input v-model="scope.row.code" maxlength="10"></el-input>
                            </el-form-item>
                        </template>
                    </el-table-column>
                    <el-table-column label="文章分类名称">
                         <template slot-scope="scope">
                             <el-form-item>
                                <el-input v-model="scope.row.val"></el-input>
                            </el-form-item>
                        </template>
                    </el-table-column>
                    <el-table-column label="文章分类描述" width="250">
                         <template slot-scope="scope">
                             <el-form-item>
                                <el-input v-model="scope.row.depict"></el-input>
                            </el-form-item>
                        </template>
                    </el-table-column>
                    <el-table-column label="状态" width="120">
                         <template slot-scope="scope">
                             <el-form-item>
                                <el-select placeholder="状态" v-model="scope.row.status">
                                    <el-option key="1" label="有效" value="1"></el-option>
                                    <el-option key="2" label="无效" value="0"></el-option>
                                </el-select>
                            </el-form-item>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="100">
                        <template slot-scope="scope">
                            <i class="el-icon-circle-plus-outline iconbtn" @click="addRow(scope.$index, sub.subConst)"></i>
                            <i class="el-icon-circle-close iconbtn" @click="delRow(scope.$index, sub.subConst)"></i>
                        </template>
                    </el-table-column>
                </el-table>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="saveSubConst">保存</el-button>
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
                    queryValue: '',
                    modules: []
                },
                editVisible: false,
                subVisible: false,
                title: '',
                constData: {},
                rules: {
                    upperCode: [{ required: true, message: '请选择文章模块', trigger: 'blur' }],
                    code: [{ required: true, message: '请输入常量代码', trigger: 'blur' }],
                    val: [{ required: true, message: '请输入常量名称', trigger: 'blur' }],
                    depict: [{ required: true, message: '请输入常量描述', trigger: 'blur' }]
                },
                sub: {
                    oneData: {},
                    subConst: []
                }
            }
        },
        created() {
            this.getPage(1);
            this.getArtModule();
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
				this.$post("/const/art/list", {
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
                // 复制对象，避免放弃修改界面数据变化
                this.constData = Object.assign({}, row);
                this.editVisible = true;
            },
            //新增或者修改提交
            saveConst(){
                let _this = this;
                _this.$refs['form'].validate((valid) => {
                    if (valid) {
                        _this.$put("/const/add", _this.constData)
                        .then(function (response) {
                            _this.$alert("提交成功", "提示").then(function(){
                                _this.editVisible = false;
                                //刷新列表
                            })
                            _this.getPage(1, _this.constList.page.pageSize);
                        })
                        .catch(function (error) {
                            console.log(error);
                        });
                    } else {
                        return false;
                    }
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
            },
            // 获取文章模块
            getArtModule(){
                let _this = this;
				this.$get("/const/art/module")
				.then(function (response) {
					_this.constList.modules = response.data;
				})
				.catch(function (error) {
					console.log(error);
				});
            },
            editSub(index, row){
                this.subVisible = true;
                this.sub.oneData = row;
                let _this = this;
                if(row.num){
                    this.$get("/const/list/" + row.code)
                    .then(function (response) {
                        _this.sub.subConst = response.data;
                        if(!_this.sub.subConst){
                            _this.sub.subConst = [{}]
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
                }else {
                     _this.sub.subConst = [{
                         status: '1'
                     }]
                }
            },
            saveSubConst(){
                let flag = true; // 代码和名称必录
                let _this = this;
                _this.sub.subConst.forEach(item => {
                    if(!item.upperCode){
                        item.upperCode = _this.sub.oneData.code;
                    }
                    if(flag){
                        if(!(item.code && item.val)){
                            flag = false;
                        }
                    }
                });
                if(flag) {
                    this.$put("/const/adds", _this.sub.subConst)
                    .then(function (response) {
                       _this.$alert("保存分类成功", "提示")
                       .then(function(){
                           _this.search();
                           _this.subVisible = false;
                       })
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
                }else {
                    _this.$alert("每一类型代码和名称都必须录入", "提示");
                }

            },
            // 增加列
            addRow(index, rows) {
                rows.splice(index + 1, 0, {status: '1'});
            },
            // 移除列
            delRow(index, rows) {
                if (rows.length > 1) {
                    rows.splice(index, 1);
                }else {
                    rows[0].code = '';
                    rows[0].val = '';
                    rows[0].depict = '';
                }
            },
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
    .iconbtn {
        font-size: 22px;
        margin-left: 10px;
        cursor: pointer;
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
    #subForm {
        margin-top: -30px;
    }
    #subForm .el-form-item{
        margin-bottom: 0px !important;
    }
</style>
