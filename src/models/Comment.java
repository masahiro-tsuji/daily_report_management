// 2021/06/17 *削除する際はpersistenceも確認
package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

// SQL文作成
@Table(name="comments")
@NamedQueries({

    // 自分の全ての日報
    @NamedQuery(
        name = "getMyAllComments",
        query = "SELECT c FROM Comment AS c WHERE c.reportId = :reportId ORDER BY c.id DESC"
        ),
    // 自分の投稿日報数
    @NamedQuery(
        name = "getMyCommentsCount",
        query = "SELECT COUNT(c) FROM Comment AS c WHERE c.reportId = :reportId"
        )
})

@Entity
public class Comment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne // 1人の従業員は毎日日報を作成するので複数。しかし日報から見たら作成者は1人の従業員
    @JoinColumn(name = "employee", nullable = false)
    private Employee employee; // Employeeデータ


    @Column(name = "report_id", nullable = false)
    private Integer reportId; // 投稿する日報

    @Lob // これで改行がなされた場合も反映される
    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    // getter.setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
