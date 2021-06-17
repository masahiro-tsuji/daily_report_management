package controllers.reports;

import java.io.IOException;
import java.sql.Date;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportEditServlet
 */
@WebServlet("/report/edit")
public class ReportEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        Report report = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        HttpSession session = request.getSession(true);
        request.setAttribute("report", report);
        session.setAttribute("reportId", report.getId());
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/edit.jsp");
        rd.forward(request, response);

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // editcheck.jspで"戻る"が押下され、edit.jspで詳細画面に戻りたい時に、reportIdが必要。
        int reportId = (int) request.getSession().getAttribute("reportId");

        // editcheck.jspの値を取得
        Report report = new Report();
        report.setReport_date(Date.valueOf(request.getParameter("report_date")));
        report.setTitle(request.getParameter("title"));
        report.setContent(request.getParameter("content"));

        // 再度セッションにreportIdをセット
        HttpSession session = request.getSession(true);
        request.setAttribute("report", report);
        session.setAttribute("reportId", reportId);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/edit.jsp");
        rd.forward(request, response);
    }
}
