<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>aSummarizer</title>

<script src="js/jquery-1.4.3.min.js"></script>
<script src="js/list.js"></script>
<script src="js/PictureSearch.js"></script>
<script src="js/reviewModify.js"></script>
<script src="js/modifyPrice.js"></script>
<script src="js/mouseEvent.js" type="text/javascript"></script>
<script src="js/viewMore.js" type="text/javascript"></script>

<link href="css/list.css" type="text/css" rel="stylesheet">
<!-- new -->
<meta name="keywords" content="slate, blog theme, free blog design, website layout, CSS, HTML" />
<meta name="description" content="Slate, Full Blog Theme, free CSS template by templatemo.com" />
<link href="css/templatemo_style.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" href="css/nivo-slider.css" type="text/css" media="screen" />


<link rel="stylesheet" type="text/css" href="css/ddsmoothmenu.css" />

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/ddsmoothmenu.js">


/***********************************************
* Smooth Navigational Menu- (c) Dynamic Drive DHTML code library (www.dynamicdrive.com)
* This notice MUST stay intact for legal use
* Visit Dynamic Drive at http://www.dynamicdrive.com/ for full source code
***********************************************/

</script>

<script type="text/javascript">

ddsmoothmenu.init({
	mainmenuid: "templatemo_menu", //menu DIV id
	orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
	classname: 'ddsmoothmenu', //class added to menu's outer DIV
	//customtheme: ["#1c5a80", "#18374a"],
	contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
})

</script>
<!-- new -->


</head>

<body>



    



					
<!-- NEW -->
<div id="templatemo_wrapper">
	<%@ include file="head.jsp" %>
    
    <div id="productInfo">
    		<h4 id="productDetail" style="z-index:1;left:50px;font-size:23px;float:left;"><a href="Index.jsp" style="color: rgb(66,139,202);text-decoration:none;">Home</a></h4>
    		<h4 id="productDetail" style="left:55px;font-size:23px;color:rgb(119,119,119);">/ Product Detail</h4>
    		<br/>
    		<hr size="1" width="850px" color="#D3D3D3"/>
    		
    		<div id="productImage" >
    			<%@ include file="productPictures.jsp"%>
    		</div>
    		
    		<hr id="phoneBrandLine" size="1" width="300px" color="#D3D3D3"/> 		
    		<div id="phoneBrandInfo"><h2 id="phoneBrandAndName"><s:property value="phoneInfo.brand" />/<s:property value="phoneInfo.productName" /></h2></div>
    		<hr id="phoneBrandLine" size="1" width="300px" color="#D3D3D3"/>
    		
    		<div id="infoResult">
    			<div class="infoResultTag">Brand:  </div><div class="infoResultParam"><s:property value="phoneInfo.brand" /></div>
    			<div class="infoResultTag">Price:   </div><div id="infoResultPrice" class="infoResultParam"><s:property value="phoneInfo.price" /></div>
    			<div class="infoResultTag">Year of Release:  </div><div class="infoResultParam"><s:property value="phoneInfo.publishDate" /></div>
    		</div>
    		
    		<div id="reviewResult">
	    		<h4 id="reviewTotal">Evaluations: <span id="reviewTotalNumber"><s:property value="reviewNumberResult.getTotalNumber()" /></span></h4>
	    		<h4 id="reviewSource">zol: <span id="reviewNumber"><s:property value="reviewNumberResult.getZol()" /></span></h4>
	    		<h4 id="reviewSource">it168: <span id="reviewNumber"><s:property value="reviewNumberResult.getIt168()" /></span></h4>
	    		<h4 id="reviewSource">sina: <span id="reviewNumber"><s:property value="reviewNumberResult.getSina()" /></span></h4>
	    		<h4 id="reviewSource">pconline: <span id="reviewNumber"><s:property value="reviewNumberResult.getPconline()" /></span></h4>
    		</div>
    		
    </div>
    
    
    <div id="productList">
    		
    		<h3 id="productListTitle">Products Lists</h3>
        <hr id="productListTitleLine" size="1" width="300px" color="#D3D3D3"/> 
        
        <ul id="productListItem">
           <s:iterator value="relativeProduct">
                <li><a href="phoneBrandChooseAction?phoneName=<s:property value='url' />"><s:property value="productName" /></a></li>
           		<hr id="productListItemLine" size="1" width="300px" color="#D3D3D3"/>    
           </s:iterator>
        </ul>  
    </div>
    
    
    <div class="Qbox"> 
    		<h4 id="productDetail">Evaluation Analysis</h4>
    		<br/>
    		<hr size="1" width="850px" color="#D3D3D3"/>
    		
	    <ul>  
		    <li class="cent" id="title01">Appearance</li>  
		    <li id="title02">Quality</li> 
		    <li id="title03">Screen</li>
		    <li id="title04">Affordability</li>
		    <li id="title05">System</li> 
		    <!-- <li id="title06">Software</li> --> 
		    <li id="title06">Operation</li>
		    <li id="title07">Battery</li>
		    <li id="title08">Keyboard</li> 
		    <li id="title09">PhoneSignal</li> 
		    <!-- <li id="title11">Message</li>  -->
		    <li id="title10">Interface</li>
		    <!-- <li id="title13">Input</li> -->
		    <li id="title11">Model</li> 
		    <li id="title12">Photograph</li> 
		    <li id="title13">Sound</li>
		    <li id="title14">Memory</li>
	  	</ul>  
	  
	 
	  <h5 id="summaryBoxTitle">Summary</h5> 
	  <h5 id="summaryBoxTitle2">Summary Source</h5> 
	  
	  
	  
	  <div id="summaryBox">	 
	  
	  <div id="title01_con" class="summaryContent"> 
	  <ol class="summarySentence">
	   <s:iterator status="i"  value="summarySet.getAppearance()">
	  	<li class="<s:property value='#i.count' />"><s:property value="getSummarySentence()"/></li>
	   	<hr size="1" width="370px" color="#D3D3D3"/>
	   </s:iterator>
	  </ol>
	  
	  </div>
	   
	  <div  class="summaryContent" id="title02_con" style="display:none;"> 
	  <ol class="summarySentence">
	  <s:iterator status="i" value="summarySet.getQuality()">
	  	<li class="<s:property value='#i.count' />"><s:property value="getSummarySentence()"/></li>
	  	<hr size="1" width="370px" color="#D3D3D3"/>
	  </s:iterator> 
	  </ol>
	  </div>  
	  
	  <div class="summaryContent"  id="title03_con" style="display:none;"> 
	  <ol class="summarySentence">
	  <s:iterator status="i" value="summarySet.getScreen()">
	  	<li class="<s:property value='#i.count' />"><s:property value="getSummarySentence()"/></li>
	  	<hr size="1" width="370px" color="#D3D3D3"/>
	  </s:iterator> 
	  </ol>
	  </div>  
	  
	  <div  class="summaryContent" id="title04_con" style="display:none;"> 
	  <ol class="summarySentence">
	  <s:iterator status="i" value="summarySet.getAffordability()">
	  	<li class="<s:property value='#i.count' />"><s:property value="getSummarySentence()"/></li>
	  	<hr size="1" width="370px" color="#D3D3D3"/>
	  </s:iterator> 
	  </ol>
	  </div>  
	  
	  <div  class="summaryContent" id="title05_con" style="display:none;"> 
	  <ol class="summarySentence">
	  <s:iterator status="i" value="summarySet.getSystem()">
	  	<li class="<s:property value='#i.count' />"><s:property value="getSummarySentence()"/></li>
	  	<hr size="1" width="370px" color="#D3D3D3"/>
	  </s:iterator> 
	  </ol>
	  </div>  
	  
	  <%-- <div  class="summaryContent" id="title06_con" style="display:none;"> 
	  <ol class="summarySentence">
	  <s:iterator status="i" value="summarySet.getSoftware()">
	  	<li class="<s:property value='#i.count' />"><s:property value="getSummarySentence()"/></li>
	  	<hr size="1" width="370px" color="#D3D3D3"/>
	  </s:iterator> 
	  </ol>
	  </div>  --%> 
	  
	  <div  class="summaryContent" id="title06_con" style="display:none;"> 
	  <ol class="summarySentence">
	  <s:iterator status="i" value="summarySet.getOperation()">
	  	<li class="<s:property value='#i.count' />"><s:property value="getSummarySentence()"/></li>
	  	<hr size="1" width="370px" color="#D3D3D3"/>
	  </s:iterator> 
	  </ol>
	  </div>  
	  
	  <div  class="summaryContent" id="title07_con" style="display:none;"> 
	  <ol class="summarySentence">
	  <s:iterator status="i" value="summarySet.getBattery()">
	  	<li class="<s:property value='#i.count' />"><s:property value="getSummarySentence()"/></li>
	  	<hr size="1" width="370px" color="#D3D3D3"/>
	  </s:iterator> 
	  </ol>
	  </div>  
	  
	  <div  class="summaryContent" id="title08_con" style="display:none;"> 
	  <ol class="summarySentence">
	  <s:iterator status="i" value="summarySet.getKeyboard()">
	  	<li class="<s:property value='#i.count' />"><s:property value="getSummarySentence()"/></li>
	  	<hr size="1" width="370px" color="#D3D3D3"/>
	  </s:iterator> 
	  </ol>
	  </div>  
	  
	  <div class="summaryContent"  id="title09_con" style="display:none;"> 
	  <ol class="summarySentence">
	  <s:iterator status="i" value="summarySet.getPhoneSignal()">
	  	<li class="<s:property value='#i.count' />"><s:property value="getSummarySentence()"/></li>
	  	<hr size="1" width="370px" color="#D3D3D3"/>
	  </s:iterator> 
	  </ol>
	  </div>  
	  
	 <%--  <div class="summaryContent"  id="title11_con" style="display:none;"> 
	  <ol class="summarySentence">
	  <s:iterator status="i" value="summarySet.getMessage()">
	  	<li class="<s:property value='#i.count' />"><s:property value="getSummarySentence()"/></li>
	 	<hr size="1" width="370px" color="#D3D3D3"/>
	  </s:iterator> 
	  </ol>
	  </div>  --%>
	  
	  <div  class="summaryContent" id="title10_con" style="display:none;"> 
	  <ol class="summarySentence">
	  <s:iterator status="i" value="summarySet.getInterface()">
	  	<li class="<s:property value='#i.count' />"><s:property value="getSummarySentence()"/></li>
	  	<hr size="1" width="370px" color="#D3D3D3"/>
	  </s:iterator> 
	  </ol>
	  </div>  
	  
	  <%-- <div class="summaryContent"  id="title13_con" style="display:none;"> 
	  <ol class="summarySentence">
	  <s:iterator status="i" value="summarySet.getInput()">
	  	<li class="<s:property value='#i.count' />"><s:property value="getSummarySentence()"/></li>
	  	<hr size="1" width="370px" color="#D3D3D3"/>
	  </s:iterator> 
	  </ol>
	  </div> --%>
	    
	  <div class="summaryContent"  id="title11_con" style="display:none;"> 
	  <ol class="summarySentence">
	  <s:iterator status="i" value="summarySet.getModel()">
	  	<li class="<s:property value='#i.count' />"><s:property value="getSummarySentence()"/></li>
	  	<hr size="1" width="370px" color="#D3D3D3"/>
	  </s:iterator> 
	  </ol>
	  </div>
	  
	  <div class="summaryContent"  id="title12_con" style="display:none;"> 
	  <ol class="summarySentence">
	  <s:iterator status="i" value="summarySet.getPhotograph()">
	  	<li class="<s:property value='#i.count' />"><s:property value="getSummarySentence()"/></li>
	  	<hr size="1" width="370px" color="#D3D3D3"/>
	  </s:iterator> 
	  </ol>
	  </div>
	  
	  <div class="summaryContent"  id="title13_con" style="display:none;"> 
	  <ol class="summarySentence">
	  <s:iterator status="i" value="summarySet.getSound()">
	  	<li class="<s:property value='#i.count' />"><s:property value="getSummarySentence()"/></li>
	  	<hr size="1" width="370px" color="#D3D3D3"/>
	  </s:iterator> 
	  </ol>
	  </div>
	  
	  <div class="summaryContent"  id="title14_con" style="display:none;"> 
	  <ol class="summarySentence">
	  <s:iterator status="i" value="summarySet.getMemory()">
	  	<li class="<s:property value='#i.count' />"><s:property value="getSummarySentence()"/></li>
	  	<hr size="1" width="370px" color="#D3D3D3"/>
	  </s:iterator> 
	  </ol>
	  </div>
	   
    </div>
    
    
    
    <div id="summarySourceBox">
    	  
    	  <div class="summaryContent" id="title01_con2" > 
	  <ol class="summarySourcePassage">
	   <s:iterator status="i"  value="summarySet.getAppearance()">  	
	  	<li class="<s:property value='#i.count' />_con">
	  	<div class="sourceWeb" style="display:none;"><s:property value="sourceWeb" /></div><br />
	  	<s:property value="getReviewHead()"/>
	  	<font color="red"><s:property value="getReviewMid()"/></font>
	  	<s:property value="getReviewEnd()"/>
	  	</li>
	   </s:iterator>
	  </ol>
	  </div>
	   
	  <div  class="summaryContent" id="title02_con2" style="display:none;"> 
	  <ol class="summarySourcePassage">
	   <s:iterator status="i"  value="summarySet.getQuality()">
	  	<li class="<s:property value='#i.count' />_con">
	  	<div class="sourceWeb" style="display:none;"><s:property value="sourceWeb" /></div><br />
	  	<s:property value="getReviewHead()"/>
	  	<font color="red"><s:property value="getReviewMid()"/></font>
	  	<s:property value="getReviewEnd()"/>
	  	</li>
	   </s:iterator>
	  </ol>
	  </div>  
	  
	  <div class="summaryContent"  id="title03_con2" style="display:none;"> 
	  <ol class="summarySourcePassage">
	   <s:iterator status="i"  value="summarySet.getScreen()">
	  	<li class="<s:property value='#i.count' />_con">
	  	<div class="sourceWeb" style="display:none;"><s:property value="sourceWeb" /></div><br />
	  	<s:property value="getReviewHead()"/>
	  	<font color="red"><s:property value="getReviewMid()"/></font>
	  	<s:property value="getReviewEnd()"/>
	  	</li>
	   </s:iterator>
	  </ol>
	  </div>  
	  
	  <div  class="summaryContent" id="title04_con2" style="display:none;"> 
	  <ol class="summarySourcePassage">
	   <s:iterator status="i"  value="summarySet.getAffordability()">
	  	<li class="<s:property value='#i.count' />_con">
	  	<div class="sourceWeb" style="display:none;"><s:property value="sourceWeb" /></div><br />
	  	<s:property value="getReviewHead()"/>
	  	<font color="red"><s:property value="getReviewMid()"/></font>
	  	<s:property value="getReviewEnd()"/>
	  	</li>
	   </s:iterator>
	  </ol>
	  </div>  
	  
	  <div  class="summaryContent" id="title05_con2" style="display:none;"> 
	  <ol class="summarySourcePassage">
	   <s:iterator status="i"  value="summarySet.getSystem()">
	  	<li class="<s:property value='#i.count' />_con">
	  	<div class="sourceWeb" style="display:none;"><s:property value="sourceWeb" /></div><br />
	  	<s:property value="getReviewHead()"/>
	  	<font color="red"><s:property value="getReviewMid()"/></font>
	  	<s:property value="getReviewEnd()"/>
	  	</li>
	   </s:iterator>
	  </ol>
	  </div>  
	  
	  <%-- <div  class="summaryContent" id="title06_con2" style="display:none;"> 
	  <ol class="summarySourcePassage">
	   <s:iterator status="i"  value="summarySet.getSoftware()">
	  	<li class="<s:property value='#i.count' />_con">
	  	<div class="sourceWeb" style="display:none;"><s:property value="sourceWeb" /></div><br />
	  	<s:property value="getReviewHead()"/>
	  	<font color="red"><s:property value="getReviewMid()"/></font>
	  	<s:property value="getReviewEnd()"/>
	  	</li>
	   </s:iterator>
	  </ol>
	  </div>  --%> 
	  
	  <div  class="summaryContent" id="title06_con2" style="display:none;"> 
	  <ol class="summarySourcePassage">
	   <s:iterator status="i"  value="summarySet.getOperation()">
	  	<li class="<s:property value='#i.count' />_con">
	  	<div class="sourceWeb" style="display:none;"><s:property value="sourceWeb" /></div><br />
	  	<s:property value="getReviewHead()"/>
	  	<font color="red"><s:property value="getReviewMid()"/></font>
	  	<s:property value="getReviewEnd()"/>
	  	</li>
	   </s:iterator>
	  </ol>
	  </div>  
	  
	  <div  class="summaryContent" id="title07_con2" style="display:none;"> 
	  <ol class="summarySourcePassage">
	   <s:iterator status="i"  value="summarySet.getBattery()">
	  	<li class="<s:property value='#i.count' />_con">
	  	<div class="sourceWeb" style="display:none;"><s:property value="sourceWeb" /></div><br />
	  	<s:property value="getReviewHead()"/>
	  	<font color="red"><s:property value="getReviewMid()"/></font>
	  	<s:property value="getReviewEnd()"/>
	  	</li>
	   </s:iterator>
	  </ol>
	  </div>  
	  
	  <div  class="summaryContent" id="title08_con2" style="display:none;"> 
	  <ol class="summarySourcePassage">
	   <s:iterator status="i"  value="summarySet.getKeyboard()">
	  	<li class="<s:property value='#i.count' />_con">
	  	<div class="sourceWeb" style="display:none;"><s:property value="sourceWeb" /></div><br />
	  	<s:property value="getReviewHead()"/>
	  	<font color="red"><s:property value="getReviewMid()"/></font>
	  	<s:property value="getReviewEnd()"/>
	  	</li>
	   </s:iterator>
	  </ol>
	  </div>  
	  
	  <div class="summaryContent"  id="title09_con2" style="display:none;"> 
	  <ol class="summarySourcePassage">
	   <s:iterator status="i"  value="summarySet.getPhoneSignal()">
	  	<li class="<s:property value='#i.count' />_con">
	  	<div class="sourceWeb" style="display:none;"><s:property value="sourceWeb" /></div><br />
	  	<s:property value="getReviewHead()"/>
	  	<font color="red"><s:property value="getReviewMid()"/></font>
	  	<s:property value="getReviewEnd()"/>
	  	</li>
	   </s:iterator>
	  </ol>
	  </div>  
	  
	  <%-- <div  class="summaryContent" id="title11_con2" style="display:none;"> 
	  <ol class="summarySourcePassage">
	   <s:iterator status="i"  value="summarySet.getMessage()">
	  	<li class="<s:property value='#i.count' />_con">
	  	<div class="sourceWeb" style="display:none;"><s:property value="sourceWeb" /></div><br />
	  	<s:property value="getReviewHead()"/>
	  	<font color="red"><s:property value="getReviewMid()"/></font>
	  	<s:property value="getReviewEnd()"/>
	  	</li>
	   </s:iterator>
	  </ol>
	  </div>  --%>
	  
	  <div  class="summaryContent" id="title10_con2" style="display:none;"> 
	  <ol class="summarySourcePassage">
	   <s:iterator status="i"  value="summarySet.getInterface()">
	  	<li class="<s:property value='#i.count' />_con">
	  	<div class="sourceWeb" style="display:none;"><s:property value="sourceWeb" /></div><br />
	  	<s:property value="getReviewHead()"/>
	  	<font color="red"><s:property value="getReviewMid()"/></font>
	  	<s:property value="getReviewEnd()"/>
	  	</li>
	   </s:iterator>
	  </ol>
	  </div>  
	  
	  <%-- <div class="summaryContent"  id="title13_con2" style="display:none;"> 
	  <ol class="summarySourcePassage">
	   <s:iterator status="i"  value="summarySet.getInput()">
	  	<li class="<s:property value='#i.count' />_con">
	  	<div class="sourceWeb" style="display:none;"><s:property value="sourceWeb" /></div><br />
	  	<s:property value="getReviewHead()"/>
	  	<font color="red"><s:property value="getReviewMid()"/></font>
	  	<s:property value="getReviewEnd()"/>
	  	</li>
	   </s:iterator>
	  </ol>
	  </div> --%>
	  
	  <div class="summaryContent"  id="title11_con2" style="display:none;"> 
	  <ol class="summarySourcePassage">
	   <s:iterator status="i"  value="summarySet.getModel()">
	  	<li class="<s:property value='#i.count' />_con">
	  	<div class="sourceWeb" style="display:none;"><s:property value="sourceWeb" /></div><br />
	  	<s:property value="getReviewHead()"/>
	  	<font color="red"><s:property value="getReviewMid()"/></font>
	  	<s:property value="getReviewEnd()"/>
	  	</li>
	   </s:iterator>
	  </ol>
	  </div>
	  
	  <div class="summaryContent"  id="title12_con2" style="display:none;"> 
	  <ol class="summarySourcePassage">
	   <s:iterator status="i"  value="summarySet.getPhotograph()">
	  	<li class="<s:property value='#i.count' />_con">
	  	<div class="sourceWeb" style="display:none;"><s:property value="sourceWeb" /></div><br />
	  	<s:property value="getReviewHead()"/>
	  	<font color="red"><s:property value="getReviewMid()"/></font>
	  	<s:property value="getReviewEnd()"/>
	  	</li>
	   </s:iterator>
	  </ol>
	  </div>
	  
	  <div class="summaryContent"  id="title13_con2" style="display:none;"> 
	  <ol class="summarySourcePassage">
	   <s:iterator status="i"  value="summarySet.getSound()">
	  	<li class="<s:property value='#i.count' />_con">
	  	<div class="sourceWeb" style="display:none;"><s:property value="sourceWeb" /></div><br />
	  	<s:property value="getReviewHead()"/>
	  	<font color="red"><s:property value="getReviewMid()"/></font>
	  	<s:property value="getReviewEnd()"/>
	  	</li>
	   </s:iterator>
	  </ol>
	  </div>
	  
	  <div class="summaryContent"  id="title14_con2" style="display:none;"> 
	  <ol class="summarySourcePassage">
	   <s:iterator status="i"  value="summarySet.getMemory()">
	  	<li class="<s:property value='#i.count' />_con">
	  	<div class="sourceWeb" style="display:none;"><s:property value="sourceWeb" /></div><br />
	  	<s:property value="getReviewHead()"/>
	  	<font color="red"><s:property value="getReviewMid()"/></font>
	  	<s:property value="getReviewEnd()"/>
	  	</li>
	   </s:iterator>
	  </ol>
	  </div>
	  
	  
	  
    </div>  
    
    <div id="summarySourceViewMore" style="width:115px;color:rgb(255,67,4);font-size:10px;position:relative;left:380px;bottom:260px;cursor:pointer;">
    			View All Evaluation
    		</div>
    
    
    <h5 id="summaryBoxTitle4">Evaluation Pictures</h5>  
    
    </div>
    
    <%@ include file="pictureShowBox.jsp"%>
    <%@ include file="pictureGroup.jsp"%>
    
    <div id="reviewWindow">
    		<h4 id="productDetail">All Evaluations</h4>
    		<br/>
    		<hr size="1" width="850px" color="#D3D3D3"/>
    		
    		<h5 id="summaryBoxTitle">Title</h5> 
	  	<h5 id="summaryBoxTitle3">Original</h5> 
	  
	  
    		<div id="evaluationTitle">
    			<ol class="evaluationTitleList">
    				<s:iterator value="reviewSet" status="i">
                    <li id="<s:property value='#i.count' />_evaluationTitle">
                    		<div id="evaluation_title"><s:property value="title"/></div>
                    		<div id="evaluation_source"><a href="<s:property value='url'/>" target="_blank">(from <s:property value="sourceWeb"/> No.<s:property value="number"/>)</a> </div>
             			<div id="evaluation_sourceWeb" style="display:none;"><s:property value='sourceWeb'/></div>
             		</li> 
                    </s:iterator> 
    			</ol>
    		</div>
    		
    		<div id="evaluationContent" >
    			<ol class="evaluationContentList">
    				<s:iterator value="reviewSet" status="i">
                    <li id="<s:property value='#i.count' />_evaluationTitle_Content" style="display:none;">
                    		<s:property value="reviewContent"/>      		
             		</li> 
                </s:iterator> 
    			</ol>
    		</div>
    		<div id="evaluationViewMore" style="width:115px;color:rgb(255,67,4);font-size:10px;position:relative;bottom:230px;left:750px;cursor:pointer;">
    			View All Evaluation
    		</div>
    		
    </div>
    

    <div id="templatemo_footer">
    	Copyright © 2015 <a href="http://www.tsinghua.edu.cn/" target="_parent">Tsinghua.AIlab </a>
    </div>
    
    
</div>

 
<%@ include file="detailPassage.jsp" %> 
 
</body>
</html>



	