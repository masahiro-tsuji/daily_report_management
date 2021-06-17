<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>更新　確認画面</h2>

        <div id= "check_message">
            <c:out value="${checkmessage}"></c:out>
        </div>

        <form method="post">
            <c:import url="_check.jsp"/>
            <button type="button" onclick="multiplication('/daily_report_management/report/update')">更新</button>&nbsp;&nbsp;&nbsp;&nbsp;
            <button type="button" onclick="multiplication('/daily_report_management/report/edit')">戻る</button><br/><br/>
        </form>
        <script>
            function multiplication(url){
                var form = document.querySelector("form");
                var action = form.setAttribute("action", url);
                document.querySelector("form").submit();
            }
        </script>
        <p><a href="<c:url value='/report/show?id=${ reportId }' />">>日報詳細に戻る</a></p>
        <p><a href="<c:url value='/report/index' />">>日報一覧に戻る</a></p>
    </c:param>
</c:import>