$(function(){
	var ProductName = $("#phoneBrandAndName").text();
	
	$.ajax({
	    async: false,
		type: "POST",
		url:"PictureShowAction.action",
		dataType:"html",
		data:{productName:ProductName,start:1,end:10},
		success:function(strValue){ 
					
			$("#wrapper #pictureShowSlider div.scroll div.scrollContainer").html(strValue);
			
			
		}
	})
})




	

/*
 * <div id="wrapper">
        <div id="pictureShowSlider">    

            <img class="scrollButtons left" src="../images/leftarrow.png">

			<div style="overflow: hidden;" class="scroll">
	
				<div class="scrollContainer">
	
	                
				
                </div>
 */

