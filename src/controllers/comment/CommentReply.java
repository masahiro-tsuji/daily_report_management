package controllers.comment;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Comment;
import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class CommentReply
 */
@WebServlet("/reply")
public class CommentReply extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentReply() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int reportId = Integer.parseInt(request.getParameter("rid"));
        int commentId = Integer.parseInt(request.getParameter("cid"));

        EntityManager em = DBUtil.createEntityManager();
        Comment comment = em.find(Comment.class, commentId);
        Comment repComment = new Comment();

        em.close();

        request.setAttribute("reportId", reportId);
        request.setAttribute("comment", comment);
        request.setAttribute("repComment", repComment);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/comments/reply.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int reportId = Integer.parseInt(request.getParameter("reportId"));
        int repComId = Integer.parseInt(request.getParameter("commentId"));

        Comment subComment =  em.find(Comment.class, repComId);

        Comment repComment = new Comment();
        repComment.setReportId(reportId); // 該当レポートのidを取得
        repComment.setEmployee((Employee) request.getSession().getAttribute("login_employee")); // セッションからログインユーザーを取得
        repComment.setComment(request.getParameter("repComment")); // 返信コメントセット
        // コメント番号を入れたいので、現在のコメント数を出力し+1した値を格納
        long comment_count = (long) em.createNamedQuery("getMyCommentsCount", Long.class)
                .setParameter("reportId", reportId)
                .getSingleResult();
        comment_count++;
        repComment.setComment_count((int) comment_count); // コメント番号
        repComment.setDelete_flag(0); // データのみDBに残しておくからFlagでセット
        repComment.setComment_id(repComId); // 返信するコメントのID格納
        repComment.setComment_num(subComment.getComment_count());
        // コメントの入力値チェックを行う

        // 投稿日時を現在で取得
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        repComment.setCreated_at(currentTime);
        // DBに反映させる
        em.getTransaction().begin();
        em.persist(repComment);
        em.getTransaction().commit();
        em.close();

        // コメント投稿完了の旨を伝えるメッセージをsetAttribute
        request.getSession().setAttribute("flush", "コメントの投稿が完了しました。");
        //request.getSession().setAttribute("subComment", subComment);
        // response.sendRedirectでコメントを投稿しても、show.jspのまま
        response.sendRedirect(request.getContextPath() + "/report/show?id=" + reportId);
    }
}
