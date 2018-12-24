<template>
    <div>
        <div class="crop-img">
            <img :src="cropImg" class="pre-img" :width="width" :height="height">
        </div>
        <el-dialog title="裁剪图片" :visible.sync="dialogVisible" width="30%">
            <vue-cropper ref='cropper' :src="imgSrc" :ready="cropImage" :zoom="cropImage" :cropmove="cropImage" style="width:100%;height:300px;"></vue-cropper>
            <span slot="footer" class="dialog-footer">
                <el-button @click="cancelCrop">取 消</el-button>
                <el-button type="primary" @click="sureCrop">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import VueCropper  from 'vue-cropperjs';
    export default {
        data: function(){
            return {
                imgSrc: '',
                cropImg: '',
                dialogVisible: false,
                fileName: '',
                // 记录上一次的图片信息
                lastImg: ''
            }
        },
        props: {
            // 上传请求路径
            url: {
                type: String
            },
            // 默认展示图片路径
            defaultSrc: {
                type: String
            },
            // 宽
            height: {
                 type: String
            },
            // 高
            width: {
                 type: String
            },
            // 是否自动上传
            autoUp: {
                type: Boolean
            }
        },
        components: {
            VueCropper
        },
        methods:{
            //开始裁剪图片
            setImage(file){
                //改为从外部父组件调用
                /* const file = e.target.files[0];
                if (!file.type.includes('image/')) {
                    return;
                } */
                //记录上一次图片路径
                if(this.cropImg){
                    this.lastImg = this.cropImg;
                }
                const reader = new FileReader();
                reader.onload = (event) => {
                    this.dialogVisible = true;
                    this.imgSrc = event.target.result;
                    this.$refs.cropper && this.$refs.cropper.replace(event.target.result);
                };
                this.fileName = file.name;
                //将图片转化为base64
                reader.readAsDataURL(file);
            },
            // 展示裁剪图片
            cropImage () {
                this.cropImg = this.$refs.cropper.getCroppedCanvas().toDataURL();
            },
            // 确定裁剪
            sureCrop() {
                this.dialogVisible = false;
                // 自动上传
                if(this.autoUp){
                    // 上传图片
                    this.imageuploaded();
                }
            },
            // 取消裁剪
            cancelCrop() {
                this.dialogVisible = false;
                // 展示上一次裁剪图片
                this.cropImg = this.lastImg;
            },
            // 设置图片
            setcropImg(img) {
                this.cropImg = img;
                this.lastImg = img;
            },
            // 图片上传
            imageuploaded() {
                let _this = this;
                //base64转化为file
                let file = _this.convertBase64UrlToBlob(this.cropImg);
                file.name = _this.fileName;
                // 接收上传文件的后台地址
                let FileController = _this.url;
                // FormData 对象
                let form = new FormData();
                // 文件对象
                form.append("fileData", file, file.name);
                // 其他参数
                //form.append("xxx", xxx);
                // XMLHttpRequest 对象
                let xhr = new XMLHttpRequest();
                xhr.open("post", FileController, true);
                //监听上传进度
                //xhr.upload.addEventListener("progress", _this.progressFunction, false);
                xhr.onload = function () {
                    //上传成功
                    _this.$emit('loadSuccess', xhr.response);
                };
                xhr.send(form);
            },
            // 将base64的图片转换为file文件
            convertBase64UrlToBlob(urlData) {
                //去掉url的头，并转换为byte
                let bytes = window.atob(urlData.split(',')[1]);
                //处理异常,将ascii码小于0的转换为大于0
                let ab = new ArrayBuffer(bytes.length);
                let ia = new Uint8Array(ab);
                for (var i = 0; i < bytes.length; i++) {
                    ia[i] = bytes.charCodeAt(i);
                }
                return new Blob([ab], { type: 'image/jpeg' });
            },
        },
        created(){
            // 初始化上一个为默认值
            this.lastImg = this.defaultSrc;
            this.cropImg = this.lastImg;
        }
    }
</script>

<style scoped>
    .pre-img {
        margin: 0 auto;
        background: #f8f8f8;
        border: 1px solid #eee;
        border-radius: 5px;
    }
    .crop-img {
        display: flex;
        align-items: flex-end;
    }
</style>