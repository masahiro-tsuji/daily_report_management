<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <title>コメント詳細</title>
        <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
        <link rel="stylesheet" href="<c:url value='/css/style.css' />">
    </head>
    <body>
        <table border="1" id="comment_list">
            <tr class="row1">
                <td class="comment_count"><c:out value="${ comment.comment_count }" /></td><%-- コメント番号 --%>
                <td class="comment_name"><c:out value="${ comment.employee.name }" /></td><%-- 投稿者名 --%>
                <td><div class="comment_create_at"><fmt:formatDate value="${comment.created_at}" pattern="yyyy年MM月dd日 HH:mm:ss" /></div>
            </tr>
                <tr><td colspan="3">
                    <c:if test="${ comment.comment_id != 0}"><a href="<c:url value='/report/commentedit?cid=${comment.comment_id}' />"  rel="noopener noreferrer" onclick="window.open(this.href, 'comment_edit', 'width=600, height=300, menubar=no, toolbar=no, scrollbars=yes '); return false;"><c:out value=">>${ comment.comment_id }" /></a></c:if>
                    <pre><c:out value="${ comment.comment }" /></pre></td>
                </tr>
        </table>
        <br/>
        <input type="button" value="閉じる" id="closebtn">

        <%-- inputの閉じるボタンが押下されたら、windoｗを閉じる。 --%>
        <script>
        let closebutton = document.getElementById('closebtn');
            closebutton.addEventListener('click', () => {
                close();
            });
        </script>
    </body>
</html>