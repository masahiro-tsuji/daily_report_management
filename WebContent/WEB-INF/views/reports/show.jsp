<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${report.id != null }">
                <h2>${report.title}　の詳細ページ</h2>
                <h3>●内容</h3>
                <table id = "report_detail" >
                    <tr>
                        <th>氏　名</th>
                        <td><c:out value="${report.employee.name }" /></td>
                    </tr>
                    <tr>
                        <th>日　付</th>
                        <td><fmt:formatDate value="${report.report_date }" pattern="yyyy年MM月dd日" /></td>
                    </tr><tr>
                        <th>内　容</th>
                        <td><pre><c:out value="${ report.content }" /></pre></td>
                    </tr>
                    <tr>
                        <th>投稿日時</th>
                        <td><fmt:formatDate value="${report.created_at}" pattern="yyyy年MM月dd日 HH:mm:ss" /></td>
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
                <!-- 投稿者のみが編集できるようにする。 -->
                <c:if test="${ sessionScope.login_employee.id == report.employee.id }">
                    <p><a href="<c:url value="/report/edit?id=${ report.id }" />">>日報を編集する</a></p>
                </c:if>
                <p><a href="<c:url value='/report/index' />">>日報一覧に戻る</a></p>
            </c:when>
            <c:otherwise>お探しの日報は見つかりませんでした。</c:otherwise>
        </c:choose>
    </c:param>
</c:import>