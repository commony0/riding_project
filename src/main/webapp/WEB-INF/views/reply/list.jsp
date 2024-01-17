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
<script type="text/javascript">
  function delete_reply(detailno, replyno) {
    let sw = confirm('삭제하시면 복구 할 수 없습니다. 삭제하시겠습니까?');
    if(sw==true){
      location.href='../reply/delete.do?detailno=' + detailno + '&replyno=' + replyno;
    } else{
      alert('삭제를 취소합니다.');
    }
  }
</script>
<body>
<c:import url = "/menu/top.do"/>
<div class='title_line'>댓글 목록</div>

<aside class="aside_right">
  <a href="javascript:location.reload();">새로고침</a>
</aside> 
<div class="menu_line"></div>
 
<table class ="table table-hover">
  <colgroup>
    <col style='width: 15%;'/>
    <col style='width: 60%;'/>
    <col style='width: 15%;'/>    
    <col style='width: 10%;'/>    
  </colgroup>
  <thead>
    <tr>
      <th class="th_bs">댓글 번호</th>
      <th class="th_bs">댓글</th>
      <th class="th_bs">등록일</th>
      <th class="th_bs"></th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="replyVO" items="${list }" varStatus="info">
      <c:set var = "replyno" value = "${replyVO.replyno }"/>
      <c:set var = "detailno" value = "${replyVO.detailno }"/>
      <tr>
        <td class="td_bs">${replyno }</td> 
        <td class="td_bs"><a href="../detail/read.do?detailno=${detailno}" style = "display: block;">${replyVO.content }</a></td>
        <td class="td_bs">${replyVO.rdate.substring(0, 16) }</td>
        <td class="td_bs">        
          <a href="javascript:delete_reply(${detailno}, ${replyno })"><img src="/reply/images/delete.png" class="icon"></a>
        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>