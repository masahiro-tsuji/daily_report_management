package listener;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CreateToken {
    private static int TOKEN_LENGTH = 16;//16*2=32バイト
    /**
     * 32バイトのCSRFトークンを作成
     */
    public static String getCsrfToken() {
      byte token[] = new byte[TOKEN_LENGTH]; // TOKEN_LENGTH分のバイト配列生成
      StringBuffer buf = new StringBuffer(); // スレッドセーフな可変の文字列。文字列バッファには常に文字列が格納されているが、文字列の長さと内容は特定のメソッドの呼出しにより変更
      SecureRandom random = null; // SecureRandom : 暗号用に強化された乱数ジェネレータ(RNG)を提供。*呼出し側は、引数なしのコンストラクタか、任意のgetInstanceメソッドを使って、SecureRandomインスタンスを取得
      try {
        random = SecureRandom.getInstance("SHA1PRNG"); // 指定された乱数ジェネレータ(RNG)アルゴリズムを実装したSecureRandomオブジェクトを返す。
        random.nextBytes(token); // ランダム・バイトを生成し、ユーザー指定のバイト配列に配置
        for (int i = 0; i < token.length; i++) {
          buf.append(String.format("%02x", token[i])); // 指定された文字列をこの文字シーケンスに追加。文字を繋げていく。(formatで16進数に変更)
        }
      } catch (NoSuchAlgorithmException e) { // アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合にスロー
        e.printStackTrace(); // スタックトレースを出力
      }
      return buf.toString(); // 文字列結合 return
    }
}
// スレッドセーフ : ある関数などが複数のスレッドから同時に実行されても矛盾や不具合が生じないようになっていること
// SHA1PRNG : Sunプロバイダが提供する擬似乱数生成(PRNG)アルゴリズム。(JDK の内部で使用可能な暗号化機能に重大な制限が課されました。このため、SUN プロバイダには、データを直接暗号化または復号化しない暗号化エンジンが含まれています。)
