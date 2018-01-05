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
  <title>账号设置</title>
  <link href="gv/common/css/reset.css" rel="stylesheet" type="text/css">
  <link href="gv/common/css/main.css" rel="stylesheet" type="text/css">
  <link href="gv/member_center/css/style.css" rel="stylesheet" type="text/css">
  </head>
<body>
  <div class="wrap" id="accountSettings">
    <form action="" method="post">
      <input type="hidden" value="{{info.accountSettingsSign}}" class="account_settings_sign"/>
      <input type="hidden" value="{{info.mobile}}" class="init_mobile"/>
      <div class="account_settings">
    	<div class="center_img">
      	<img src="gv/member_center/images/icon_key.png"/>
      </div>
      <p class="notice">尊敬的客户您好，为了保证您的{{info.encryptMobile}}账户安全，请设置您的账户专属密码，我们将为您提供更多服务新体验哦！</p>
      <ul class="center_data">
      	<li>
          <input type="text" value="{{info.encryptMobile}}" disabled="disabled">
        </li>
      	<li>
          <input type="text" placeholder="请输入图片验证码" class="short img_code" maxlength="4">
          <span class="img_pic"><img src=""></span>
          <p class="wrong_tips"></p>
        </li>
      	<li>
          <input type="text" placeholder="请输入短信验证码" class="short mobile_code" maxlength="6">
          <div class="code_align">
            <input type="hidden" value="{{info.mobile}}" class="mobile" />
            <input type="button" value="获取验证码" class="key_btn btn_member_mobile_code" style="padding:0 0.5rem;" edor_app_type='account'>
          </div>
          <p class="wrong_tips"></p>
        </li>
      	<li>
          <input type="password" placeholder="请输入登录密码（6~18位数字或字母）" class="icon_lock password"  maxlength="18">
          <p class="wrong_tips"></p>
        </li>
      	<li>
          <input type="password" placeholder="再次输入登录密码" class="icon_lock repeat_password" maxlength="18">
          <p class="wrong_tips"></p>
        </li>
      </ul>
      <div class="regist_btn single_btn">
      	<input type="button" value="提交" class="default_btn regist_success">
      </div>
      <p class="hotline">若您需使用其它账号登录请致电客服<a href="javasript:void(0);">95300</a></p>
    </div>
    </form>
    <!--弹层-->
    <div class="layer_box_member tips_layer">
        <div class="layer_main">
          <div class="layer_password">
            <h3>账号设置成功</h3>
            <p class="detail" style="margin-bottom:0.75rem;">您的用户名为{{mobile}}，<br/>请牢记您的登录密码。</p>
          </div>
          <div class="layer_btn">
            <input type="button" value="我知道了" onclick="location.href=sUrl+'center/toIndex'"/>
          </div>
        </div>
      </div>
    <!--弹层结束-->
  </div>
</body>
<script type="text/javascript" src="gv/common/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="gv/common/js/vue.min.js"></script>
<script type="text/javascript" src="gv/common/js/main.js"></script>
<script type="text/javascript" src="gv/member_center/js/style.js"></script>
<script>
  window.onload = function(){
    var accSetting = new Vue({
      el:'#accountSettings',
      data:{
        info:{},//手机号基本信息
        mobile:''//账户设置成功用户名
      }
    })
    //请求获取页面信息（key、手机号）
    $.ajax({
      url:sUrl + 'accountSettings/getMobile',
      data:{
        openid:"${openid}"
      },
      success:function(data){
        if(data.result){
          accSetting.info = data.object;
        }else{
          fnLayerBox(data.info);
        }
      }
    })
    fnTabImgCode($('.img_pic'),sUrl);//换一张图片验证码
    //实时校验（图片验证码、短信验证码、登陆密码、确认密码）
    $('.btn_member_mobile_code').prop('disabled',true);
    $('.img_code').keyup(function(){
        var bCode = fnImgCode($('.img_code'));
        if(bCode){
           fnTimeImgCode();
        }
    })
    fnCheckPassword($('.password'));
    fnCheckPassword($('.repeat_password'));
    fnCheckRePassword($('.password'),$('.repeat_password'));

    //账户设置提交
    $('.regist_success').click(function(){
      //提交时校验（图片验证码、短信验证码、登陆密码、确认密码）
        var bCode = fnImgCode($('.img_code'));
        var newState = fnPassword($('.password'));
        var reState = fnPassword($('.repeat_password'));
        var compState = fnRePassword($('.password'),$('.repeat_password'));
        if(bCode){fnTimeImgCode();}
        if(bCode && newState && reState && compState){
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
            url:sUrl+'accountSettings/supplyPassword',
            data:{
              openid:"${openid}",
              accountSettingsSign:$('.account_settings_sign').val(),//key
              mobile:$('.init_mobile').val(),//手机号
              code:$('.img_code').val(),//图片验证码
              smsCode:$('.mobile_code').val(),//手机
              password:$('.password').val(),//密码
              repeatPassword:$('.repeat_password').val(),//确认密码
              passwordLevel:passwordLevel
            },
            success:function(data){
              if(data.result){
                accSetting.mobile = data.object.mobile;
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
