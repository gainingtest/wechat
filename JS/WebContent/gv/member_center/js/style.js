document.getElementsByTagName("html")[0].style.fontSize=document.documentElement.clientWidth/16+"px";
//改变窗口的时候重新计算大小
window.onresize = function(){
  document.getElementsByTagName("html")[0].style.fontSize=document.documentElement.clientWidth/16+"px";}
$(function(){
	//弹层的管理
	$('.regist_success').click(function(){
		$('.layer_box').show();
		})
		$('.layer_btn').click(function(){
		$(this).parents('.layer_box_member').hide();
		})
		$('.login_btn').click(function(){
		$(this).parents('.layer_box_member').hide();
		})
	//会员注册页面
	$('.layer_box_top i').click(function(){
		$(this).parents('.layer_box_member').hide();
		})
	//未验证页面
	$('.personal_data_btn').click(function(){
		$('.layer_box_member').show();
	})
})
//密码级别（6-16位数字字母）
var regNumber = /[0-9]+/;// 数字校验正则表达式
var regUpper = /[A-Z]+/;// 大写字母校验正则表达式
var regLower = /[a-z]+/;// 小写校验正则表达式
var passwordLevel;
function fnPasswordLevel(obj){
  var val = obj.val();
  if(regNumber.test(val) && regUpper.test(val) && regLower.test(val)) {
    //安全级别高
    passwordLevel = 3;
  } else if(regNumber.test(val) && regUpper.test(val)) {
    //安全级别中
    passwordLevel = 2;
  }else if(regNumber.test(val) && regLower.test(val)) {
    //安全级别中
    passwordLevel = 2;
  }else if(regUpper.test(val) && regLower.test(val)) {
    //安全级别中
    passwordLevel = 2;
  }else{
    //安全级别低
    passwordLevel = 1;
  }
}
//图片验证码切换
$('.img_pic img').attr('src',sUrl+'captcha/drawing');//设置默认图片验证码
function fnTabImgCode(obj,path){
  obj.click(function(){
    $(this).find('img').attr("src",path+"captcha/drawing?time="+(new Date()).getTime())
  })
}
//短信验证码
$('.btn_member_mobile_code').click(function(){
  var that = $(this);
  var mobile = $(this).siblings('.mobile').val();
  //请求验证码
  $.ajax({
    url:sUrl+'common/SMSSend',
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
/*+++++++++++++++++++++++++++++会员中心表单校验+++++++++++++++++++++++++++++++++*/
//客户身份信息（非空&正确性校验）-姓名、证件类型、证件号码、性别、出生日期
//姓名：2个及2个以上汉字或英文，将目前上限2-15扩增到2-40
function fnUserName(obj){
  var val = obj.val();
  var enName = /^(([A-Za-z]+\s?)*[A-Za-z]){2,40}$/;//英文
  var chName = /^[\u4E00-\u9FA5]{2,40}$/;  //中文
  if(val==''){
    obj.parents('li').find('.wrong_tips').text('请输入姓名！').show();
    return false;
  }else if(!(enName.test(val)|| chName.test(val))){
    obj.parents('li').find('.wrong_tips').text('请输入2个及2个以上汉字或英文！').show();
    return false;
  }else{
    obj.parents('li').find('.wrong_tips').text('').hide();
    return true;
  }
}
function fnCheckName(obj){
  obj.keyup(function(){
    fnUserName(obj);
  })
  obj.bind('input',function(){
    fnUserName(obj);
  })
}
//身份证件号码校验  & 其他类型证件号校验（除身份证号） ---测试
function fnId(obj,objType){
  var val = obj.val();
  var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;//身份证号
  var regNo = /^[\da-zA-Z\u4E00-\u9FA5]{6,25}$/;//6-25个数字字母 其他证件号码
  if(objType.find('option:selected').val() != '请选择证件类型'){
    if(objType.find('option:selected').val() == '01'){
      if(val == ''){
        obj.parents('li').find('.wrong_tips').text('请输入证件号码！').show();
        $('#birthdayDate').val('');
        $('.gender').get(0).selectedIndex = 0;
        return false;
      }else if(!reg.test(val) ){
        obj.parents('li').find('.wrong_tips').text('请输入正确的证件号码！').show();
        $('#birthdayDate').val('');
        $('.gender').get(0).selectedIndex = 0;
        return false;
      }else{
        var birthdayNo,genderNo;
        if(val.length == 15){
          birthdayNo = val.charAt(6) + val.charAt(7);
          if (parseInt(birthdayNo) < 10) {
            birthdayNo = '20' + val.substring(6,12);
          } else {
            birthdayNo = '19' + val.substring(6,12);
          }
          genderNo = val.substring(14,15);
          var year = val.substring(6, 8);
          var month = val.substring(8, 10);
          var day = val.substring(10, 12);
          var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day),1,0,0);
          // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法
          if (temp_date.getYear() != parseFloat(year) || temp_date.getMonth() != parseFloat(month) - 1 || temp_date.getDate() != parseFloat(day)) {
            $('#birthdayDate').val('');
            $('.gender').get(0).selectedIndex = 0;
            obj.parents('li').find('.wrong_tips').text('请输入正确的证件号码！').show();
            return false;
          }
        }else if(val.length == 18){
          birthdayNo = val.substring(6,14);
          genderNo = val.substring(16,17);
          var year = val.substring(6, 10);
          var month = val.substring(10, 12);
          var day = val.substring(12, 14);
          var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day),1,0,0);
          // 这里用getFullYear()获取年份，避免千年虫问题
          if (temp_date.getFullYear() != parseFloat(year) || temp_date.getMonth() != parseFloat(month) - 1 || temp_date.getDate() != parseFloat(day)) {
            $('#birthdayDate').val('');
            $('.gender').get(0).selectedIndex = 0;
            obj.parents('li').find('.wrong_tips').text('请输入正确的证件号码！').show();
            return false;
          }
        }else{
          $('#birthdayDate').val('');
          $('.gender').get(0).selectedIndex = 0;
          obj.parents('li').find('.wrong_tips').text('请输入正确的证件号码！').show();
          return false;
        }
        //获取日期
        $('#birthdayDate').val(birthdayNo.substring(0, 4) + "-" + getZero(parseInt(birthdayNo.substring(4, 6))) + "-" + birthdayNo.substring(6, 8));
        $("#birthdayDate").scroller('setDate',new Date(birthdayNo.substring(0, 4),getZero(parseInt(birthdayNo.substring(4, 6))-1),birthdayNo.substring(6, 8),1,0,0),true);
        //获取性别
        if (parseInt(genderNo) % 2 == 1) {
          fnCurBank($('.gender'),'0',$('.gender_name'));
        } else {
          fnCurBank($('.gender'),'1',$('.gender_name'));
        }
        obj.parents('li').find('.wrong_tips').text('').hide();
        return true;
      }
    }else{
      if(val == ''){
        obj.parents('li').find('.wrong_tips').text('请输入证件号码！').show();
        return false;
      }else if(!regNo.test(val) ){
        obj.parents('li').find('.wrong_tips').text('请输入正确的证件号码！').show();
        return false;
      }else{
        obj.parents('li').find('.wrong_tips').text('').hide();
        return true;
      }
    }
  }
}
function fnCheckId(obj,objType){
  obj.keyup(function(){
    fnId(obj,objType);
  })
  obj.bind('input',function(){
    fnId(obj,objType);
  })
}
//校验出生日期非空
function fnBirthday(obj){
  var val = obj.val();
  if(val === ''){
    obj.parents('li').find('.wrong_tips').text('请输入生日日期！').show();
    return false;
  }else{
    obj.parents('li').find('.wrong_tips').text('').hide();
    return true;
  }
}
function fnCheckBirthday(obj){
  obj.change(function(){
    fnBirthday(obj);
  })
}
//校验邮箱
function fnEmail(obj){
  var emailReg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
  var val = obj.val();
  if(val.length>100){
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
function fnCheckEmail(obj){
  obj.keyup(function(){
    fnEmail(obj);
  })
  obj.bind('input',function(){
    fnEmail(obj);
  })
}
//校验详细地址
function fnDetailsAddress(obj){
  var val = obj.val();
  if(val.length>500){
    obj.parents('li').find('.wrong_tips').text('详细地址长度过长！').show();
    return false;
  }else{
    obj.parents('li').find('.wrong_tips').text('').hide();
    return true;
  }
}
function fnCheckDetailsAddress(obj){
  obj.keyup(function(){
    fnDetailsAddress(obj);
  })
  obj.bind('input',function(){
    fnDetailsAddress(obj);
  })
}
//手机号校验
function fnIphone(obj){
  var regIphone = /^1[3|4|5|7|8]\d{9}$|^0085[23]\d{8}$|^00886\d{8}$/;
  var val = obj.val();
  if(val==''){
    obj.siblings('.wrong_tips').text('请输入手机号！').show();
    return false;
  }else if(!regIphone.test(val)){
    obj.siblings('.wrong_tips').text('请输入正确的手机号！').show();
    return false;
  }else{
    obj.siblings('.wrong_tips').text('').hide();
    return true;
  }
}
function fnCheckIphone(obj){
  obj.keyup(function(){
    fnIphone(obj);
  })
}
//密码校验
function fnPassword(obj){
  var regPassword = /^[0-9a-zA-Z]{6,18}$/;
  var val = obj.val();
  if(val==''){
    obj.siblings('.wrong_tips').text('请输入密码！').show();
    return false;
  }else if(!regPassword.test(val)){
    obj.siblings('.wrong_tips').text('请输入6-18位数字、字母！').show();
    return false;
  }else{
    obj.siblings('.wrong_tips').text('').hide();
    return true;
  }
}
function fnCheckPassword(obj){
  obj.keyup(function(){
    fnPassword(obj);
  })
  obj.bind('input',function(){
    fnPassword(obj);
  })
}
//确认密码校验
function fnRePassword(obj,reObj){
  var qvalue = reObj.val();
  var value = obj.val();
  var bRes = fnPassword(reObj);//验证返回值（true or false）
  if(bRes){
    if(qvalue != value){
      reObj.parents('li').find('.wrong_tips').text('您输入的两次密码不一致，请重新输入！').show();
      return false;
    }else{
      reObj.parents('li').find('.wrong_tips').text('').hide();
    }
  }
  return bRes;
}
function fnCheckRePassword(obj,reObj){
  obj.keyup(function(){
    fnRePassword(obj,reObj);
  })
  reObj.keyup(function(){
    fnRePassword(obj,reObj);
  })
  obj.bind('input',function(){
    fnRePassword(obj,reObj);
  })
  reObj.bind('input',function(){
    fnRePassword(obj,reObj);
  })
};

//图片验证码校验（非空、4位数字）
function fnImgCode(obj){
  var regImgCode = /^\d{4}$/;
  var val = obj.val();
  if(val==''){
    obj.siblings('.wrong_tips').text('请输入图片验证码！').show();
    $('.btn_member_mobile_code').prop('disabled',true);
    return false;
  }else if(!regImgCode.test(val)){
    obj.siblings('.wrong_tips').text('请输入正确的图片验证码！').show();
    $('.btn_member_mobile_code').prop('disabled',true);
    return false;
  }else{
    obj.siblings('.wrong_tips').text('').hide();
    $('.btn_member_mobile_code').prop('disabled',false);
    return true;
  }
}
function fnCheckImgCode(obj){
  obj.keyup(function(){
    fnImgCode(obj);
  })
}
//实时校验图片验证码
function fnTimeImgCode(){
  var returnValue;
  var imgCode = $('.img_code').val();//图片验证码
  $.ajax({
    url:sUrl+'captcha/validate',
    async:false,
    data:{
      code:imgCode
    },
    success:function(data){
      if(data.result){
        if(data.object.state){
          $('.img_code').siblings('.wrong_tips').text('').hide();
          $('.btn_member_mobile_code').prop('disabled',false);
          returnValue = true;
        }else{
          $('.img_code').siblings('.wrong_tips').text(data.object.note).show();
          $('.btn_member_mobile_code').prop('disabled',true);
          returnValue = false;
        }
      }else{
        fnLayerBox(data.info);
      }
    }
  })
  return returnValue;
}
/*+++++++++++++++++++++++++++++END会员中心表单校验++++++++++++++++++++++++++++++*/


