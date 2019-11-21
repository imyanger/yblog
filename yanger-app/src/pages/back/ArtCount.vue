<template>
    <div>
        <div class="container">
            <div class="schart-box">
                <schart class="schart"  canvasId="line" :data="data1" type="line" :options="options2"></schart>
            </div>
            <div class="schart-box">
                <div class="content-title">
                    <schart class="schart" canvasId="bar" :data="data1" type="bar" :options="options1"></schart>
                </div>
            </div>
            <div class="schart-box">
                <schart class="schart" canvasId="pie" :data="data2" type="pie" :options="options3"></schart>
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
            data1:[
                {name:'2012',value:1141},
                {name:'2013',value:1499},
                {name:'2014',value:2260},
                {name:'2015',value:1170},
                {name:'2016',value:970},
                {name:'2017',value:1450}
            ],
            data2 : [
                {name:'短袖',value:1200},
                {name:'休闲裤',value:1222},
                {name:'连衣裙',value:1283},
                {name:'外套',value:1314},
                {name:'羽绒服',value:2314}
            ],
            options1: {
                title: '某商店近年营业总额',
                autoWidth: true,   // 设置宽高自适应
                showValue: false,
                bgColor: '#F9EFCC',
                fillColor: '#00887C',
                contentColor: 'rgba(46,199,201,0.3)',
                yEqual: 7
            },
            options2: {
                title: '某商店近年营业总额',
                bgColor: '#D5E4EB',
                titleColor: '#00887C',
                fillColor: 'red',
                contentColor: 'rgba(46,199,201,0.3)'
            },
            options3: {
                title: '某商店各商品年度销量',
                bgColor: '#829dca',
                titleColor: '#ffffff',
                legendColor: '#ffffff',
                radius: 120
            }
        }),
        created(){
            let _this = this;
            //获取下拉选项的值
            this.$get("/kind/summary")
            .then(function (response) {
                console.log(response.data)
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