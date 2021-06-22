package controllers.comment;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        Comment comment = new Comment();
        int id = Integer.parseInt(request.getParameter("report_id"));
        comment.setReportId(Integer.parseInt(request.getParameter("report_id")));
        comment.setEmployee((Employee) request.getSession().getAttribute("login_employee")); // セッションからログインユーザーを取得
        comment.setComment(request.getParameter("comment"));
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        comment.setCreated_at(currentTime);

        em.getTransaction().begin();
        em.persist(comment);
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("flush", "投稿が完了しました。");
        //request.setAttribute("repordId", id);
        // response.sendRedirectでコメントを投稿しても、show.jspのまま
        response.sendRedirect(request.getContextPath() + "/report/show?id=" + id);
        //getServletContext().getRequestDispatcher("/WEB-INF/views/reports/show.jsp").forward(request, response);
    }
}
