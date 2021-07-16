<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <head>
        <meta charset="UTF-8">
        <title>コメント番号<c:out value="${comment.comment_count}" />への返信</title>
    </head>

    <form method="POST" action= "<c:url value='/reply' />">
        <label for="comment">●コメント返信</label><br/>
        <textarea id="comment_area" rows = "10" cols = "50" name = "repComment" ></textarea>
        <input type="hidden" name="reportId" value="${ reportId }" />
        <input type="hidden" name="commentId" value="${ comment.id }"/>
        <br/></br>
        <button type="submit" >投稿</button>
    </form><br/>
