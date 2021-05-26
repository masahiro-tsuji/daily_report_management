<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

 <!-- 下記の内容～～～を表示 -->
<div id="check_ok">
    <c:out value="${checkmessage}"></c:out>
</div>

<!-- 入力内容 -->
<label for = "code">社員番号</label> <br/>
<input id = "check" type="text" name="code" value="${ employee.code }" readonly>
<br/><br/>

<label for = "name">氏名</label> <br/>
<input id = "check" type="text" name="name" value="${ employee.name }" readonly>
<br/><br/>

<label for = "pass">パスワード</label> <br/>
<input id = "check" type="text" name="pass" value="${ employee.pass }" readonly>
<br/><br/>

<label for = "flag">権限</label> <br/>
<!-- 一般なら  -->
<c:if test = "${ employee.admin_flag == 0 }">
    <input id = "check" type="text" value="一般" readonly>
    <input type="hidden" name="flag" value="${ employee.admin_flag }" readonly>
</c:if>
<!-- 管理者なら  -->
<c:if test = "${ employee.admin_flag == 1 }">
    <input id = "check" type="text" value="管理者" readonly>
    <input type="hidden" name="flag" value="${ employee.admin_flag }" readonly>
</c:if>
<br/><br/>
<!-- トークンを送る -->
<input type = "hidden" name = "token" value = "${_token }"/>
