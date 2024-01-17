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
<div class='title_line'>전체 글 목록</div>
 
<aside class="aside_right">
  <a href="javascript:location.reload();">새로고침</a>
</aside> 
<div class="menu_line"></div>

<table class ="table table-hover">
  <colgroup>
    <col style="width: 10%;"></col>
    <col style="width: 80%;"></col>
    <col style="width: 10%;"></col>
  </colgroup>
  <thead>
    <tr>
      <th style='text-align: center;'>파일</th>
      <th style='text-align: center;'>제목</th>
      <th style='text-align: center;'>기타</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="detailVO" items="${list }" varStatus="info">
      <c:set var = "detailno" value = "${detailVO.detailno }"/>
      <c:set var="thumb1" value="${detailVO.thumb1 }"/>
      
      <tr onclick="location.href='./read.do?detailno=${detailno }'" style="cursor:pointer;">
        <td >
          <c:choose>
          <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}"><%-- 이미지인지 검사 --%>
            <img src="/detail/storage/${thumb1 }" style="width: 120px; height: 90px;">
          </c:when>
          <c:otherwise> <!-- 이미지가 없는 경우 기본 이미지 출력: /static/detail/images/none1.png -->
            <img src="/detail/images/none1.png" style="width: 120px; height: 90px;">
          </c:otherwise>
          </c:choose>
        </td>
        <td class="td_bs_left">
          <span style="font-weight:bold">${detailVO.title } </span><br>
          <c:choose>
            <c:when test="${detailVO.content.length() > 160 }">
              ${detailVO.content.substring(0, 160) }...
            </c:when>
            <c:otherwise>
              ${detailVO.content }
            </c:otherwise>
          </c:choose>
            ${detailVO.rdate.substring(0,16) }
        </td>
        <td class="td_bs">
          <a href="#" title="삭제"><img src="/detail/images/map.png" class="icon"></a>
          <a href="#" title="삭제"><img src="/detail/images/youtube.png" class="icon"></a>
          <a href="#" title="삭제"><img src="/detail/images/delete.png" class="icon"></a>
        </td>
      </tr>
    </c:forEach>
  </tbody>
 </table>
 

 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>