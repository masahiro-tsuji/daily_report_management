package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportDestroyServlet
 */
@WebServlet("/report/destroy")
public class ReportDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 削除の実装http://libro.tuyano.com/index3?id=9736003&page=4

        EntityManager em = DBUtil.createEntityManager();
        Report report = em.find(Report.class, request.getSession().getAttribute("reportId"));

        em.getTransaction().begin();
        em.remove(report);  // ここでreportを削除
        em.flush(); // 反映させる(これはないことでデータが反映されない場合もあるらしい)
        em.getTransaction().commit();
        em.close();

        // 不要なセッションを削除
        request.getSession().removeAttribute("reportId");
        // 作業が終わったら、フラッシュメッセージと共にindexのサーブレットへ遷移
        request.getSession().setAttribute("flush", "削除が完了しました。");
        response.sendRedirect(request.getContextPath() + "/report/index");
    }

}
