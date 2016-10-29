<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script src="js/jquery-1.4.2.min.js"></script>

</head>
<style type="text/css">


#mask{  
	
    width: 100%;  
    height: 100%; 
    background:  black ;  
    filter: ALPHA(opacity =60); 
    opacity:0.6;  
    z-index: 2;  
    visibility: hidden;  
}  

#around{
	width: 100%;  
    height: 100%; 
    background:  white ;  
    filter: ALPHA(opacity =0); 
    opacity:0;  
    z-index: 3;  
    visibility: hidden;  
}

#teamcont{
	width: 50%px;
	height: 50%px;
	margin: 0 auto;
	padding:0 auto;

}


.parinforma{
	bottom:100px;
	z-index:10;
	width:710px;
	height:500px;
	color:black;
	display:none;
	border: 2px solid rgb(227,227,227);
	-moz-border-radius:12px;
	-webkit-border-radius:12px;
	border-radius:12px;
	background:rgb(238,238,238);

	overflow-y:auto;
}

.showRight{
	text-align:center;
}

.showRight  .huncontent{
	padding-left:50px;
	font-size:18px;
	line-height:35px;
	width:600px;
	text-align:left;
}

.closebtn{
	clear:both;
	position:relative;
	top:3px;
	left:670px;
	width:36px;
	height:36px;
	background-image:url(img/close-n.png);
	cursor:pointer;
}

</style>
<body>

<div id="mask"></div>
<div id="around"></div>

<div id="teamcont" >
	<div style="width:780px;margin:30px auto; text-align:center">
			<div class="parinforma ">
				<div class="closebtn"></div>
				
				<div class="showRight">
					<div class="huncontent">	
						<!-- 动态生成 -->	
					</div>
				</div>
				<div style="clear:both"></div>
			</div>
	</div>		
</div>	

<script type="text/javascript">
$("#evaluationViewMore").click(function(){
	
	var passage = $("#evaluationContent ol.evaluationContentList li:visible").html();
	
	$(".huncontent").html(passage);
	
	var top = ($(window).height() - $(".parinforma").height())/2;   
    var left = ($(window).width() - $(".parinforma").width())/2;   
    var scrollTop = $(document).scrollTop();   /* 滚动条顶部偏移 */
    var scrollLeft = $(document).scrollLeft(); 
    $("#mask").css({position : 'absolute', 'top' : scrollTop, left : scrollLeft, visibility:"visible"});   //将ID为mask的CSS属性改为显示
    $("#around").css({position : 'absolute', 'top' : scrollTop, left : scrollLeft, visibility:"visible"});   //将ID为mask的CSS属性改为显示
   
    $(".parinforma").css( {  position : 'absolute', 'top' : top + scrollTop, left : left + scrollLeft } ).slideDown("normal");  
    $(".parinforma").scrollTop(0);
   
    $("body").css({overflow:"hidden"});    //禁用滚动条
	
    
	
})

$("#around").click(function(){
	$(".parinforma").hide();
	 $("body").css({overflow:"auto"}); 
	 $("#mask").css({visibility:"hidden"});
	 $("#around").css({visibility:"hidden"});
})
	


$("#teamcont .closebtn").mouseenter(function(){
	$(this).css({'background':'url(img/close-s.png)'});
}).mouseleave(function(){
	$(this).css({'background':'url(img/close-n.png)'});
})

$("#teamcont .closebtn").click(function(){
	$(".parinforma").hide();
	
	 $("body").css({overflow:"auto"}); 
	 $("#mask").css({visibility:"hidden"});
	 $("#around").css({visibility:"hidden"}); 
})


</script>


</body>
</html>