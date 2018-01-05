<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no" />
  <meta name="format-detection" content="telephone=no,email=no">
  <base href="../"/>
  <title>提示信息</title>
  <link href="gv/common/css/reset.css" rel="stylesheet" type="text/css">
  <link href="gv/common/css/main.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="wrap sys_fail">
  	<img src="gv/common/images/icon_fail.png">
    <p>错误提示：<br/>
    ${message }
    </p>
   </div>
</body>
<script type="text/javascript" src="gv/common/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="gv/common/js/main.js"></script>
</html>
