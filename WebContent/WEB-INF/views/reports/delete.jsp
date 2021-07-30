<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url ="../layout/app.jsp">
    <c:param name="content">
        <h2>削除確認画面</h2>
        <div id="flush_error">
        <h3>●以下の日報を削除します。よろしいですか？</h3>
        </div>
        <br/><br/>
        <form method="POST">
            <c:import url="_check.jsp"/>
            <button type="button" onclick="multipleaction('/daily_report_management/report/destroy')">削除する</button>
            <button type="button" onclick="multipleaction('/daily_report_management/report/edit')">戻る</button>
        </form>
        <script>
          function multipleaction(url){
              var form = document.querySelector("form");
              var action = form.setAttribute("action", url);
              document.querySelector("form").submit();
          }
        </script>
    </c:param>
</c:import>