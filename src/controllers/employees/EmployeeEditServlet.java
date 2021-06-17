package controllers.employees;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import listener.CreateToken;
import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeeEditServlet
 */
@WebServlet("/employee/edit")
public class EmployeeEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager(); // DBオープン
        Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("id"))); // Show.jspから受け取ったidがDBにあるか

        // トークン生成
        HttpSession session = request.getSession(true);
        session.setAttribute("_token", CreateToken.getCsrfToken());
        request.setAttribute("employee", e); // DBデータをJSPへ送信
        session.setAttribute("employeeId", e.getId());
        em.close(); // DBを閉じる
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/edit.jsp");
        rd.forward(request, response);
    }
    // edit.jspから”戻る”が押された時の遷移先
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Editのセッションを取得
        HttpSession session = request.getSession(true);

        int employeeId = (int) session.getAttribute("employeeId");
        EntityManager em = DBUtil.createEntityManager(); // DBオープン
        Employee e = em.find(Employee.class, employeeId);

        // 値をedit.jspへ送る
        session.setAttribute("_token", CreateToken.getCsrfToken());
        request.setAttribute("employee", e);
        em.close();
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/edit.jsp");
        rd.forward(request, response);
    }
}
