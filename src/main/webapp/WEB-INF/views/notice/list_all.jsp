<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>Cycling Club</title>
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
  
</head>
<body>
<c:import url = "/menu/top.do"/>
<div class='title_line'>공지사항 목록</div>

<aside class="aside_right">
  <a href="./create.do">등록</a>
  <span class='menu_divide' >│</span>
  <a href="javascript:location.reload();">새로고침</a>
</aside> 
<div class="menu_line"></div>
 
<table class ="table table-hover">
  <colgroup>
    <col style='width: 15%;'/>
    <col style='width: 50%;'/>
    <col style='width: 15%;'/>    
    <col style='width: 20%;'/>
  </colgroup>
  <thead>
    <tr>
      <th class="th_bs">공지 번호</th>
      <th class="th_bs">제목</th>
      <th class="th_bs">등록일</th>
      <th class="th_bs">기타</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="noticeVO" items="${list }" varStatus="info">
      <c:set var = "noticeno" value = "${noticeVO.noticeno }"/>
      <tr>
        <td class="td_bs">${noticeno }</td> 
        <td class="td_bs"><a href="./read.do?noticeno=${noticeno }" style = "display: block;">${noticeVO.title }</a></td>
        <td class="td_bs">${noticeVO.rdate.substring(0, 16) }</td>
        <td class="td_bs">
          <c:if test="${sessionScope.manager_id != null }">        
          <a href="./update.do?noticeno=${noticeno }" title="수정"><img src="/notice/images/update.png" class="icon"></a>
          <a href="./delete.do?noticeno=${noticeno }" title="삭제"><img src="/notice/images/delete.png" class="icon"></a>
          </c:if>
        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>