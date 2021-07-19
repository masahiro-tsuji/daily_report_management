package controllers.employees;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
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
import utils.DBUtil;
import utils.EncryptUtil;

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

        EntityManager em = DBUtil.createEntityManager();
        Employee e = em.find(Employee.class, (Integer)(request.getSession().getAttribute("employeeId"))); // Entityの検索
        // 編集フォームからの入力内容を受け取る
        // 現在の値と異なる社員番号が入力されていたら
        // 重複チェックを行う指定をする
        Boolean codeDuplicateCheckFlag = true;
        if(e.getCode().equals(request.getParameter("code"))) {
            codeDuplicateCheckFlag = false;
        } else {
            e.setCode(request.getParameter("code"));
        }

        // パスワード欄に入力があったら
        // パスワードの入力値チェックを行う指定をする
        Boolean passwordCheckFlag = true;
        String password = request.getParameter("pass");
        if(password == null || password.equals("")) {
            passwordCheckFlag = false;
        } else {
            e.setPass(
                    EncryptUtil.getPasswordEncrypt(
                            password,
                            (String)this.getServletContext().getAttribute("pepper")
                            )
                    );
        }
        e.setName(request.getParameter("name"));
        e.setAdmin_flag(Integer.parseInt(request.getParameter("flag")));
        e.setUpdate_date(new Timestamp(System.currentTimeMillis()));
        e.setDelete_flag(0);

        // エラーがあったらエラー文と共にedit.jsp(編集入力画面に戻す)
        List<String> errors = EmployeeValidator.validate(e, codeDuplicateCheckFlag, passwordCheckFlag);
        if(errors.size() > 0) {
            em.close(); // データベースを閉じる
            // エラー情報、該当従業員情報をedit.jspへ返す。
            request.setAttribute("employee", e);
            request.setAttribute("errors", errors);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/edit.jsp");
            rd.forward(request, response);
        // エラーがなければ、データベースを更新させる
        }else{
        // 確認画面へ値をセット
        request.setAttribute("employee", e);
        session.setAttribute("employeeId", employeeId);
        session.setAttribute("_token", CreateToken.getCsrfToken());
        request.setAttribute("checkmessage", "下記の内容で更新します。");
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/editcheck.jsp");
        rd.forward(request, response);
        }
    }
}
