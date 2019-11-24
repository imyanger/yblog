<template>
    <div>
        <el-form ref="form" label-width="100px" :rules="rules" :model="artData" >
            <el-row :gutter="30">
                <el-col :span="18">
                    <div class='left_info'>
                        <el-row :gutter="0">
                            <el-col :span="12">
                                <el-form-item label="文章标题" prop="title">
                                    <el-input v-model="artData.title"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="检索关键词" prop="ruxWords">
                                    <el-input v-model="artData.ruxWords"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row :gutter="20">
                            <el-col :span="8">
                                <el-form-item label="文章模块" prop="module">
                                    <el-select placeholder="请选择" v-model="artData.module" 
                                        @change="typeChange(1, artData.module)">
                                        <el-option v-for="(type, index) in consts.modules" :key="index" 
                                            :label="type.val" :value="type.code"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="文章类型" prop="type">
                                    <el-select placeholder="请选择" v-model="artData.type" 
                                        @change="typeChange(2, artData.type)">
                                        <el-option v-for="(type, index) in consts.types" :key="index" 
                                            :label="type.val" :value="type.code"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="文章分类" prop="classify">
                                    <el-select placeholder="请选择" v-model="artData.classify">
                                        <el-option v-for="(type, index) in consts.classifys" :key="index" 
                                            :label="type.val" :value="type.code"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="文章简介" prop="summary">
                                        <el-input type="textarea" rows="5" v-model="artData.summary"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                </el-col>
                <el-col :span="6" :aria-rowspan="4">
                    <div class="right_info">
                        <el-row>
                            <el-col>
                                <upload ref="upload" :defaultSrc='uploadInfo.defaultSrc' :url="uploadInfo.uploadUrl" 
                                    width='135' height='130' :autoUp=true v-on:loadSuccess='loadSuccess'></upload>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col>
                                <div class="crop-demo-btn">
                                    选择图片
                                    <input class="crop-input" type="file" name="image" accept="image/*" @change="setImage"/>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </el-col>
            </el-row>
        </el-form>
        <el-row :gutter="20">
           <div class="editor-container">
                <UE :defaultMsg='artData.wordContent' :config='config' ref="ue"></UE>
            </div>
        </el-row>
        <el-row>
            <div class="art-btn">
                <el-button type="primary" icon="search" @click="reset">重置</el-button>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <el-button type="primary" icon="search" @click="saveArt('01')">保存</el-button>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <el-button type="primary" icon="search" @click="saveArt('02')">发表</el-button>
            </div>
        </el-row>
    </div>
</template>

<script>
    import UE from '@/components/common/ueditor'; //ueditor富文本编辑器
    import Upload from '@/components/common/upload';
    import axios from 'axios';
    export default {
        data() {
            return {
                config: {
                    // ueditor默认文本框大小
                    initialFrameHeight: 300,
                },
                artData: {
                    wordType: '1',
                    content: '',
                    wordContent: '',
                    module: '',
                    type: '',
                    classify: ''
                },
                imageUrl:'',
                uploadInfo: {
                    // 头像选择框默认展示图片
                    defaultSrc: 'static/img/blog/img-upload.jpg',
                    // 头像框图片上传接口
                    uploadUrl: '/api/core/file/fileUpload',
                    // 图片上传后的响应信息
                    response: {}
                },
                consts: {
                    modules: [],
                    types: [],
                    classifys: []
                },
                rules: {
                    title: [{ required: true, message: '请输入文章标题', trigger: 'blur' }],
                    ruxWords: [{ required: true, message: '请输入检索关键词', trigger: 'blur' }],
                    module: [{ required: true, message: '请选择文章模块', trigger: 'blur' }],
                    type: [{ required: true, message: '请选择文章类型', trigger: 'blur' }],
                    classify: [{ required: true, message: '请选择文章分类', trigger: 'blur' }],
                    summary: [{ required: true, message: '请输入文章简介', trigger: 'blur' }]
                }
            }
        },
        created() {
            this.getMtcs();
            this.getArt();
        },
        computed: {},
        components: {
            UE, Upload
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
                            _this.artData.type = '';
                            _this.consts.types = item.children;
                            _this.artData.classify = '';
                            _this.consts.classifys = [];
                        } else if(2 === type) {
                            _this.artData.classify = '';
                            _this.consts.classifys = item.children;
                        }
                    }
                });
            },
            //选择图片裁剪
            setImage(e){
                const file = e.target.files[0];
                if (!file.type.includes('image/')) {
                    return;
                }
                this.$refs.upload.setImage(file);
            },
            //上传图片
            uploadFile(){
                this.$refs.upload.imageuploaded();
            },
            //上传成功后回调方法
            loadSuccess(response){
                this.uploadInfo.response = JSON.parse(response);
            },
            //重置数据
            reset(){
                // 基础数据重置
                this.artData = {
                    wordType: '1',
                    content: '',
                    wordContent: '',
                    module: '',
                    type: '',
                    classify: ''
                }
                //ue重置
                this.$refs.ue.clear();
                // 重置选择的图片
                this.$refs.upload.setcropImg(this.uploadInfo.defaultSrc);
                // 上传成功状态重置
                this.uploadInfo.response = {};
            },
            // 保存文章
            saveArt(state){
                let _this = this;
                _this.$refs['form'].validate((valid) => {
                    if (valid) {
                        _this.artData.artState = state;
                        // 确认图片是否上传
                        if(this.uploadInfo.response.status === 0) {
                            // 图片路径
                            this.artData.artImgPath = this.uploadInfo.response.data;
                            // 文章内容
                            this.artData.content = this.$refs.ue.getContent();
                            // 文档原内容
                            this.artData.wordContent = this.$refs.ue.getContentTxt();
                            // 提交文章信息
                            this.$put("/art/add", this.artData)
                            .then(function (response) {
                                _this.$alert(state === '01' ? "文章保存成功" : "文章发表成功", "提示");
                                if(!_this.$route.query.key){
                                    _this.reset();
                                }
                            })
                            .catch(function (error) {
                                console.log(error);
                            });
                        }else {
                            this.$alert("请先上传图片", "提示");
                        }
                    } else {
                        return false;
                    }
                });
            },
            // 根据id获取文章信息
            getArt() {
                let articleId = this.$route.query.key;
                if(articleId){
                    let _this = this;
                    this.$get("/art/" + articleId)
                    .then(function (response) {
                        let data = response.data;
                        _this.artData = data;
                        let type = data.type;
                        let classify = data.classify;
                        if(type){
                            _this.typeChange(1, data.module);
                            _this.artData.type = type;
                            if(classify){
                                _this.typeChange(2, type);
                                _this.artData.classify = classify;
                            }
                        }
                        let imgUrl = data.serverPath + '/file/thumbImg?wh=150&path=' + data.artImgPath;
                        _this.$refs.upload.setcropImg(imgUrl);
                        // 图片上传状态
                        _this.uploadInfo.response.status = 0;
                        _this.uploadInfo.response.data = data.artImgPath;
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
                }
            }
        }
    }
</script>

<style scoped>
    .el-row .el-select {
        width: 100%;
    }
    .crop-demo-btn{
        position: relative;
        width: 120px;
        height: 35px;
        line-height: 40px;
        margin: 0 auto;
        margin-top: 12px;
        text-align: center;
        background-color: #409eff;
        color: #fff;
        font-size: 14px;
        border-radius: 4px;
        box-sizing: border-box;
    }
    .crop-input{
        position: absolute;
        width: 100px;
        height: 40px;
        left: 0;
        top: 0;
        opacity: 0;
        cursor: pointer;
    }
    .art-btn {
        text-align: center;
        margin-top: 15px;
    }
    .art-btn button {
        width: 80px;
    }
</style>
