<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Moving Boxes</title>
	
<link rel="stylesheet" href="css/style.css" type="text/css" media="screen" charset="utf-8">

<script src="js/jquery-1.3.1.min.js" type="text/javascript" charset="utf-8"></script>
<script src="js/pictureShow.js"></script>
<script src="js/slider.js" type="text/javascript" charset="utf-8"></script>


	
</head>


<style type="text/css">


#mask2{  
	position:absolute;
    width: 100%;  
    height: 100%; 
    background:  black ;  
    filter: ALPHA(opacity =60); 
    opacity:0.6;  
    z-index: 2;  
    visibility: hidden;  
}  

#around2{
	position:absolute;
	width: 100%;  
    height: 100%; 
    background:  white ;  
    filter: ALPHA(opacity =0); 
    opacity:0;  
    z-index: 3;  
    visibility: hidden;  
}

#teamcont2{
	
	margin: 0 auto;
	padding:0 auto;
}


.parinforma2{
	bottom:100px;
	z-index:10;
	width:70%;
	height:80%;
	color:black;
	display:none;
	border: 2px solid rgb(227,227,227);
	-moz-border-radius:12px;
	-webkit-border-radius:12px;
	border-radius:12px;
	background:rgb(238,238,238);

	overflow-y:auto;
}

.showRight2{
	text-align:center;
	width:100%;
	height:100%;
	
}

.showRight2  .huncontent2{
	
	font-size:18px;
	line-height:35px;
	width:90%;
	height:90%;
	left:20px;
	text-align:center;

}

.closebtn2{
	clear:both;
	position:relative;
	top:3px;
	left:95%;
	width:36px;
	height:36px;
	background-image:url(img/close-n.png);
	cursor:pointer;
}

</style>

<body>

<div id="mask2"></div>
<div id="around2"></div>

<div id="teamcont2" >
	<div style="width:780px;margin:30px auto; text-align:center">
			<div class="parinforma2">
				<div class="closebtn2"></div>
				
				<div class="showRight2">
					<div class="huncontent2">	
						动态生成	
					</div>
				</div>
				<div style="clear:both"></div>
			</div>
	</div>		
</div>

<div id="wrapper">
        <div id="pictureShowSlider">    

			<div style="overflow: hidden;" class="scroll">
	
				<div class="scrollContainer">	
	               <!-- ajax动态生成 -->				
                </div>

				<div id="left-shadow"></div>
				<div id="right-shadow"></div>
            </div>			
        </div>
        
    </div>
       
</body>
</html>