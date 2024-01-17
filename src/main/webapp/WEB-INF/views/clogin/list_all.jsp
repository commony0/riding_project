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
<div class='title_line'>회원 접속 기록</div>

<aside class="aside_right">
  <a href="javascript:location.reload();">새로고침</a>
</aside> 
<div class="menu_line"></div>
 
<table class ="table table-hover">
  <colgroup>
    <col style='width: 15%;'/>
    <col style='width: 15%;'/>
    <col style='width: 30%;'/>    
    <col style='width: 25%;'/>
    <col style='width: 15%;'/>
  </colgroup>
  <thead>
    <tr>
      <th class="th_bs">회원 접속 번호</th>
      <th class="th_bs">회원 번호</th>
      <th class="th_bs">IP</th>
      <th class="th_bs">등록일</th>
      <th class="th_bs">관리</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="cloginVO" items="${list }" varStatus="info">
      <c:set var = "cloginno" value = "${cloginVO.cloginno }"/>
      <tr>
        <td class="td_bs">${cloginno }</td> 
        <td class="td_bs"><a href="./list_all_c.do?cyclistno=${cloginVO.cyclistno }" style = "display: block;">${cloginVO.cyclistno }</a></td>
        <td class="td_bs">${cloginVO.ip }</td>
        <td class="td_bs">${cloginVO.logindate.substring(0, 16) }</td>
        <td class="td_bs">      
          <c:if test="${sessionScope.manager_id != null }">
            <a href="./delete.do?cloginno=${cloginno }" title="삭제"><img src="/clogin/images/delete.png" class="icon"></a>
          </c:if>  
        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>

 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>