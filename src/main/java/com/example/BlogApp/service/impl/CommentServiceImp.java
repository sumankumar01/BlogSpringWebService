package com.example.BlogApp.service.impl;

import com.example.BlogApp.entity.Comment;
import com.example.BlogApp.entity.Post;
import com.example.BlogApp.exception.BlogApiException;
import com.example.BlogApp.exception.ResourceNotFoundException;
import com.example.BlogApp.payload.CommentDto;
import com.example.BlogApp.repository.CommentRepository;
import com.example.BlogApp.repository.PostRepository;
import com.example.BlogApp.service.CommentService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImp  implements CommentService {


    private CommentRepository commentRepository;
private PostRepository postRepository;
    public CommentServiceImp(CommentRepository commentRepository,PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository=postRepository;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {

         Comment comment=mapToEntity(commentDto);
        Post post=postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post","id",postId));

        comment.setPost(post);
        Comment newComment=commentRepository.save(comment);
        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentByPostId(Long postId) {

        List<Comment> comments=commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {



        Post post=postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post","id",postId));

        Comment comment=commentRepository.findById((commentId)).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));
        if(!comment.getPost().getId().equals(post.getId()))
        {
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belog to post");

        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        commentRepository.delete(comment);
    }

    private CommentDto mapToDto(Comment comment)
    {
        CommentDto commentDto=new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());


        return commentDto;

    }

    private Comment mapToEntity(CommentDto commentDto)
    {
        Comment comment=new Comment();
       // comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }


}
