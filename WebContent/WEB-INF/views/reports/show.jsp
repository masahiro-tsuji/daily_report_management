<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${report.id != null}">
                <c:if test="${flush != null }">
                    <c:out value="${flush }"/>
                </c:if>

                <h2>${report.title}　の詳細ページ</h2>
                <h3>●内容</h3>
                <%-- 日報詳細表示 --%>
                <table id = "report_detail" >
                    <tr>
                        <th>氏　名</th><td><c:out value="${report.employee.name }" /></td>
                    </tr>
                    <tr>
                        <th>日　付</th><td><fmt:formatDate value="${report.report_date }" pattern="yyyy年MM月dd日" /></td>
                    </tr><tr>
                        <th>内　容</th><td><pre><c:out value="${ report.content }" /></pre></td>
                    </tr>
                    <tr>
                        <th>投稿日時</th><td><fmt:formatDate value="${report.created_at}" pattern="yyyy年MM月dd日 HH:mm:ss" /></td>
                    </tr>
                    <tr>
                        <th>更新日時</th>
                        <td>
                        <c:choose>
                            <c:when test="${ report.updated_at != null }">
                                <fmt:formatDate value="${report.updated_at }" pattern="yyyy年MM月dd日 HH:mm:ss" />
                            </c:when>
                            <c:otherwise>-まだ更新されていません。-</c:otherwise>
                        </c:choose>
                        </td>
                    </tr>
                </table>
                <br/>

                <%-- コメント表示とページネーション  --%>

                <%-- コメントが無ければその旨を伝えるif文を実装する。 --%>

                <h3>●コメント一覧</h3>
                <table border="1" id="comment_detail">
                    <c:forEach var="comment" items="${comments}" varStatus="status">
                        <tr class="row1">
                            <td><c:out value="${ comment.employee.name }" /></td>
                            <td><fmt:formatDate value="${comment.created_at}" pattern="yyyy年MM月dd日 HH:mm:ss" /></td>
                        </tr>
                        <tr><td colspan="2"><pre><c:out value="${ comment.comment }" /></pre></td></tr>
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
                <br/>

                <%-- コメント投稿フォーム --%>

                <%-- 投稿者と管理者がコメントできるよう実装する --%>

                <form method="POST" action= "<c:url value='/comment/new' />">
                    <label for="comment">●コメント入力フォーム</label><br/>
                    <textarea rows = "10" cols = "50" name = "comment" placeholder="ここにコメントを入力して下さい。"  >${ comment.comment }</textarea>
                    <input type="hidden" name="report_id" value="${ report.id }" />
                    <br/>
                    <button type="submit">投稿</button>
                </form><br/>

                <%-- 投稿者のみが編集できるようにする。 --%>
                <c:if test="${ sessionScope.login_employee.id == report.employee.id }">
                    <p><a href="<c:url value="/report/edit?id=${ report.id }" />">>日報を編集する</a></p>
                </c:if>

            </c:when>
            <c:otherwise>お探しの日報は見つかりませんでした。</c:otherwise>
        </c:choose>
        <p><a href="<c:url value='/report/index' />">>日報一覧に戻る</a></p>
    </c:param>
</c:import>