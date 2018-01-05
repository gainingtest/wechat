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
  <title>会员注册</title>
  <link href="gv/common/css/reset.css" rel="stylesheet" type="text/css">
  <link href="gv/common/css/main.css" rel="stylesheet" type="text/css">
  <link href="gv/member_center/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
  <div class="wrap member_regist">
    <div class="member_center">
    	<div class="center_img">
      	<img src="gv/member_center/images/icon_login.png"/>
      </div>
    </div>
      <ul class="center_data">
      	<li>
            <input type="text" placeholder="请输入手机号" class="icon_phone" maxlength="13">
            <p class="wrong_tips"></p>
        </li>
      	<li>
            <input type="text" placeholder="请输入图片验证码" class="short icon_pic img_code" maxlength="4">
            <span class="img_pic"><i></i><img src="" /></span>
            <p class="wrong_tips"></p>
        </li>
        <li>
            <input type="text" placeholder="请输入短信验证码" class="short icon_message mobile_code" maxlength="6">
            <div class="code_align">
                <input type="hidden" value="" class="mobile" />
                <input type="button" value="获取验证码" class="key_btn btn_member_mobile_code" style="padding:0 0.5rem;" edor_app_type='wxRegist' disabled="disabled">
            </div>
            <p class="wrong_tips"></p>
        </li>
      	<li>
            <input type="password" placeholder="请设置登录密码（6~18位数字或字母）" class="icon_lock password" maxlength="18">
            <p class="wrong_tips"></p>
        </li>
      	<li>
            <input type="password" placeholder="请确认登录密码" class="icon_lock repeat_password" maxlength="18">
            <p class="wrong_tips"></p>
        </li>
      </ul>
      <p class="check_tips"><input type="checkbox" class="check_tips_checked">我已认真阅读并同意<a href="javascript:;">《**会员注册协议》</a></p>
      <div class="regist_btn">
      	<input type="button" value="注册" class="default_btn regist_success">
      	<input type="button" value="已有账号，马上登录" class="unbundling" onclick="location.href=sUrl+'user/toLogin?openid=${openid}'">
      </div>
      <!--弹层-->
      <div class="layer_box_member tips_layer">
          <div class="layer_main">
            <div class="layer_password">
              <h3>恭喜您，注册成功！</h3>
              <p style="margin-bottom:1.2rem;"><a href="javascript:void(0);" onclick="location.href=sUrl+'personalInfo/toInfo?openid=${openid}'">完善会员信息</a>可帮您<br/>自动关联保单哦！</p>
            </div>
            <div class="layer_btn clearfix">
              <input type="button" value="我知道了" onclick="location.href=sUrl+'center/toIndex'" />
            </div>
          </div>
        </div>
      <!--弹层结束-->
  </div>
  <!--注册协议弹层-->
<div class="wrap clause">
  	<p class="clause_word">
自定义内容<br>
<b>一、会员的权利义务：</b><br>
 1、会员必须遵循：<br>
 (1)中华人民共和国关于网络和BBS的相关法律法规。<br>
 (2)依法使用网络服务，不作非法用途。<br>
 (3)不干扰或混乱网络服务。<br>
 (4)遵守所有使用网络服务的网络协议、规定、程序和惯例。<br>
同时会员承诺：<br>
 (1)不传输任何非法的、骚扰性的、中伤他人的、辱骂性的、恐吓性的、伤害性的、庸俗的或淫秽的信息资料；<br>
 (2)不传输任何教唆他人实施犯罪行为的资料；<br>
 (3)不传输任何不符合国家法律法规的资料；<br>
 (4)不得未经许可而非法进入其它电脑系统；<br>
 (5)法律规定的其他义务。<br>
若会员的行为不符合本服务条款，本网站将作出独立判断，并采用相关行动。会员需对自己在本网站的行为承担法律责任。会员若在本网站上散布和传播反动、色情或其他违反国家法律法规的信息，本网站将完全配合司法机关的行动。<br>
2、基于网络服务的特性及重要性，会员同意：<br>
 (1)提供详尽、准确的个人资料。<br>
 (2)及时更新其会员信息以维持该信息的有效性。若您提供任何错误、不实、过时或不完整的资料，或者本网站有合理的理由怀疑前述资料为错误、不实、过时或不完整，本网站有权暂停或终止您的帐号，并拒绝您於现在和未来使用本服务之全部或一部分。<br>
 (3)自行配备上网的所需设备，包括个人电脑、调制解调器或其他必备上网装置。<br>
 (4)自行负担个人上网所支付的与此服务有关的电话费用、 网络费用等相关费用。<br>
 (5) 允许邮件系统不定时向注册邮箱发送信息。<br>
 3、会员的帐号、密码和安全性 <br>
会员一旦注册成功，成为本网站的合法会员，将得到一个密码和会员名。会员有权利拥有自己在本网站的用户名和密码并有权利使用自己的用户名和密码随时登录网站。享受本网站提供的互联网技术和网站信息服务，并按网站服务规则进行个人信息和保单信息管理、平台交易、参与活动及咨询等其他服务。会员有义务保证个人账号和密码的安全，不得以任何形式擅自转让或授权他人使用自己在本网站的会员账号，会员对其在本网站注册的会员名和密码、账户资金及其它保单资料的安全性负全部责任，并对以其会员名进行的所有操作负全部责任。如会员发现账号遭到未授权的使用或发生其他任何安全问题，应立即修改账号密码并妥善保管，如有必要，请通知网站客服人员协助处理。另外，每个会员应当对以其会员名进行的所有活动和事件负全责。<br>
4、会员发布宣传及广告信息<br>
会员在其发表的信息中加入宣传资料或参与广告策划、人才招聘等，在本网站的免费服务平台上展示其产品，任何这类促销方法所产生的权利义务，包括运输货物、付款、服务、商业条件、担保及与广告有关的描述，都只在相关的会员和广告销售商之间发生。本网站不为这类广告销售承担任何责任。发布信息的会员应当对此承担全部责任。<br>
5、会员应当保障和维护本网站全体会员的利益。<br>
 <b>二、本网站的权利义务</b><br>
 1、对会员个人信息的保密义务<br>
    &#12288;尊重会员个人隐私、保障会员个人信息安全是本网站的一项基本政策，本网站不会公开、编辑或透露会员的注册资料，除非符合以下情况：<br>
 (1)根据中华人民共和国国家安全部门、公安部门依法提出的要求及根据相应的法律程序要求。<br>
 (2)维护本网站的商标所有权及其它权益。<br>
 (3)在紧急情况下竭力维护会员个人、其它社会个体和社会公众的安全。<br>
 (4)严重违反本网站有关规定。本网站保留取消会员资格的权利，并保证结束会员资格后仍为会员保密所有个人隐私。<br>
2、本网站有权利对平台服务及系统进行升级改造，出现此类情况时平台有义务提前告知会员，此过程中出现的服务中断，平台无需承担任何责任。<br>
3、会员管理<br>
   &#12288;本网站对会员的管理依据国家法律、地方法律和国际法律等标准。<br>
4、对会员发布信息的存储和限制<br>
&#12288;本网站不对会员所发布信息的删除或储存失败承担法律责任。本网站有判定会员的行为是否符合国家法律法规和本服务条款的权利。如果会员违背了国家法律法规及本服务条款的规定，本网站有权选择适当的处理方法直至取消会员资格。<br>
5、迅速处理会员投诉。<br>
<b>三、授权条款</b><br>

<b>十一、其他</b><br>
您勾选本协议下方的"同意"按钮即视为您完全接受本协议，在勾选之前请您再次确认已知悉并完全理解本协议的全部内容。<br>
    </p>
    <div class="regist_btn">
      <input type="button" value="我知道了" class="default_btn clause_btn">
    </div>
  </div>
  <!--注册协议弹层END-->
</body>
<script type="text/javascript" src="gv/common/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="gv/common/js/main.js"></script>
<script type="text/javascript" src="gv/member_center/js/style.js"></script>
<script>
  window.onload = function(){
    fnTabImgCode($('.img_pic'),sUrl);//换一张图片验证码
    //实时校验（手机号、图片验证码、短信验证码、登陆密码、确认密码）
    $('.icon_phone').keyup(function(){
        var bIphone = fnIphone($('.icon_phone'));
        if(bIphone){
            $('.mobile').val($(this).val());
        }
    })
    fnCheckImgCode($('.img_code'));
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
    $('.check_tips,.check_tips_checked').click(function(){
       if($('.check_tips_checked').prop('checked')){
         $('.check_tips_checked').prop('checked',false);
       }else{
         $('.check_tips_checked').prop('checked',true);
       }
    });
    //打开注册协议
    $('.check_tips a').click(function(e){
       //注册协议弹层
       $('.clause').show();
       e.stopPropagation(); //阻止事件冒泡
       return false;
    });
    //关闭注册协议
    $('.clause_btn').click(function(){
        $(this).parents('.clause').hide();
    })
    //会员注册
    $('.regist_success').click(function(){
      //注册时校验（手机号、图片验证码、短信验证码、登陆密码、确认密码）
        var bIphone = fnIphone($('.icon_phone'));
        var bCode = fnImgCode($('.img_code'));
        var bPsw = fnPassword($('.password'));
        var bRePsw = fnPassword($('.repeat_password'));
        var bComRePsw = fnRePassword($('.password'),$('.repeat_password'));
        if(bCode){fnTimeImgCode();}
        if(bIphone&&bCode&&bPsw&&bRePsw&&bComRePsw){
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
            //勾选注册协议(提交时判断)
            if(!$('.check_tips input').prop('checked')){
                fnLayerBox('请阅读注册协议！');
                return false;
            }
              fnPasswordLevel($('.password'));//密码安全级别
              $.ajax({
                url:sUrl+'user/register',
                data:{
                  openid:"${openid}",
                  code:$('.img_code').val(),
                  smsCode:$('.mobile_code').val(),
                  passwordLevel:passwordLevel,
                  password:$('.password').val(),
                  repeatPassword:$('.repeat_password').val(),
                  mobile:$('.icon_phone').val()
                },
                success:function(data){
                  if(data.result){
                    $('.layer_box_member').show();
                  }else{
                    fnLayerBox(data.info);
                    $('.img_pic').find('img').attr("src",sUrl+"captcha/drawing?time="+(new Date()).getTime());
                  }
                }
              })
            }
        }
    })
  }
</script>
</html>
