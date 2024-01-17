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
<div class='title_line'>게시판 목록</div>

<aside class="aside_right">
  <a href="./create.do?regionno=${regionVO.regionno }">등록</a>
  <span class='menu_divide' >│</span>
  <a href="javascript:location.reload();">새로고침</a>
</aside> 
<div class="menu_line"></div>

<form name='frm' method='post' action='/region/create.do'>
  <div style="text-align: center;">
    <label>게시판 이름</label>
    <input type="text" name="name" value="" required="required" autofocus="autofocus" 
               class="" style="width: 50%">
    <button type="submit" class="btn btn-secondary btn-sm" style = "height:28px; margin-bottom: 5px;">등록</button>
    <button type="button" onclick="location.href= './list_all.do'" class="btn btn-secondary btn-sm" 
                 style = "height:28px; margin-bottom: 5px;">목록</button> 
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
      <c:set var="seqno" value = "${regionVO.seqno }"/>
      <tr>
        <td class="td_bs">${seqno }</td> <!-- 순서를 반드시 1부터 출력하고 싶을때 -->
        <td class="td_bs"><a href="./read.do?regionno=${regionno }" style = "display: block;">${regionVO.name }</a></td>
        <td class="td_bs">${count_by_regionno }</td>
        <td class="td_bs">${regionVO.rdate.substring(0, 10) }</td>
        <td class="td_bs">
           <c:choose>
            <c:when test="${regionVO.visible == 'Y' }">
              <a href="./update_visible_n.do?regionno=${regionno }" title="게시판 공개 설정">
              <img src="/region/images/show.png" class="icon"></a>
            </c:when>
            <c:otherwise>
              <a href="./update_visible_y.do?regionno=${regionno }" title="게시판 비공개 설정">
              <img src="/region/images/hide.png" class="icon"></a>
            </c:otherwise>
          </c:choose>        
          <a href="./update_seqno_forward.do?regionno=${regionno }" title="우선 순위 높임"><img src="/region/images/decrease.png" class="icon"></a>
          <a href="./update_seqno_backward.do?regionno=${regionno }" title="우선 순위 낮춤"><img src="/region/images/increase.png" class="icon"></a>
          <a href="./update.do?regionno=${regionno }" title="수정"><img src="/region/images/update.png" class="icon"></a>
          <a href="./delete.do?regionno=${regionno }" title="삭제"><img src="/region/images/delete.png" class="icon"></a>
        </td>
      </tr>
    </c:forEach>
  </tbody>
 </table>

 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>