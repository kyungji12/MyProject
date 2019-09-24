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
<title>사원리스트 전체 조회</title>
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
table {
	border-collapse: collapse;
	border-spacing: 0;
	width: 80%;
	border: 1px solid #ddd;
	float: center;
	margin: auto;
	text-align: center;
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
  margin: 2% 2% 0 0;
}

.custom-select select {
  display: none; /*hide original SELECT element:*/
}

.select-selected {
  background-color: #9696e3;
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
  background-color: #9696e3;
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
form.example input[type=text] {
	padding: 8.5px;
	font-size: 17px;
	border: 1px solid grey;
	float: left;
	width: 80%;
	background: #f1f1f1;
	margin:auto;
}

form.example button {
	float: left;
	width: 20%;
	padding: 10px;
	background: #9696e3;
	color: white;
	font-size: 17px;
	border: 1px solid grey;
	border-left: none;
	cursor: pointer;
}

form.example button:hover {
	background: #6d6dab;
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
  padding: 6px 24px; /* Some padding */
  cursor: pointer; /* Pointer/hand icon */
  margin:auto;
  margin-top:2%;
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


/*pagination*/
.pagination a {
  color: black;
  float: center;
  padding: 8px 16px;
  text-decoration: none;
  transition: background-color .3s;
}

.pagination a.active {
  background-color: #9696e3;
  color: white;
}

.pagination a:hover:not(.active) {background-color: #ddd;}

</style>
<script>
	//항목별 검색
	function searchByOption() {		
		var act = "/search/" + document.getElementById("srOption").value;
		searchOption.action = act;
		searchOption.submit();
	}
	//한 명 삭제 확인 팝업
	function deleteOK(id){
		var delForm = document.del;
		if(confirm("정말 삭제하시겠습니까?") == true){
			location.href='/delete/'+id;
		}else{ //취소
			return false;
		}
	}
	//checkBox 전체선택, 전체 해제
	function checkingAll(){
		if($("#checkAll").is(':checked')){
			$("input[name=checkOne]").prop("checked",true);
		}else{
			$("input[name=checkOne]").prop("checked",false);
		}
	}
	//삭제 확인 팝업(체크박스 된 것 전부)
		function deleteSelectionOK(){
		var delForm = document.del;
		if(confirm("정말 삭제하시겠습니까?") == true){
			list.submit();
		}else{ //취소
			return false;
		}
	}
</script>
</head>
<body>
	<h2>사원 리스트 조회 </h2> <!-- (총 ${totalCnt } 건) -->
	<br>
	<div style="width: 80%; margin: auto;">
	<form method="POST" id="list" action="/deleteSelection.html">
	<table style="width: 100%;">
		<tr style="color: white; background-color: #9696e3">
			<td width=10><input type="checkbox" name="checkAll" id="checkAll" onclick="checkingAll();"/></td>
			<td width=40>사번</td>
			<td width=80>이름</td>
			<td width=80>직급</td>
			<td width=80>부서</td>
			<td width=150>등록일</td>
			<td width=90>관리</td>
		</tr>
		<c:choose>
			<c:when test="${empty page }">
				<a>등록된 사원이 없습니다.</a>
			</c:when>
		<c:otherwise>
		<c:forEach items="${page.content}" var="allView">
			<tr>
				<td width=10><input type="checkbox" name="checkOne" id="checkOne" value="${allView.id }"/></td>
				<td width=40>${allView.id }</td>
				<td width=80><a style="text-decoration:none;"href="./oneView/${allView.id }"><b>${allView.name }</b></a></td>
				<td width=80>${allView.position }</td>
				<td width=80>${allView.department }</td>		
				<td width=150><fmt:formatDate value="${allView.regiDate }" pattern="yyyy-MM-dd"/></td>	
				<td width=90>
					<button type="button" class="editbtn edit" onclick="location.href='/editForm/${allView.id }'">수정</button>
					<button type="button" class="editbtn edit" onclick="deleteOK('${allView.id }');">삭제</button>
				</td>
			</tr>
		</c:forEach>
		</c:otherwise>
		</c:choose>
	</table>
		<div class="btn-group" style="width:100%; margin:auto; text-align:right;">
			<button type="button" style="padding: 6px 30px;" onclick="deleteSelectionOK();">선택 삭제</button>
		</div>
	</form>
	</div>
	<div style="width: 80%; margin: auto; display:flex;">
		<form class="example" id="searchOption" name="insert" 
				method="GET" style="width:80%; display:flex; " onsubmit="searchByOption()">
			<div class="custom-select" style="width:15%;">
				<select id="srOption">
					<option value="all">전체</option>
					<option value="name">이름</option>
					<option value="department">부서</option>
					<option value="position">직급</option>
					<option value="contact">연락처</option>
				</select>
			</div>
			<div style="width: 35%; text-align:center; margin-top:auto;">
				<input type="text" maxlength="30" required placeholder="검색어를 입력해주세요." name="key">
				<button type="submit"><i class="fa fa-search"></i></button>
			</div>
		</form>
		<div class="btn-group" style="text-align:right; display:flex; margin-left:auto; margin-top:auto; margin-bottom:auto;">
			<button onclick="location.href='newForm.html'">사용자 추가</button>
		</div>
	</div>
<br>
<div class="pagination" >
	<a href='/0'>&laquo;</a>	
	<c:if test="${!page.first}">
		<a href="${page.number-1}">&lt;</a>
	</c:if>
	<c:forEach begin="${startRange }" end="${endRange }" var="e">
		 <a  href="${e }">${e+1 }</a>
	</c:forEach>
	<c:if test="${!page.last}">
		 <a href="${page.number+1}">&gt;</a>
	</c:if> 
	<a href='/${pageNum-1 }'>&raquo;</a>
</div>
<br><br>
<!-- 검색옵션용 스크립트 -->
<script>
var x, i, j, selElmnt, a, b, c;
/*look for any elements with the class "custom-select":*/
x = document.getElementsByClassName("custom-select");
for (i = 0; i < x.length; i++) {
  selElmnt = x[i].getElementsByTagName("select")[0];
  /*for each element, create a new DIV that will act as the selected item:*/
  a = document.createElement("DIV");
  a.setAttribute("class", "select-selected");
  a.innerHTML = selElmnt.options[selElmnt.selectedIndex].innerHTML;
  x[i].appendChild(a);
  /*for each element, create a new DIV that will contain the option list:*/
  b = document.createElement("DIV");
  b.setAttribute("class", "select-items select-hide");
  for (j = 1; j < selElmnt.length; j++) {
    /*for each option in the original select element,
    create a new DIV that will act as an option item:*/
    c = document.createElement("DIV");
    c.innerHTML = selElmnt.options[j].innerHTML;
    c.addEventListener("click", function(e) {
        /*when an item is clicked, update the original select box,
        and the selected item:*/
        var y, i, k, s, h;
        s = this.parentNode.parentNode.getElementsByTagName("select")[0];
        h = this.parentNode.previousSibling;
        for (i = 0; i < s.length; i++) {
          if (s.options[i].innerHTML == this.innerHTML) {
            s.selectedIndex = i;
            h.innerHTML = this.innerHTML;
            y = this.parentNode.getElementsByClassName("same-as-selected");
            for (k = 0; k < y.length; k++) {
              y[k].removeAttribute("class");
            }
            this.setAttribute("class", "same-as-selected");
            break;
          }
        }
        h.click();
    });
    b.appendChild(c);
  }
  x[i].appendChild(b);
  a.addEventListener("click", function(e) {
      /*when the select box is clicked, close any other select boxes,
      and open/close the current select box:*/
      e.stopPropagation();
      closeAllSelect(this);
      this.nextSibling.classList.toggle("select-hide");
      this.classList.toggle("select-arrow-active");
    });
}
function closeAllSelect(elmnt) {
  /*a function that will close all select boxes in the document,
  except the current select box:*/
  var x, y, i, arrNo = [];
  x = document.getElementsByClassName("select-items");
  y = document.getElementsByClassName("select-selected");
  for (i = 0; i < y.length; i++) {
    if (elmnt == y[i]) {
      arrNo.push(i)
    } else {
      y[i].classList.remove("select-arrow-active");
    }
  }
  for (i = 0; i < x.length; i++) {
    if (arrNo.indexOf(i)) {
      x[i].classList.add("select-hide");
    }
  }
}
/*if the user clicks anywhere outside the select box,
then close all select boxes:*/
document.addEventListener("click", closeAllSelect);
</script>
</body>
</html>