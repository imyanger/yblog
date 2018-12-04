<template>
    <div>
        <el-row :gutter="20">
           <div class="editor-container">
                <UE :defaultMsg='msg' :config='config' ref="ue"></UE>
            </div>
        </el-row>
    </div>
</template>

<script>
    import UE from '@/components/blog/ueditor'; //ueditor富文本编辑器
    export default {
        data() {
            return {
                config: {
                    initialFrameHeight: 300,
                },
                msg: ''
            }
        },
        computed: {},
        components: {
            UE
        },
    }
</script>

<style scoped>
    .el-row {
        margin-bottom: 20px;
    }
    img {
            width: 100%;; /*图片自适应宽度*/
    }
    
</style>
