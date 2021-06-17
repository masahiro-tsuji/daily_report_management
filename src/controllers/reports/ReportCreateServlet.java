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

import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportCreateServlet
 */
@WebServlet("/report/create")
public class ReportCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportCreateServlet() {
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

            EntityManager em = DBUtil.createEntityManager();// DBオープン
            Report report = new Report();   // 日報のインスタンス生成
            // 入力フォームの内容(new.jsp)をインスタンスにセット
            report.setEmployee((Employee) request.getSession().getAttribute("login_employee")); // セッションからログインユーザーを取得
            report.setReport_date(Date.valueOf(request.getParameter("report_date"))); // JSPのdate型をsetReport_dateにセットできるようDate.valueOfで変換
            report.setTitle(request.getParameter("title"));
            report.setContent(request.getParameter("content"));

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            report.setCreated_at(currentTime);

            em.getTransaction().begin();
            em.persist(report);
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "投稿が完了しました。");
            response.sendRedirect(request.getContextPath() + "/report/index");

        }else{
            // トークン不正内容を記載
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/tokenerror.jsp");
            rd.forward(request, response);
        }
    }
}
