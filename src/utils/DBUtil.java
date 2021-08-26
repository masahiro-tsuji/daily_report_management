package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


// Daoの役割
public class DBUtil {

    private static final String PERSISTENCE_UNIT_NAME = "daily_report_management";
    private static EntityManagerFactory emf; // *１

    public static EntityManager createEntityManager(){
        return getEntityManagerFactory().createEntityManager();
    }

    private static EntityManagerFactory getEntityManagerFactory() {
    // アプリケーションがアプリケーション管理のEntityManagerを取得するために使用
        if(emf == null){
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return emf;
    }
}
/*
 ・Entity : データベースに保管するオブジェクト。データベースレコードに相当
 ・EntityManagerはデータベースに対してエンティティ(ここでいうID,名前,Pass,Flag..)を登録したり，
 削除したりするためのインタフェースを持つオブジェクト
 *１：EntityManagerがエンティティを受け取り、さまざまなデータベース処理を一括して実行
 *Java Persistence API : 関係データベースのデータを扱う Java SE および Java EE のアプリケーションを開発するためのJava用フレームワーク
 *Hibernate : Java で O/R マッピングを実現するためのツール。
 *O/R マッピング : オブジェクト指向とリレーショナルデータベースの溝を埋めることを目的
 *EntityManagerクラスに、データベースアクセスに関する各種の機能がまとめられている。
 *それらを呼び出すことで、データベースを操作できる
 *createEntityManagerFactory : インスタンス生成(Persistenceクラスの「createEntityManagerFactory」というメソッドを使い、引数にパーシスタンス・ユニット名を指定して呼び出し)
 *Persistence : EntityManagerFactoryを得るために使用されるブートストラップクラス
 */
