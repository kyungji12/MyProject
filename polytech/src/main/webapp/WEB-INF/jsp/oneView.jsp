<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://code.jquery.com/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>  
<html>
<head>
<meta charset="UTF-8">
<title>사원 조회</title>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body{
	width: 100%;
	text-align: center;
	min-width: 1065px;
}
h2 {
text-align : center;
	margin: auto;
}
table {
	border-collapse: collapse;
	border-spacing: 0;
	width: 60%;
	border: 1px solid #ddd;
	text-align : center;
	margin: auto;
}

th, td {
	text-align: left;
	padding: 16px;
}

tr:nth-child(odd) {
	background-color: #f7f7f7
}


/*검색 옵션 스타일*/
/*the container must be positioned relative:*/
.custom-select {
  position: relative;
  font-family: Arial;
}

.custom-select select {
  display: none; /*hide original SELECT element:*/
}

.select-selected {
  background-color: #a66bb5;
}

/*style the arrow inside the select element:*/
.select-selected:after {
  position: absolute;
  content: "";
  top: 14px;
  right: 10px;
  width: 0;
  height: 0;
  border: 6px solid transparent;
  border-color: #fff transparent transparent transparent;
}

/*point the arrow upwards when the select box is open (active):*/
.select-selected.select-arrow-active:after {
  border-color: transparent transparent #fff transparent;
  top: 7px;
}

/*style the items (options), including the selected item:*/
.select-items div,.select-selected {
  color: #ffffff;
  padding: 8px 16px;
  border: 1px solid transparent;
  border-color: transparent transparent rgba(0, 0, 0, 0.1) transparent;
  cursor: pointer;
  user-select: none;
}

/*style items (options):*/
.select-items {
  position: absolute;
  background-color: #a66bb5;
  top: 100%;
  left: 0;
  right: 0;
  z-index: 99;
}

/*hide the items when the select box is closed:*/
.select-hide {
  display: none;
}

.select-items div:hover, .same-as-selected {
  background-color: rgba(0, 0, 0, 0.1);
}

/* 검색창 스타일 */
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

form.example button {
	float: left;
	width: 20%;
	padding: 10px;
	background: #a66bb5;
	color: white;
	font-size: 17px;
	border: 1px solid grey;
	border-left: none;
	cursor: pointer;
}

form.example button:hover {
	background: #8b6694;
}

form.example::after {
	content: "";
	clear: both;
	display: table;
}

/*button group*/
.btn-group button {
  background-color: #9696e3; /* Green background */
  border: 1px solid #9696e3; /* Green border */
  color: white; /* White text */
  padding: 10px 24px; /* Some padding */
  cursor: pointer; /* Pointer/hand icon */
  margin:2%;
}

/* Clear floats (clearfix hack) */
.btn-group:after {
  content: "";
  clear: both;
  display: table;
}

.btn-group button:not(:last-child) {
  border-right: none; /* Prevent double borders */
}

/* Add a background color on hover */
.btn-group button:hover {
  background-color: #6d6dab;
}
/*수정, 삭제 관리버튼*/
.editbtn {
  border: 2px solid black;
  background-color: white;
  color: black;
  padding: 3px 5px;
  cursor: pointer;
  border-radius: 5px;
}
/* Gray */
.edit {
  border-color: #9696e3;
  color: black;
}

.edit:hover {
  background: #9696e3;
}

hr.new3 {
  border-top: 1px dotted #9696e3;
}
hr.new4 {
  border: 1px solid #9696e3;
}
</style>
<script>
function deleteOK(){
	result = confirm("정말 삭제하시겠습니까?")
	if(result == true){
		location.href='/delete/${oneView.id}'
	}else{ //취소
		return false;
	}
}	
function deleteOK2(userid, id){
	result = confirm("정말 삭제하시겠습니까?")
	if(result == true){
		location.href='./'+userid+'/deleteContact/'+id;
	}else{ //취소
		return false;
	}
}
</script>
</head>
<body>
	<br>
	<h2>${oneView.name }님의 기본 정보 조회</h2>
	<br>
		<table border=1>
			<tr align="center" ><td>사번</td><td>${oneView.id}</td></tr>
			<tr align="center" ><td>이름</td><td>${oneView.name}</td></tr>
			<tr align="center"><td>직급</td><td>${oneView.position}</td></tr>
			<tr align="center"><td>부서</td><td>${oneView.department}</td></tr>
			<tr align="center"><td>이메일</td><td>${oneView.email}</td></tr>
			<tr align="center"><td>등록일</td>
				<td><fmt:formatDate value="${oneView.regiDate}" pattern="yyyy-MM-dd"/></td></tr>
			<tr align="center"><td>최근 수정일</td>
				<td><fmt:formatDate value="${oneView.lastUpdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td></tr>
			<tr align="center"><td>비고</td><td>${oneView.special}</td></tr>	
		</table>
		<div class="btn-group" style="text-align: center;">
		<button onclick="location.href='/editForm/${oneView.id}'">프로필 수정</button>
	<button onclick="deleteOK();">프로필 삭제</button>
	<button onclick="location.href='/0'">목록</button>
	
	</div>
		<hr class="new3">
	<div class="btn-group" style="text-align: center;">
		<button onclick="location.href='./${oneView.id}/newContactForm'">연락처
			추가</button>
	</div>
	<c:choose>
		<c:when test="${empty oneView.contacts }">
			<a>등록된 연락처 정보가 없습니다.</a>
		</c:when>
	<c:otherwise>
		<table border=1>
		<tr>
			<td>타입</td>
			<td>번호</td>
			<td>관리</td>
		</tr>
	<c:forEach items="${oneView.contacts}" var="cont">
		<tr>
			<td>${cont.type}</td>
			<td>${cont.contact}</td>
			<td>
				<button class="editbtn edit" onclick="location.href='./${cont.userId.id}/editContactForm/${cont.id }'">수정</button>
				<button class="editbtn edit" onclick="deleteOK2('${cont.userId.id}','${cont.id}');">삭제</button>
			</td>
		</tr>
	</c:forEach>
	</table>
</c:otherwise>
</c:choose>
<br><br><br><br><br><br>
</body>
</html>