$(function() {  
	   $(".Qbox ul li").click(function(){  
	   $(this).addClass("cent").siblings().removeClass("cent");  
	   var idName = $(this).attr("id");  
	   $("#" + idName + "_" + "con").show().siblings("div").hide(); 
	   $("#" + idName + "_" + "con2").show().siblings("div").hide();
	   });  
	});  

$(function() {  
	   $(".Qbox ol li").click(function(){
		   
	   var idName = $(this).attr("class"); 
	   $(this).addClass("cent2").siblings().removeClass("cent2");  
	   
	   $("." + idName + "_" + "con").show().siblings("li").hide();   
	   });  
	}); 



$(function() { 	
	 $(".1").addClass("cent2");
	 $(".1_con").show().siblings("li").hide(); 
	 
}); 



$(function() {  
	   $("#reviewWindow ol.evaluationTitleList li").click(function(){  		 
		   
	   $(this).addClass("cent3").siblings().removeClass("cent3");  
	   var idName = $(this).attr("id");  
	   $("#" + idName + "_" + "Content").show().siblings("li").hide();   
	   
	   
	   var idPassage = "#" + idName + "_" + "Content";
	   var passage = $(idPassage).html();
	  
		var SourceWeb = $("#evaluation_sourceWeb").text();
		var ProductName = $("#phoneBrandAndName").text();
		
		$.ajax({
			type: "POST",
			url:"PictureSearchAction.action",
			dataType:"html",
			data:{passageValue:passage,sourceWeb:SourceWeb,productName:ProductName},
			success:function(strValue){ 
				
				$(idPassage).html(strValue);
			}
		})
			   
	   });  
	}); 


$(function(){
	$("#1_evaluationTitle").addClass("cent3");
	 $("#1_evaluationTitle_Content").show().siblings("li").hide();
	 var idPassage = "#1_evaluationTitle_Content";
	   var passage = $(idPassage).html();
	  
		var SourceWeb = $("#evaluation_sourceWeb").text();
		var ProductName = $("#phoneBrandAndName").text();
		
		$.ajax({
			type: "POST",
			url:"PictureSearchAction.action",
			dataType:"html",
			data:{passageValue:passage,sourceWeb:SourceWeb,productName:ProductName},
			success:function(strValue){ 
				
				$(idPassage).html(strValue);
			}
		})
})


