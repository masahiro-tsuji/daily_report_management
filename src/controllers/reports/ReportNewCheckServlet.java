package controllers.reports;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import listener.CreateToken;
import models.Report;

/**
 * Servlet implementation class ReportNewCheckServlet
 */
@WebServlet("/report/newcheck")
public class ReportNewCheckServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportNewCheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Report report = new Report();

        report.setReport_date(Date.valueOf(request.getParameter("report_date")));
        report.setTitle(request.getParameter("title"));
        report.setContent(request.getParameter("content"));

        session.setAttribute("_token", CreateToken.getCsrfToken());
        request.setAttribute("report", report);
        request.setAttribute("checkmessage", "●下記の内容で投稿します。よろしいですか？"); // 確認メッセージ格納
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/newcheck.jsp");
        rd.forward(request, response);
    }
}

/*List<String> errors = ReportValidator.validate(report);
        if(errors.size() > 0){
            request.setAttribute("report", report);
            request.setAttribute("errors", errors);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/new.jsp");
            rd.forward(request, response);
*/
