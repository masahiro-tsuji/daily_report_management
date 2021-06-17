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
public class EmployeeNewCheckServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeNewCheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // セッションのトークンを取得
        HttpSession session = request.getSession(true);

        // インスタンス生成
        Employee e = new Employee();
        // new.jspからの値を受け取り、Employeeクラスにセットする。
        e.setCode(request.getParameter("code"));
        e.setName(request.getParameter("name"));
        e.setPass(request.getParameter("pass"));
        e.setAdmin_flag(Integer.parseInt(request.getParameter("flag")));

        // エラーがあったらnew.jspへエラー文とともに返す。(全てのチェックを行いたいからBooleanは全てtrue)
        List<String> errors = EmployeeValidator.validate(e, true, true);
        if(errors.size() > 0) {
            // 入力値を保持させる
            request.setAttribute("employee", e); // 入力社員情報
            request.setAttribute("errors", errors); // エラー文言
            // 入力値、エラー文等をnew.jspへ返す。
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/new.jsp");
            rd.forward(request, response);
        }else{ // elseを付けなければ下記が実行されトークンが作られ、エラーになる
            // エラーがなければ、確認画面へ
            session.setAttribute("_token", CreateToken.getCsrfToken()); // トークン
            request.setAttribute("employee", e); // 入力社員情報
            request.setAttribute("checkmessage", "下記の内容で登録します。"); // 確認メッセージ格納
            // エラーが無ければ、最終確認画面へ
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/newcheck.jsp");
            rd.forward(request, response);
        }
    }
}