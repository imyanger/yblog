/**
 * @author YangHao
 * @description 文章列表底部公共区域
 */
<template>
    <p class="autor">
        <span class="dtime">
            <i class="el-icon-date"></i>
            <span class="dtime_c">{{createDate | formatDate}}</span>
        </span>
        <span class="agree" style="padding-left: 0;">
            <i class="el-icon-star-off"></i>
            <a href="javascript:void(0)">
                <span  class="agree_c">喜欢（{{likes}}）</span>
            </a>
        </span>
        <span class="pingl">
            <i class="el-icon-edit"></i>
            <a href="javascript:void(0)">
                <span class="pingl_c">评论（{{commons}}）</span>
            </a>
        </span>
        <span class="viewnum">
            <i class="el-icon-view"></i>
            <span class="viewnum_c">浏览（{{views}}）</span>
        </span>
    </p>
</template>

<script>
    import { formatDate } from 'static/js/date'; //date格式化
    export default {
        props: ['createDate', 'likes', 'commons', 'views'],
        filters: {
            formatDate(time) {
                var date = new Date(time);
                return formatDate(date, 'yyyy-MM-dd');
            }
        }
    }
</script>

<style scoped>
    .dtime {
        float: left;
    }
    .viewnum, .pingl, .agree {
    float: right;
    margin-right: 20px;
    }
    .dtime, .viewnum, .pingl, .agree span {
        font-size: 14px;
    }
    .pingl_c, .agree_c {
        color: #72afd2;
    }
</style>