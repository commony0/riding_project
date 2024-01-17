<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="noticeno" value="${noticeVO.noticeno }" />
<c:set var="title" value="${noticeVO.title }" />
<c:set var="content" value="${noticeVO.content }" />
<c:set var="rdate" value="${noticeVO.rdate }" />
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
  <div class='title_line'><a href="./list_all.do" class='title_link'>공지사항</a></div>
  
  <aside class="aside_right">
    <%-- 관리자로 로그인해야 메뉴가 출력됨 --%>
    <c:if test="${sessionScope.manager_id != null }">
      <a href="./create.do">등록</A>
      <span class='menu_divide' >│</span>
      <a href="./update.do?noticeno=${noticeno}">글 수정</A>
      <span class='menu_divide' >│</span>
      <a href="./delete.do?noticeno=${noticeno}">삭제</A>  
      <span class='menu_divide' >│</span>
    </c:if>

    <a href="javascript:location.reload();">새로고침</A>
    <span class='menu_divide' >│</span>    
    <a href="./list_all.do">목록</A>    
  </aside> 
  
  <DIV class='menu_line'></DIV>
  
  <fieldset class="fieldset_basic">
    <ul>
      <li class="li_none">
        <div style="width: 100%; word-break: break-all;">
          <span style="font-size: 1.5em; font-weight: bold;">${title }</span>
          <span style="font-size: 1em;">${rdate }</span><br><br>
          ${content }
        </div>
      </li>     
    </ul>
  </fieldset>
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>