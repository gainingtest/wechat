//根字体大小设置
document.getElementsByTagName("html")[0].style.fontSize=document.documentElement.clientWidth/15+"px";
//改变窗口的时候重新计算大小
window.onresize = function(){
  document.getElementsByTagName("html")[0].style.fontSize=document.documentElement.clientWidth/15+"px";
}
//微信屏蔽分享公共JS
function onBridgeReady(){
  WeixinJSBridge.call('hideOptionMenu');
}
if (typeof WeixinJSBridge == "undefined"){
  if( document.addEventListener ){
    document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
  }else if (document.attachEvent){
    document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
    document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
  }
}else{
  onBridgeReady();
}
//针对url发送数据处理
var aValue = [];//url参数列表
function fnUrlData(){
  var curUrl = location.href;
  if(curUrl.indexOf('?') != -1){
    var strData = curUrl.split('?')[1];
    var Adata = strData.split('&');
    var dataLen = Adata.length;
    for(var i=0;i<dataLen;i++){
      aValue.push(Adata[i].split('=')[1]);
    }
    return aValue;
  }
}
//url添加时间戳（css link href、img src、script src）解决浏览器缓存问题
function convertURL(url){
//  var timestamp = new Date().getTime();
  var version = 1;//更新文件后手动更新版本号
  if(url.indexOf("?")>=0){
    url = url + "&v=" + version;
  }else{
    url = url + "?v=" + version;
  }
  return url;
}
var sUrl = ''; //ajax请求路径
//ajax公用设置
$.ajaxSetup({
  url: "/xmlhttp/",
  global: false,
  type:'post',
  dataType:'json',
  cache:false,
  timeout:10000,
  beforeSend:function(){
    fnLoading();//加载loading
  },
  complete:function(){
    $('.loading_layer').remove();//删除loading
  },
  error:function(jqXHR, textStatus, errorThrown){
    switch(jqXHR.status){
      case 404:
      case 500:
      case 502:
      case 503:
        location.href=sUrl+"pageTo/to404";
        break;
    }
    if(textStatus == 'timeout'){
      fnLayerBox('系统链接超时，请稍后再试');
    }
  }
})
//禁止复制剪切粘贴input的内容
$(function(){
  $('input').each(function(){
    this.oncopy = function(){return false;};
    this.oncut = function(){return false;}
    this.onpaste = function(){return false;}
    this.oncontextmenu = function(){return false;}
  })
})
//去掉所有空格
function fnTrim(str,is_global)
{
  var result;
  result = str.replace(/(^\s+)|(\s+$)/g,"");
  if(is_global.toLowerCase()=="g")
  {
    result = result.replace(/\s/g,"");
  }
  return result;
}
/*++++++++++++++++++++++++++++++公共校验++++++++++++++++++++++++++++++++++*/
//手机验证码验证
function fnMobileCode(mobileCodeObj){
  var regMobile = /^\d{6}$/;//手机验证码正则（6位数字）
  var mobileCodeVal = mobileCodeObj.val();
  if(mobileCodeVal == ''){
    mobileCodeObj.siblings('.wrong_tips').text('请输入验证码！').show();
    return false;
  }else if(!regMobile.test(mobileCodeVal)){
    mobileCodeObj.siblings('.wrong_tips').text('请输入正确验证码！').show();
    return false;
  }else{
    mobileCodeObj.siblings('.wrong_tips').text('').hide();
    return true;
  }
}
$('.mobile_code').blur(function(){
  fnMobileCode($(this));
})
//手机验证码倒计时
//验证码倒计时
var countdown=120;
var timer;
var clickTime = 0;//点击验证码次数
function settimeCode(val) {
  timer = setTimeout(function() {
    settimeCode(val)
  },1000)
  if (countdown == 0) {
    clearTimeout(timer);
    val.prop('disabled',false);
    val.val('重新发送');
    countdown = 120;
    clickTime = 0;//点击验证码次数
  } else {
    val.prop('disabled',true);
    val.val(countdown+'秒后重发');
    countdown--;
  }
}
$('.btn_mobile_code').click(function(){
  var that = $(this);
  var mobile = $(this).siblings('.mobile').val();
  //请求验证码
  $.ajax({
    url:sUrl+'common/sendSMSCode',
	  data:{
        smsCode:that.attr('edor_app_type'),
        mobile:mobile
      },
    success:function(data){
      if(data.result){
        //发送短信验证码倒计时（成功开始倒计时）
        settimeCode(that);
        clickTime = 1;
      }else{
        fnLayerBox(data.info);
      }
    }
  })
})
//开户行
function fnCurBank(aBank,dataBank,sBank){
  aLen = aBank.find('option').length;
  for(var i=0;i<aLen;i++){
    if(aBank.find('option').eq(i).val() === dataBank){
      aBank[0].selectedIndex = i;
    }
  }
  sBank.val(aBank.find('option:selected').text());//获取银行名称
  aBank.change(function(){
    sBank.val(aBank.find('option:selected').text());//获取银行名称
  })
}
//开户行change,银行卡号和确认银行卡号都清空
function fnClearBank(aBank,accNo,reAccNo){
  aBank.change(function () {
    accNo.val('');
    reAccNo.val('');
  });
}
//校验选择银行
function fnInitBank(aBank,str){
  if(aBank.find('option:selected').text().indexOf('请选择')>=0){
    aBank.parents('li').find('.wrong_tips').text('请选择'+str+'！').show();
    return false;
  }else{
    aBank.parents('li').find('.wrong_tips').text('').hide();
    return true;
  }
}
function fnCheckBank(obj,str){
  obj.change(function(){
    fnInitBank(obj,str);
  })
}
//所在省市区校验
function fnInitRegion(aBank){
  if(aBank.find('option:selected').text()==='请选择'){
    aBank.parents('li').find('.wrong_tips').text('请完善您的地址信息！').show();
    return false;
  }else{
    aBank.parents('li').find('.wrong_tips').text('').hide();
    return true;
  }
}
function fnCheckRegion(obj){
  obj.change(function(){
    fnInitRegion(obj);
  })
}
//开户行所在地校验
function fnBankAdress(obj){
  if(obj.val() === ''){
    obj.parents('li').find('.wrong_tips').text('请选择开户行所在地！').show();
    return false;
  }else{
    obj.parents('li').find('.wrong_tips').text('').hide();
    return true;
  }
}
function fnCheckAdress(obj){
  obj.blur(function(){
    fnBankAdress(obj);
  })
}
//已阅读校验
function fnCheckRead(obj){
  obj.click(function(){
    if(obj.find('input').prop('checked')){
    obj.find('input').prop('checked',false);
    return false;
  }else{
    obj.find('input').prop('checked',true);
    return true;
  }
 })
}
//校验银行卡号(非空 && 规则 && 确认银行卡号一致)
function fnCheckBankNo(obj){
  var regBankNo = /^(\d{12,19})$/ ;//银行卡号12-19位数字
  var val = fnTrim(obj.val(),'g');
  if(val == ''){
    obj.parents('li').find('.wrong_tips').text('请输入银行卡号！').show();
    return false;
  }else if(!regBankNo.test(val)){
    obj.parents('li').find('.wrong_tips').text('请输入正确的银行卡号！').show();
    return false;
  }else{
    obj.parents('li').find('.wrong_tips').text('').hide();
    return true;
  }
}
function fnCheckReBank(obj,comfirmObj){
  var qvalue = comfirmObj.val();
  var value = obj.val();
  var bRes = fnCheckBankNo(comfirmObj);//验证返回值（true or false）
  if(bRes){
    if(qvalue != value){
      comfirmObj.parents('li').find('.wrong_tips').text('两次输入银行卡号不一致！').show();
      return false;
    }else{
      comfirmObj.parents('li').find('.wrong_tips').text('').hide();
    }
  }
  return bRes;
}
function fnObjCheck(obj,comfirmObj){
  obj.blur(function(){
    fnCheckReBank(obj,comfirmObj);
  })
  comfirmObj.blur(function(){
    fnCheckReBank(obj,comfirmObj);
  })
};
//ajax请求加载时间
//信息是否修改
function fnModify(obj,oldVal){
  var newVal = obj.val();
  if(newVal == oldVal){
    return false;
  }else if(newVal == '请选择'){
    return false;
  }else{
    return true
  }
}
//工作单位长度校验
function fnCheckWork(obj){
  var val = obj.val();
  if(val.length>200){
    obj.parents('li').find('.wrong_tips').text('此项长度过长！').show();
    return false;
  }else{
    obj.parents('li').find('.wrong_tips').text('').hide();
    return true;
  }
}
//联系地址非空长度校验
function fnCheckAddress(obj,str){
  var val = obj.val();
  if(val==''){
    obj.parents('li').find('.wrong_tips').text(str).show();
    return false;
  }else if(val=='-1'){
    obj.parents('li').find('.wrong_tips').text('请输入正确的联系地址！').show();
    return false;
  }else if(val.length>200){
    obj.parents('li').find('.wrong_tips').text('此项长度过长！').show();
    return false;
  }else{
    obj.parents('li').find('.wrong_tips').text('').hide();
    return true;
  }
}

function fnBlurWork(obj){
  obj.blur(function(){
    fnCheckWork(obj);
  })
}
function fnBlurAddress(obj,str){
  obj.blur(function(){
    fnCheckAddress(obj,str);
  })
}
function fnBlurEmail(obj){
  obj.blur(function(){
    fnCheckEmail(obj);
  })
}
function fnBlurPost(obj){
  obj.blur(function(){
    fnCheckPost(obj);
  })
}
//邮箱校验
function fnCheckEmail(obj){
  var emailReg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
  var val = obj.val();
  if(val.length>50){
    obj.parents('li').find('.wrong_tips').text('邮箱长度过长！').show();
    return false;
  }else if(!emailReg.test(val) && val!=''){
    obj.parents('li').find('.wrong_tips').text('邮箱格式不正确！').show();
    return false;
  }else{
    obj.parents('li').find('.wrong_tips').text('').hide();
    return true;
  }
}
//邮编校验
function fnCheckPost(obj){
  var postReg = /^[0-9]{6}$/;
  var val = obj.val();
  if(!postReg.test(val) && val!=''){
    obj.parents('li').find('.wrong_tips').text('邮编格式不正确！').show();
    return false;
  }else{
    obj.parents('li').find('.wrong_tips').text('').hide();
    return true;
  }
}
//部分领取金额>0
function fnCheckPartMoney(obj,valueObj){
  var numReg = /^[1-9]*[1-9][0-9]*$/;
  var val = obj.val();
  var currentValue = valueObj.val();
  if(val == ''){
    obj.parents('li').find('.wrong_tips').text('金额不能为空！').show();
    return false;
  }else if(!numReg.test(val)){
    obj.parents('li').find('.wrong_tips').text('金额为100的整数倍！').show();
    return false;
  }else if(parseInt(val) <= 0){
    obj.parents('li').find('.wrong_tips').text('金额必须大于0！').show();
    return false;
  }else if(parseInt(val)<1000){
    obj.parents('li').find('.wrong_tips').text('金额最低为1000元！').show();
    return false;
  }else if(parseInt(val)%100 != 0){
    obj.parents('li').find('.wrong_tips').text('金额为100的整数倍！').show();
    return false;
  }else if(parseInt(val) >50000){
    obj.parents('li').find('.wrong_tips').text('领取金额不得超过5万！').show();
    return false;
  }else if(parseInt(val) > parseInt(currentValue)){
    obj.parents('li').find('.wrong_tips').text('领取金额不能大于账户价值！').show();
    return false;
  }else{
    obj.parents('li').find('.wrong_tips').text('').hide();
    return true;
  }
}
function fnBlurPartMoney(obj,valueObj){
  obj.blur(function(){
    fnCheckPartMoney(obj,valueObj);
  })
}
//金额校验
function fnCheckMoney(obj){
  var moneyReg = /^[1-9]*[1-9][0-9]*$/;//正整数（不包括0）
  var val = obj.val();
  if(val == ''){
    obj.parents('li').find('.wrong_tips').text('金额不能为空！').show();
    return false;
  }else if(!moneyReg.test(val)){
    obj.parents('li').find('.wrong_tips').text('请输入整数金额！').show();
    return false;
  }else if(parseInt(val)<1000){
    obj.parents('li').find('.wrong_tips').text('金额最低为1000元！').show();
    return false;
  }else if(parseInt(val)%100 != 0){
    obj.parents('li').find('.wrong_tips').text('金额为100的整数倍！').show();
    return false;
  }else{
    obj.parents('li').find('.wrong_tips').text('').hide();
    return true;
  }
}
function fnBlurMoney(obj){
  obj.blur(function(){
    fnCheckMoney(obj);
  })
}/*银行卡号格式化，用户输入和显示*/
var preValue;
function fnFormatBankNo(obj,prev){
  if(obj.attr('show') == 'true'){
    var value = obj.text().replace(/\s/g,'').replace(/([\d\*]{4})/g,"$1 ");
    obj.text(value);
  }else{
    var position = obj.get(0).selectionStart;
    var value = obj.val().replace(/\s/g,'').replace(/(\d{4})(?=\d)/g,"$1 ");
    obj.val(value);
    //按下删除键
    if(prev && prev.length >= obj.val().length){
      setCaretPosition(obj.get(0),position);
    }else if(prev && prev.length < obj.val().length){//插入数值
      if(obj.val().charAt(position-1) == ' '){
        setCaretPosition(obj.get(0),position+1);
      }else{
        setCaretPosition(obj.get(0),position);
      }
    }
  }
}
function fnRunFormat(obj){
  fnFormatBankNo(obj,preValue);
  obj.keydown(function(e){
    preValue = $(this).val();
  });
  obj.keyup(function(){
    fnFormatBankNo(obj,preValue);
  })
  obj.bind('input',function(){
    fnFormatBankNo(obj,preValue);
  })
}
//设置光标位置
function setCaretPosition(ctrl, pos){
  if(ctrl.setSelectionRange)
  {
    ctrl.focus();
    ctrl.setSelectionRange(pos,pos);
  }
  else if (ctrl.createTextRange) {
    var range = ctrl.createTextRange();
    range.collapse(true);
    range.moveEnd('character', pos);
    range.moveStart('character', pos);
    range.select();
  }
}
/*++++++++++++++++++++++++++++++公共组件++++++++++++++++++++++++++++++++++*/
/*加载loading-所有页面*/
function fnLoading(){
  $('.loading_layer').remove();
  var html = '<div class="loading_layer">' +
  '<div class="spinner">' +
    '<div class="rect1"></div>' +
    '<div class="rect2"></div>' +
    '<div class="rect3"></div>' +
    '<div class="rect4"></div>' +
    '<div class="rect5"></div>' +
    '</div>'+
  '</div>'
  $('body').append(html);
}
//监听加载状态改变
document.onreadystatechange = fnCompleteLoading;
//加载状态为complete时移除loading效果
function fnCompleteLoading() {
  if (document.readyState == "complete") {
    $('.loading_layer').remove();
  }else{
    fnLoading();
  }
}
/*弹出层*/
function fnLayerBox(msg){
  $('.layer_box').remove();
  var html = '<div class="layer_box">'+
    '<div class="layer_main">'+
    '<div class="layer_main_tips">'+
    '<p>'+msg+'</p>'+
    '</div>'+
    '<input type="button" value="我知道了" class="default_btn layer_btn">'+
    '</div>'+
    '</div>';
  $('body').append(html);
  $('.layer_btn').click(function(){
    $(this).parents('.layer_box').remove();
  })
}

/*动态创建隐藏域*/
function fnHideInput(inputObj,formObj){
  var str ='';
  for(var item in inputObj){
    str += '<input type="hidden" name="'+ item +'" value="'+ inputObj[item]  +'" />'
  }
  formObj.prepend(str);
}