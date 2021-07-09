package controllers.reports;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.comment.CommentComparator;
import models.Comment;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportShowServlet
 */
@WebServlet("/report/show")
public class ReportShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // DBopen.
        EntityManager em = DBUtil.createEntityManager();
        int reportId = Integer.parseInt(request.getParameter("id"));
        // index.jspからのidでDB内の該当データを取得
        Report report = em.find(Report.class, reportId);
        int page; // ページ数格納
        // コメントの全件数
        long comment_count = (long) em.createNamedQuery("getMyCommentsCount", Long.class)
                .setParameter("reportId", reportId)
                .getSingleResult(); // 型なしの単一の結果を返す。(ResultListの場合は、投稿者、コメント内容、日時のデータがあるからSingleResultではNG)

        try{
            // 選択されたページナンバーを取得
            page = Integer.parseInt(request.getParameter("page"));
        }catch (Exception e){
            // ページ最大値に新しいコメントを出したいから
            page = (int) (((comment_count - 1) / 5) + 1);
        }

        // 日報に対してのコメントを取得
        List<Comment> comments = em.createNamedQuery("getMyAllComments", Comment.class)
                // 日報のidをパラメーターにセットして対象の日報を取得
                .setParameter("reportId", Integer.parseInt(request.getParameter("id")))
                // １ページ目のデータは、配列同様0番目から1.2.3.4....を取得。２ページ目は、5番目から6.7.8.9.....を取得。３ぺ時目は、10....
                .setFirstResult(5 * (page - 1)) // 何件目からデータを取得するか（配列と同じ0番目から) setFirstResult:取得する最初の結果の位置を設定
                // １ページ目であれば０番目から～～ある内の５件を取得
                .setMaxResults(5) // データの最大取得件数。
                .getResultList(); // 型なしのリストで返す。

        // コメントをmodels.Commentのcomment_count降順に並べ替える。
        Collections.sort(comments, new CommentComparator());

        // DBclose.
        em.close();
        // 取得データをshow.jspで使えるようsetAttribute()する。
        request.setAttribute("report", report); // 日報オブジェクト
        request.setAttribute("comments", comments); // コメントオブジェクト
        request.setAttribute("page", page); // 現在ページ
        request.setAttribute("comment_count", comment_count); // コメント数

        // CommentNewServletでerrorsに引っ掛かった場合セッションをセットして出続けないように削除
        if(request.getSession().getAttribute("errors") != null) { // セッションの"errors"があるなら
            request.setAttribute("errors", request.getSession().getAttribute("errors")); // 内容をsetAttributeする
            request.getSession().removeAttribute("errors"); // setAttributeしたので、不要なセッションを削除する。
        }                                                   // (これをしないと要らないとこで表示される)
        // 上記同様コメント投稿完了を伝える様実装
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }
        // show.jspへ遷移
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }
}
