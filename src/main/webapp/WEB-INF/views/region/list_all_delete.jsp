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
  <div class='title_line'>게시판 삭제</div>
  
  <aside class="aside_right">
    <a href="./create.do?regionno=${regionVO.regionno }">등록</a>
    <span class='menu_divide' >│</span>
    <a href="javascript:location.reload();">새로고침</a>
  </aside> 
  <div class="menu_line"></div>
  
  <div id='panel_delete' style='padding: 10px 0px 10px 0px; background-color: #F9F9F9; width: 100%; text-align: center;'>
    <form name='frm_delete' id='frm_delete' method='post' action='./delete.do'>
      <input type="hidden" name="regionno" value="${param.regionno }"> <%-- 삭제할 게시판 번호 --%>

      <c:choose>
        <c:when test="${count_by_regionno >= 1 }"> <%-- 자식 레코드가 있는 상황 --%>
          <div class="msg_warning">
            관련 자료 ${count_by_regionno } 건이 발견되었습니다.<br>
            관련 자료와 게시판을 모두 삭제하시겠습니까?
          </div>
            
          <label>관련 게시판 이름</label>: ${regionVO.name } 
          <a href="../detail/list_by_regionno.do?regionno=${regionVO.regionno }&now_page=1" title="관련 게시판으로 이동"><img src='/region/images/link.png'></a>
          &nbsp;      
          <button type="submit" id='submit' class='btn btn-danger btn-sm' style='height: 28px; margin-bottom: 5px;'>관련 자료와 함께 게시판 삭제</button>
          
        </c:when>
        <c:otherwise>
          <div class="msg_warning">게시판을 삭제하면 복구 할 수 없습니다.</div>
          <label>게시판 이름</label>: ${regionVO.name }
      
          <button type="submit" id='submit' class='btn btn-warning btn-sm' style='height: 28px; margin-bottom: 5px;'>삭제</button>          
        </c:otherwise>
      </c:choose>      

      <button type="button" onclick="location.href='/region/list_all.do'" class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>취소</button>
    </form>
  </div>
  
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