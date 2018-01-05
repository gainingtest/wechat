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
  <title>我的资料</title>
  <link href="gv/common/css/reset.css" rel="stylesheet" type="text/css">
  <link href="gv/common/css/main.css" rel="stylesheet" type="text/css">
  <link href="gv/member_center/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
  <div class="wrap personal_setting">
    	<div class="center_img">
      	<img style="border-radius:100%;-webkit-border-radius:100%;padding:2px;border:1px solid #ff444a;box-sizing:border-box;" src="gv/member_center/images/icon_main_img.png"/>
      </div>
      <ul class="personal_setting_con">
      	<li>
        <a href="javascript:void(0);" onclick="location.href=sUrl+'personalInfo/toInfo'">
        <label>个人资料：</label>
        <div class="fr"><em style="margin-right:0.35rem;">${userName}</em><i></i></div>
        </a></li>
      	<li>
        <a href="javascript:void(0);" onclick="location.href=sUrl+'personalSettings/toPasswordModify'">
        <label>我的密码 ：</label>
        <div class="fr">
          <c:choose>
            <c:when test="${passwordLevel eq 3}"><span class="green_level">安全级别高</span></c:when>
            <c:when test="${passwordLevel eq 2}"><span class="orange">安全级别中</span></c:when>
            <c:otherwise><span class="red">安全级别低</span></c:otherwise>
          </c:choose>
          <i></i>
        </div>
        </a></li>
      	<li><a href="javascript:void(0);"><label>手机验证：</label><label class="fr phone_num">已绑定${encryptMobile}</label></a></li>
        <li>
            <a href="javascript:void(0);">
                <label>实名认证：</label>
                <div class="fr">
                    <c:if test="${authenState == '1'}">
                        <em class="certified">已认证</em>
                    </c:if>
                    <c:if test="${authenState != '1'}">
                        <em class="certified">未认证</em>
                    </c:if>

                </div>
            </a>
        </li>
        </ul>
      <p class="unbundling align_right"><span class="icon_tips"></span><span style="vertical-align:middle;">如何申请解绑</span></p>
      <div class="regist_btn single_btn">
      	<input type="button" value="退出登录" class="default_btn regist_success" id="closeWindow">
      </div>
      <!--弹层-->
      <div class="layer_box_member tips_layer">
          <div class="layer_main">
            <div class="layer_password">
              <p>亲，如您需要解除会员账号与微信的绑定，请您直接拨打<a href="tel:95300">95300</a>服务热线。</p>
            </div>
          <div class="layer_btn">
            <input type="button" value="我知道了"/>
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
    $('.center_img img').attr('src','${imgUrl}');
    //申请解绑
    $('.unbundling').click(function(){
      $('.layer_box_member').show();
    });
    $('#closeWindow').click(function(){
        $.ajax({
          url:sUrl+'logout',
          success:function(data){
            if(data.result){
              function onBridgeReady() {
                  WeixinJSBridge.invoke('closeWindow',{
                  },function(res){
                  });
              }
              if (typeof WeixinJSBridge === "undefined") {
                document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
              } else {
                onBridgeReady();
              }
            }else{
              fnLayerBox(data.info);
            }
          }
        })
    });
  }

</script>
</html>
