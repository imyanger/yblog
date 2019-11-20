<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-input v-model="listData.queryValue" placeholder="登录账号 | 用户名 | 手机号" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
            </div>
            <el-table :data="listData.page.data" border style="width: 100%" :row-style="{height:'15px'}" 
                :header-row-style="{'height': '40px'}" ref="multipleTable" stripe>
                <el-table-column prop="userCode" label="账号" sortable></el-table-column>
                <el-table-column prop="userNickName" label="昵称" sortable></el-table-column>
                <el-table-column prop="moblie" label="手机号" sortable></el-table-column>
                <el-table-column prop="email" label="邮箱" sortable></el-table-column>
                <el-table-column prop="userType" label="类型" sortable :formatter="typeFormatter"></el-table-column>
                <el-table-column prop="gender" label="性别" sortable width="100"></el-table-column>
                <el-table-column prop="age" label="年龄" width="100"></el-table-column>
                <el-table-column label="操作" width="120">
                    <template slot-scope="scope">
                        <div class="operate">
                            <i class="el-icon-remove" title="禁用" @click="editConst(scope.$index, scope.row)"></i>
                            <i class="el-icon-delete" title="删除" @click="delConst(scope.$index, scope.row)"></i>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination @current-change="handleCurrentChange" :page-sizes="[10, 20, 30, 50]" @size-change="handleSizeChange"
                    :current-page.sync="listData.page.pageNo" :page-size="listData.page.pageSize" 
                    layout="total, sizes, jumper, prev, pager, next" :total="listData.page.totalCount">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
//import { formatDate } from 'static/js/date'; //date格式化
export default {
    components: {},
    data() {
        return {
            listData: {
                page: {},
                queryValue: ''
            }
        }
    },
    created() {
        this.getPage(1, 10);
    },
    computed: {},
    methods: {
        // 分页导航
        handleCurrentChange(val) {
            this.getPage(val, this.listData.page.pageSize);
        },
        handleSizeChange(val) {
            this.getPage(1, val);
        },
        //获取分页数据
        getPage(pageNo, pageSize) {
            let _this = this;
            this.$post("/user/list", {
                pageNo: pageNo,
                pageSize: pageSize,
                queryValue: _this.listData.queryValue
            })
            .then(function (response) {
                _this.listData.page = response.data;
            })
            .catch(function (error) {
                console.log(error);
            });
        },
        //搜索
        search() {
            this.getPage(1, this.listData.page.pageSize ? this.listData.page.pageSize : 10);
        },
        //禁用用户
        editConst(index, row){

        },
        delConst(index, row) {
            let _this = this;
            _this.$confirm("确定删除吗？").then(_ => {
                this.$del("/user/del/" + row.userId)
                    .then(function (response) {
                        _this.$alert("删除成功", "提示");
                        //刷新列表
                        _this.getPage(1, _this.listData.page.pageSize);
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            })
        },
        typeFormatter(row, column){ 
            return row.userType === '1' ? '门户用户' : '管理账号';
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
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
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
.operate span {
  color: #409eff;
}
</style>

<style>
body .el-table th.gutter{
    display: table-cell!important;
}
.el-table .cell {
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  white-space: normal;
  word-break: break-all;
  line-height: 16px;
}
</style>
