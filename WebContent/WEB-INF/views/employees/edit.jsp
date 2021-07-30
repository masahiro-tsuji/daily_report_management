<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url ="../layout/app.jsp">
    <c:param name="content">
        <h2>従業員　編集ページ</h2>
        <h3>※パスワードは変更する場合のみ入力して下さい。</h3>
        <c:choose>
<%-- 従業員情報編集のフォーム --%>
            <c:when test="${ employee != null }">
                <form method="POST" action ="<c:url value = '/employee/editcheck' /> ">
                    <c:import url = "_form.jsp" />
                    <button type = "submit">確認</button>
                </form>
                <br/>
                <a href="<c:url value='/employee/show?id=${ employee.id }' />">>詳細画面に戻る</a>&nbsp;&nbsp;&nbsp;&nbsp;

                <a href="#" onclick="confirmDestroy();">>この従業員データを削除する</a>
                <form method="POST" action="<c:url value='/employee/destroy'/>">
                    <input type="hidden" name="token" value="${ _token }"/>
                    <input type="hidden" name="employeeId" value="${ employee.id }"/>
                </form>
<%-- 削除のJavaScript --%>
                <script>
                    function confirmDestroy(){
                        if(confirm("本当に削除してよろしいですか？")){
                            document.forms[1].submit();
                        }
                    }
                </script>
            </c:when>
<%-- 対象の従業員がいなかったら --%>
            <c:otherwise>
                お探しのデータは見つかりませんでした。
            </c:otherwise>
        </c:choose>
        <p><a href="<c:url value='/employee/index' />">>一覧に戻る</a></p>
    </c:param>
</c:import>
