<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>Cycling Club</title>e>
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

</head>
<body>
<c:import url = "/menu/top.do"/>

<DIV class='title_line'><A href="/detail/list_by_regionno.do?regionno=${regionVO.regionno }" class="title_link">${regionVO.name }</A> > ${detailVO.title } > 지도 등록/수정/삭제</DIV>
 

  <ASIDE class="aside_right">
    <A href="javascript:location.reload();">새로고침</A>
    <span class='menu_divide' >│</span>    
    <A href="./list_by_regionno.do?regionno=${param.regionno }&now_page=${param.now_page == null ? 1 : param.now_page}&word=${param.word }">기본 목록형</A>    
    <span class='menu_divide' >│</span>
    <A href="./list_by_regionno_grid.do?regionno=${param.regionno }&now_page=${param.now_page == null ? 1 : param.now_page}&word=${param.word }">갤러리형</A>

  </ASIDE>
  
  <DIV style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_by_regionno.do'>
      <input type='hidden' name='regionno' value='${regionVO.regionno }'>  <%-- 게시판의 구분 --%>
      
      <c:choose>
        <c:when test="${param.word != '' }"> <%-- 검색하는 경우 --%>
          <input type='text' name='word' id='word' value='${param.word }' class='input_word'>
        </c:when>
        <c:otherwise> <%-- 검색하지 않는 경우 --%>
          <input type='text' name='word' id='word' value='' class='input_word'>
        </c:otherwise>
      </c:choose>
      <button type='submit' class='btn btn-secondary btn-sm' style="padding: 2px 8px 3px 8px; margin: 0px 0px 2px 0px;">검색</button>
      <c:if test="${param.word.length() > 0 }">
        <button type='button' class='btn btn-secondary btn-sm' 
                    onclick="location.href='./list_by_regionno.do?regionno=${regionVO.regionno}&word='" 
                    style="padding: 2px 8px 3px 8px; margin: 0px 0px 2px 0px;">검색 취소</button>  
      </c:if>  
    </form>
  </DIV>   
  
  <DIV class='menu_line'></DIV>
  <%--등록 폼 --%>
  <FORM name='frm_youtube' method='POST' action='./youtube.do'>
    <input type="hidden" name="detailno" value="${param.detailno }">
    
    <div>
       <label>Youtube 스크립트</label>
       <textarea name='youtube' class="form-control" rows="12" style='width: 100%;'>${detailVO.youtube }</textarea>
    </div>
    <div class="content_body_bottom">
      <button type="submit" class="btn btn-secondary btn-sm">저장</button>
      <button type="button" onclick="frm_youtube.youtube.value=''; frm_youtube.submit();" class="btn btn-secondary btn-sm">Youtube 삭제</button>
      <button type="button" onclick="history.back();" class="btn btn-secondary btn-sm">취소</button>
    </div>
  
  </FORM>

  <HR>
  <DIV style="text-align: center;">
      <H5>[참고] Youtube의 등록 방법</H5>
      <IMG src='/detail/images/youtube01.jpg' style='width: 60%;'><br><br>
      <IMG src='/detail/images/youtube02.jpg' style='width: 60%;'><br>
  </DIV>

 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>