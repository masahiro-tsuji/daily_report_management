<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url ="../layout/app.jsp">
    <c:param name="content">
       <h2>従業員　新規登録</h2>

       <form id="employee_form" method="POST" action ="<c:url value = '/employee/check' /> ">
           <c:import url = "_form.jsp" />
           <button type = "submit" onclick="return check()">確認</button>
       </form>
<%-- 空白や改行しか入力されていない場合、それらを削除してfocusさせる。 --%>
<script>
    function check(){
        var comment = document.getElementById('employee_form')
        if( !document.getElementById('code_text').value && !document.getElementById('name_text').value && !document.getElementById('pass_text').value ){
             alert("社員番号と氏名、パスワードを入力して下さい。");
             document.getElementById('code_text').style.backgroundColor = '#ff0000'; <%-- 背景色を変える --%>
             document.getElementById('name_text').style.backgroundColor = '#ff0000';
             document.getElementById('pass_text').style.backgroundColor = '#ff0000';
             document.getElementById('code_text').focus();    <%-- 未入力のcode欄にフォーカス --%>
             return false;
        }else if(!document.getElementById('code_text').value){
            alert("社員番号が未入力です");
            document.getElementById('code_text').style.backgroundColor = '#ff0000';
            document.getElementById('code_text').focus();
            return false;
        }else if(!document.getElementById('name_text').value){
            alert("氏名が未入力です");
            document.getElementById('name_text').style.backgroundColor = '#ff0000';
            document.getElementById('name_text').focus();
            return false;

        }else if(!document.getElementById('pass_text').value){
            alert("パスワードが未入力です");
            document.getElementById('pass_text').style.backgroundColor = '#ff0000';
            document.getElementById('pass_text').focus();
            return false;
        }else{
            return true;
        }
    }
</script>
<script>
    function alertValue($this) {
        $this.style.backgroundColor = ''
    }
</script>
       <p><a href="<c:url value='/employee/index' />">>従業員一覧に戻る</a></p>
    </c:param>
</c:import>