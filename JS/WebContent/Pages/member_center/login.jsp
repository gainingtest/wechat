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
  <title>会员登录</title>
  <base href="../"/>
  <link href="gv/common/css/reset.css" rel="stylesheet" type="text/css">
  <link href="gv/common/css/main.css" rel="stylesheet" type="text/css">
  <link href="gv/member_center/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
  <div class="wrap">
    <div class="member_center">
    	<div class="center_img">
      	<img src="gv/member_center/images/icon_login.png"/>
      </div>
    </div>
    <ul class="center_data">
      <li>
        <input type="text" placeholder="请输入手机号" maxlength="13" class="iphone" value="${mobile}">
        <p class="wrong_tips"></p>
      </li>
      <li>
        <input type="password" placeholder="请输入登录密码" class="short password" maxlength="18">
        <div class="code_align">
              <input type="button" value="找回密码" class="key_btn" onclick="location.href=sUrl+'personalSettings/toPasswordRecovery?openid=${openid}'">
        </div>
        <p class="wrong_tips"></p>
      </li>
      <li>
        <input type="text" placeholder="请输入验证码" class="short img_code" maxlength="4">
        <span class="img_pic"><i></i><img src=""></span>
        <p class="wrong_tips"></p>
    </li>
    </ul>
    <div class="regist_btn">
      <input type="button" value="登录" class="default_btn regist_success">
<!--  <input type="button" style="display:none;" value="还不是会员，立即注册" class="unbundling" onclick="location.href=sUrl+'user/toRegister?openid=${openid}'"> -->
      <input type="button" style="display:none;" value="还不是会员，立即注册" class="unbundling" onclick="location.href=sUrl+'user/toRegister?openid=12345'">
      <p class="regist_btn_tips" style="display:none;">若您需使用其它账号登录，请致电99999999</p>
    </div>
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
    $('.unbundling').show();
    $('.regist_btn_tips').hide();
  }else{
    $('.iphone').prop('disabled',true);
    $('.regist_btn_tips').show();
    $('.unbundling').hide();
  }
  
  fnTabImgCode($('.img_pic'),sUrl);//换一张图片验证码
  //实时校验（手机号、登录密码、图片验证码）
  fnCheckIphone($('.iphone'));
  fnCheckPassword($('.password'));
  fnCheckImgCode($('.img_code'));
  $('.img_code').keyup(function(){
    var bCode = fnImgCode($('.img_code'));
    if(bCode){
       fnTimeImgCode();
    }
  })
  //登录
  $('.regist_success').click(function(){
  //提交时校验（手机号、登录密码、图片验证码）
      var bIphone = fnIphone($('.iphone'));
      var bPsw = fnPassword($('.password'));
      var bCode = fnImgCode($('.img_code'));
      if(bIphone && bPsw && bCode){
          $.ajax({
            url:sUrl+'loginByMobile',
            data:{
              openid:"${openid}",
              mobile:$('.iphone').val(),
              password:$('.password').val(),
              code:$('.img_code').val()
            },
            success:function(data){
              //1、未查询到用户，或未绑定微信或登录错误，2、没密码跳转完善信息页面,3
              if(data.result){
                switch(data.object){
                  case 1:fnLayerBox(data.info);
                  break;
                  case 2:location.href=sUrl+"accountSettings/toSupplyPass";
                  break;
                  //default:location.href=sUrl+"/center/toIndex";//调转会员中心首页
                  default:location.href=sUrl+"center/toIndex";//调转会员中心首页
                  break;
                }
              }else{
                fnLayerBox(data.info);
                $('.img_pic').find('img').attr("src",sUrl+"captcha/drawing?time="+(new Date()).getTime());
              }
            }
          })
      }
  })
}

</script>
</html>
