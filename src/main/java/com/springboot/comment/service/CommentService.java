package com.springboot.comment.service;

import com.springboot.comment.entity.Comment;
import com.springboot.comment.mapper.CommentMapper;
import com.springboot.comment.repository.CommentRepository;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper mapper;

    public CommentService(CommentRepository commentRepository,
                          CommentMapper mapper) {
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    public Comment createComment(Comment comment) {
        comment.setCreateAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public Comment updateComment(Comment comment) {
        Comment findComment = findeverifiedComment(comment.getCommentId());

        Optional.ofNullable(comment.getContent())
                .ifPresent(content -> findComment.setContent(content));
        findComment.setModifiedAt(LocalDateTime.now());

        return commentRepository.save(findComment);
    }

    public Comment findComment(long commentId) {
        return findeverifiedComment(commentId);
    }

    public Page<Comment> findComments(int page, int size) {
        return commentRepository.findAll(PageRequest.of(page, size,
                Sort.by("commentId").descending()));
    }


    public void deleteComment(long commentId) {
        Comment findComment = findeverifiedComment(commentId);
        commentRepository.delete(findComment);
    }

    // 댓글이 있는지 확인
    public Comment findeverifiedComment(long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment findComment = optionalComment.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        return findComment;
    }
}
