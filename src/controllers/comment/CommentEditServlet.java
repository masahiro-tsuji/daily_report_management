package controllers.comment;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Comment;
import utils.DBUtil;

/**
 * Servlet implementation class CommentEditServlet
 */
@WebServlet("/report/commentedit")
public class CommentEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();
        int commentId = Integer.parseInt(request.getParameter("cid"));
        Comment comment = em.find(Comment.class, commentId);
        em.close();
        request.setAttribute("comment", comment);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/comments/edit.jsp");
        rd.forward(request, response);
    }
}

/*
 * import javax.xml.stream.events.Comment; ← modelsパッケージのCommentではなく、左記の物をインポートしてしまった為、
 *                                            HTTPステータス 500 - Unable to locate persister:javax.xml.stream.events.Comment
 *                                            のエラーが出た。
 */
