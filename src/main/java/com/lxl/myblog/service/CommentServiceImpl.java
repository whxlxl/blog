package com.lxl.myblog.service;

import com.lxl.myblog.dao.CommentRepository;
import com.lxl.myblog.po.Blog;
import com.lxl.myblog.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId){
        List<Comment> createTime = commentRepository.findByBlogIdAndParentCommentNull(blogId, Sort.by(Sort.Direction.DESC, "createTime"));
        return searchComment(createTime);
    }

    private List<Comment> searchComment(List<Comment> createTime) {

        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : createTime){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        commentChildren(commentsView);
        return commentsView;
    }

    private List<Comment> tempReplys = new ArrayList<>();
    private void commentChildren(List<Comment> commentsView) {
        for (Comment comment:commentsView){
            List<Comment> replayComment = comment.getReplayComment();
            for (Comment reply:replayComment){
                recursvely(reply);
            }
            comment.setReplayComment(tempReplys);
            tempReplys = new ArrayList<>();
        }
    }

    private void recursvely(Comment reply) {
        tempReplys.add(reply);
        if (reply.getReplayComment().size()>0){
            List<Comment> replayComment = reply.getReplayComment();
            for (Comment temp:replayComment){
                tempReplys.add(temp);
                if (temp.getReplayComment().size() > 0) recursvely(temp);
            }
        }
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment){
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1){
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteByBlogId(Integer id) {

    }

    @Override
    public void deleteComment(Blog blog) {
        commentRepository.deleteByBlog(blog);
    }
}
