<template>
    <div>
        <div class="container">
            <div class="schart-box">
                <schart class="schart"  canvasId="line" :data="data.dateSums" type="line" :options="options2"></schart>
            </div>
            <div class="schart-box">
                <div class="content-title">
                    <schart class="schart" canvasId="bar" :data="data.articleKindVos" type="bar" :options="options1"></schart>
                </div>
            </div>
            <div class="schart-box">
                <schart class="schart" canvasId="pie" :data="data.moduleSums" type="pie" :options="options3"></schart>
            </div>
        </div>
    </div>
</template>

<script>
    import Schart from 'vue-schart';
    export default {
        components: {
            Schart
        },
        data: () => ({
            data: {
                dateSums: [],
                articleKindVos: [],
                moduleSums: []
            },
            options1: {
                title: '发表时间-文章数量统计',
                bgColor: '#D5E4EB',
                titleColor: '#00887C',
                fillColor: 'red',
                contentColor: 'rgba(46,199,201,0.3)'
            },
            options2: {
                title: '文章分类-数量统计',
                autoWidth: true,   // 设置宽高自适应
                showValue: true,
                bgColor: '#F9EFCC',
                fillColor: '#00887C',
                contentColor: 'rgba(46,199,201,0.3)',
                yEqual: 7
            },
            options3: {
                title: '文章模块-数量统计',
                bgColor: '#829dca',
                titleColor: '#ffffff',
                legendColor: '#ffffff',
                radius: 100
            }
        }),
        created(){
            let _this = this;
            //获取下拉选项的值
            this.$get("/kind/summary")
            .then(function (response) {
                response.data.dateSums.forEach(dateSum => {
                    _this.data.dateSums.push({
                        name: dateSum.artDate,
                        value: dateSum.sum
                    })
                });
                response.data.articleKindVos.forEach(articleKindVo => {
                    _this.data.articleKindVos.push({
                        name: articleKindVo.typeVal + '-' + articleKindVo.classifyVal,
                        value: articleKindVo.sum
                    })
                });
                response.data.moduleSums.forEach(moduleSum => {
                    _this.data.moduleSums.push({
                        name: moduleSum.module + '（' + moduleSum.sum + '）',
                        value: moduleSum.sum
                    })
                });
            })
            .catch(function (error) {
                console.log(error);
            });
        }
    }
</script>

<style scoped>
    .schart-box{
        display: inline-block;
        margin: 20px;
    }
    .schart{
        width: 1000px;
        height: 300px;
    }
    .content-title{
        clear: both;
        font-weight: 400;
        line-height: 50px;
        margin: 10px 0;
        font-size: 22px;
        color: #1f2f3d;
    }
</style>