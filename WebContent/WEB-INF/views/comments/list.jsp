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
            <td class="comment_count"><c:out value="${ comment.comment_count }" /></td><%-- コメント番号 --%>
            <td class="comment_name"><c:out value="${ comment.employee.name }" /></td><%-- 投稿者名 --%>
            <td><div class="comment_create_at"><fmt:formatDate value="${comment.created_at}" pattern="yyyy年MM月dd日 HH:mm:ss" /></div><%-- 投稿日時 --%>
                <c:if test="${ comment.delete_flag != 1 }"><%-- 削除されていなければ、返信、削除機能を出す。 --%>
                    <div class="comment_option" >
<%-- 返信機能の処理 --%>
                        <form name="reply_form" method="get" action="<c:url value='/reply'/>">
                            <input type="hidden" name="cid" value="${ comment.id }"/>
                            <input type="hidden" name="rid" value="${ report.id }"/>
                            <input type="submit" value="返信" />
                        </form>
<%-- 削除機能の処理 --%>
                        <c:if test="${ comment.employee.id == sessionScope.login_employee.id }">
                                <%-- <a href="<c:url value='/comment/destroy?cid=${comment.id}&rid=${report.id }' />" onclick="commentDestroy();" >>削除</a> --%>
                                <form name="destroy_form"  action="<c:url value='/comment/destroy'/>"onclick="return commentDestroy();" >
                                    <input type="hidden" name="cid" value="${ comment.id }"/>
                                    <input type="hidden" name="rid" value="${ report.id }"/>
                                    <input type="hidden" name="page" value="${ page }"/>
                                    <input type="submit" value="削除"  />
                                    <%-- <a href="#" onclick="commentDestroy()">>削除</a> <c:redirect url="リダイレクトURL" ・・・・ />--%>
                                </form>
                                <script>
                                    function commentDestroy(){
                                        if(confirm("コメントを削除してよろしいですか？")){
                                            document.forms[delete_form].submit();
                                        }else{
                                            return false;
                                        }
                                    }
                                </script>
                        </c:if>
                    </div>
                </c:if>
            </td>
        </tr>
<!-- コメント内容の表示  -->
        <c:choose>
                <c:when test="${ comment.delete_flag == 0 }">
                    <%-- colspan : セルが３つあるので、colspan="3"　とする。２つの場合は２  --%>
                    <tr><td colspan="3">

                         失敗(完成まで残しておく)
                        <c:if test="${ comment.comment_id != 0}"><a href="comment/edit?cid=${ comment.comment_id }"  rel="noopener noreferrer" onclick="window.open(this.href, 'comment_edit', 'width=400, height=300, menubar=no, toolbar=no, scrollbars=yes '); return false;"><c:out value=">>${ comment.comment_id }" /></a></c:if>

                        <pre><c:out value="${ comment.comment }" /></pre></td>
                    </tr>
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