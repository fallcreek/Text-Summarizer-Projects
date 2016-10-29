

$(function(){

	var Price = $("#infoResultPrice").html();
	
	$.ajax({
		type: "POST",
		url:"modifyPriceAction.action",
		dataType:"html",
		data:{price:Price},
		success:function(strValue){ 
			$("#infoResultPrice").html(strValue);
		}
	})
		
})

$(function(){
	$(".infoResultPriceClass").each(function(){
		var Price = $(this).html();
		var $this = $(this);
	$.ajax({
		async: false,
		type: "POST",
		url:"modifyPriceAction.action",
		dataType:"html",
		data:{price:Price},
		success:function(strValue){ 	

			$($this).html(strValue);
	
		}
	})
	
	
	})
})