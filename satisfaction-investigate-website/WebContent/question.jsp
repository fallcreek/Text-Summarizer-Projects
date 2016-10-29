<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>评测页面</title>
<script src="js/jquery-1.4.3.min.js"></script>

<script type="text/javascript">
var left = 0
var right = 0

var q1 = -1
var q2 = -1
var q3 = -1


$(function(){
	$(".item").each(function(i){
		var sentence1 = $(this).text().substr(2)
		var obj1 = $(this)
		$(".item").each(function(j){
			if(i < j)
			{
				var sentence2 = $(this).text().substr(2)
				if(sentence1 == sentence2)
				{
					obj1.css("color","orange")
					$(this).css("color","orange")
				}
			}		
		})
	})
})

$(function(){
	$("#submit").click(function(){
		url = "completeServlet?id="+ ${question.id} + "&q1=" + q1 + "&q2=" + q2 + "&q3=" + q3;
		location.href = url
	})
	
})


$(function(){
	$(".button").click(function(){
		var id = $(this).attr("id")
		$(this).siblings(".result").html(id)
		var result = -1
		if(id == "左侧更好"){
			$(this).siblings(".result").css("color","red")
			result = left
		}	
		else if(id == "右侧更好"){
			$(this).siblings(".result").css("color","blue")
			result = right
		}		
		else if(id == "两者一致"){
			$(this).siblings(".result").css("color","green")
			result = 0
		}
		
		var question = $(this).parent().attr("id")
		if(question == "q1")
			q1 = result
		else if(question == "q2")
			q2 = result
		else if(question == "q3")
			q3 = result
		
		if(q1 != -1 && q2 != -1 && q3 != -1)
		{
			$("#submit").css("display","block")
		}
			
	})
}) 

$(function(){
	var random = getRandom(2)
	var finished = 0
	$(".abox").each(function(i){
		if(random == 1 )
		{
			if(i == 0)
			{
				$(this).css("left","50%")	
				right = $(this).attr("id")			
			}
			else
			{
				left = $(this).attr("id")
			}
					
		}
		else if(random == 2)
		{
			if(i == 1)
			{
				$(this).css("left","50%")	
				right = $(this).attr("id")
			}
			else
			{
				left = $(this).attr("id")			
			}
		}		
	})
})

function getRandom(n){
        return Math.floor(Math.random()*n+1)
        }
        


</script>

</head>

<style type="text/css">
.abox{
	width:45%;
	top:150px;
	position:absolute;
	background-color:rgba(227,227,227,0.2);
	-webkit-border-radius: 5px; 
	border: 1px solid rgb(227,227,227);
	left:2%;
}


.button{
	width:25%;
	cursor:pointer;
	margin:3%;
}

.aspect{
	background-color:rgba(227,227,227,0.2);
	-webkit-border-radius: 5px; 
	border: 1px solid rgb(227,227,227);
	width:200px;
	height:100px;
	text-align:center;
	position:relative;
	top:430px;
	left:40%;
}

.aspectText{
	font-size:15px;
}

#submit{
	font-size:25px;
	position:absolute;
	top:480px;
	left:46%;
	color:orange;
	cursor:pointer;
}

#allquestion{
	position:absolute;
	left:70%;
	font-size:30px;
	color:red;
	cursor:pointer;
}

.img{
	width:30%;
	position:absolute;
	top:550px;
	left:5%;
	z-index:-2;
}
</style>

<body>

<c:if test="${empty user}">
您尚未登录！
<a href="index.jsp">立即登录</a>
</c:if>

<c:if test="${not empty user}">
<c:if test="${empty question}">
<a href="instruction.jsp">回话超时或暂无题目，请返回开始界面</a>
<div id="allquestion"><a href="allquestionServlet">预览全部题目</a></div>
</c:if>
<c:if test="${not empty question}">
编号：${question.id}<br>
产品名称：${question.productname}<br>

方面：${question.aspect}:
<c:choose>  
  
   <c:when test="${question.aspect == 1}">
    外观 外形 外型 外壳 外表
   </c:when> 
   <c:when test="${question.aspect == 2}">
    质量 材质 手感 质感 作工 做工
   </c:when> 
   <c:when test="${question.aspect == 3}">
    屏幕 触摸屏 分辨率 led 触摸板 液晶屏 电阻屏 显示屏 触屏
   </c:when> 
   <c:when test="${question.aspect == 4}">
    性价比 价位 价钱 价格 售价
   </c:when> 
   <c:when test="${question.aspect == 5}">
    系统 稳定性 性能 速度  兼容性
   </c:when> 
   <c:when test="${question.aspect == 6}">
    操控 操作 触控
   </c:when> 
   <c:when test="${question.aspect == 7}">
    电池 待机 电量 续航 耗电
   </c:when> 
   <c:when test="${question.aspect == 8}">
    键盘 按键 功能键 按钮
   </c:when> 
   <c:when test="${question.aspect == 9}">
    信号 网络 蓝牙 通话  天线 通信 通讯
   </c:when> 
   <c:when test="${question.aspect == 10}">
    界面 画面 画质 ui
   </c:when> 
   <c:when test="${question.aspect == 11}">
    机型 机身  款式 样式
   </c:when> 
   <c:when test="${question.aspect == 12}">
    照相 摄像 照像 相机 拍照 镜头 像素 闪光灯 录音
   </c:when> 
   <c:when test="${question.aspect == 13}">
    音效 音色 音质 话筒 听筒 扬声器 喇叭 话音 音响 语音 立体声
   </c:when> 
   <c:when test="${question.aspect == 14}">
    存储 内存 储存卡 扩展卡
   </c:when>     
   <c:otherwise>  
   出错了！  
   </c:otherwise>  
</c:choose>

<br>

<div id="allquestion"><a href="allquestionServlet">预览全部题目</a></div>

<c:if test="${not empty question.mmr}">
<div id="1" class="abox">
<c:forEach var="sentence" items="${question.mmr}" varStatus="status">    
   <div class="item">${status.count}:${sentence}</div>
</c:forEach>
</div>

</c:if>



<c:if test="${not empty question.mlp}">
<div id="2" class="abox">
<c:forEach var="sentence" items="${question.mlp}" varStatus="status">    
   <div class="item">${status.count}:${sentence}</div>
</c:forEach>  
</div>  

</c:if>


<c:if test="${not empty question.ilp}">
<div id="3" class="abox">
<c:forEach var="sentence" items="${question.ilp}" varStatus="status">    
   <div class="item">${status.count}:${sentence}</div>
</c:forEach>     
</div>  
</c:if>

<br>
<div id="q1" class="aspect">
<div class="aspectText">相关性</div>
<div class="result"></div>
<img alt="zan" src="img/zan.png" id="左侧更好" class="button">
<img alt="zan" src="img/equal.png" id="两者一致" class="button">
<img alt="zan" src="img/zan.png" id="右侧更好" class="button"> 
</div>

<div id="q2" class="aspect">
<div class="aspectText">丰富性</div>
<div class="result"></div>
<img alt="zan" src="img/zan.png" id="左侧更好" class="button">
<img alt="zan" src="img/equal.png" id="两者一致" class="button">
<img alt="zan" src="img/zan.png" id="右侧更好" class="button">  
</div>

<div id="q3" class="aspect">
<div class="aspectText">冗余性</div>
<div class="result"></div>
<img alt="zan" src="img/zan.png" id="左侧更好" class="button">
<img alt="zan" src="img/equal.png" id="两者一致" class="button">
<img alt="zan" src="img/zan.png" id="右侧更好" class="button">  
</div>

<div id="submit" style="display:none">提交</div>

<img alt="1" src="img/2.png" class="img">
</c:if>


</c:if>

</body>
</html>