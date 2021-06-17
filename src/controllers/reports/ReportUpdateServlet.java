package controllers.reports;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

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
 * Servlet implementation class ReportUpdateServlet
 */
@WebServlet("/report/update")
public class ReportUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        String _token = (String) session.getAttribute("_token");
        String token = request.getParameter("token");

        if(_token != null && _token.equals(token)){

            EntityManager em = DBUtil.createEntityManager();
            // HttpSession session = request.getSession(true);
            int reportId = (int) request.getSession().getAttribute("reportId");

            Report report = em.find(Report.class, reportId);
            report.setReport_date(Date.valueOf(request.getParameter("report_date")));
            report.setTitle(request.getParameter("title"));
            report.setContent(request.getParameter("content"));
            report.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            em.getTransaction().begin(); // トランザクションの開始
            em.getTransaction().commit(); // コミット、更新SQL発行(処理を実行)
            em.close(); // データベースを閉じる
            // 不要なセッションを削除
            request.getSession().removeAttribute("reportId");
            // 作業が終わったら、フラッシュメッセージと共にindexのサーブレットへ遷移
            request.getSession().setAttribute("flush", "更新が完了しました。");
            response.sendRedirect(request.getContextPath() + "/report/index");
        }else{
            // トークン不正内容を記載
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/tokenerror.jsp");
            rd.forward(request, response);
        }
    }

}
