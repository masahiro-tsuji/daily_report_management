package controllers.employees;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import listener.CreateToken;
import models.Employee;

/**
 * Servlet implementation class EmployeeUpdateCheckServlet
 */
@WebServlet("/employee/editcheck")
public class EmployeeEditCheckServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeEditCheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Editのセッションを取得
        HttpSession session = request.getSession(true);
        int employeeId = (int) session.getAttribute("employeeId");

        // インスタンス生成
        Employee e = new Employee();
        e.setCode(request.getParameter("code"));
        e.setName(request.getParameter("name"));
        e.setPass(request.getParameter("pass"));
        e.setAdmin_flag(Integer.parseInt(request.getParameter("flag")));
        // 確認画面へ値をセット
        request.setAttribute("employee", e);
        session.setAttribute("employeeId", employeeId);
        session.setAttribute("_token", CreateToken.getCsrfToken());
        request.setAttribute("checkmessage", "下記の内容で更新します。");
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/editcheck.jsp");
        rd.forward(request, response);
    }
}
