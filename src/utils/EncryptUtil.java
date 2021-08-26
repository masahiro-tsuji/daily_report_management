package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class EncryptUtil {
    public static String getPasswordEncrypt(String plain_p, String pepper) {
        String ret = "";

        if(plain_p != null && !plain_p.equals("")) {
            byte[] bytes;
            String password = plain_p + pepper;
            try {
                bytes = MessageDigest.getInstance("SHA-256").digest(password.getBytes());
                ret = DatatypeConverter.printHexBinary(bytes);
            } catch(NoSuchAlgorithmException ex) {} // ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合にスロー
        }

        return ret;
    }
}
/*
MessageDigestクラス : SHA-1やSHA-256などのメッセージ・ダイジェスト・アルゴリズムの機能を提供。メッセージ・ダイジェストは、任意サイズのデータを取得して固定長のハッシュ値を出力する安全な一方向のハッシュ機能
getInstance : 指定されたダイジェスト・アルゴリズムを実装するMessageDigestオブジェクトを返す。
digest : 最終処理を行ってハッシュ計算を完了します。この呼出しのあと、ダイジェストはリセット(戻り値：結果として得られるハッシュ値に対するバイト・データの配列)
printHexBinary : バイト配列を文字列に変換
*/
// 引数で受け取った文字列にペッパー文字列を連結させたものを SHA256 でハッシュ化
// 引数の文字列が何もなければ、空の文字列を返す。
