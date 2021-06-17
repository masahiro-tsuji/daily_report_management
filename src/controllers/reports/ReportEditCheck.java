package controllers.reports;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import listener.CreateToken;
import model.validators.ReportValidator;
import models.Report;

/**
 * Servlet implementation class ReportEditCheck
 */
@WebServlet("/report/editcheck")
public class ReportEditCheck extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportEditCheck() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        //値を取得
        Report report = new Report();

        report.setReport_date(Date.valueOf(request.getParameter("report_date")));
        report.setTitle(request.getParameter("title"));
        report.setContent(request.getParameter("content"));

        // エラーがあったら、edit.jspでエラー文言の表示
        List<String>errors = ReportValidator.validate(report);
        if(errors.size() > 0){
            request.setAttribute("report", report);
            request.setAttribute("errors", errors);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/edit.jsp");
            rd.forward(request, response);
        }else{
            // 問題がなければeditcheck.jspへ遷移
            session.setAttribute("_token", CreateToken.getCsrfToken());
            request.setAttribute("report", report);
            request.setAttribute("checkmessage", "●下記の内容で更新します。よろしいですか？");
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/editcheck.jsp");
            rd.forward(request, response);
        }
    }
}
