<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Cycling Club</title>
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

</head>
 
<body>
<c:import url="/menu/top.do" />
 
<div class='title_line'>${regionVO.name } > 글 등록</div>

  <aside class="aside_right">
    <a href="./create.do?regionno=${regionVO.regionno }">등록</a>
    <span class='menu_divide' >│</span>
    <a href="javascript:location.reload();">새로고침</a>
    <span class='menu_divide' >│</span>
    <a href="./list_by_regionno.do?regionno=${param.regionno }&now_page=${param.now_page == null ? 1 : param.now_page }&word=${param.word }">목록형</A>    
    <span class='menu_divide' >│</span>
    <a href="./list_by_regionno_grid.do?regionno=${regionVO.regionno }">갤러리형</a>
  </aside> 
  
  <div style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_by_regionno_search_paging.do'>
      <input type='hidden' name='regionno' value='${regionVO.regionno }'>  <%-- 게시판의 구분 --%>
      
      <c:choose>
        <c:when test="${param.word != '' }"> <%-- 검색하는 경우 --%>
          <input type='text' name='word' id='word' value='${param.word }' class='input_word'>
        </c:when>
        <c:otherwise> <%-- 검색하지 않는 경우 --%>
          <input type='text' name='word' id='word' value='' class='input_word'>
        </c:otherwise>
      </c:choose>
      <button type='submit' class='btn btn-secondary btn-sm'>검색</button>
      <c:if test="${param.word.length() > 0 }">
        <button type='button' class='btn btn-secondary btn-sm' 
                    onclick="location.href='./list_by_regionno_search.do?regionno=${regionVO.regionno}&word='">검색 취소</button>  
      </c:if>    
    </form>
  </div>
  
  <div class='menu_line'></div>
  
  <form name='frm' method='post' action='./create.do' enctype="multipart/form-data">
    <input type="hidden" name="regionno" value="${param.regionno }">
    
    <div>
       <label>제목</label>
       <input type='text' name='title' value='코스 지정' required="required" 
                 autofocus="autofocus" class="form-control" style='width: 100%;'>
    </div>
    <div>
       <label>내용</label>
       <textarea name='content' required="required" class="form-control" rows="12" style='width: 100%;'>
코스: 
거리: 
소요 시간: 
주소: 
       </textarea>
    </div>
    <div>
       <label>검색어</label>
       <input type='text' name='word' value='자전거, 라이딩, 초심자 ' required="required" 
                 class="form-control" style='width: 100%;'>
    </div>   
    <div>
       <label>이미지</label>
       <input type='file' class="form-control" name='file1MF' id='file1MF' 
                 value='' placeholder="파일 선택">
    </div>   
    <div>
       <label>패스워드</label>
       <input type='password' name='passwd' value='1234' required="required" 
                 class="form-control" style='width: 50%;'>
    </div>   
    <div class="content_body_bottom">
      <button type="submit" class="btn btn-secondary btn btn-sm">등록</button>
      <button type="button" onclick="location.href='./list_by_regionno_search_paging.do?regionno=${param.regionno}'" class="btn btn-secondary btn btn-sm">목록</button>
    </div>
  
  </form>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>