$(function(){
	
$("#summarySourceViewMore").click(function(){
	
	
	var passage = $("#summarySourceBox div.summaryContent:visible ol li:visible").html();
	
	$(".huncontent").html(passage);
	
	var top = ($(window).height() - $(".parinforma").height())/2;   
    var left = ($(window).width() - $(".parinforma").width())/2;   
    var scrollTop = $(document).scrollTop();   /* 滚动条顶部偏移 */
    var scrollLeft = $(document).scrollLeft(); 
    $("#mask").css({position : 'absolute', 'top' : scrollTop, left : scrollLeft, visibility:"visible"});   //将ID为mask的CSS属性改为显示
    $("#around").css({position : 'absolute', 'top' : scrollTop, left : scrollLeft, visibility:"visible"});   //将ID为mask的CSS属性改为显示
    $(".parinforma").css( { position : 'absolute', 'top' : top + scrollTop, left : left + scrollLeft } ).slideDown("normal");  
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
})