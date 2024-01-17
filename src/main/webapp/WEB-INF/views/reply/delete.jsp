<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="replyno" value="${replyVO.replyno }" />
<c:set var="content" value="${replyVO.content }" />

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
<div class='title_line'>댓글 삭제</div>

 <div id='panel_delete' style='padding: 10px 0px 10px 0px; background-color: #F9F9F9; width: 100%; text-align: center;'>
  <form name='frm_delete' id='frm_delete' method='post' action='./delete.do'>
    <input type="hidden" name="replyno" value="${replyno }">
      
    <div class="msg_warning">댓글을 삭제하시겠습니까?</div>
    <label>댓글 내용</label>: ${content }
  
    <button type="submit" id='submit' class='btn btn-warning btn-sm' style='height: 28px; margin-bottom: 5px;'>삭제</button>
    <button type="button" onclick = "history.back()" class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>취소</button>
  </form>
</div>

 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>