<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="mloginno" value="${mloginVO.mloginno }" />
<c:set var="logindate" value="${mloginVO.logindate }" />


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
<div class='title_line'>매니저 접속 기록 삭제</div>

 <div id='panel_delete' style='padding: 10px 0px 10px 0px; background-color: #F9F9F9; width: 100%; text-align: center;'>
  <form name='frm_delete' id='frm_delete' method='post' action='./delete.do'>
    <input type="hidden" name="mloginno" value="${mloginno }">
      
    <div class="msg_warning">매니저 접속 기록을 삭제하면 복구 할 수 없습니다.</div>
    <label>삭제할 로그인 내역</label>: ${logindate }
  
    <button type="submit" id='submit' class='btn btn-warning btn-sm' style='height: 28px; margin-bottom: 5px;'>삭제</button>
    <button type="button" onclick="location.href='/clogin/list_all.do'" class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>취소</button>
  </form>
</div>

<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>