<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="dev.mvc.region.RegionVO" %>
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
<c:import url="/menu/top.do" />

<div class='title_line'>지역 조회</div>
  <%
  RegionVO regionVO = (RegionVO)request.getAttribute("regionVO");
  %>
  <div class="container mt-3">
    <ul class="list-group list-group-flush">
      <li class="list-group-item">번호: <%=regionVO.getRegionno() %></li>
      <li class="list-group-item">이름: <%=regionVO.getName() %></li>
      <li class="list-group-item">등록 글수: <%=regionVO.getCnt() %></li>
      <li class="list-group-item">등록일: <%=regionVO.getRdate() %></li>
    </ul>
  </div>
  
  <div class="content_body_bottom">
    <button type="button" onclick = "location.href='./create.do'" class="btn btn-secondary btn-sm">등록</button>
    <button type="button" onclick = "location.href='./list_all.do'" class="btn btn-secondary btn-sm">목록</button> 
  </div>

 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>