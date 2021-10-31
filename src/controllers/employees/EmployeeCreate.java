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
 * Servlet implementation class EmployeeCreate
 */
@WebServlet("/employee/create")
public class EmployeeCreate extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeCreate() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String token = request.getParameter("token"); // JSPトークンの取得
        // セッションのトークンを取得
        HttpSession session = request.getSession(true);
        String _token = (String)session.getAttribute("_token");
        // トークンが一致しているなら。
        if(_token != null && _token.equals(token) ){
            EntityManager em = DBUtil.createEntityManager(); // データベースopen
            Employee e = new Employee(); // インスタンス生成

            // new.jspからの値を受け取る。
            e.setCode(request.getParameter("code"));
            e.setName(request.getParameter("name"));
            e.setPass(EncryptUtil.getPasswordEncrypt(request.getParameter("pass"),
            (String)this.getServletContext().getAttribute("pepper")));
            e.setAdmin_flag(Integer.parseInt(request.getParameter("flag")));

            // 作成日・更新日を登録　デリートフラグは消さないので”0”設定
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            e.setCreate_date(currentTime);
            e.setUpdate_date(currentTime);
            e.setDelete_flag(0);

            // 登録
            em.getTransaction().begin(); // トランザクションの開始
            em.persist(e); // 永続化の実行(Entityオブジェクトが変わる) *引数にクラスのインスタンスかEntity
            em.getTransaction().commit(); // コミット、更新SQL発行(処理を実行)
            request.getSession().setAttribute("flush", "登録が完了致しました。");
            em.close();
            // 作業が終わったら、index.jspへ遷移させたいので、まずはindexのサーブレットへ行く。
            response.sendRedirect(request.getContextPath() + "/employee/index");
        }else{
            // トークン不正内容を記載
            response.sendRedirect("/WEB-INF/views/topPage/tokenerror.jsp");
            return;
        }
    }
}
