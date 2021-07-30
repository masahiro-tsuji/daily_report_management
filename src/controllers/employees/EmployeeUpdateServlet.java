package controllers.employees;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Employee;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class EmployeeUpdateServlet
 */
@WebServlet("/employee/update")
public class EmployeeUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        String _token = (String)session.getAttribute("_token");
        String token = request.getParameter("token");
        String subPass = (String)session.getAttribute("subPass");
        if(_token != null && _token.equals(token)){
            // データベースを開き、既存データを持ってくる
            EntityManager em = DBUtil.createEntityManager();
            Employee e = em.find(Employee.class, (Integer)(request.getSession().getAttribute("employeeId"))); // Entityの検索
            // 編集フォームからの入力内容を受け取る
            // 現在の値と異なる社員番号が入力されていたら
            if(!(e.getCode().equals(request.getParameter("code")))) {
                e.setCode(request.getParameter("code"));
            }

            // パスワード欄に入力があったら
            if(subPass == null){
                String password = request.getParameter("pass");
                if(!(password == null || password.equals(""))) {
                    e.setPass(EncryptUtil.getPasswordEncrypt(password, (String)this.getServletContext().getAttribute("pepper")));
                }
            }else{
                e.setPass(subPass);
            }
            e.setName(request.getParameter("name"));
            e.setAdmin_flag(Integer.parseInt(request.getParameter("flag")));
            e.setUpdate_date(new Timestamp(System.currentTimeMillis()));
            e.setDelete_flag(0);

            // データベース更新作業
            em.getTransaction().begin(); // トランザクションの開始
            em.getTransaction().commit(); // コミット、更新SQL発行(処理を実行)
            em.close(); // データベースを閉じる
            // 不要なセッションを削除
            request.getSession().removeAttribute("empId");
            // 作業が終わったら、フラッシュメッセージと共にindexのサーブレットへ遷移
            request.getSession().setAttribute("flush", "更新が完了しました。");
            response.sendRedirect(request.getContextPath() + "/employee/index");
        }else{
            // トークン不正内容を記載
            response.sendRedirect("/WEB-INF/views/topPage/tokenerror.jsp");
            return;
        }
    }

}
