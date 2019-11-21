<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-select placeholder="文章模块" v-model="artList.module" class="handle-select mr10"
                    @change="typeChange(1, artList.module)">
                    <el-option v-for="(type, index) in consts.modules" :key="index" 
                        :label="type.val" :value="type.code"></el-option>
                </el-select>
                <el-select placeholder="文章类型" v-model="artList.type" class="handle-select mr10"
                    @change="typeChange(2, artList.type)">
                    <el-option v-for="(type, index) in consts.types" :key="index" 
                        :label="type.val" :value="type.code"></el-option>
                </el-select>
                <el-select placeholder="文章分类" v-model="artList.classify" class="handle-select mr10">
                    <el-option v-for="(type, index) in consts.classifys" :key="index" 
                        :label="type.val" :value="type.code"></el-option>
                </el-select>
                <el-input v-model="artList.queryValue" placeholder="筛选关键词" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
            </div>
            <el-table :data="artList.page.data" border style="width: 100%" :row-style="{height:'15px'}" 
                :header-row-style="{'height': '40px'}" ref="multipleTable" stripe>
                <el-table-column type="expand" label="详情">
                    <template slot-scope="props">
                        <el-form label-position="left" inline class="demo-table-expand">
                            <el-form-item>
                                <div class="art_img">
                                    <img alt="" :src="props.row.serverPath + '/file/thumbImg?wh=50&path=' + props.row.artImgPath">
                                </div>
                            </el-form-item>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <el-form-item label="作者：">
                                <span>{{ props.row.author }}</span>
                            </el-form-item>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <el-form-item label="文档编辑类型：">
                                <span>{{ props.row.wordType === '1' ? "UE编辑器" : 'Markdown' }}</span>
                            </el-form-item>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <el-form-item label="关键字：">
                                <span>{{ props.row.ruxWords }}</span>
                            </el-form-item>
                            <br/>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="summary">{{ props.row.summary }}</span>
                        </el-form>
                    </template>
                </el-table-column>
                <el-table-column prop="title" label="标题"></el-table-column>
                <el-table-column prop="moduleVal" label="模块"></el-table-column>
                <el-table-column prop="typeVal" label="分类"></el-table-column>
                <el-table-column prop="classifyVal" label="类型"></el-table-column>
                <el-table-column prop="likes" label="喜爱" width="60%"></el-table-column>
                <el-table-column prop="views" label="浏览" width="60%"></el-table-column>
                <el-table-column prop="commons" label="评论" width="60%"></el-table-column>
                <el-table-column prop="artState" label="文章状态" :formatter="statusFormatter" width="80%"></el-table-column>
                <el-table-column label="操作">
                   <template slot-scope="scope">
                        <div class="operate">
                            <i class="el-icon-edit" title="修改" @click="editOne(scope.row)"></i>
                            <i class="el-icon-s-promotion" title="发表" @click="updateState(scope.row, '02')" v-if='scope.row.artState === "01"'></i>
                            <i class="el-icon-document-delete" title="撤回" @click="updateState(scope.row, '01')" v-if='scope.row.artState === "02"'></i>
                            <i class="el-icon-delete" title="删除" @click="delOne(scope.row)"></i>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination @current-change="handleCurrentChange" :page-sizes="[10, 20, 30, 50]" @size-change="handleSizeChange"
                    :current-page.sync="artList.page.pageNo" :page-size="artList.page.pageSize" 
                    layout="total, sizes, jumper, prev, pager, next" :total="artList.page.totalCount">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
    import { formatDate } from 'static/js/date'; //date格式化
    export default {
        data() {
            return {
                artList: {
                    page: {},
                    queryValue: '',
                    type: '',
                    classify: '',
                },
                consts: {
                    modules: [],
                    types: [],
                    classifys: []
                }
            }
        },
        created() {
            this.search();
            this.getMtcs();
        },
        computed: {
        },
        methods: {
            // 获取文章类型选择下拉值
            getMtcs(){
                let _this = this;
                //获取下拉选项的值
                this.$get("/const/art/mtcs")
                .then(function (response) {
                    _this.consts.modules = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                });
            },
            // 分页导航
            handleCurrentChange(val) {
                this.getPage(val, this.artList.page.pageSize);
            },
            handleSizeChange(val){
                this.getPage(1, val);
            },
           //获取分页数据
			getPage(pageNo, pageSize) {
				let _this = this;
				this.$post("/art/list", {
					pageNo: pageNo,
                    pageSize: pageSize,
					queryValue: _this.artList.queryValue,
					type: _this.artList.type,
					classify: _this.artList.classify
				})
				.then(function (response) {
					_this.artList.page = response.data;
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
                return row.artState === "01" ? '暂存' : '已发表';
            },
            // 模块、类型、分类改变
            typeChange(type, val){
                let _this = this;
                let consts = [];
                if (1 === type) {
                    consts = _this.consts.modules;
                } else if(2 === type) {
                    consts = _this.consts.types;
                }
                consts.forEach(item => {
                    if(val === item.code){
                        if (1 === type) {
                            _this.artList.type = '';
                            _this.consts.types = item.children;
                            _this.artList.classify = '';
                            _this.consts.classifys = [];
                        } else if(2 === type) {
                            _this.artList.classify = '';
                            _this.consts.classifys = item.children;
                        }
                    }
                });
            },
            // 更新文章状态
            updateState(row, state){
                let _this = this;
                this.$put("/art/state/" + row.articleId + '/' + state)
                .then(function (response) {
                    _this.$alert(state === '01' ? "撤回成功" : "发表成功", "提示")
                    .then(function(){
                        _this.search();
                    })
                })
                .catch(function (error) {
                    console.log(error);
                });
            },
            delOne(row){
                let _this = this;
                this.$del("/art/" + row.articleId)
                .then(function (response) {
                    _this.$alert("删除成功", "提示")
                    .then(function(){
                        _this.search();
                    })
                })
                .catch(function (error) {
                    console.log(error);
                });
            },
            editOne(row){
                this.$router.push({
                    path: '/back/art/edit', 
                    query: {
                        key: row.articleId,
                        type: row.wordType
                    }
                })
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
    .art_img {

    }
    .iconbtn {
        font-size: 18px;
        margin-left: 10px;
        cursor: pointer;
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
