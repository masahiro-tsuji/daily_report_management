<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

 <%-- コメント未入力だった場合その旨を伝えるif文
                forEachで回さないと"[ ]"が消えなかった。 --%>
<c:forEach var="error" items="${errors}">
    <c:out value="${error}" />
    <br /><br />
</c:forEach>

<%-- コメント表示とページネーション  --%>
<h3>●コメント一覧</h3>
<table border="1" id="comment_list">
    <c:forEach var="comment" items="${comments}" varStatus="status">
        <tr class="row1">
            <td class="comment_count"><c:out value="${ comment.id }" /></td>
            <td class="comment_name"><c:out value="${ comment.employee.name }" /></td>
            <td>
                <div class="comment_create_at"><fmt:formatDate value="${comment.created_at}" pattern="yyyy年MM月dd日 HH:mm:ss" /></div>
                <c:if test="${ comment.delete_flag != 1 }">
                    <div class="comment_option" >
                        <c:if test="${ comment.employee.id == sessionScope.login_employee.id }">

                        <%-- ここから --%>
                            <a href="comment/destroy" onclick="commentDestroy();" >>削除</a>
                            <form method="GET" action="<c:url value='/comment/destroy' />">
                                <input type="hidden" name="commentId" value="${ comment.id }"/>
                                <input type="hidden" name="reportId" value="${ report.id }"/>
                            </form>
                            <script>
                                function commentDestroy(){
                                    if(confirm("本当に削除してよろしいですか？")){
                                     document.forms[1].submit();
                                    }
                                }
                            </script>


                        </c:if>
                    </div>
                </c:if>
            </td>
        </tr>
        <c:choose>
                <c:when test="${ comment.delete_flag == 0 }">
                    <%-- colspan : セルが３つあるので、colspan="3"　とする。２つの場合は２  --%>
                    <tr><td colspan="3"><pre><c:out value="${ comment.comment }" /></pre></td></tr>
                </c:when>
                <c:otherwise><tr><td colspan="3" >（コメントは削除されました。）</td></tr></c:otherwise>
        </c:choose>
    </c:forEach>
</table>

<%-- ページ表示 --%>
<div id="pagination">
（全 ${comment_count} 件）<br/>
    <c:forEach var="i" begin="1" end="${((comment_count - 1) / 5) + 1}" step="1">
        <c:choose>
            <c:when test="${i == page}">
              <c:out value="${i}" />&nbsp;
            </c:when>
            <c:otherwise>
                <%-- servletに値を複数渡したい場合、下記のように"&"を付けて値を渡す。 --%>
                <a href="<c:url value='/report/show?page=${i}&id=${report.id }' />"><c:out value="${i}" /></a>&nbsp;
            </c:otherwise>
        </c:choose>
    </c:forEach>
</div>