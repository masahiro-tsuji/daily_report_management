package models;

import java.sql.Date;
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
@Table(name = "reports")
@NamedQueries({
    // 全ての日報
    @NamedQuery(
        name = "getAllReport",
        query = "SELECT r FROM Report AS r ORDER BY r.id Desc"
        ),
    // 日報数
    @NamedQuery(
        name = "getReportsCount",
        query = "SELECT COUNT(r) FROM Report AS r"
        ),
    // 自分の全ての日報
    @NamedQuery(
        name = "getMyAllReports",
        query = "SELECT r FROM Report AS r WHERE r.employee = :employee ORDER BY r.id DESC"
        ),
    // 自分の投稿日報数
    @NamedQuery(
        name = "getMyReportsCount",
        query = "SELECT COUNT(r) FROM Report AS r WHERE r.employee = :employee"
        )
})
@Entity
public class Report {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne // 1人の従業員は毎日日報を作成するので複数。しかし日報から見たら作成者は1人の従業員
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee; // Employeeデータ

    @Column(name = "report_date", nullable = false)
    private Date report_date;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob // これで改行がなされた場合も反映される
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = true)
    private Timestamp updated_at;
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

    public Date getReport_date() {
        return report_date;
    }

    public void setReport_date(Date report_date) {
        this.report_date = report_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
