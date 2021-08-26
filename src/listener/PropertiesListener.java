package listener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class PropertiesListener
 *
 */
@WebListener
public class PropertiesListener implements ServletContextListener {

    /**
     * Default constructor.
     */
    public PropertiesListener() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  {
         // TODO Auto-generated method stub
    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  {
         // パスワードに連結させる文字列のことを ペッパー文字列
         // 入力されたパスワード文字列に何らかの文字列を連結させたものを
         // SHA256 でハッシュ化し、そのハッシュ化した文字列をデータベースに登録
        ServletContext context = arg0.getServletContext(); //ServletContext オブジェクトを返すので JRun と対話するメソッドにアクセスできます。(JRun : WebサーバーがJavaサーブレット、JSP、およびEJBを含むJ2EE アプリケーションを処理するために必要なサービスを提供) 

        String path = context.getRealPath("/META-INF/application.properties"); // 指定された仮想パスに対応する実際のパスを取得
        try {
            InputStream is = new FileInputStream(path); // inputStream : バイト入力ストリームを表現するすべてのクラスのスーパー・クラス. ファイル・システム内のファイルから入力バイトを取得。どのファイルが有効であるかはホスト環境に依存
            Properties properties = new Properties(); // プロパティの永続セットを表します。Propertiesを、ストリームへ保管したり、ストリームからロードしたりできる。プロパティ・リストの各キー、に対応する値は文字列
            properties.load(is); // 入力バイトストリームからキーと要素が対になったプロパティーリストを読み込み*ドキュメント読み直しする
            is.close();

            Iterator<String> pit = properties.stringPropertyNames().iterator();
            while(pit.hasNext()) {
                String pname = pit.next();
                context.setAttribute(pname, properties.getProperty(pname));
            }
        } catch(FileNotFoundException e) { // 指定されたパス名で示されるファイルが開けなかったことを通知
        } catch(IOException e) {} // 入出力処理の失敗、または割り込みの発生によって生成される例外の汎用クラス.入力ストリームからの読み込み中にエラーが発生した場合。
    }
}
/*
*ストリーム: Javaではデータの流れとその通り道を意味し、データの受け渡しを抽象化したもの
*イテレーター : 集合の要素に順番にアクセスする時に使用するインターフェースです。集合にはリスト、セット、マップなど複数の種別がありますが、イテレータはそれらにアクセスするための、共通の仕組みを提供
*string PropertyNames : メイン・プロパティ・リストに同じ名前のキーが見つからない場合は、デフォルトのプロパティ・リストにある個別のキーを含む、キーとそれに対応する値が文字列であるようなこのプロパティ・リスト内のキーのセットを返す。キーまたは値がString型でないプロパティは省略
*getProperty : 指定されたキーを持つプロパティーを、プロパティーリストから探します。そのキーがプロパティーリストにない場合は、デフォルトのプロパティーリスト、さらにそのデフォルト値が再帰的に調べられます。そのプロパティーが見つからない場合は、デフォルト値の引数が返されます。
*Javadocなど読み直し必要。
*/
