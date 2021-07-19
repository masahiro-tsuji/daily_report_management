<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%---- 入力フォーム ----------------------------------------------------------------------------------------------------%>
<form method="POST" action="<c:url value='/report/newcheck' />">
    <label for="report_date">日付</label><br/>
    <input type="date" name="report_date" value="<fmt:formatDate value='${report.report_date}' pattern='yyyy-MM-dd' />" />
    <br/><br/>

    <label for="name">氏名</label><br/>
    <c:out value="${sessionScope.login_employee.name}" />
    <br/><br/>

    <label for="title">タイトル</label><br/>
    <input type="text" id="title" name="title"  value="${report.title}" onChange="alertValue(this)" />
    <br/><br/>

    <label for="content">内容</label><br/>
    <textarea id="report" name="content" rows="10" cols="50" onChange="alertValue(this)">${report.content}</textarea>
    <br/><br/>

    <button type="submit"  onclick="return check();">確認</button>
    <br/><br/>
</form>
<%----------------------------------------------------------------------------------------------------------------------%>

<%-- 下記のscriptでタイトルに自動的にフォーカスさせる  --%>
<script type="text/javascript">
    document.getElementById('title').focus();
</script>

<%-- 入力値チェック -------------------------------------%>
<script type="text/javascript">
    function check(){
        if( !document.getElementById('title').value && !document.getElementById('report').value ){
            alert("タイトルと内容を入力して下さい。");
            document.getElementById('title').style.backgroundColor = '#FDF895'; <%-- 背景色を変える --%>
            document.getElementById('report').style.backgroundColor = '#FDF895';
            document.getElementById('title').focus();    <%-- 未入力のcode欄にフォーカス --%>
            return false;
        }else if(!document.getElementById('title').value){
            alert("タイトルが未入力です");
            document.getElementById('title').style.backgroundColor = '#FDF895';
            document.getElementById('title').focus();
            return false;
        }else if(!document.getElementById('report').value){
            alert("内容が未入力です");
            document.getElementById('report').style.backgroundColor = '#FDF895';
            document.getElementById('report').focus();
            return false;
        }else{
            return true;
        }
    }
</script>

<%-- 入力欄からfocusが外れた場合、背景色を戻す --%><%-- https://hacknote.jp/archives/6109/ --%>
<script>
    function alertValue($this) {
        $this.style.backgroundColor = ''
    }
</script>


<%--
//入力エラーがあった時のメッセージ
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>
    </div>
</c:if>
--%>