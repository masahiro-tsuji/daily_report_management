<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
<%-- ログアウトしたときのメッセージを伝える --%>
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
<%-- 社員番号やパスワードが一致しなかったら --%>
        <c:if test="${errors != null}">
            <div id="flush_error">
                <c:out value="${errors}"></c:out>
            </div>
        </c:if>

        <h2>ログイン画面</h2>
<%-- 入力フォーム -------------------------------------------------------%>
        <form method="POST"  class="login_form" action="<c:url value='/login' />">
            <label for="code">社員番号</label> <br/>
            <input type="text" id="code"  name="code" value="${ code }" onChange="alertValue(this)" />
            <br/><br/>
            <label for="pass">パスワード</label> <br/>
            <input type="password" id="pass"  name="pass" onChange="alertValue(this)"  />
            <br/><br/>
            <input type="hidden" name="token" value="${ _token }" />
            <input type="submit"  value="ログイン"   onclick="return check()" />
        </form>

<!-- JavaScript  -->

<%-- 下記のscriptで社員番号に自動的にフォーカスさせる  --%>
        <script type="text/javascript">
            document.getElementById('code').focus();
        </script>

<%-- 入力値チェック -------------------------------------%>
        <script type="text/javascript">
        function check(){
            if( !document.getElementById('code').value && !document.getElementById('pass').value ){
                 alert("社員番号とパスワードを入力して下さい。");
                 document.getElementById('code').style.backgroundColor = '#ff0000'; <%-- 背景色を変える --%>
                 document.getElementById('pass').style.backgroundColor = '#ff0000';
                 document.getElementById('code').focus();    <%-- 未入力のcode欄にフォーカス --%>
                 return false;
            }else if(!document.getElementById('code').value){
                alert("社員番号が未入力です");
                document.getElementById('code').style.backgroundColor = '#ff0000';
                document.getElementById('code').focus();
                return false;
            }else if(!document.getElementById('pass').value){
                alert("パスワードが未入力です");
                document.getElementById('pass').style.backgroundColor = '#ff0000';
                document.getElementById('pass').focus();
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
    </c:param>
</c:import>
