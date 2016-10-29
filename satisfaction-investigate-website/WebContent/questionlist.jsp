<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>问题列表</title>
<script src="js/jquery-1.4.3.min.js"></script>

<script type="text/javascript">
$(function(){
	$(".again").click(function(){
		var id = $(this).siblings(".id").text()
		url = "questionServlet?id=" + id
		location.href = url
	})
})
</script>
</head>


<style type="text/css">
.again{
	cursor:pointer;
	color:red;
}
</style>

<body>
<c:if test="${empty user}">
您尚未登录！
<a href="index.jsp">立即登录</a>
</c:if>

<c:if test="${not empty user}">
<c:if test="${empty qlist}">
<a href="instruction.jsp">回话超时，请返回开始界面</a>
</c:if>
<c:if test="${not empty qlist}">
<a href="instruction.jsp">返回</a>

<c:forEach var="question" items="${qlist}" varStatus="status">    
   <div class="item">题号：<a class="id">${question.id}</a>;产品名：${question.productname};方面：${question.aspect};是否已经作答：${question.answer}
   <a class="again">重新作答</a>
   </div>
</c:forEach> 
</c:if>
</c:if>

</body>
</html>