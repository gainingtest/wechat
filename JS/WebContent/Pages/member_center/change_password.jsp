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
  <title>修改密码</title>
  <link href="gv/common/css/reset.css" rel="stylesheet" type="text/css">
  <link href="gv/common/css/main.css" rel="stylesheet" type="text/css">
  <link href="gv/member_center/css/style.css" rel="stylesheet" type="text/css">
</head>
<body class="bg_white">
  <div class="wrap change_password">
    <form action="" method="post">
    <div class="member_center">
    	<div class="center_img">
      	<img src="gv/member_center/images/icon_key.png"/>
      </div>
      <ul class="center_data">
      	<li>
          <input type="password" placeholder="请输入原密码" class="icon_lock old_password"  maxlength="18">
          <p class="wrong_tips"></p>
        </li>
      	<li>
          <input type="password" placeholder="请输入新密码" class="icon_lock new_password"  maxlength="18">
          <p class="wrong_tips"></p>
        </li>
      	<li>
          <input type="password" placeholder="请再次确认新密码" class="icon_lock repeat_new_password"  maxlength="18">
          <p class="wrong_tips"></p>
        </li>
      </ul>
    </div>
      <div class="regist_btn single_btn">
      	<input type="button" value="确认" class="default_btn regist_success">
      </div>
    </form>
      <!--弹层-->
      <div class="layer_box_member tips_layer">
          <div class="layer_main">
            <div class="layer_password">
              <h3>密码重置成功，<br/>请牢记您的密码！</h3>
            </div>
          <div class="layer_btn">
            <input type="button" value="返回个人中心" onclick="location.href=sUrl+'center/toIndex'"/>
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
    //实时校验（原密码、新密码、确认密码）
    fnCheckPassword($('.old_password'));
    fnCheckPassword($('.new_password'));
    fnCheckPassword($('.repeat_new_password'));
    fnCheckRePassword($('.new_password'),$('.repeat_new_password'));

    //确认修改密码
    $('.regist_success').click(function(){
    //提交时校验（原密码、新密码、确认密码）
    var oldState = fnPassword($('.old_password'));
    var newState = fnPassword($('.new_password'));
    var reState = fnPassword($('.repeat_new_password'));
    var compState = fnRePassword($('.new_password'),$('.repeat_new_password'));

    if(oldState && newState && reState && compState){
        var bValue = fnOldPassword();//原密码服务器校验
        if(bValue){
            fnPasswordLevel($('.new_password'));//密码安全级别
            $.ajax({
                url:sUrl + "personalSettings/passwordModify",
                data:{
                  passwordLevel:passwordLevel,
                  oldPassword:$('.old_password').val(),
                  newPassword:$('.new_password').val(),
                  repeatNewPassword:$('.repeat_new_password').val()
                },
                success:function(data){
                  if(data.result){
                    $('.layer_box_member').show();
                  }else{
                    fnLayerBox(data.info);
                  }
                }
            })
        }
    }
    })
    //验证原密码（提交时校验）
    function fnOldPassword(){
        var returnValue;
        $.ajax({
          url:sUrl+"personalSettings/checkPassword",
          async:false,
          data:{
            oldPassword:$('.old_password').val()
          },
          success:function(data){
            if(data.result){
              if(data.object.status){
                $('.old_password').siblings('.wrong_tips').text('').hide();
                returnValue = true;
              }else{
                $('.old_password').siblings('.wrong_tips').text(data.object.msg).show();
                returnValue = false;
              }
            }else{
              fnLayerBox(data.info);
            }
          }
        })
        return returnValue;
    }
  }
</script>
</html>
