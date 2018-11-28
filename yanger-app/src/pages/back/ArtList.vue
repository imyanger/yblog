<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-select v-model="artList.type" placeholder="文章类型" class="handle-select mr10">
                    <el-option key="1" label="2017年" value="2017"></el-option>
                    <el-option key="2" label="java相关" value="java"></el-option>
                </el-select>
                <el-select v-model="artList.classify" placeholder="文章分类" class="handle-select mr10">
                    <el-option key="1" label="随笔" value="1"></el-option>
                    <el-option key="2" label="春" value="spring"></el-option>
                </el-select>
                <el-input v-model="artList.queryValue" placeholder="筛选关键词" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
            </div>
            <el-table :data="artList.page.data" border style="width: 100%" :row-style="{height:'15px'}" 
                :header-row-style="{'height': '40px'}" ref="multipleTable" stripe>
                <el-table-column type="expand" label="详情">
                    <template slot-scope="props">
                        <el-form label-position="left" inline class="demo-table-expand">
                            <el-form-item label="作者：">
                                <span>{{ props.row.author }}</span>
                            </el-form-item>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <el-form-item label="关键字：">
                                <span>{{ props.row.ruxWords }}</span>
                            </el-form-item>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <el-form-item label="图片路径：">
                                <span>{{ props.row.artImgPath }}</span>
                            </el-form-item>
                            <br/>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="summary">{{ props.row.summary }}</span>
                        </el-form>
                    </template>
                </el-table-column>
                <el-table-column prop="title" label="标题"></el-table-column>
                <el-table-column prop="type" label="分类" sortable></el-table-column>
                <el-table-column prop="classify" label="类型"></el-table-column>
                <el-table-column prop="likes" label="喜爱" sortable></el-table-column>
                <el-table-column prop="views" label="浏览" sortable></el-table-column>
                <el-table-column prop="commons" label="评论" sortable></el-table-column>
                <el-table-column prop="updateTime" label="编辑时间" :formatter="dateFormatter" sortable width="130%"></el-table-column>
                <el-table-column prop="status" label="状态" sortable width="70%"></el-table-column>
                <el-table-column label="操作">
                   
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
        name: 'basetable',
        data() {
            return {
                artList: {
                    page: {},
                    queryValue: '',
                    type: '',
                    classify: ''
                }
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
</style>

<style>
    .el-table .cell {
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        white-space: normal;
        word-break: break-all;
        line-height: 16px;
    }
    .el-form-item--small .el-form-item__content, .el-form-item--small .el-form-item__label {
        line-height: 18px;
    }
</style>
