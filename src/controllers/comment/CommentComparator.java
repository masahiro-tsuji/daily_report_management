// コメントの並べ替えをするクラス。
package controllers.comment;

import java.util.Comparator;

import models.Comment;

public class CommentComparator implements Comparator<Comment> {
    // 自動生成されるメソッド
    @Override
    public int compare(Comment c1, Comment c2) { // 5.4.3.2.1と並べる。
        if(c1.getComment_count() < c2.getComment_count())return 1;
        else if(c1.getComment_count() > c2.getComment_count())return -1;
        else return 0;
    }
}
