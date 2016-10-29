<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="JDBC.*" import="entity.*"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>aSummarizer</title>
<!-- <link href="css/IndexCss.css" rel="stylesheet" type="text/css" />
 -->
 
<script src="js/jquery-1.4.3.min.js"></script>


<script type="text/javascript">

function validate(){
	var text = document.getElementById("searchfield").value;

	if(text == "")
	{
		document.getElementById("searchfield").setAttribute("placeholder", "Can not be empty");
		return false;	
	}
	else
	{
		return true;
	}	
}
</script>
       

<script type="text/javascript">

$(function(){
	/* var top = ($(window).height() - $(".parinforma").height())/2;   
    var left = ($(window).width() - $(".parinforma").width())/2;   
     */
    var indexPictureLeft = ($(window).width() - $("#indexPicture").width())/2; 
	var templatemo_footerLeft = ($(window).width() - $("#templatemo_footer").width())/2;
		
    $("#indexPicture").css("left",indexPictureLeft);
    $("#MobilePhone").css({"left":indexPictureLeft - 50});
   	$("#searchBox").css({"left":indexPictureLeft - 50});
    $("#templatemo_footer").css({"left":templatemo_footerLeft});
    
})

</script>


</head>

<style type="text/css">

@font-face {
    font-family: 'PT DIN2';
    src: url('css/fonts/PT DIN.ttf');
  }

body{
	height: 100%;
	margin: 0;
}

#indexPicture{
	
	position:absolute;
	
	top:30%;
	
	z-index:1;
}

#MobilePhone{
	position:absolute;	
	top:50%;

	
	width:160px;
	height:40px;
	line-height:45px; 
	text-align:center;  
	
	color:rgb(85,85,85);
	font-size:25px;
	font-family:PT DIN2;
	
	-webkit-border-radius: 5px; 
	border: 1px solid rgb(227,227,227);
	background-color: white;
	
}




#searchBox { 
	position: absolute;
	top:58%;

	
	float: right; 
	width:650px;
	height:200px;
	
	
	 
}



#searchfield { 
	
	display: block; 
	line-height: 20px; 
	width: 600px; 
	height:30px;
	padding: 9px 10px; 
	font-size: 18px; 
	color: #7a7a7a; 
	font-variant: normal;
	background: none; 
	
	-webkit-border-radius: 5px; 
	border: 1px solid rgb(227,227,227);
	background-color: white;
} 

#searchbutton { 
	position:relative;
	left:625px;
	bottom:51px;
	
	display: block; 
	height: 52px; 
	width: 120px; 
	padding: 0; 
	margin: 0; 
	cursor: pointer; 

	
	-webkit-border-radius: 5px; 
	border: 1px solid rgb(227,227,227);
	background-color: rgb(255,67,4); 
	
	font-size:20px;
	color:white;
}

#templatemo_footer {
	position: absolute;
	top:95%;
	clear: both;
	text-align: center;
	line-height: 40px;
	height: 50px;
	
	
}
</style>




<body>

<img id="indexPicture" src="images/index.png" width="600" height="150" />
<div id="MobilePhone">Mobile&nbsp&nbspPhone</div>


       <div id="searchBox">
            <form action="fuzzySearch.action" method="post" onsubmit="return validate()">
                <input type="text" name="phoneName"  id="searchfield" title="searchfield"  placeholder = "like iphone 6"/>
                <input type="submit" name="Search" value="Search" id="searchbutton" title="Search" />
            </form>
        </div> 
        
     <div id="templatemo_footer">
    	Copyright © 2015 <a href="http://www.tsinghua.edu.cn/" target="_parent">Tsinghua.AIlab </a>
    </div>
        
 


			
</body>
</html>