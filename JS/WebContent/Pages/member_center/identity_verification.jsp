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
  <title>个人资料</title>
  <link href="gv/common/css/reset.css" rel="stylesheet" type="text/css">
  <link href="gv/common/css/mobiscroll.custom-2.5.0.min.css" rel="stylesheet" type="text/css">
  <link href="gv/common/css/main.css" rel="stylesheet" type="text/css">
  <link href="gv/member_center/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="wrap">
    <div class="member_center">
    	<div class="center_img">
      	<img src="gv/member_center/images/icon_card.png"/>
      </div>
    </div>
    <div class="personal_data_container">
      <p class="personal_data_tab clearfix">
        <a href="javascript:;" class="current">身份信息</a>
        <a href="javascript:;">其他信息</a>
      </p>
      <div class="data_container" id="personal_info" v-cloak>
      	<div class="data_list customer_info">
        	<p class="data_list_title">完善身份信息后，将自动绑定您在华夏人寿保险购买的保单。为了您的账户安全，绑定保单后，身份信息将不能修改哦！</p>
        	<ul class="personal_setting_con" v-if='perInfo.bindStatus' id="user_info">
                <li>
                  <label>姓名</label>
                  <span>{{perInfo.userInfo.userName}}<input type="hidden" class="user_name" value="{{perInfo.userInfo.userName}}"/></span>
                </li>
                <li>
                  <label>证件类型</label>
                  <span>{{perInfo.idTypeName}}<input type="hidden" class="id_type" value="{{perInfo.userInfo.idType}}" /></span>
                </li>
                <li>
                  <label>证件号码</label>
                  <span>{{perInfo.userInfo.idNo}}<input type="hidden" class="id_no" value="{{fullIdNo}}" /></span>
                </li>
                <li>
                  <label>性别</label>
                  <input type="hidden" class="gender" value="{{perInfo.userInfo.gender}}" />
                  <span v-if="perInfo.userInfo.gender=='0'">男</span>
                  <span v-if="perInfo.userInfo.gender=='1'">女</span>
                </li>
                <li>
                  <label>出生日期</label>
                  <input type="hidden" class="birthday" value="{{perInfo.userInfo.birthday}}" />
                  <span>{{perInfo.userInfo.birthday}}</span>
                </li>
            </ul>
            <ul class="personal_setting_con" v-else>
                <li>
                  <label>姓名</label>
                  <input type="text" placeholder="请输入2个及2个以上汉字或英文" value="{{perInfo.userInfo.userName}}" maxlength="40" class="user_name" oncopy="return false;" oncut="return false;" onpaste="return false;" oncontextmenu="return false;"/>
                  <p class="wrong_tips"></p>
                </li>
                <li>
                  <label>证件类型</label>
                  <input type="hidden" name="" class="id_type_name" />
                  <select name="idType" class="id_type">
                    <option v-if="!perInfo.userInfo.idType">请选择证件类型</option>
                    <option v-for="item in idTypes" value="{{item.codeCode}}">{{item.codeCName}}</option>
                  </select>
                  <p class="wrong_tips"></p>
                </li>
                <li>
                  <label>证件号码</label>
                  <input type="text" placeholder="请输入有效的证件号码" value="{{perInfo.userInfo.idNo}}" class="id_no" maxlength="25" oncopy="return false;" oncut="return false;" onpaste="return false;" oncontextmenu="return false;"/>
                  <p class="wrong_tips"></p>
                </li>
                <li>
                  <label>性别</label>
                  <input type="hidden" name="" class="gender_name" />
                  <select class="gender">
                    <option v-if="!perInfo.userInfo.gender">请选择性别</option>
                    <option value="0">男</option>
                    <option value="1">女</option>
                  </select>
                  <p class="wrong_tips"></p>
                </li>
                <li>
                  <label>出生日期</label>
                  <input type="text" value="{{perInfo.userInfo.birthday}}" readonly id="birthdayDate" placeholder="请选择出生日期" class="birthday" />
                  <p class="wrong_tips"></p>
                </li>
          </ul>
        </div>
        <div class="data_list other_info" >
          <ul class="personal_setting_con">
            <li>
                <label>邮箱地址</label>
                <input type="text" placeholder="请输入邮箱地址" value="{{perInfo.userInfo.email}}" name="email" class="email">
                <p class="wrong_tips"></p>
            </li>
            <li>
                <label>所在省份</label>
                <input  type="hidden" name="" value="" class="province" />
                <select name="provice" class="province_select">
                    <option value="">请选择</option>
                    <option v-for="item in provinceList" value="{{item.gId}}">{{item.gName}}</option>
                </select>
            </li>
            <li>
                <label>所在市</label>
                <input  type="hidden" name="" value="" class="city" />
                <select name="" class="city_select">
                    <option value="">请选择</option>
                    <option v-for="item in cityList" value="{{item.gId}}">{{item.gName}}</option>
                </select>
            </li>
            <li>
                <label>区/县</label>
                <input  type="hidden" name="" value="" class="area" />
                <select name="" class="area_select">
                    <option value="">请选择</option>
                    <option v-for="item in areaList" value="{{item.gId}}">{{item.gName}}</option>
                </select>
            </li>
            <li>
                <label>详细地址</label>
                <input type="text" placeholder="请输入详细地址" value="{{perInfo.userInfo.address}}" name="address" class="address">
                <p class="wrong_tips"></p>
            </li>
          </ul>
        </div>
      </div>
      <div class="regist_btn single_btn">
        <input type="button" value="保存" class="default_btn save_btn">
      </div>
    </div>
      <!--弹层1-->
      <div class="layer_box_member">
        <div class="layer_main">
          <div class="layer_password load_see">
          	<img src="gv/member_center/images/img_woman.gif">
            <div>
            	<h3>信息提交成功！</h3>
            </div>
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
<script type="text/javascript" src="gv/common/js/mobiscroll.custom-2.5.0.min.js"></script>
<script type="text/javascript" src="gv/common/js/main.js"></script>
<script type="text/javascript" src="gv/member_center/js/style.js"></script>
<script>
function getZero(num){return num < 10 ? '0'+num : num;}
    //倒计时
    var timer;
    var countdown=5;
    function fnCountDown(val) {
      timer = setTimeout(function() {
        fnCountDown(val)
      },1000)
      if (countdown == 0) {
        clearTimeout(timer);
        val.prop('disabled',false);
        val.val('我知道了');
        $('.layer_btn').click(function(){
            location.href=sUrl+"center/toIndex";
        })
      } else {
        val.prop('disabled',true);
        val.val('我知道了('+countdown+'s)');
        countdown--;
      }
    }
window.onload = function(){
  var personalInfo = new Vue({
    el:"#personal_info",
    data:{
      perInfo:{},//客户身份信息&其他信息
      idType:'',//证件类型code
      idTypes:[],//证件类型列表
      provinceList:[],//所在省列表
      province:'',//所在省code
      cityList:[],//所在市列表
      city:'',//所在市code
      areaList:[],//所在区列表
      area:'',//所在区code
      memberSign:'',//key
      fullIdNo:''//完整证件号
    }
  })
  //身份信息与其他信息切换
  $('.data_list').eq(0).show().siblings('.data_list').hide();
  var bOffBtn = 0;//是否点击其他信息
  $('.personal_data_tab a').click(function(){
    var ind = $(this).index();
    $(this).addClass('current').siblings('a').removeClass('current');
    $('.data_list').eq(ind).show().siblings('.data_list').hide();
    if(ind == 0){
        $('.save_btn').attr('bOff',false);
    }else{
        $('.save_btn').attr('bOff',true);
        bOffBtn = 1;
        fnPlace();
    }
    return false;
  })
  //日期控件
  var now = new Date();
  var currYear = now.getFullYear();
  var currMonth = now.getMonth() + 1;
  var currDay = now.getDate();
  var hours = now.getHours();
  var minutes = now.getMinutes();
  var seconds = now.getSeconds();
  var milliseconds = now.getMilliseconds();
  //初始化日期控件
  var opt1 = {
    preset: 'date', //日期，可选：date\datetime\time\tree_list\image_text\select
    theme: 'android-ics light', //皮肤样式，可选：default\android\android-ics light\android-ics\ios\jqm\sense-ui\wp light\wp
    display: 'modal', //显示方式 ，可选：modal\inline\bubble\top\bottom
    mode: 'scroller', //日期选择模式，可选：scroller\clickpick\mixed
    lang: 'zh',
    dateFormat: 'yyyy-mm-dd', // 日期格式
    setText:'确认', //确认按钮名称
    cancelText:'取消', //取消按钮名籍我
    dateOrder: 'yyyymmdd', //面板中日期排列格式
    dayText: '日',
    monthText: '月',
    yearText: '年', //面板中年月日文字
    showNow: false,
    nowText: "今",
    minDate: new Date(currYear - 100, currMonth - 1, currDay+1),
    maxDate: new Date(currYear, currMonth - 1, currDay)
  };
  $("#birthdayDate").mobiscroll(opt1);

  //Ajax请求客户身份信息&其他信息（返回所有数据）
  $.ajax({
    url:sUrl+'personalInfo/getInfo',
    data:{
        openid:"${openid}"
    },
    success:function(data){
      if(data.result){
        personalInfo.perInfo = data.object;
        personalInfo.memberSign = data.object.memberSign;
        personalInfo.fullIdNo = data.object.fullIdNo;
        personalInfo.idType = data.object.userInfo.idType;
        personalInfo.province = data.object.userInfo.province;
        personalInfo.city = data.object.userInfo.city;
        personalInfo.area = data.object.userInfo.area;
        personalInfo.$set("idTypes",data.object.idTypes);//证件类型列表
        Vue.nextTick(function(){
          //证件类型
          fnCurBank($('.id_type'),personalInfo.idType,$('.id_type_name'));
          fnAutoId();
          //出生日期控件显示
          if($('.birthday').val()!=''){
              var y = $('.birthday').val().substring(0,4);
              var m = $('.birthday').val().substring(5,7);
              var d = $('.birthday').val().substring(8,10);
              $("#birthdayDate").scroller('setDate',new Date(y,m-1,d,1,0,0),true);
          }
          fnInit();
        })
      }else{
        fnLayerBox(data.info);
      }
    }
  })
function fnInit(){
    //5要素不可修改，没有保存按钮
    if($('#user_info').length){
      $('.regist_btn').hide();
      $('.personal_data_tab a').click(function(){
        var ind = $(this).index();
        if(ind == 0){
          $('.regist_btn').hide();
        }else{
          $('.regist_btn').show();
        }
        return false;
      })
    }
    if(personalInfo.perInfo.userInfo.gender){
      fnCurBank($('.gender'),personalInfo.perInfo.userInfo.gender,$('.gender_name'));//性别
    }else{
      $('.gender option').eq(0).prop('selected',true);
    }

    //实施校验5要素（可修改的情况下）
    fnCheckAll();

    //提交后再次加载保单数据
    function fnAjaxPolicys(data){
        setTimeout(function(data){
            $.ajax({
                url:sUrl+'personalInfo/getPolicysNo',
                beforeSend:function(){
                    $('.loading_layer').remove();//删除loading
                },
                success:function(data){
                    if(data.result){
                         $('.layer_main_tips p').text(data.info);
                    }
                }
            });
        },2000);
    }
    //保存个人资料
    $('.save_btn').click(function(){
    //保存校验5要素
    var bName = fnUserName($('.user_name'));
    var bId = fnId($('.id_no'),$('.id_type'));
    var bType = fnInitBank($('.id_type'),'证件类型');
    var bGender = fnInitBank($('.gender'),'性别');
    var bBirth = fnBirthday($('.birthday'));
    var bEmail = fnEmail($('.email'));
    var bAddress=fnDetailsAddress($('.address'));
    var bOff = $(this).attr('bOff');
    //保存时，请先完善要素
    if(bOff=='true' && bEmail && bAddress){
        if(!bName || !bId || !bType || !bGender || !bBirth){
           fnLayerBox('请先完善客户身份信息！');
        }
    }
    if(bName && bId && bType && bGender && bBirth && bEmail && bAddress){
      var province,city,area;
      if(bOffBtn != 1){
        province = $('.province_select').val() ? $('.province_select').val() : personalInfo.province;
        city = $('.city_select').val() ? $('.city_select').val() : personalInfo.city;
        area = $('.area_select').val() ? $('.area_select').val() : personalInfo.area;
      }else{
        province = $('.province_select').val();
        city = $('.city_select').val();
        area = $('.area_select').val();
      }
      $.ajax({
        url:sUrl+'personalInfo/changeInfo',
        data:{
          openid:"${openid}",
          memberSign:personalInfo.memberSign,
          userName:$('.user_name').val(),
          idNo:$('.id_no').val(),
          idType:$('.id_type').val(),
          gender:$('.gender').val(),
          birthday:$('.birthday').val(),
          email:$('.email').val(),
          province:province,
          city:city,
          area:area,
          address:$('.address').val()
        },
        success:function(data){
          if(data.result){
            if(data.object.hasChange){
              //匹配了保单
              fnLayerBox(data.info);
              //2秒后请求
                fnAjaxPolicys(data);
              $('.layer_btn').prop('disabled',true);
              $('.layer_btn').val('我知道了(5s)');
              fnCountDown($('.layer_btn'));
            }else{
              //正在匹配保单
              $('.layer_box_member').show();
            }
          }else{
            fnLayerBox(data.info);
          }
        }
      })
    }
    })

}
//选择省市区
function fnPlace(){
    //Ajax所在省市区
    personalInfo.$set("provinceList",[]);
    $.ajax({
        url:sUrl+'basicData/queryAreaData',
        data:{
            areaLeve:'00',
            areaCode:''
        },
        success:function(data){
            if(data.result){
                personalInfo.$set("provinceList",data.object.baseAreaList);
              //数据加载完成后执行
                Vue.nextTick(function(){
                    //证件类型
                    fnCurBank($('.province_select'),personalInfo.province,$('.province'));
                    personalInfo.$set('cityList',[]);
                    fnLineCity($('.province_select'));
                })
            }else{
              fnLayerBox(data.info);
            }
        }
    })
    function fnLineCity(obj){
      var areaCode = obj.find('option:selected').val();
      //请求市
      $.ajax({
        url:sUrl+'basicData/queryAreaData',
        data:{
          areaCode:areaCode
        },
        success:function(data){
          if(data.result){
            personalInfo.$set('cityList',data.object.baseAreaList);
            Vue.nextTick(function(){
              fnCurBank($('.city_select'),personalInfo.city,$('.city'));
              personalInfo.$set('areaList',[]);
              fnLineArea($('.city_select'));
            })
          }else{
            fnLayerBox(data.info);
          }
        }
      });
    }
    //所在市加载区
    function fnLineArea(obj){
        var areaCode = obj.find('option:selected').val();
        //请求区
        $.ajax({
            url:sUrl+'basicData/queryAreaData',
            data:{
                areaCode:areaCode
            },
            success:function(data){
                if(data.result){
                    personalInfo.$set('areaList',data.object.baseAreaList);
                    Vue.nextTick(function(){
                    fnCurBank($('.area_select'),personalInfo.area,$('.area'));
                })
                }else{
                    fnLayerBox(data.info);
                }
            }
        });
    }

    $('.province_select').change(function(){
        personalInfo.$set('cityList',[]);
        fnLineCity($('.province_select'));
    })
    $('.city_select').change(function(){
        personalInfo.$set('areaList',[]);
        fnLineArea($('.city_select'));
    })
}

}

//10要素默认校验
function fnCheckAll(){
  //5要素
  fnCheckName($('.user_name'));
  fnCheckBank($('.id_type'),'证件类型');
  fnCheckId($('.id_no'),$('.id_type'));
  $('.id_no').keyup(function(){
    fnAutoId();
  })
  $('.id_no').bind('input',function(){
    fnAutoId();
  })
  $('.id_type').change(function(){
    fnAutoId();
  })
  fnCheckBank($('.gender'),'性别');
  fnCheckBirthday($('.birthday'));

  //邮箱&详细地址正确性校验
  fnCheckEmail($('.email'));
  fnCheckDetailsAddress($('.address'));
}
//如果选择的是身份证 证件类型，输入身份证号检验通过，自动带出性别和生日
function fnAutoId(){
  var bOff = fnId($('.id_no'),$('.id_type'));
  if(bOff && $('.id_type option:selected').val()=="01"){
        $('.gender').prop('disabled',true);
        $('#birthdayDate').prop('disabled',true);
  }else{
    $('.gender').prop('disabled',false);
    $('#birthdayDate').prop('disabled',false);
  }
}

</script>
</html>
