<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://code.jquery.com/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>  
<html>
<head>
<meta charset="UTF-8">
<title>신규 입력</title>
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

input[type=text] {
  width: 100%;
  margin-bottom: 20px;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 3px;
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

<script>

//이름 유효성 검사
function checkName(name){
	var regExpName = /^[가-힣a-zA-Z\s]+$/;
	if(regExpName.test(name)){
		return true;
	}else{
		return false;
	}
}

//직급 유효성 검사
function checkPosition(position){
	var regExpPosition = /^[가-힣a-zA-Z0-9\s]+$/;
	if(regExpPosition.test(position)){
		return true;
	}else{
		return false;
	}
}	
//부서 유효성 검사
function checkDepartment(department){
	var regExpDepartment = /^[가-힣a-zA-Z0-9\s]+$/;
	if(regExpDepartment.test(department)){
		return true;
	}else{
		return false;
	}
}	

//이메일 유효성 검사		
function checkEmail(email){
	var regExpEmail = /^[-!'#$%&*+./0-9=?A-Z^_a-z{|}~]+@[-!#$'%&*+/0-9=?A-Z^_a-z{|}~]+.[-!#$%&*+./0-9=?A-Z'^_a-z{|}~]+$/;
      if(regExpEmail.test(email)) { // test 는 정규 Methods 입니다. test는 변경없음
        return true;
      } else {
        return false;
      }
}
////////////////////////////////////////////////////////////////
function input_check(){ ///form 안에 onsubmit='return input_check();'으로 넣기위한 함수
	var form = document.insert;//form의 name = insert
	if(form.name.value == ''){
		alert("이름을 입력하세요.");
		form.name.focus();
		return false;
	}else{
		if(!checkName(form.name.value)){
			alert('이름은 형식에 맞는 한글이나 영문으로만 입력할 수 있습니다.');
			form.name.focus();
			return false;
		}
	}
	///////////////////////////////////////////////////
	if(form.position.value == ''){
			alert("직급을 입력하세요.");
			form.position.focus();
			return false;
	}else{
		if(!checkPosition(form.position.value)){
			alert("직급명을 확인해주세요.\n직급란에 -, <, >, ', \" 특수문자는 사용하실 수 없습니다.")
			form.position.focus();
			return false;
		}
	}
	///////////////////////////////////////////////////
	if(form.department.value == ''){
		alert("부서를 입력하세요.");
		form.department.focus();
		return false;
	}else{
		if(!checkDepartment(form.department.value)){
			alert("부서명을 확인해주세요.\n부서란에  -, <, >, ', \" 특수문자는 사용하실 수 없습니다.")
			form.department.focus();
			return false;
		}
	}
	///////////////////////////////////////////////////
	if(form.email.value == '') {
     	alert('이메일 주소를 입력해 주십시오.');
      	form.email.focus();
     	 return false;
    }else {
     	if(!checkEmail(form.email.value)) {
        alert('이메일 주소를 바르게 입력해 주십시오.');
        form.email.focus();
        return false;
      }
    }	
}
</script>

</head>
	<body>
		<div class="container">
		<form name="insert" onsubmit='return input_check();' method="POST" action="/save.html">
			<h2>신규 사원 입력</h2>
			
			<label for="fname"><i class="fa fa-user"></i> 이름</label>
			<input type="text" id="name" name="name" maxlength="20" required placeholder="이름을 입력해주세요."> <!-- limitbyte="20" -->
			
			<label for="email"><i class="fa fa-user"></i> 직급</label>			
			 <input type="text" id="position" name="position" maxlength="30" required placeholder="직급을 입력해주세요.">
			 
			 <label for="adr"><i class="fa fa-address-card-o"></i> 부서</label>			 
			 <input type="text" id="department" name="department" maxlength="30" required placeholder="부서를 입력해주세요.">
			 
			 <label for="adr"><i class="fa fa-address-card-o"></i> 이메일</label>			 
			 <input type="text" id="email" name="email" maxlength="50" required placeholder="이메일을 입력해주세요.">
			  
			  <label for="city"><i class="fa fa-institution"></i> 비고란</label>			  
			   <input type="text" id="special" name="special" maxlength="255"  placeholder="특이사항을 입력해주세요.">
			   
			<div style="text-align: center;">
				<input type="submit" value="추가" class="btn"> 
				<input type="button" value="취소" onclick="history.go(-1)" class="btn">
			</div>
		</form>
	</div>
	</body>
</html>