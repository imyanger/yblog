const IS_DEV = process.env.NODE_ENV !== 'production'

// ueditor后台地址
export const UE_API_ROOT = IS_DEV ? 
            'http://localhost:8080/api/core/ueditor/img' : 'http://58.87.66.244:80/api/core/ueditor/img'