<template>
    <div class="table">
        <div class="container">
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
                   
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination @current-change="handleCurrentChange" :page-sizes="[10, 20, 30, 50]" @size-change="handleSizeChange"
                    :current-page.sync="constList.page.pageNo" :page-size="constList.page.pageSize" 
                    layout="total, sizes, jumper, prev, pager, next" :total="constList.page.totalCount">
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
                constList: {
                    page: {},
                    queryValue: ''
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
