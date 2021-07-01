<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url ="../layout/app.jsp">
    <c:param name="content">
       <h2>従業員　新規登録</h2>

       <form method="POST" action ="<c:url value = '/employee/check' /> ">
           <c:import url = "_form.jsp" />
       </form>

       <p><a href="<c:url value='/employee/index' />">>従業員一覧に戻る</a></p>
    </c:param>
</c:import>