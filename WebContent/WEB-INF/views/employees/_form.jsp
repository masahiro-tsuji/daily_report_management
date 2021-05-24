<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- エラーメッセージがあるなら表示 -->
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <!-- for文で回す。 -->
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>
    </div>
</c:if>

<!-- 入力内容 -->
<label for = "code">社員番号</label> <br/>
<input type = "text" name ="code" value = "${ employee.code }" />
<br/><br/>

<label for = "name">氏名</label> <br/>
<input type = "text" name ="name" value = "${ employee.name }" />
<br/><br/>

<label for = "pass">パスワード</label> <br/>
<input type = "text" name = "pass" value = "${ employee.pass }" />
<br/><br/>

<label for = "flag">権限</label> <br/>
<select name = "flag">
    <option value = "0" <c:if test = "${ employee.admin_flag == 0 }"> selected </c:if>>一般</option>
    <option value = "1" <c:if test = "${ employee.admin_flag == 1 }}"> selected </c:if>>管理者</option>
</select>
<br/><br/>
<!-- トークンを送る -->
<input type = "hidden" name = "_token" value = "${_token }"/>
<button type = "submit">登録</button>