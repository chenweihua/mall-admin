<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<!--
        ===
        This comment should NOT be removed.

        Charisma v2.0.0

        Copyright 2012-2014 Muhammad Usman
        Licensed under the Apache License v2.0
        http://www.apache.org/licenses/LICENSE-2.0

        http://usman.it
        http://twitter.com/halalit_usman
        ===
    -->
<meta charset="utf-8">
<title>小麦公社后台管理系统</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="小麦公社后台管理系统">
<meta name="author" content="tom">

<!-- The styles -->
<link id="bs-css" href="/css/bootstrap-cerulean.min.css"
	rel="stylesheet">

<link href="/css/charisma-app.css" rel="stylesheet">
<link href='/bower_components/fullcalendar/dist/fullcalendar.css'
	rel='stylesheet'>
<link href='/bower_components/fullcalendar/dist/fullcalendar.print.css'
	rel='stylesheet' media='print'>
<link href='/bower_components/chosen/chosen.min.css' rel='stylesheet'>
<link href='/bower_components/colorbox/example3/colorbox.css'
	rel='stylesheet'>
<link href='/bower_components/responsive-tables/responsive-tables.css'
	rel='stylesheet'>
<link
	href='/bower_components/bootstrap-tour/build/css/bootstrap-tour.min.css'
	rel='stylesheet'>
<link href='/css/jquery.noty.css' rel='stylesheet'>
<link href='/css/noty_theme_default.css' rel='stylesheet'>
<link href='/css/elfinder.min.css' rel='stylesheet'>
<link href='/css/elfinder.theme.css' rel='stylesheet'>
<link href='/css/jquery.iphone.toggle.css' rel='stylesheet'>
<link href='/css/uploadify.css' rel='stylesheet'>
<link href='/css/animate.min.css' rel='stylesheet'>
<link href='/css/datepicker3.css' rel='stylesheet'>

<!-- jQuery -->
<script src="/bower_components/jquery/jquery.min.js"></script>

<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<!-- The fav icon -->
<link rel="shortcut icon" href="/img/favicon.ico">

</head>
<body>
      <%@ include file="modifymypassword.jsp"%>
	<!-- topbar starts -->
	<div class="navbar navbar-default" role="navigation">

		<div class="navbar-inner">
			<button type="button" class="navbar-toggle pull-left flip">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" style="width: 500px;" href="#"> <!-- <img alt="小麦公社logo" src="" class="hidden-xs"/> -->
				<span>小麦公社后台管理系统</span></a>

			<!-- user dropdown starts -->
			<div class="btn-group pull-right">
				<button class="btn btn-default dropdown-toggle"
					data-toggle="dropdown">
					<i class="glyphicon glyphicon-user"></i><span
						class="hidden-sm hidden-xs"> ${user.user_name }</span> <span
						class="caret"></span>
				</button>
				<ul class="dropdown-menu">
					<!-- <li><a href="#">Profile</a></li> -->
					<!-- <li class="divider"></li> -->
					<li><a class="blueletter" href="javascript:void(0)" onclick="modifyMyPassword();">修改密码</a></li>
					<li><a href="/admin/logout">退出</a></li>
				</ul>
			</div>
			<!-- user dropdown ends -->

		</div>
	</div>
	
	<div class="ch-container">
		<div class="row">
			<%@ include file="menu.jsp"%>
			<div id="content" class="col-lg-10 col-sm-10">