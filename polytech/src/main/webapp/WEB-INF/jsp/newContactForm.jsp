<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://code.jquery.com/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>  
<html>
<head>
<meta charset="UTF-8">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<title>추가 연락처 입력</title>
<style>
body {
  font-family: Arial;
  font-size: 17px;
  padding: 8px;
  margin: auto;
  width:80%;
}

* {
  box-sizing: border-box;
}

.container {
  background-color: #f2f2f2;
  padding: 5px 20px 15px 20px;
  border: 1px solid lightgrey;
  border-radius: 3px;
  width:600px;
  float: center;
  margin: auto;
}

input[type=text], select {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  margin-top: 6px;
  margin-bottom: 16px;
  resize: vertical;
}

label {
  margin-bottom: 10px;
  display: block;
}

.icon-container {
  margin-bottom: 20px;
  padding: 7px 0;
  font-size: 24px;
}

.btn {
  background-color: #9696e3;
  color: white;
  padding: 12px;
  margin: 10px 0;
  border: none;
  width: 30%;
  border-radius: 3px;
  cursor: pointer;
  font-size: 17px;
}

.btn:hover {
  background-color: #6d6dab;
}

a {
  color: #2196F3;
}

hr {
  border: 1px solid lightgrey;
}

span.price {
  float: right;
  color: grey;
}
</style>
</head>

<script>
//핸드폰 번호만  중복검사 
function duplication(type, contact){
	if(type == "휴대전화"){
		$.ajax({
			url:"/checkExist/"+type+"/"+contact,
			cache: false,
			type: "GET",
			success: function(data){
				console.log(data);
				if(data === "false"){
					$('#notice').text('중복되는 휴대전화 번호입니다.');
					$('#saveBtn').prop("disabled", true);
				}else {
					$('#notice').text('사용가능한 휴대전화 번호입니다.');
					$('#saveBtn').prop("disabled", false);
				}
			}
		});
	}else { //팩스, 사무실, 기타 번호일 경우 버튼 다시 풀어주기 
// 		$('#notice').text('사용가능한 번호입니다.');
		$('#saveBtn').prop("disabled", false);
	}
}
////////연락처 유효성 검사		
function checkContact(contact){
      var regExpContact = /^\d{2,4}-\d{3,4}-\d{4}$/;
      if(regExpContact.test(contact)) { 
        return true;
      } else {
        return false;
      }
}
function input_check(){
	var form = document.insert;
    if(form.contact.value == '') {
	      alert('연락처를 입력해 주십시오.');
	      form.contact.focus();
	      return false;
	    }
	    else {
	      if(!checkContact(form.contact.value)) {
	        alert('잘못된 전화번호입니다. 형식에 맞게 입력하세요.');
	        form.contact.focus();
	        return false;
	      }
	    }	
}
//////////////////////////////////////////////////////////////////
		//전화번호에 자동'-'생성
		 function addCommas(x) {
		     return x.toString().replace(/\B(?=(\d{4})+(?!\d))/g, "-");
		 }
		 //모든 콤마 제거
		 function removeCommas(x) {
		     if(!x || x.length == 0) return "";
		     else return x.split("-").join("");
		 }
		 
		 $(document).ready(function(){
		 $('#contact').on("focus", function() {
			    var x = $(this).val();
			    x = removeCommas(x);
			    $(this).val(x);
			}).on("focusout", function() {
			    var x = $(this).val();
			    if(x && x.length > 0) {
			        if(!$.isNumeric(x)) {
			            x = x.replace(/[^0-9]/g,"");
			        }
			        x = addCommas(x);
			        $(this).val(x);
			    }
			}).on("keyup", function() {
			    $(this).val($(this).val().replace(/[^0-9]/g,""));
			}); 
		 });
		 
		 $(document).ready(function(){
				$("input, textarea").keyup(function(){
					var value = $(this).val();
					var arr_char = new Array();
					arr_char.push("'");
					arr_char.push("\"");
					arr_char.push("<");
					arr_char.push(">");
				
					for(var i=0 ; i<arr_char.length ; i++)	{
						if(value.indexOf(arr_char[i]) != -1)
						{	window.alert("<, >, ', \" 특수문자는 사용하실 수 없습니다.");
							value = value.substr(0, value.indexOf(arr_char[i]));
							$(this).val(value);		
						}
					}
				});
			});
</script>
<body>
		<div class="container">
		<form name="insert"onsubmit='return input_check();' method="POST" action="/saveContact.html">
			<h2>${oneViewCon.name}님의 연락처 추가 입력</h2>
			
			<input type="hidden" name="userId" id="userId" value="${id }"> 
			<label for="email"><i class="fa fa-user"></i>타입</label> 
			<select name="type" id="type" required>
				<option value="" selected disabled hidden>타입 선택</option>
				<option value="휴대전화">휴대전화</option>
				<option value="직장">직장</option>
				<option value="팩스">팩스</option>
				<option value="기타">기타</option>
			</select>

			<label for="contact"><i class="fa fa-user"></i>번호</label> 
			<input type="text" name="contact" id="contact" maxlength="11" placeholder="'-'는 자동생성 됩니다.">
			 <script>
			 	$("#contact").on("input",function(){
			 		var type = $("#type").val(); //타입가져오기
			 		var contact = $("#contact").val(); //번호 가져오기
			 		duplication(type, addCommas(contact));
			 	});
			 	$("#type").on("change",function(){ //타입 바꿨을 경우
			 		var type = $("#type").val(); //타입가져오기
			 		var contact = $("#contact").val(); //번호 가져오기
			 		duplication(type, addCommas(contact));
			 	});
			 </script>
			 <span style="font-size:14; color:red;" id="notice" name="notice"></span>
			<div style="text-align: center;">
				<input type="submit" id="saveBtn" disabled name="saveBtn" value="추가" class="btn"> 
				<input type="button" value="취소" onclick="history.go(-1)" class="btn">
			</div>
		</form>
		</div>
</body>
</html>