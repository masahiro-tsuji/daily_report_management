package controllers.employees;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeeIndexServlet
 */
@WebServlet("/employee/index")
public class EmployeeIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();// EntityManager()データベースに対してエンティティを登録したり，削除したりするためのインタフェースを持つオブジェクトの作成

        int pageNum = 1;// ページ番号。デフォルトは１ページ目

        try{
            pageNum = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e) { }

        // 最大件数と開始位置を指定して従業員を取得
        List<Employee> employee = em.createNamedQuery("getAllEmployee",Employee.class)
                .setFirstResult(15 * (pageNum - 1)) // 何件目からデータを取得するか（配列と同じ0番目から数える
                .setMaxResults(15) // データの最大取得件数（15件で固定）
                .getResultList(); // リストで結果を取得

        // 全従業員数を取得
        long employees_count = (long)em.createNamedQuery("getEmployeesCount", Long.class)
        .getSingleResult();

        em.close(); // データベースを閉じる

        // パラメーターを追加
        request.setAttribute("employee", employee); // 従業員のデータ
        request.setAttribute("employee_count", employees_count); // 全件数
        request.setAttribute("page", pageNum); // ページ数
        // フラッシュメッセージ関連 * １
        // リクエストスコープにフラッシュメッセージをセットすると途中で削除されてしまう。
        // そこで、フラッシュメッセージをセッションスコープに保存、index.jsp を呼び出したときにセッションスコープから取り出して表示
        if(request.getSession().getAttribute("flush") != null) { // オブジェクトがあるなら
            // セッションスコープ内のフラッシュメッセージをリクエストスコープに保存
            request.setAttribute("message", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");  // 引数に指定したセッションオブジェクト(スコープ)を削除
        }
        //index.jspへ遷移
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/index.jsp");
        rd.forward(request, response);
    }
}

/*
 *１: getSession() : HttpServletRequestからセッションのインスタンスを取得。
      getAttribute() : セッションオブジェクトを取り出して読み込む

 */
