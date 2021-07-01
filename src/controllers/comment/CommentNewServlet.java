package controllers.comment;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.validators.ReportValidator;
import models.Comment;
import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class CommentNewServlet
 */
@WebServlet("/comment/new")
public class CommentNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentNewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        //Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");
        // インスタンス生成とshow.jspから値取得

        Comment comment = new Comment();
        int id = Integer.parseInt(request.getParameter("report_id")); // 日報のidを取得(リダイレクトでこのidが無いと日報が表示されない)
        comment.setReportId(id); // 該当レポートのidを取得
        comment.setEmployee((Employee) request.getSession().getAttribute("login_employee")); // セッションからログインユーザーを取得
        comment.setComment(request.getParameter("comment")); // コメント内相セット
        // コメント番号を入れたいので、現在のコメント数を出力し+1した値を格納
        long comment_count = (long) em.createNamedQuery("getMyCommentsCount", Long.class)
                .setParameter("reportId", id)
                .getSingleResult();
        comment_count++;

        comment.setComment_count((int) comment_count); // コメント番号
        comment.setDelete_flag(0); // データのみDBに残しておくからFlagでセット

        // コメントの入力値チェックを行う
        List<String> errors = ReportValidator.commetvalidate(comment); // errorsの型をList<String>に合わせ、commentの情報を渡す。
        if(errors.size() > 0){
            request.getSession().setAttribute("errors", errors);
            response.sendRedirect(request.getContextPath() + "/report/show?id=" + id);
        }
        else{

            // 投稿日時を現在で取得
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            comment.setCreated_at(currentTime);
            // DBに反映させる
            em.getTransaction().begin();
            em.persist(comment);
            em.getTransaction().commit();
            em.close();
            // コメント投稿完了の旨を伝えるメッセージをsetAttribute
            request.getSession().setAttribute("flush", "コメントの投稿が完了しました。");
            // response.sendRedirectでコメントを投稿しても、show.jspのまま
            response.sendRedirect(request.getContextPath() + "/report/show?id=" + id);
        }
    }
}
