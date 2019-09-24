<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://code.jquery.com/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<html>
<head>
<meta charset="UTF-8">
<title>사원리스트 전체 조회</title>
<script>
	//항목별 검색
	function searchByOption() {
		var act = "/search/" + document.getElementById("srOption").value;
		searchOption.action = act;
		searchOption.submit();
	}
	function deleteOK() {
		var delForm = document.del;
		if (confirm("정말 삭제하시겠습니까?") == true) {
			delForm();
		} else { //취소
			return false;
		}
	}
</script>
</head>
<body>
	<h1>사원 리스트 전체 조회</h1>
	<br><br><br>
	<table cellspacing=1 width=620 border=1>
		<tr>
			<td width=40>사번</td>
			<td width=80>이름</td>
			<td width=80>직급</td>
			<td width=80>부서</td>
			<td width=150>등록일</td>
			<td width=80>관리</td>
		</tr>
		<c:forEach items="${allView}" var="allView">
			<tr>
				<td width=40>${allView.id }</td>
				<td width=80><a href="./oneView/${allView.id }"><b>${allView.name }</b></td>
				<td width=80>${allView.position }</td>
				<td width=80>${allView.department }</td>
				<td width=150><fmt:formatDate value="${allView.regiDate }"
						pattern="yyyy-MM-dd" /></td>
				<td width=80><a style="text-decoration: none;"
					href="/editForm/${allView.id }">수정</a> <a id="del" name="del"
					style="text-decoration: none;" onclick="deleteOK();"
					href="/delete/${allView.id }">삭제</a></td>
			</tr>
		</c:forEach>
	</table>

	<!-- 검색창 -->
	<form id="searchOption" name="insert" method="GET"
		onsubmit="searchByOption()">
		<select id="srOption">
			<option value="all">전체</option>
			<option value="name">이름</option>
			<option value="department">부서</option>
			<option value="position">직급</option>
			<option value="contact">번호</option>
		</select>

		<!-- input type="hidden" id="option" name="option"-->
		<input type="text" maxlength="30" required name="key"> <input
			type="submit" required value="검색">
	</form>

	<button onclick="location.href='newForm.html'">사용자 추가</button>
</body>
</html>