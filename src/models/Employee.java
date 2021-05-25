package models;
//persistence.xml はGit管理の対象にするべきファイルではない。
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

//SQL文作成とテーブル指定
@Table(name = "employees")  // @Table : Entityにマッピングされるテーブル名を指定
@NamedQueries({     // @NamedQueries : 複数のNamedQueryをまとめたもの
    @NamedQuery(    // @NamedQuery : 主キー以外の項目などで検索し、複数件の結果を取得したい場合に定義(SQL文に名前を付けたもの)
        name = "getAllEmployee",   // すべての従業員情報を取得
        query = "SELECT e FROM Employee AS e ORDER BY e.id Desc"
        ),
    @NamedQuery(    // 従業員情報の全件数を取得
        name = "getEmployeesCount",
        query = "SELECT COUNT(e) FROM Employee AS e"
        ),
    @NamedQuery(    // 指定された社員番号がすでにデータベースに存在しているか
            name = "checkRegisteredCode",
            query = "SELECT COUNT(e) FROM Employee AS e WHERE e.code = :code"
            ),
    @NamedQuery(    // 従業員がログインするときに社員番号とパスワードが正しいかチェック
            name = "checkLogin",
            query = "SELECT e FROM Employee AS e WHERE e.delete_flag = 0 AND e.code = :code AND e.pass = :pass"
            )
    })

@Entity
    public class Employee {

//インスタンス変数生成
    // 同名のローカル変数がある時、「this.フィールド」、または「インスタンスを格納している変数名.フィールド」でアクセスする
    //ID
    @Id //主キーのフィールドに指定
    @Column(name = "id")    //Entityの各フィールドにマッピングされるテーブルのカラム名を指定
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // *@GeneratedValue : プライマリキーカラムに値を自動で生成，付与する方法を指定するアノテーション
    // *GenerationType.IDENTITY : エンティティクラスのプライマリキー値を生成する方法を指定する属性。付与するタイプ。
    private Integer id;

    //社員コード
    @Column(name = "code", nullable = false, unique = true)
    // nullable = は、データ入力の際nullを許容するかを指定。trueでnullでもOK, falseだとNG。
    // unique = は、 一意制約といい、すでに存在している社員番号は登録できない旨をデータベースに伝える為の設定。
    private String code;

    //名前
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    //パスワード
    @Column(name = "password", length =100, nullable = false)// パスの桁数を直す。
    private String pass;

    //権限(管理者か一般者)
    @Column(name = "admin_flag", nullable = false)
    private Integer admin_flag;

    //登録日
    @Column(name = "create_date", nullable = false)
    private Timestamp create_date;

    //更新日
    @Column(name = "update_date", nullable = false)
    private Timestamp update_date;

    //削除(退職)された社員か
    @Column(name = "delete_flag", nullable = false)
    private Integer delete_flag;

//getter,setterの生成  *値を追加・変更するのがsetter, 値を取得するのがgetter
    //ID
    public Integer getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }

    //社員番号
    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }

    //名前
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    //パスワード
    public String getPass(){
        return this.pass;
    }
    public void setPass(String pass){
        this.pass = pass;
    }

    //管理者
    public Integer getAdmin_flag() {
        return admin_flag;
    }
    public void setAdmin_flag(Integer admin_flag) {
        this.admin_flag = admin_flag;
    }

    //作成
    public Timestamp getCreate_date() {
        return create_date;
    }
    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }

    //アップデート
    public Timestamp getUpdate_date() {
        return update_date;
    }
    public void setUpdate_date(Timestamp update_date) {
        this.update_date = update_date;
    }

    //削除フラグ
    public Integer getDelete_flag() {
        return delete_flag;
    }
    public void setDelete_flag(Integer delete_flag) {
        this.delete_flag = delete_flag;
    }
}