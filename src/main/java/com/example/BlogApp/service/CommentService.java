package com.example.BlogApp.service;

import com.example.BlogApp.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(Long postId, CommentDto commentDto);
    List<CommentDto> getCommentByPostId(Long postId);
    CommentDto getCommentById(Long postId,Long commentId);
    CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest);

    void deleteComment(Long postId, Long commentId);
}
