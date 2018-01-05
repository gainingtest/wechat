<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no" />
  <meta name="format-detection" content="telephone=no,email=no">
  <base href="../"/>
  <title>找回密码</title>
  <link href="gv/common/css/reset.css" rel="stylesheet" type="text/css">
  <link href="gv/common/css/main.css" rel="stylesheet" type="text/css">
  <link href="gv/member_center/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
  <div class="wrap retrieve_password">
    	<div class="center_img">
      	<img src="gv/member_center/images/icon_key.png"/>
      </div>
      <p class="notice">为了您的账户安全，我们将会发送短信验证码至您的手机，如2分钟内没收到，请点击重新发送。</p>
      <ul class="center_data">
      	<li>
          <input type="text" placeholder="请输入手机号" maxlength="13" class="iphone" value="${mobile}">
          <p class="wrong_tips"></p>
        </li>
      	<li>
          <input type="text" placeholder="请输入图片验证码" class="short img_code" maxlength="4">
          <span class="img_pic"><i></i><img src=""></span>
          <p class="wrong_tips"></p>
        </li>
      	<li>
          <input type="text" placeholder="请输入短信验证码" class="short mobile_code" maxlength="6">
          <div class="code_align">
              <input type="hidden" class="mobile" />
              <input type="button" value="获取验证码" class="key_btn btn_member_mobile_code" style="padding:0 0.5rem;" disabled="disabled" edor_app_type='password'>
          </div>
          <p class="wrong_tips"></p>
        </li>
      	<li>
          <input type="password" placeholder="请输入登录密码（6~18位数字或字母）" maxlength="18" class="password">
          <p class="wrong_tips"></p>
        </li>
      	<li>
          <input type="password" placeholder="再次输入登录密码" class="icon_lock repeat_password" maxlength="18">
          <p class="wrong_tips"></p>
        </li>
      </ul>
      <div class="regist_btn single_btn">
      	<input type="button" value="确认" class="default_btn regist_success">
      </div>
      <!--弹层-->
      <div class="layer_box_member tips_layer">
          <div class="layer_main">
            <div class="layer_password">
              <h3>密码成功找回，<br/>请牢记您的密码！</h3>
            </div>
            <div class="layer_btn clearfix">
              <input type="button" value="我知道了" onclick="location.href=sUrl+'center/toIndex'"/>
            </div>
          </div>
        </div>
      <!--弹层结束-->
    </div>
</body>
<script type="text/javascript" src="gv/common/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="gv/common/js/main.js"></script>
<script type="text/javascript" src="gv/member_center/js/style.js"></script>
<script>
  window.onload = function(){
    //手机号自动带出，不可修改
    if("${mobile}" == ''){
        $('.iphone').prop('disabled',false);
    }else{
        $('.iphone').prop('disabled',true);
        $('.mobile').val($('.iphone').val());
    }
    fnTabImgCode($('.img_pic'),sUrl);//换一张图片验证码
    //实时校验（手机号、图片验证码、短信验证码、登陆密码、确认密码）
    $('.iphone').keyup(function(){
        var bIphone = fnIphone($('.iphone'));
        if(bIphone){
            $('.mobile').val($(this).val());
        }
    })
    fnCheckPassword($('.password'));
    fnCheckPassword($('.repeat_password'));
    fnCheckRePassword($('.password'),$('.repeat_password'));
    $('.btn_member_mobile_code').prop('disabled',true);
    $('.img_code').keyup(function(){
        var bCode = fnImgCode($('.img_code'));
        if(bCode){
           fnTimeImgCode();
        }
    })
    //确认找回密码
    $('.default_btn').click(function(){
      //确认时校验（手机号、图片验证码、短信验证码、登陆密码、确认密码）
      var bIphone = fnIphone($('.iphone'));
      var bCode = fnImgCode($('.img_code'));
      var bPsw = fnPassword($('.password'));
      var bRePsw = fnPassword($('.repeat_password'));
      var bComRePsw = fnRePassword($('.password'),$('.repeat_password'));
      if(bCode){fnTimeImgCode();}
      if(bIphone && bCode && bPsw && bRePsw && bComRePsw){
        var bValue = fnTimeImgCode();
        if(bValue){
            //校验是否点击获取验证码
            if(clickTime == 0){
                fnLayerBox('请获取短信验证码！');
                return false;
            }
            //校验验证码
            var mobileCode = $('.mobile_code').val();
            if(!fnMobileCode($('.mobile_code'))){
                $('.mobile_code').focus();
                return false;
            }
              fnPasswordLevel($('.password'));//密码安全级别
              $.ajax({
                url:sUrl+'personalSettings/passwordRecovery',
                data:{
                  openid:"${openid}",
                  mobile:$('.iphone').val(),
                  code:$('.img_code').val(),
                  smsCode:$('.mobile_code').val(),
                  passwordLevel:passwordLevel,
                  password:$('.password').val(),
                  repeatPassword:$('.repeat_password').val()
                },
                success:function(data){
                  if(data.result){
                    $('.layer_box_member').show();
                  }else{
                    fnLayerBox(data.info);
                    $('.img_pic').find('img').attr("src",sUrl+"/captcha/drawing?time="+(new Date()).getTime());
                  }
                }
              })
          }
        }
    })
  }

</script>
</html>
