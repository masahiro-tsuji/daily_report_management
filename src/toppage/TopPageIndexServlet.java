package toppage;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TopPageIndexServlet
 */
@WebServlet("/index.html")
public class TopPageIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopPageIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
        rd.forward(request,response);
    }

}
/*
 * ●インターフェース RequestDispatcher
 * クライアントからリクエストを受信し、サーバー上の任意のリソース
 *（サーブレット、HTML ファイル、JSP ファイルなど）に送信するオブジェクトを定義
 *●.include : 転送先を呼び出すメソッド。
 *●.forward : 処理を転送するメソッド...転送先では、”req.getAttribute("name")”の様にして値を取得
 *  ※request.getRequestDispatcher("/遷移先 or 処理先URL"); で、指定。
 *
 */
