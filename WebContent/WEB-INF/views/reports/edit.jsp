<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url ="../layout/app.jsp">
    <c:param name="content">
        <h2>日報　編集ページ</h2>
            <c:choose>
               <c:when test="${report != null }">
                   <form method="POST" action ="<c:url value = '/report/editcheck' /> ">
                        <c:import url = "_form.jsp" />
                    </form>
                    <a href="<c:url value='/report/show?id=${ reportId }' />">>日報詳細に戻る</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="<c:url value='/report/delete?id=${ reportId }' />">>日報を削除する</a>
               </c:when>
               <c:otherwise>お探しのデータは見つかりませんでした。</c:otherwise>
        </c:choose>
        <p><a href="<c:url value='/report/index' />">>日報一覧に戻る</a></p>
    </c:param>
</c:import>