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
<c:import url="/menu/top.do" />
<div class='title_line'>지역 수정</div>

<aside class="aside_right">
  <a href="./create.do?regionno=${regionVO.regionno }">등록</a>
  <span class='menu_divide' >│</span>
  <a href="javascript:location.reload();">새로고침</a>
</aside> 
<div class="menu_line"></div>

<form name='frm' method='post' action='/region/update.do'>
  <input type='hidden' name='regionno' value='${regionVO.regionno }'>
  <div style="text-align: center;">
    <label>지역 이름</label>
    <input type="text" name="name" value="${regionVO.name }" required="required" autofocus="autofocus" 
               class="" style="width: 30%">
    <label>글수</label>
    <input type="text" name="cnt" value="${regionVO.cnt }" required="required" autofocus="autofocus" 
               class="" style="width: 20%">
    <button type="submit" class="btn btn-secondary btn-sm">저장</button>
    <button type="button" onclick = "history.back();" class="btn btn-secondary btn-sm">취소</button> 
  </div>
</form>

  <table class ="table table-hover">
  <colgroup>
    <col style='width: 10%;'/>
    <col style='width: 45%;'/>
    <col style='width: 10%;'/>    
    <col style='width: 20%;'/>
    <col style='width: 15%;'/>
  </colgroup>
  <thead>
    <tr>
      <th class="th_bs">순서</th>
      <th class="th_bs">지역 이름</th>
      <th class="th_bs">자료수</th>
      <th class="th_bs">등록일</th>
      <th class="th_bs">기타</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="regionVO" items="${list }" varStatus="info">
      <c:set var = "regionno" value = "${regionVO.regionno }"/>
      
      <tr>
        <td class="td_bs">${info.count }</td> <!-- 순서를 반드시 1부터 출력하고 싶을때 -->
        <td class="td_bs"><a href="./read.do?regionno=${regionno }" style = "display: block;">${regionVO.name }</a></td>
        <td class="td_bs">${regionVO.cnt }</td>
        <td class="td_bs">${regionVO.rdate.substring(0, 10) }</td>
        <td class="td_bs">
           <img src="/region/images/show.png" class="icon">
          <img src="/region/images/decrease.png" class="icon">
          <img src="/region/images/increase.png" class="icon">
          <a href="./update.do?regionno=${regionno }"><img src="/region/images/update.png" class="icon"></a>
          <a href="./delete.do?regionno=${regionno }"><img src="/region/images/delete.png" class="icon"></a>
        </td>
      </tr>
    </c:forEach>
  </tbody>
 </table>

 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>