package com.example.BlogApp.service;

import com.example.BlogApp.payload.PostDto;
import com.example.BlogApp.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);

    PostDto getPostById(long id);
    PostDto updatePost(PostDto postDto,Long id);
    void  deletePost(long id);
}
