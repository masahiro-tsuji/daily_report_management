package controllers.reports;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;

/**
 * Servlet implementation class ReportNewServlet
 */
@WebServlet("/report/new")
public class ReportNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportNewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // インスタンス
        Report report = new Report();
        // 日報の日時は、事前に本日の日付を取得して格納
        report.setReport_date(new Date(System.currentTimeMillis()));

        request.setAttribute("report", report);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/new.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Report report = new Report(); // インスタンス生成
        // 日報の日時は、事前に本日の日付を取得して格納
        report.setReport_date(new Date(System.currentTimeMillis()));
        report.setReport_date(Date.valueOf(request.getParameter("report_date"))); // // JSPのdate型をsetReport_dateにセットできるようDate.valueOfで変換
        report.setTitle(request.getParameter("title"));
        report.setContent(request.getParameter("content"));

        request.setAttribute("report", report);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/new.jsp");
        rd.forward(request, response);
    }

}
