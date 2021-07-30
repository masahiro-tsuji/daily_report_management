<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- エラーメッセージがあるなら表示 -->
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <!-- for文で回す。 -->
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
            <c:choose>
                <c:when test="${ error == '社員番号を入力してください。' or error == '入力された社員番号はすでに存在しています。'}">
                    <style>#code_text{background-color:#ee2121;}</style>
                </c:when>
                <c:when test="${ error == '氏名を入力して下さい。'}">
                    <style>#name_text{background-color:#ee2121;}</style>
                </c:when>
                <c:when test="${ error == 'パスワードを入力して下さい。'}">
                    <style>#pass_text{background-color:#ee2121;}</style>
                </c:when>
            </c:choose>
        </c:forEach>
    </div>
</c:if>

<!-- 入力内容 -->
<label for = "code">【社員番号】</label> <br/>
<input id="code_text" type = "text" name ="code" value = "${ employee.code }" onChange="alertValue(this)"/>
<br/><br/>

<label for = "name">【氏名】</label> <br/>
<input id="name_text" type = "text" name ="name" value = "${ employee.name }" onChange="alertValue(this)"/>
<br/><br/>

<label for = "pass">【パスワード】</label> <br/>
<input id="pass_text" type = "text" name = "pass" onChange="alertValue(this)"/>
<br/><br/>

<label for = "flag">【権限】</label> <br/>
<select name = "flag">
    <option value = '0' <c:if test = "${ employee.admin_flag == 0 }"> selected </c:if>>一般</option>
    <option value = '1' <c:if test = "${ employee.admin_flag == 1 }"> selected </c:if>>管理者</option>
</select>
<br/><br/>
<!-- トークンを送る -->
<input type = "hidden" name = "token" value = "${_token }"/>




<%--
入力欄からfocusが外れた場合、背景色を戻す
<script>
    function alertValue($this) {
        $this.style.backgroundColor = ''
    }
</script>
--%>