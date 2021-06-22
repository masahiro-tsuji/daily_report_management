package controllers.reports;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Comment;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportShowServlet
 */
@WebServlet("/report/show")
public class ReportShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // DBopen.
        EntityManager em = DBUtil.createEntityManager();
        int reportId = Integer.parseInt(request.getParameter("id"));
        // index.jspからのidでDB内の該当データを取得
        Report report = em.find(Report.class, reportId);
        // 2021/06/21---------------------------------------------------------------------------------------
        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        }catch (Exception e){
            page = 1;
        }
        // 2021/06/21---------------------------------------------------------------------------------------
        // 2021/06/17------------------------------------------------------------------------------------
        //Comment comment = em.find(Comment.class, Integer.parseInt(request.getParameter("id")));
        List<Comment> comments = em.createNamedQuery("getMyAllComments", Comment.class)
                                  .setParameter("reportId", Integer.parseInt(request.getParameter("id")))

                                  .setFirstResult(5 * (page - 1))
                                  .setMaxResults(5)

                                  .getResultList();
        //-----------------------------------------------------------------------------------------------
        long comment_count = (long) em.createNamedQuery("getMyCommentsCount", Long.class)
                .setParameter("reportId", reportId)
                .getSingleResult();
        // DBclose.
        em.close();
        // 取得データをshow.jspで使えるようsetAttribute()する。
        request.setAttribute("report", report);
        request.setAttribute("comments", comments);

        request.setAttribute("page", page);
        request.setAttribute("comment_count", comment_count);
        // show.jspへ
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}
