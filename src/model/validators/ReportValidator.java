package model.validators;

import java.util.ArrayList;
import java.util.List;

import models.Report;

public class ReportValidator {
    // 日報タイトル、内容の入力値のチェック
    public static List<String> validate(Report r){
        List<String> errors = new ArrayList<String>(); // エラーがあった際の文言を格納するList生成

        String title_error = _validateTitle(r.getTitle());
        if(!title_error.equals("")) {
            errors.add(title_error);
        }

        String content_error = _validateContent(r.getContent());
        if(!content_error.equals("")) {
            errors.add(content_error);
        }
        return errors;
    }

    // 日報タイトルが未入力かチェック
    private static String _validateTitle(String title) {
        if(title == null || title.equals("")) {
            return "タイトルを入力してください。";
        }
        return "";
    }

    // 日報内容が未入力かチェック
    private static String _validateContent(String content) {
        if(content == null || content.equals("")) {
            return "内容を入力してください。";
        }
        return "";
    }
}

/* * メモ
 * equals(System.getProperty("line.separator"))は、OSに依存した改行コードを検出
 *
 * *JavaScriptにて実装
 *
 *     // コメント入力値のチェック
    public static List<String> commetvalidate(Comment comment){
        List<String> errors = new ArrayList<String>();

        String comment_error = _validateComment(comment.getComment());
            if(!comment_error.equals("")){
                errors.add(comment_error);
            }
        return errors;
    }
 *
 *  // コメントが未入力かチェック
    private static String _validateComment(String comment){
        if(comment == null || comment.equals("") || comment.equals(System.getProperty("line.separator")) ){
            return "コメントを入力して下さい。";
        }
        return "";
    }
 *
 * */