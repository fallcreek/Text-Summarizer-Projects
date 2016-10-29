

$(function(){
	var passage = $("#summarySourceBox div.summaryContent:visible ol li:visible").html();
	var SourceWeb = $("#summarySourceBox div.summaryContent:visible ol li:visible div.sourceWeb").text();
	var ProductName = $("#phoneBrandAndName").text();
	
	
	$.ajax({
		type: "POST",
		url:"PictureSearchAction.action",
		dataType:"html",
		data:{passageValue:passage,sourceWeb:SourceWeb,productName:ProductName},
		success:function(strValue){ 
			
			$("#summarySourceBox div.summaryContent:visible ol li:visible").html(strValue);
		}
	})
})

$(function() {  
	   $(".Qbox ol li").click(function(){
		 
			var passage = $("#summarySourceBox div.summaryContent:visible ol li:visible").html();
			var SourceWeb = $("#summarySourceBox div.summaryContent:visible ol li:visible div.sourceWeb").text();
			var ProductName = $("#phoneBrandAndName").text();
			
			$.ajax({
				type: "POST",
				url:"PictureSearchAction.action",
				dataType:"html",
				data:{passageValue:passage,sourceWeb:SourceWeb,productName:ProductName},
				success:function(strValue){ 
					
					$("#summarySourceBox div.summaryContent:visible ol li:visible").html(strValue);
				}
			})
	   });  
	}); 


$(function() {  
	   $(".Qbox ul li").click(function(){
			 
			var passage = $("#summarySourceBox div.summaryContent:visible ol li:visible").html();
			var SourceWeb = $("#summarySourceBox div.summaryContent:visible ol li:visible div.sourceWeb").text();
			var ProductName = $("#phoneBrandAndName").text();
			
			$.ajax({
				type: "POST",
				url:"PictureSearchAction.action",
				dataType:"html",
				data:{passageValue:passage,sourceWeb:SourceWeb,productName:ProductName},
				success:function(strValue){ 
					
					$("#summarySourceBox div.summaryContent:visible ol li:visible").html(strValue);
				}
			})
	  });  
	});  