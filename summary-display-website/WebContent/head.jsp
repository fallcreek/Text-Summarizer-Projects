<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="css/templatemo_style.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript">
function clearText(field){

    if (field.defaultValue == field.value) field.value = '';
    else if (field.value == '') field.value = field.defaultValue;

}
</script>

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
	$("#headTitle").css("width",$(window).width());
})

</script>

<style type="text/css">
  
#search_box { 
	background: url(images/templatemo_search.png) no-repeat; 
}


</style>

</head>



<body>
<div id="headTitle" >
    	<div id="site_title"><h2><a id="headLogo" href="Index.jsp" style="width:100px;">aSummarizer</a></h2></div>
        <div class="cleaner"></div>
 
         
        <div id="search_box">
            <form action="fuzzySearch.action" method="post" onsubmit="return validate()">
                <input type="text" value="Search" name="phoneName" size="10" id="searchfield" title="searchfield" onfocus="clearText(this)" onblur="clearText(this)" />
                <input type="submit" name="Search" value="" id="searchbutton" title="Search" />
            </form>
        </div>
        <br style="clear: left" />
  
    </div>
    
</body>
</html>