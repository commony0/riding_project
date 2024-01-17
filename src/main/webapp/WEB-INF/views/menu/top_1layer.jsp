<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/JavaScript" src="http://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>

<div class='container_main'>
  <div class='top_img'>
    <div class="top_menu_label">라이딩 코스 추천 version 4.0 2023</div>      
    <nav class="top_menu">
      <a href="/" class="menu_link">홈</a><span class="top_menu_sep"> </span>
      
      <c:forEach var="regionVO" items="${list_top }">
        <c:set var="regionno" value="${regionVO.regionno }"/>
        <c:set var="name" value="${regionVO.name }"/>
        <a href="/detail/list_by_regionno.do?regionno=${regionno }&now_page=1" class="menu_link">${name }</a><span class="top_menu_sep"> </span> 
      </c:forEach>
      
      <a href="/cyclist/create.do" class="menu_link">회원 가입</a><span class="top_menu_sep"> </span>
      <a href="/cyclist/list.do" class="menu_link">회원 목록</a><span class="top_menu_sep"> </span>
      
      <c:choose>
        <c:when test="${sessionScope.id == null}">
          <a href="/cyclist/login.do" class="menu_link">로그인</a><span class="top_menu_sep"> </span>
        </c:when>
        <c:otherwise>
          <a href='/cyclist/logout.do' class="menu_link">${sessionScope.id } 로그아웃</a><span class="top_menu_sep"> </span>
        </c:otherwise>
      </c:choose>
      
      <c:choose>
        <c:when test = "${sessionScope.manager_id == null }"> 
          <a href="/manager/login.do" class= "menu_link">매니저 로그인</a>
        </c:when>
        <c:otherwise>                                                                                   
          <a href="/region/list_all.do" class="menu_link">지역 전체 목록</a><span class ="top_menu_sep"> </span>
          <a href="/detail/list_all.do" class= "menu_link">전체 글 목록</a><span class ="top_menu_sep"> </span>
          <a href="/manager/logout.do" class= "menu_link">매니저 ${sessionScope.manager_id } 로그아웃</a>
        </c:otherwise>
      </c:choose>
    </nav>
  </div>
  <div class='content_body'> <!--  내용 시작 -->  
   