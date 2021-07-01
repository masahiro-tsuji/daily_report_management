package toppage;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class TopPageIndexServlet
 */
@WebServlet("/index.html")
public class TopPageIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopPageIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();
        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee"); // ログインしてるユーザーの情報を格納

        int pageNum;

        try{
            pageNum = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            pageNum = 1;
        }

        // ログインユーザーの全ての日報を取得
        List<Report> reports = em.createNamedQuery("getMyAllReports", Report.class)
                                  .setParameter("employee", login_employee)
                                  .setFirstResult(15 * (pageNum - 1))
                                  .setMaxResults(15)
                                  .getResultList();
        // ログインユーザーの全日報数
        long reports_count = (long)em.createNamedQuery("getMyReportsCount", Long.class)
                                        .setParameter("employee", login_employee)
                                        .getSingleResult();
        em.close();

        request.setAttribute("reports", reports);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", pageNum);


        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
        rd.forward(request, response);
    }
}
/*
 * ●インターフェース RequestDispatcher
 * クライアントからリクエストを受信し、サーバー上の任意のリソース
 *（サーブレット、HTML ファイル、JSP ファイルなど）に送信するオブジェクトを定義
 *●.include : 転送先を呼び出すメソッド。
 *●.forward : 処理を転送するメソッド...転送先では、”req.getAttribute("name")”の様にして値を取得
 *  ※request.getRequestDispatcher("/遷移先 or 処理先URL"); で、指定。
 *
 */
