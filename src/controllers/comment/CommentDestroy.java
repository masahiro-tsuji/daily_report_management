package controllers.comment;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Comment;
import utils.DBUtil;

/**
 * Servlet implementation class CommentDestroy
 */
@WebServlet("/report/comment/destroy")
public class CommentDestroy extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentDestroy() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int commentId = Integer.parseInt(request.getParameter("commentId")); // コメントのidを取得
        int id = Integer.parseInt(request.getParameter("reportId"));

        EntityManager em = DBUtil.createEntityManager(); // DBオープン

        Comment comment = em.find(Comment.class, commentId); // コメントのインスタンスオブジェクトに該当データを入れる。
        comment.setDelete_flag(1);

        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("flush", "削除が完了しました。");
        response.sendRedirect(request.getContextPath() + "/report/show?id=" + id);
    }
}
