<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url ="../layout/app.jsp">
    <c:param name="content">
        <h2>確認画面</h2>

        <form method="POST">
          <c:import url = "check.jsp" />
          <button type="button" onclick="multipleaction('/daily_report_management/employee/create')">登録</button>
          <button type="button" onclick="multipleaction('/daily_report_management/employee/new')">戻る</button>
        </form>
        <script>
          function multipleaction(url){
              var form = document.querySelector("form");
              var action = form.setAttribute("action", url);
              document.querySelector("form").submit();
          }
        </script>
        <p><a href="<c:url value='/employee/index' />">>一覧に戻る</a></p>
    </c:param>
</c:import>