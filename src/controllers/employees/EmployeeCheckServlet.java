package controllers.employees;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import listener.CreateToken;
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
        String token = request.getParameter("token"); // JSPトークンの取得
        // セッションのトークンを取得
        HttpSession session = request.getSession(true);
        String _token = (String)session.getAttribute("_token");
        Employee e = new Employee(); // インスタンス生成
        // トークンが一致しているなら.
        if(_token != null && _token.equals(token) ){
            // new.jspからの値を受け取る。
            e.setCode(request.getParameter("code"));
            e.setName(request.getParameter("name"));
            e.setPass(request.getParameter("pass"));
            e.setAdmin_flag(Integer.parseInt(request.getParameter("flag")));

            // エラーがあったらnew.jspへエラー文とともに返す。
            List<String> errors = EmployeeValidator.validate(e, true, true);
            if(errors.size() > 0) {
                // 入力値を保持させる
                session.setAttribute("_token", CreateToken.getCsrfToken());
                request.setAttribute("employee", e);
                request.setAttribute("errors", errors);
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/new.jsp");
                rd.forward(request, response);
            }else{ // elseを付けなければ下記が実行されトークンが作られ、エラーになる
                // エラーがなければ、確認画面へ
                session.setAttribute("_token", CreateToken.getCsrfToken());
                request.setAttribute("employee", e);
                request.setAttribute("checkmessage", "下記の内容で登録します。");
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/create.jsp");
                rd.forward(request, response);
            }
        }else{ // トークンエラーがあったら
            response.sendRedirect("/WEB-INF/views/topPage/tokenerror.jsp");
            return;
        }
    }
}