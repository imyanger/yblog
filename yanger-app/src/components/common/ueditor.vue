<template>
    <div>
        <script id="editor" type="text/plain"></script>
    </div>
</template>

<script>
    //引入ueditor组件
    import 'static/ueditor/ueditor.config.js';
    import 'static/ueditor/ueditor.all.min.js';
    import 'static/ueditor/lang/zh-cn/zh-cn.js';
    import 'static/ueditor/ueditor.parse.min.js';
    export default {
        name: 'UE',
        data () {
            return {
                editor: null
            }
        },
        props: {
            defaultMsg: {
                type: String
            },
            config: {
                type: Object
            }
        },
        mounted() {
            const _this = this;
            this.editor = UE.getEditor('editor', this.config); // 初始化UE
            this.editor.addListener("ready", function () {
                _this.editor.setContent(_this.defaultMsg); // 确保UE加载完成后，放入内容。
            });
        },
        methods: {
            // 获取html内容
            getContent() { 
                return this.editor.getContent();
            },
            // 获取纯文本内容
            getContentTxt() {
                return this.editor.getContentTxt();
            },
            // 隐藏编辑器
            setHide() {
                this.editor.setHide();
            },
            // 显示编辑器
            setShow() {
                this.editor.setShow();
            },
            //判断编辑器是否有内容
            hasContents() {
                this.editor.hasContents();
            },
            //清空内容
            clear() {
                this.editor.execCommand('cleardoc');
            }
        },
        destroyed() {
            this.editor.destroy();
        }
    }
</script>