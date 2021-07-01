<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${hasError}">
            <div id="flush_error">
                社員番号かパスワードが間違っています。
            </div>
        </c:if>

        <c:if test="${ codePassNull }">
            <div id="fkush_error">
                社員番号とパスワード、又は、片方が未入力です。
            </div>
        </c:if>

        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>

        <h2>ログイン画面</h2>

        <form method="POST" action="<c:url value='/login' />">
            <label for="code">社員番号</label> <br/>
            <input type="text" name="code" value="${ code }" />
            <br/><br/>

            <label for="pass">パスワード</label> <br/>
            <input type="password" name="pass" />
            <br/><br/>

            <input type="hidden" name="token" value="${ _token }" />

            <button type="submit">ログイン</button>
        </form>
    </c:param>
</c:import>