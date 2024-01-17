<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="name" value="${regionVO.name }" />

<c:set var="regionno" value="${detailVO.regionno }" />
<c:set var="detailno" value="${detailVO.detailno }" />
<c:set var="thumb1" value="${detailVO.thumb1 }" />
<c:set var="file1saved" value="${detailVO.file1saved }" />
<c:set var="title" value="${detailVO.title }" />
<c:set var="content" value="${detailVO.content }" />
<c:set var="rdate" value="${detailVO.rdate }" />
<c:set var="youtube" value="${detailVO.youtube }" />
<c:set var="map" value="${detailVO.map }" />
<c:set var="file1" value="${detailVO.file1 }" />
<c:set var="word" value="${detailVO.word }" />
<c:set var="size1_label" value="${detailVO.size1_label }" />

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
  <div class='title_line'><a href="./list_by_regionno.do?regionno=${regionno }" class='title_link'>${name }</a></div>
  <%-- <div class='title_line'><a href="./list_by_regionno.do?regionno=${regionVO.regionno }" class='title_link'>${regionVO.name }</a></div> --%>
  
  <aside class="aside_right">
    <%-- 관리자로 로그인해야 메뉴가 출력됨 --%>
    <c:if test="${sessionScope.manager_id != null }">
      <%--
      http://localhost:9092/detail/create.do?regionno=1
      http://localhost:9092/detail/create.do?regionno=2
      http://localhost:9092/detail/create.do?regionno=3
      --%>
      <a href="./create.do?regionno=${regionno }">등록</A>
      <span class='menu_divide' >│</span>
      <a href="./update_text.do?detailno=${detailno}&now_page=${param.now_page}&word=${param.word }">글 수정</A>
      <span class='menu_divide' >│</span>
      <a href="./update_file.do?detailno=${detailno}&now_page=${param.now_page}">파일 수정</A>  
      <span class='menu_divide' >│</span>
      <a href="./map.do?regionno=${regionno }&detailno=${detailno}">지도</A>
      <span class='menu_divide' >│</span>
      <a href="./youtube.do?regionno=${regionno }&detailno=${detailno}">Youtube</A>
      <span class='menu_divide' >│</span>
      <a href="./delete.do?detailno=${detailno}&now_page=${param.now_page}&regionno=${regionno}">삭제</A>  
      <span class='menu_divide' >│</span>
    </c:if>
    <!-- 회원으로 로그인 했으면 댓글 링크 출력, top.jsp 참고 -->
    <c:if test="${sessionScope.id != null }">
      <a href="../reply/create.do?detailno=${detailno }">댓글 등록</A>
      <span class='menu_divide' >│</span>
    </c:if>
    
    <a href="javascript:location.reload();">새로고침</A>
    <span class='menu_divide' >│</span>    
    <a href="./list_by_regionno.do?regionno=${regionno }&now_page=${param.now_page == null ? 1 : param.now_page}&word=${param.word }">목록형</A>    
    <span class='menu_divide' >│</span>
    <a href="./list_by_regionno_grid.do?regionno=${regionno }&now_page=${param.now_page == null ? 1 : param.now_page}&word=${param.word }">갤러리형</A>
    <span class='menu_divide' >│</span>
    <a href="../reply/list_by_detailno_id.do?detailno=${detailno }">댓글 보기</A>
  </aside> 
  
  <div style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_by_regionno.do'>
      <input type='hidden' name='regionno' value='${regionno }'>  <%-- 게시판의 구분 --%>
      
      <c:choose>
        <c:when test="${param.word != '' }"> <%-- 검색하는 경우 --%>
          <input type='text' name='word' id='word' value='${param.word }'>
        </c:when>
        <c:otherwise> <%-- 검색하지 않는 경우 --%>
          <input type='text' name='word' id='word' value='' >
        </c:otherwise>
      </c:choose>
      <button type='submit' class='btn btn-secondary btn-sm' style="padding: 2px 8px 3px 8px; margin: 0px 0px 2px 0px;">검색</button>
      <c:if test="${param.word.length() > 0 }"> <!-- 검색 상태라면 검색 취소 버튼을 출력-->
        <button type='button' class='btn btn-secondary btn-sm' style="padding: 2px 8px 3px 8px; margin: 0px 0px 2px 0px;"
                    onclick="location.href='./list_by_regionno.do?regionno=${regionno}&word='">검색 취소</button>  
      </c:if>    
    </form>
  </div>
  
  <DIV class='menu_line'></DIV>

  <fieldset class="fieldset_basic">
    <ul>
      <li class="li_none">
        <div style="width: 100%; word-break: break-all;">
          <c:choose>
            <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}">
              <%-- /static/detail/storage/ --%>
              <img src="/detail/storage/${file1saved }" style='width: 50%; float: left; margin-top: 0.5%; margin-right: 1%;'> 
            </c:when>
            <c:otherwise> <!-- 기본 이미지 출력 -->
              <img src="/detail/images/none1.png" style='width: 50%; float: left; margin-top: 0.5%; margin-right: 1%;'> 
            </c:otherwise>
          </c:choose>

          <span style="font-size: 1.5em; font-weight: bold;">${title }</span>
          <span style="font-size: 1em;">${rdate }</span><br>
          ${content }
        </div>
      </li>
      
      <c:if test="${youtube.trim().length() > 0 }">
        <li class="li_none" style="clear: both; padding-top: 5px; padding-bottom: 5px;">
          <DIV style="text-align: center;">
            ${youtube }
          </DIV>
        </li>
      </c:if>
      
      <c:if test="${map.trim().length() > 0 }">
        <li class="li_none" style="clear: both; padding-top: 5px; padding-bottom: 5px;">
          <DIV style='text-align: center; width:640px; height: 360px; margin: 0px auto;'>
            ${map }
          </DIV>
        </li>
      </c:if>
      
      <li class="li_none" style="clear: both;">
        <DIV style='text-decoration: none;'>
          <br>
          검색어(키워드): ${word }
        </DIV>
      </li>
      <li class="li_none">
        <DIV>
          <c:if test="${file1.trim().length() > 0 }"> <%-- ServletRegister.java: registrationBean.addUrlMappings("/download"); --%>
            첨부 파일: <a href='/download?dir=/detail/storage&filename=${file1saved}&downname=${file1}'>${file1}</a> (${size1_label})  
            <a href='/download?dir=/detail/storage&filename=${file1saved}&downname=${file1}'><img src="/detail/images/download.png"></a>
          </c:if>
        </DIV>
      </li>   
    </ul>
    <span style="font-size: 1.2em; font-weight: bold;">댓글()</span>
    <hr>

    <table class ="table table-hover">

    <tbody>
      <c:forEach var="replyCyclistVO" items="${list }" varStatus="info">
        <c:set var = "id" value = "${replyCyclistVO.id }"/>
        <c:set var = "detailno" value = "${replyCyclistVO.detailno }"/>
        <c:set var = "replyno" value = "${replyCyclistVO.replyno }"/>
        
        <span style="font-size: 1.2em; font-weight: bold;">${id }</span><br>
        <span style="font-size: 1em;">${replyCyclistVO.content } </span>
        <c:if test="${sessionScope.manager_id != null || sessionScope.cyclistno == replyCyclistVO.cyclistno }">
          <a href="javascript:delete_reply(${detailno}, ${replyno })"><img src="/reply/images/delete.png" class="icon"></a>
        </c:if>
        <br>
        <span style="font-size: 1em;">${replyCyclistVO.rdate.substring(0, 16) }</span><br>
        <hr>
      </c:forEach>  
    </tbody>
  </table>
  <form name='frm' method='post' action='/reply/create.do'>
    <input type="hidden" name="detailno" value="${param.detailno }">
    <div>
      <label>댓글 작성</label>
      <textarea name='content' required="required" class="form-control form-control"
                    rows="4" placeholder="댓글을 입력해 주세요."  style='width: 97%;'></textarea>
    </div>
    <div class="content_body_bottom">
      <button type="submit" class="btn btn-secondary btn-sm">등록</button>
    </div>
  </form>
  </fieldset>
  

<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>