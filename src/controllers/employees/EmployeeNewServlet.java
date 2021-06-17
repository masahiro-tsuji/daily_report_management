package controllers.employees;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;

/**
 * Servlet implementation class EmployeeNewServlet
 */
@WebServlet("/employee/new")
public class EmployeeNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeNewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    // 初期入力
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // インスタンス生成 Employee employee = new Employee();と同じ
        request.setAttribute("employee", new Employee());

        // new.jspへ遷移
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/new.jsp");
        rd.forward(request, response);

    }

    // 確認画面から訂正したいとき
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // インスタンス生成 Employee employee = new Employee();と同じ
        Employee e = new Employee(); // インスタンス生成
        // new.jspからの値を受け取る。
        e.setCode(request.getParameter("code"));
        e.setName(request.getParameter("name"));
        e.setPass(request.getParameter("pass"));
        // 登録していた情報をnew.jspへ送る
        request.setAttribute("employee", e);
        // new.jspへ遷移
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/new.jsp");
        rd.forward(request, response);
    }

}
