<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>aSummarizer</title>
<script src="js/jquery-1.4.3.min.js"></script>
<script src="js/modifyPrice.js" type="text/javascript"></script>


<script type="text/javascript">
/* 动态调整网页宽度以及改变背景颜色 */
$(function(){
	var number = $("#numberOfTotal").text();
	var firstIndex = -2;
	
	$("#fuzzyResultBox").css("height",function(){return 150+number*200});
	
	$(".itemBox").each(function(){
		var itemid = $(this).attr("id");
		if(firstIndex == -2)
			firstIndex = $(this).index();
		var index = $(this).index() - firstIndex + 1;
	
		if(index % 2 != 0)//奇数
		{
			$("#" + itemid).css("background-color","rgb(249,249,249)");
		}
		else
		{
			$("#" + itemid).css("background-color","white");
		}		
	})
	
})
</script>

<script type="text/javascript">
/* 动态改变鼠标经过的div的背景颜色 */
$(function(){
	
	var bgcolor;
	
	$(".itemBox").mouseenter(function(){
		bgcolor = $(this).css("background-color");
		$(this).css("background-color","rgb(245,245,245)");
	}).mouseleave(function(){
		$(this).css("background-color",bgcolor);
	})
})
</script>

<script type="text/javascript">
$(function(){
	$(".phoneNameLink").each(function(){
		var allName = $(this).text();
		var startIndex = allName.indexOf(" ");
		var phoneName = allName.substring(startIndex + 1);
		
		phoneName = phoneName.replace(" ","%20");
		phoneName = phoneName.replace(" ","%20");
		phoneName = phoneName.replace(" ","%20");
		phoneName = phoneName.replace(" ","%20");
		phoneName = phoneName.replace("+","%2B");
		phoneName = "phoneBrandChooseAction?phoneName=" + phoneName;
		
		$(this).attr("href",phoneName);
	})
}) 
</script>

</head>

<style type="text/css">

@font-face {
    font-family: 'Minion Pro2';
    src: url('css/fonts/Minion Pro.otf');
  }
  
body{
width:100%;
}

#fuzzyResultBox{

width:96%;
height:150px;
-webkit-border-radius: 5px; 
border: 1px solid rgb(227,227,227);
background-color: white;

position:absolute;
top:80px;
left:2%;
overflow-x:auto;
}

.itemBox{
width:90%;
height:200px;
position:relative;
left:55px;

bolder:none;
background-color:rgb(249,249,249);
overflow-x:auto;
overflow-y:hidden;
}


.phonePictureBox{
width:150px;
height:150px;
-webkit-border-radius: 5px; 
border: 1px solid rgb(227,227,227);
background-color: white;

position:relative;
left:20px;
top:24px;
}


</style>

<body>
 <%@ include file="head.jsp" %> 
 <div id="numberOfTotal" style="display:none;"><s:property value="numberOfItem"/></div>
<div id="fuzzyResultBox">
	<h4 id="productDetail" style="z-index:1;left:50px;font-size:23px;float:left;"><a href="Index.jsp" style="color: rgb(66,139,202);text-decoration:none;">Home</a></h4>
    <h4 id="productDetail" style="width:150px;left:55px;font-size:23px;color:rgb(119,119,119);"> / Result</h4> 
    <br style="clear:both;"/> 
    	<hr  size="1" width="93%" color="#D3D3D3"/>
    	 
    		
    	<s:iterator value="summarySet" status="i">
		<div class="itemBox" id="<s:property value='#i.count'/>_item">
			<div class="phonePictureBox">	
				<img style="max-height:95%;width:auto;position:relative;left:36px;top:4px;" src="<s:property value='pictureCoverURL'/>" alt="<s:property value='pictureCoverURL'/>"/>
			</div>
			<div class="phoneNameLine" style="color:rgb(255,67,4);position:relative;left:250px;bottom:130px;width:200px;">
				<a class="phoneNameLink" href="" style="color:rgb(255,67,4);text-decoration:none;cursor:pointer;"><s:property value="brand"/> <s:property value="productName"/></a>
			</div>
			<div class="phonePriceLine" style="color:black;position:relative;left:250px;bottom:100px;width:500px;">
				<div style="font-family:'Minion Pro2';font-size: 18px;font-weight:800;">Price:</div>
				<div style="position:relative;left:50px;bottom:30px;color:black;font-family:'Myanmar MN';font-size:16px;" id="infoResultPrice" class="infoResultPriceClass"><s:property	value="price"/></div>
    			</div>
			<div class="phonePublishLine" style="color:black;position:relative;left:250px;bottom:90px;width:300px;">
				<div style="position:relative;bottom:20px;font-family:'Minion Pro2';font-size: 18px;font-weight:800;">Year of Release:</div>
				<div style="position:relative;left:130px;bottom:50px;color:black;font-family:'Myanmar MN';font-size:16px;" ><s:property value="publishDate"/></div>			
			</div>
		
		
			<div id="reviewResult" style="z-index:1;position:relative; left:800px; bottom:280px;width:130px;">
	    				<div id="reviewTotal" style="color:black;">Evaluations: <span id="reviewTotalNumber"><s:property value="reviewNumberResult[#i.index].getTotalNumber()" /></span></div>
	    				<div id="reviewSource" style="color:black;">zol: <span id="reviewNumber"><s:property value="reviewNumberResult[#i.index].getZol()" /></span></div>
	    				<div id="reviewSource" style="color:black;">it168: <span id="reviewNumber"><s:property value="reviewNumberResult[#i.index].getIt168()" /></span></div>
	    				<div id="reviewSource" style="color:black;">sina: <span id="reviewNumber"><s:property value="reviewNumberResult[#i.index].getSina()" /></span></div>
	    				<div id="reviewSource" style="color:black;">pconline: <span id="reviewNumber"><s:property value="reviewNumberResult[#i.index].getPconline()" /></span></div>
    			</div> 
		
		</div>
	</s:iterator> 
    		
</div>
	 

</body>
</html>