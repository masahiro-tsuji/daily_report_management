<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<label for="report_date">日付</label><br/>
<input type="date" name="report_date" value="<fmt:formatDate value='${report.report_date}' pattern='yyyy-MM-dd' />" readonly>
<br/><br/>

<label for="name">氏名</label><br/>
<c:out value="${sessionScope.login_employee.name}" />
<br/><br/>

<label for="title">タイトル</label><br/>
<input type="text" name="title" value="${report.title}" readonly/>
<br/><br/>

<label for="content">内容</label><br/>
<textarea name="content" rows="10" cols="50" readonly>${report.content}</textarea>
<br/><br/>
<input type="hidden" name="token" value="${ _token }" />