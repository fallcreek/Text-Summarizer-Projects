var currentPageNumber;
// JavaScript Document
$(function(){
	//根据总页数判断，如果小于5页，则显示所有页数，如果大于5页，则显示5页。根据当前点击的页数生成
	currentPageNumber = 1;
	
	var ProductName = $("#phoneBrandAndName").text();
	var pageCount;
	var Start;
	var End;
	
	$.ajax({
	    async: false,
		type: "POST",
		url:"findPictureGroupNumber.action",
		dataType:"text",
		data:{productName:ProductName},
		success:function(strValue){ 
			
		pageCount = Math.ceil(strValue / 10);
			
		}
	})
	
	
	//模拟后台总页数
	//生成分页按钮	

	
	
	if(pageCount>5){
		page_icon(1,5,0);
	}else{
		page_icon(1,pageCount,0);
	}
	
	//点击分页按钮触发
	$("#pageGro li").live("click",function(){
		if(pageCount > 5){
			var pageNum = parseInt($(this).html());//获取当前页数		
			currentPageNumber = pageNum;
			
			pageGroup(pageNum,pageCount);
			
			Start = (pageNum - 1) * 10 + 1;
			End = (pageNum) * 10;
			
			changePicture(Start,End);			
			
			
		}else{
			$(this).addClass("on");
			$(this).siblings("li").removeClass("on");
		}
	});
	
	//点击上一页触发
	$("#pageGro .pageUp").click(function(){
		if(pageCount > 5){
			var pageNum = parseInt($("#pageGro li.on").html());//获取当前页
			currentPageNumber = pageNum - 1;
			pageUp(pageNum,pageCount);
		}else{
			var index = $("#pageGro ul li.on").index();//获取当前页
			if(index > 0){
				$("#pageGro li").removeClass("on");//清除所有选中
				$("#pageGro ul li").eq(index-1).addClass("on");//选中上一页
			}
		}
		var pageNum = parseInt($("#pageGro li.on").html());

		Start = (pageNum - 1) * 10 + 1;
		End = (pageNum) * 10;
		
		changePicture(Start,End);	
	});
	
	//点击下一页触发
	$("#pageGro .pageDown").click(function(){
		if(pageCount > 5){
			var pageNum = parseInt($("#pageGro li.on").html());//获取当前页
			currentPageNumber = pageNum + 1;
			pageDown(pageNum,pageCount);
		}else{
			var index = $("#pageGro ul li.on").index();//获取当前页
			if(index+1 < pageCount){
				$("#pageGro li").removeClass("on");//清除所有选中
				$("#pageGro ul li").eq(index+1).addClass("on");//选中上一页
			}
		}
		var pageNum = parseInt($("#pageGro li.on").html());

		Start = (pageNum - 1) * 10 + 1;
		End = (pageNum) * 10;
		
		changePicture(Start,End);
	});
});

//点击跳转页面
function pageGroup(pageNum,pageCount){
	switch(pageNum){
		case 1:
			page_icon(1,5,0);
		break;
		case 2:
			page_icon(1,5,1);
		break;
		case pageCount-1:
			page_icon(pageCount-4,pageCount,3);
		break;
		case pageCount:
			page_icon(pageCount-4,pageCount,4);
		break;
		default:
			page_icon(pageNum-2,pageNum+2,2);
		break;
	}
}

//根据当前选中页生成页面点击按钮
function page_icon(page,count,eq){
	var ul_html = "";
	for(var i=page; i<=count; i++){
		ul_html += "<li>"+i+"</li>";
	}
	$("#pageGro ul").html(ul_html);
	$("#pageGro ul li").eq(eq).addClass("on");
}

//上一页
function pageUp(pageNum,pageCount){
	switch(pageNum){
		case 1:
		break;
		case 2:
			page_icon(1,5,0);
		break;
		case pageCount-1:
			page_icon(pageCount-4,pageCount,2);
		break;
		case pageCount:
			page_icon(pageCount-4,pageCount,3);
		break;
		default:
			page_icon(pageNum-2,pageNum+2,1);
		break;
	}
}

//下一页
function pageDown(pageNum,pageCount){
	switch(pageNum){
		case 1:
			page_icon(1,5,1);
		break;
		case 2:
			page_icon(1,5,2);
		break;
		case pageCount-1:
			page_icon(pageCount-4,pageCount,4);
		break;
		case pageCount:
		break;
		default:
			page_icon(pageNum-2,pageNum+2,3);
		break;
	}
}


function changePicture(Start,End){
	var ProductName = $("#phoneBrandAndName").text();
	
	$.ajax({
	    async: false,
		type: "POST",
		url:"PictureShowAction.action",
		dataType:"html",
		data:{productName:ProductName,start:Start,end:End},
		success:function(strValue){ 
			
			
		$("#wrapper #pictureShowSlider div.scroll div.scrollContainer").html(strValue);		
		}
	})
	
	//showBigPicture();

	
	var totalPanels			= $(".scrollContainer").children().size();
	
	var regWidth			= $(".panel").css("width");
	var regImgWidth			= $(".panel img").css("width");
	var regTitleSize		= $(".panel h2").css("font-size");
	var regParSize			= $(".panel p").css("font-size");
	
	var movingDistance	    = 300;
	
	var curWidth			= 350;
	var curImgWidth			= 300;
	var curTitleSize		= "20px";
	var curParSize			= "15px";

	var $panels				= $('#pictureShowSlider .scrollContainer > div');
	var $container			= $('#pictureShowSlider .scrollContainer');

	$panels.css({'float' : 'left','position' : 'relative'});
    
	$("#pictureShowSlider").data("currentlyMoving", false);

	//alert(currentPanel);
	
	$container
		.css('width', ($panels[0].offsetWidth * $panels.length) + 100 )
		.css('left', "-350px");
	
	var scroll = $('#pictureShowSlider .scroll').css('overflow', 'hidden');

	function returnToNormal(element) {
		$(element)
			.animate({ width: regWidth })
			.find("img")
			.animate({ width: regImgWidth })
		    .end()
			.find("h2")
			.animate({ fontSize: regTitleSize })
			.end()
			.find("p")
			.animate({ fontSize: regParSize });
	};
	
	function growBigger(element) {
		$(element)
			.animate({ width: curWidth })
			.find("img")
			.animate({ width: curImgWidth })
		    .end()
			.find("h2")
			.animate({ fontSize: curTitleSize })
			.end()
			.find("p")
			.animate({ fontSize: curParSize });
	}
	
	//direction true = right, false = left
	function change(direction) {
		//alert("change"+curPanel);
	    //if not at the first or last panel
		if((direction && !(curPanel < totalPanels)) || (!direction && (curPanel <= 1))) { return false; }	
        
        //if not currently moving
        if (($("#pictureShowSlider").data("currentlyMoving") == false)) {
            
			$("#pictureShowSlider").data("currentlyMoving", true);
			
			var next         = direction ? curPanel + 1 : curPanel - 1;
			var leftValue    = $(".scrollContainer").css("left");
			var movement	 = direction ? parseFloat(leftValue, 10) - movingDistance : parseFloat(leftValue, 10) + movingDistance;
		
			$(".scrollContainer")
				.stop()
				.animate({
					"left": movement
				}, function() {
					$("#pictureShowSlider").data("currentlyMoving", false);
				});
			
			returnToNormal("#panel_"+curPanel);
			growBigger("#panel_"+next);
			
			curPanel = next;
			currentPanel = curPanel;
			
			//remove all previous bound functions
			$("#panel_"+(curPanel+1)).unbind();	
			
			//go forward
			$("#panel_"+(curPanel+1)).click(function(){ change(true); });
			
            //remove all previous bound functions															
			$("#panel_"+(curPanel-1)).unbind();
			
			//go back
			$("#panel_"+(curPanel-1)).click(function(){ change(false); }); 
			
			//remove all previous bound functions
			$("#panel_"+curPanel).unbind();
		}
	}
	
	// Set up "Current" panel and next and prev
	growBigger("#panel_3");	
	var curPanel = 3;
	currentPanel = curPanel;
	
	$("#panel_"+(curPanel+1)).click(function(){ change(true); });
	$("#panel_"+(curPanel-1)).click(function(){ change(false); });
	
	$(".showBigPicture").click(function(){

		//alert(currentPanel);
		//alert(currentPageNumber);
		
		Start = (currentPageNumber - 1) * 10 + currentPanel;
		End = Start;
//		alert(Start);
		changePicture_showBigPicture(Start,End);	
	})
	
	//when the left/right arrows are clicked
	$(".right").click(function(){ change(true); });	
	$(".left").click(function(){ change(false); });
	
	$(window).keydown(function(event){
	  switch (event.keyCode) {
			case 13: //enter
				$(".right").click();
				break;
			case 32: //space
				$(".right").click();
				break;
	    case 37: //left arrow
				$(".left").click();
				break;
			case 39: //right arrow
				$(".right").click();
				break;
	  }
	});
}

function changePicture_showBigPicture(Start,End){
	var ProductName = $("#phoneBrandAndName").text();
	
	$.ajax({
	    async: false,
		type: "POST",
		url:"PictureShowAction.action",
		dataType:"html",
		data:{productName:ProductName,start:Start,end:End},
		success:function(strValue){ 
			
			
			var top = ($(window).height() - $(".parinforma2").height())/2;   
		    var left = ($(window).width() - $(".parinforma2").width())/2;   
		    var top2 = ($(window).height() - $("#teamcont2").height())/2;   
		    var left2 = ($(window).width() - $("#teamcont2").width())/2;   
		    
		    var scrollTop = $(document).scrollTop();   /* 滚动条顶部偏移 */
		    var scrollLeft = $(document).scrollLeft(); 
		  
		
		var start = strValue.indexOf("<img");
		var end = strValue.indexOf(">",start);
		
		var head = strValue.substring(start,start + 4);
		var tail = strValue.substring(start + 4,end + 1);
		head = head + " style = 'width:90%;height:auto;'";
		strValue = head + tail;
		
		//strValue = strValue.substring(start,end + 1);
		//alert(strValue);
		$(".huncontent2").html(strValue);	
		
		$("#mask2").css({position : 'absolute', 'top' : scrollTop, left : scrollLeft, visibility:"visible"});   //将ID为mask的CSS属性改为显示
	    $("#around2").css({position : 'absolute', 'top' : scrollTop, left : scrollLeft, visibility:"visible"});   //将ID为mask的CSS属性改为显示
	    $(".parinforma2").css( { position : 'absolute', 'top' : top + scrollTop, left : left + scrollLeft } ).slideDown("normal");  
	    $("body").css({overflow:"hidden"});    //禁用滚动条
		
		}
	})
	
}


$("#around2").click(function(){
	$(".parinforma2").hide();
	 $("body").css({overflow:"auto"}); 
	 $("#mask2").css({visibility:"hidden"});
	 $("#around2").css({visibility:"hidden"});
	 
})
	

$("#teamcont2 .closebtn2").mouseenter(function(){
	$(this).css({'background':'url(img/close-s.png)'});
}).mouseleave(function(){
	$(this).css({'background':'url(img/close-n.png)'});
})

$("#teamcont2 .closebtn2").click(function(){
	$(".parinforma2").hide();
	
	 $("body").css({overflow:"auto"}); 
	 $("#mask2").css({visibility:"hidden"});
	 $("#around2").css({visibility:"hidden"}); 
})

function showBigPicture(){
	$(".showBigPicture").click(function(){

		//alert(currentPanel);
		//alert(currentPageNumber);
		
		Start = (currentPageNumber - 1) * 10 + currentPanel;
		End = Start;
//		alert(Start);
		changePicture_showBigPicture(Start,End);
		
	})
}

$(function(){
	showBigPicture();
})

