/**
 * @author YangHao
 * @description 验证规则工具类
 */
const validateRules = {}

//用户名正则 只能输入6-20个以字母、可带数字、“_”、“.”、“@”的字串
validateRules.isUsername = function(v) {
    var pattern = /^([a-zA-Z0-9]|[._-]){6,19}$/;
    if (!pattern.exec(v)) return false;
    return true;
};

//用户密码 只能输入6-20个字母、数字
validateRules.isPassword = function(v) {
    var pattern = /^(\w){6,20}$/;
    if (!pattern.exec(v)) return false;
    return true;
};

//邮箱验证
validateRules.isEmail = function(v) {
    var pattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
    if (!pattern.exec(v)) return false;
    return true;
};

//手机号验证 手机号段13*, 14*, 15*, 17*, 18*
validateRules.isTelphone = function(v) {
    var pattern = /^1[34578]\d{9}$/;
    if (!pattern.exec(v)) return false;
    return true;
};

// 是否为整数
validateRules.isInteger = function(v) {
    var pattern = /^-?\d+$/;
    if (!pattern.exec(v)) return false;
    return true;
};

// 数字，整数和小数
validateRules.isNumber = function(v) {
    var pattern = /^-?\d*\.?\d+$/;
    if (!pattern.exec(v)) return false;
    return true;
};

// 18位身份证号
validateRules.isIdCard = function(v) {
    var pattern = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
    if (!pattern.exec(v)) return false;
    return true;
};

//URL正则
validateRules.isURL = function(v) {
    var pattern = /^((https?|ftp|file):\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/;
    if (!pattern.exec(v)) return false;
    return true;
};

// qq号码，5-11位数字
validateRules.isQQ = function(v) {
    var pattern = /^[1-9][0-9]{4,10}$/;
    if (!pattern.exec(v)) return false;
    return true;
};

// 微信号正则，6至20位，以字母开头，字母，数字，减号，下划线
validateRules.isWeixin = function(v) {
    var pattern = /^[a-zA-Z]([-_a-zA-Z0-9]{5,19})+$/;
    if (!pattern.exec(v)) return false;
    return true;
};

//包含中文正则
validateRules.hasCN = function(v) {
    var pattern = /[\u4E00-\u9FA5]/;
    if (!pattern.exec(v)) return false;
    return true;
};

//昵称--校验是否包含特殊字符
validateRules.isNickName = function(v) {
    let flag = false;
    if(v.length > 2 && v.length < 13){
        var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]");
        if(!pattern.test(v)){
            flag = true;
        }
    }
    return flag;
};

export default validateRules;