<template>
    <div>
        <el-form ref="form" label-width="80px">
            <el-row :gutter="30">
                <el-col :span="18">
                    <div class='left_info'>
                        <el-row :gutter="20">
                            <el-col :span="12">
                                <el-form-item label="文章标题">
                                    <el-input></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="关键字">
                                    <el-input></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row :gutter="20">
                            <el-col :span="8">
                                <el-form-item label="模块选择">
                                    <el-select placeholder="请选择" v-model="artData.module">
                                        <el-option key="bbk" label="步步高" value="bbk"></el-option>
                                        <el-option key="xtc" label="小天才" value="xtc"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="所属类型">
                                    <el-select placeholder="请选择" v-model="artData.module">
                                        <el-option key="bbk" label="步步高" value="bbk"></el-option>
                                        <el-option key="xtc" label="小天才" value="xtc"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="分类选择">
                                    <el-select placeholder="请选择" v-model="artData.module">
                                        <el-option key="bbk" label="步步高" value="bbk"></el-option>
                                        <el-option key="xtc" label="小天才" value="xtc"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="文章简介">
                                        <el-input type="textarea" rows="5"></el-input>
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
                                    width='140' height='140' v-on:loadSuccess='loadSuccess'></upload>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col>
                                <div class="crop-demo-btn">
                                    选择图片
                                    <input class="crop-input" type="file" name="image" accept="image/*" @change="setImage"/>
                                </div>
                                <button class="btn" @click="uploadFile" type='button'>shang传</button>
                            </el-col>
                        </el-row>
                    </div>
                </el-col>

            </el-row>
        </el-form>
        <el-row :gutter="20">
           <div class="editor-container">
                <UE :defaultMsg='artData.content' :config='config' ref="ue"></UE>
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
                    initialFrameHeight: 300,
                },
                artData: {
                    content: '',
                },
                qfile: {},
                imageUrl:'',
                uploadInfo: {
                    defaultSrc: './static/img/img.jpg',
                    uploadUrl: '/api/core/file/fileUpload'
                }
            }
        },
        computed: {},
        components: {
            UE, Upload
        },
        methods: {
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
            loadSuccess(res){
                if(1 == res){
                    alert('上传成功')
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
        width: 100px;
        height: 40px;
        line-height: 40px;
        padding: 0 20px;
        margin-left: 30px;
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
</style>
