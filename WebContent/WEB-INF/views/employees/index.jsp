<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- c:import : ファイルを取り込む -->
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <!-- フラッシュメッセージがあれば表示 -->
        <c:if test="${message != null}">
            <div id="flush_success">
                <c:out value="${message}"></c:out>
            </div>
        </c:if>
        <h2>従業員　一覧</h2>
<!-- 従業員リストの表示 -->
        <table id="employee_list">
            <tbody>
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                    <th>操作</th>
                    <th>権限</th>
                </tr>
                <!--c:forEach : 繰り返し(for文の様なもの)　var：itemから取り出した要素を格納する変数名、item：配列やリスト、varStatus：現在のループの状態を表すステータス変数。 -->
                <c:forEach var="employee" items="${employee}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <!-- c:out : 表示。またこれを利用すると、サニタイジングが実行され、HTMLタグの < や > が無害化 -->
                        <td><c:out value="${employee.code}" /></td>
                        <td><c:out value="${employee.name}" /></td>
                        <td>
                        <!-- elseがある場合の条件分岐(if)。c:chooseで括り、条件を満たすものはc:when、elseの場合はc:otherwise を使う -->
                            <c:choose>
                                <c:when test="${employee.delete_flag == 1}">
                                    （削除済み）
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/employees/show?id=${employee.id}' />">詳細を表示</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${employee.admin_flag == 1}">
                                    管理者
                                </c:when>
                                <c:otherwise>
                                    一般
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
<!-- ページ数の表示 -->
        <div id="pagination">
            （全 ${employee_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((employee_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/employees/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/employee/new' />">新規従業員の登録</a></p>
    </c:param>
</c:import>