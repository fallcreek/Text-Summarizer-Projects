<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆</title>
</head>

<style type="text/css">
#return{
	color:grey;
	text-decoration:none;
	position:absolute;
}

#loginbox{
	position:absolute;
	width:30%;
	height:250px;
	top:30%;
	left:35%;
	background-color:rgba(227,227,227,0.5);
	-webkit-border-radius: 5px; 
	border: 1px solid rgb(227,227,227);
		
}

#errorinfo{
	color:red;
	font-size:20px;
	position:absolute;
	left:40px;	
}

#formbox{
	position:absolute;
	top:40px;
	left:20%;
}
.input{
	font-size:20px;
}
#register{
	position:absolute;
	top:75%;
	left:20%;
	color:blue;
	text-decoration:none;
	
}
</style>


<body>

<div id="loginbox">
<div id="errorinfo">${errInfo}</div>
 
<div id="formbox">
<form action="loginServlet" method="post">
	<div class="input">用户名：</div><input name="username" type="text"> <br/>
	<div class="input">密码：</div><input name="password" type="password"><br/>
	
	<input type="submit" value="提交"><br/>
</form>
</div>

</div>

</body>
</html>