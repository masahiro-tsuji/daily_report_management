package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

// Daoの役割
public class DBUtil {

    private static final String PERSISTENCE_NAME = "daily_report_management";
    private static EntityManagerFactory emf; // *１

    public static EntityManager createEntityManager(){
        return getEntityManagerFactory().createEntityManager();
    }

    private static EntityManagerFactory getEntityManagerFactory() {
    // アプリケーションがアプリケーション管理のEntityManagerを取得するために使用
        if(emf == null){
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_NAME);
        }
        return emf;
    }
}
/*
 ・Entity : データベースに保管するオブジェクト。データベースレコードに相当
 *１：EntityManagerがエンティティを受け取り、さまざまなデータベース処理を一括して実行

 */
