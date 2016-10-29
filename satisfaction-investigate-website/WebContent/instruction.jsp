<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>评分细则</title>
</head>

<style type="text/css">
.img{
	width:100%;
}
</style>

<body>

<c:if test="${empty user}">
您尚未登录！
<a href="index.jsp">立即登录</a>
</c:if>

<c:if test="${not empty user}">
您已登陆！


<a href="scoreServlet">开始打分</a>
</c:if>
<br>

首先说明一下我的工作：现在我有许多关于iphone6的评测文章，这些文章对iphone6的各个方面（外观、电池、屏幕...）进行了评价。
我要做的就是通过算法生成这些评测文章在iphone6不同方面（外观、电池、屏幕...）的摘要。
通过3种不同的摘要算法，我已经有了3份不同的摘要结果。现在，我需要知道这三个算法孰优孰劣。于是请求你帮我为对3个算法生成的结果进行评价。
一共有14个不同的方面（外观、电池、屏幕...），这3个算法在这14个方面都会有一份摘要结果。
对于算法A,B,C，我将依次展示A和B，A和C，B和C的摘要结果，请你为其进行评价。每一次呈现的算法结果的位置是随机的，以免你受到干扰。
也希望你对本轮评价的结果不要受到之前评价结果的影响，谢谢！
点击上面的“开始打分”按钮，你将看到下述内容：

<img alt="1" src="img/1.png" class="img">
<img alt="1" src="img/2.png" class="img">
<img alt="1" src="img/3.png" class="img">



</body>
</html>