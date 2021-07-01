package controllers.login;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import listener.CreateToken;
import models.Employee;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    // ログイン画面の表示
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request.getSession(false); // 勝手にセッションが作成されてしまうため、明示的にfalseにする。

        // ログインできなかった場合のエラーを表示をtrue,falseで表示。今回はlogin.jspに遷移するだけなのでfalse
        request.setAttribute("hasError", false);
        // フラシュメッセージがあったら表示させセッションを削除する。"ログアウトしました。"等のメッセージ
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }
        // トークンセット
        request.getSession().setAttribute("_token", CreateToken.getCsrfToken());
        // ログイン画面に遷移
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/login.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    // ログイン処理の実行
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String token = request.getParameter("token");
        String _token = (String) request.getSession().getAttribute("_token");

        if(_token != null && _token.equals(token) ){

            // 認証結果を残す変数
            Boolean check_result = false;

            String code = request.getParameter("code");
            String plain_pass = request.getParameter("pass");

            // 全てが未入力なら
            if(code == null || code.equals("") || plain_pass == null || plain_pass.equals("")){
                // トークン
                request.getSession().setAttribute("_token", CreateToken.getCsrfToken());
                request.setAttribute("codePassNull", true);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/login.jsp");
                rd.forward(request, response);
            // 社員番号とパスワードが入力されているなら
            }else{

                Employee employee = null;
                // 入力された社員番号とパスワードが入力されていたら。
                if(code != null && !code.equals("") && plain_pass != null && !plain_pass.equals("") ){
                    // DBオープン
                    EntityManager em = DBUtil.createEntityManager();
                    // パスワードはハッシュ化されてデータベースに登録されていいるから
                    // フォームから入力されたパスワードに EncryptUtil.getPasswordEncrypt()
                    // を使ってペッパー文字列を連結した文字列をハッシュ化し、そのデータとデータベース上のデータで照合をする。
                    String password = EncryptUtil.getPasswordEncrypt(plain_pass, (String)this.getServletContext().getAttribute("pepper"));
                    // 社員番号とパスワードが合致しているか
                    try{
                        // 検索結果１件を取得、取得できなければNoResultExceptionへ
                        employee = em.createNamedQuery("checkLogin", Employee.class)
                                .setParameter("code", code)
                                .setParameter("pass", password)
                                .getSingleResult();
                    }catch(NoResultException ex){}

                    em.close(); // DB閉じる
                    // 社員情報があれば,check_resultをtrueにしてログインする。
                    if(employee != null) {
                        check_result = true;
                    }

                    // check_resultがfalseならログイン画面に戻る
                    if(!check_result){
                        // 認証できなかったらログイン画面に戻る
                        //トークンセット

                        request.setAttribute("hasError", true);
                        request.setAttribute("code", code);

                        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/login.jsp");
                        rd.forward(request, response);
                    }else{
                        // 認証できたらログインする(トップページのServletへ遷移)
                        request.getSession().setAttribute("login_employee", employee); // セッションスコープ従業員情報のオブジェクトを格納
                                                                                // *セッションスコープに login_employee という名前で従業員情報の
                                                                                //  オブジェクトが保存されている状態をログインしている状態

                        request.getSession().setAttribute("flush", "ログインしました。");
                        // 自動でログインページにリダイレクト
                        response.sendRedirect(request.getContextPath() + "/");
                    }
                }
            }
        }else{
            // トークン不正内容を記載
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/tokenerror.jsp");
            rd.forward(request, response);
        }
    }
}
