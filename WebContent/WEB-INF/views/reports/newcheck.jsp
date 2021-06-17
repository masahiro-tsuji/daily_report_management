<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url ="../layout/app.jsp">
    <c:param name="content">
        <h2>確認画面</h2>

        <div id= "check_message">
            <c:out value="${checkmessage}"></c:out>
        </div>

        <form method="POST">
          <c:import url = "_check.jsp" />
          <input type="hidden" name="token" value="_token" />
          <button type="button" onclick="multipleaction('/daily_report_management/report/create')">投稿</button>&nbsp;&nbsp;&nbsp;&nbsp;
          <button type="button" onclick="multipleaction('/daily_report_management/report/new')">戻る</button>
        </form>
        <script>
          function multipleaction(url){
              var form = document.querySelector("form");
              var action = form.setAttribute("action", url);
              document.querySelector("form").submit();
          }
        </script>
        <p><a href="<c:url value='/report/index' />">>一覧に戻る</a></p>
    </c:param>
</c:import>

