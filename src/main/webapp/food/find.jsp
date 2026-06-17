<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<style type="text/css">
.container {
	margin-top: 50px;
}
.row {
	margin: 0px auto;
	width: 960px;
}
p {
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}
</style>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
function commons(page) {
	let types=[]
	$('input[name=type]:checked').each(function(){
		types.push($(this).val())
	})
	//console.log(types)
	let ss=$('#ss').val()
	let column=$('#column').val()
	//console.log("ss: "+ss)
	//console.log("column: "+column)
	$.ajax({
		type:'post',
		url:'../food/find_ajax.do',
		data:{"ss":ss,"column":column,"type":types,"page":page},
		traditional:true,
		success:function(result){
			let json=JSON.parse(result)
			console.log(json)
			$('#ss').val(json[0].ss)
			jsonView(json)
		}
	})
}
function jsonView(json) {
	let html=''
	json.forEach((food)=>{
		html+='<div class="col-sm-3">'+
			'<a href="#">'+
			'<div class="thumbnail">'+
			'<img src="'+food.poster+'" style="width: 200px; height: 170px; object-fit: cover;">'+
			'<p>'+food.name+'</p>'+
			'</div>'+
			'</a>'+
			'</div>'
	})
	$('#print').html(html)
}
$(function(){
	commons(1)
	$('.btns').on('click',function(){
		let types=[]
		let count=$('input[name=type]:checked').length
		if(count===0) {
			alert("체크박스에 체크 하세요")
			return
		}
		$('input[name=type]:checked').each(function(){
			types.push($(this).val())
		})
		
		let ss=$('#ss').val()
		if(ss.trim()==="") {
			$('#ss').focus()
			return
		}
		let column=$('#column').val()
		$.ajax({
			type:'post',
			url:'../food/find_ajax.do',
			data:{"ss":ss,"column":column,"type":types,"page":1},
			traditional:true,
			success:function(result){
				let json=JSON.parse(result)
				console.log(json)
				$('#ss').val(json[0].ss)
				jsonView(json)
			}
		})
	})
})
</script>
</head>
<body>
<div class="container">
	<div class="row">
		<select id="column">
			<option value="address">주소</option>
			<option value="name">맛집명</option>
		</select>
		<input type="checkbox" name="type" value="A"> 한식
		<input type="checkbox" name="type" value="B"> 양식
		<input type="checkbox" name="type" value="C"> 일식
		<input type="checkbox" name="type" value="D"> 중식
		<input type="checkbox" name="type" value="E"> 분식
		<input type="text" size="15" id="ss" class="input-sm" value="마포">
		<button class="btn btn-sm btn-info btns">검색</button>

	</div>
	<div class="row" style="margin-top: 20px" id="print">
		<%-- <c:forEach var="vo" items="${list }">
			<div class="col-sm-3">
				<a href="#">
					<div class="thumbnail">
						<img src="${vo.poster }" title="${vo.type }" style="width: 200px; height: 170px; object-fit: cover;">
						<p>${vo.name }</p>
					</div>
				</a>
			</div>
		</c:forEach> --%>
	</div>
	<div class="row text-center" style="margin-top: 20px" id="pagePrint">
		
	</div>
</div>
</body>
</html>