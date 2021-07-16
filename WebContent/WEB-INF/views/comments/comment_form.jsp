<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- 投稿者と管理者がコメントできるよう実装 --%>
<c:if test="${sessionScope.login_employee.id == report.employee.id || login_employee.admin_flag == 1 }">
    <form method="POST" action= "<c:url value='/comment/new' />">
        <label for="comment">●コメント入力フォーム</label><br/>
        <textarea id="comment_area" rows = "10" cols = "50" name = "comment" placeholder="ここにコメントを入力して下さい。"  ></textarea>
        <input type="hidden" name="report_id" value="${ report.id }" />
        <br/>
        <input type="submit"  value="投稿"   onclick="return check()" onclick="return check();"/>
    </form>
<%-- scriptでコメント入力チェック --%>
    <script>
        function check(){
            var comment = document.getElementById('comment_area')
            if(!comment.value || !comment.value.match(/\S/g)){
                alert("コメントが未入力です。");
                comment.value = ''; <%-- 空白や改行しか入力されていない場合、それらを削除してfocusさせる。 --%>
                document.getElementById('comment_area').focus();
                return false;
            }
        }
    </script>
</c:if>

<%--

* value.match(/\S/g)で空白や改行のみの判定を行ってくれる。

--%>