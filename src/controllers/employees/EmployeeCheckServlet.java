package controllers.employees;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.validators.EmployeeValidator;
import models.Employee;

/**
 * Servlet implementation class EmployeeCreateServlet
 */
@WebServlet("/employee/check")
public class EmployeeCheckServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeCheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("_token"); // トークンの取得
        Employee e = new Employee();
        // トークンが一致しているなら。
        if(token != null && token.equals(request.getSession().getId()) ){

            // new.jspからの値を受け取る。
            e.setCode(request.getParameter("code"));
            e.setName(request.getParameter("name"));
            e.setPass(request.getParameter("pass"));
            e.setAdmin_flag(Integer.parseInt(request.getParameter("flag")));

            // エラーがあったらnew.jspへエラー文とともに返す。
            List<String> errors = EmployeeValidator.validate(e, true, true);
            if(errors.size() > 0) {
                // 入力値を保持させる
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("employee", e);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/new.jsp");
                rd.forward(request, response);
            }
            // エラーがなければ、確認画面へ
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("employee", e);
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/create.jsp");
                rd.forward(request, response);
        }
    }
}