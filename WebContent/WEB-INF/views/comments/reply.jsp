<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
    <meta charset="UTF-8">
    <title>コメント番号<c:out value="${comment.comment_count}" />への返信</title>
    <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
    <link rel="stylesheet" href="<c:url value='/css/style.css' />">
</head>

<body>
    <div id = coment_rep>
        <table border="1" id="comment_list">
         <tr class="row1">
                <td class="comment_count"><c:out value="${ comment.comment_count }" /></td><%-- コメント番号 --%>
                <td class="comment_name"><c:out value="${ comment.employee.name }" /></td><%-- 投稿者名 --%>
                <td><div class="comment_create_at"><fmt:formatDate value="${comment.created_at}" pattern="yyyy年MM月dd日 HH:mm:ss" /></div></td>
        </tr>
         <tr><td colspan="3"> <pre><c:out value="${ comment.comment }" /></pre></td></tr>
        </table>
        <br/><br/>
        <form method="POST" action= "<c:url value='/reply' />">
            <label for="comment">●返信入力欄</label><br/>
            <textarea id="comment_area" rows = "10" cols = "50" name = "repComment"  placeholder="ここにコメントを入力して下さい。"></textarea>
            <input type="hidden" name="reportId" value="${ reportId }" />
            <input type="hidden" name="commentId" value="${ comment.id }"/>
            <br/></br>
            <button type="submit"  onclick="return check()">投稿</button>
        </form>
        <br/>
        <a href="<c:url value="/report/show?id=${reportId }" />">>日報の詳細に戻る</a>

    <%-- 空白や改行しか入力されていない場合、それらを削除してfocusさせる。 --%>
        <script>
            function check(){
                var comment = document.getElementById('comment_area')
                if(!comment.value || !comment.value.match(/\S/g)){
                    alert("コメントが未入力です。");
                    comment.value = '';
                    document.getElementById('comment_area').focus();
                    return false;
                }
            }
        </script>
    </div>
</body>