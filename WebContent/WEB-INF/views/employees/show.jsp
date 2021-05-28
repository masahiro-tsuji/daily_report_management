<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url ="../layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${employee.id != null }">
                <h2>${employee.name}　さんの従業員詳細ページ</h2>
                <h3>●詳細情報</h3>
                <table id = "empllyee_detail" >
                    <tbody>
                        <tr><th>社員番号</th><td><c:out value="${employee.code}" /></td></tr>
                        <tr><th>氏　名</th><td><c:out value="${employee.name}" /></td></tr>
                        <tr><th>権　限</th><td><c:choose>
                                                   <c:when test="${employee.admin_flag == 0 }">一般</c:when>
                                                   <c:otherwise>管理者</c:otherwise>
                                               </c:choose>
                        </td></tr>
                        <tr><th>登録日時</th><td><fmt:formatDate value="${employee.create_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td></tr>
                        <tr><th>更新日時</th><td><c:choose>
                                                   <c:when test="${employee.update_date == employee.create_date }">-まだ更新されていません-</c:when>
                                                   <c:otherwise><fmt:formatDate value="${employee.update_date}" pattern="yyyy-MM-dd HH:mm:ss" /></c:otherwise>
                                               </c:choose>
                        </td></tr>
                    </tbody>
                </table>
                <p><a href="<c:url value='/employee/edit?id=${employee.id}' />">>この従業員を編集</a></p>
            </c:when>
            <c:otherwise><h2>お探しの従業員データは見つかりませんでした。</h2></c:otherwise>
        </c:choose>
        <p><a href="<c:url value='/employee/index' />">>一覧に戻る</a></p>
    </c:param>
</c:import>