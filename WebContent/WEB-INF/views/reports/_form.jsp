<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- 入力エラーがあった時のメッセージ --%>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>
    </div>
</c:if>
<%---------------------------------------%>

<%---- 入力フォーム ----------------------------------------------------------------------------------------------------%>
<form method="POST" action="<c:url value='/report/newcheck' />">
    <label for="report_date">日付</label><br/>
    <input type="date" name="report_date" value="<fmt:formatDate value='${report.report_date}' pattern='yyyy-MM-dd' />" />
    <br/><br/>

    <label for="name">氏名</label><br/>
    <c:out value="${sessionScope.login_employee.name}" />
    <br/><br/>

    <label for="title">タイトル</label><br/>
    <input type="text" id="title" name="title" value="${report.title}"  />
    <br/><br/>

    <label for="content">内容</label><br/>
    <textarea id="content" name="content" rows="10" cols="50">${report.content}</textarea>
    <br/><br/>

    <button type="submit" >確認</button>
    <br/><br/>
</form>
<%----------------------------------------------------------------------------------------------------------------------%>

<%-- 下記のscriptでタイトルに自動的にフォーカスさせる  --%>
<script type="text/javascript">
    document.getElementById('title').focus();
</script>