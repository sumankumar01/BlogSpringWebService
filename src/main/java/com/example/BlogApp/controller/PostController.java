package com.example.BlogApp.controller;


import com.example.BlogApp.payload.PostDto;
import com.example.BlogApp.payload.PostResponse;
import com.example.BlogApp.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
@PreAuthorize("hasRole('ADMIN')")
    //creating blockPost
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto)
    {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
   @GetMapping
    public PostResponse getAllPosts(@RequestParam(value="pageNo",defaultValue = "0",required = false) int pageNo,
                                    @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
                                    @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
                                    @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir)
    {
     return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") long id)
    {
        return ResponseEntity.ok(postService.getPostById(id));
    }
    @PreAuthorize("hasRole('ADMIN')")
@PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable(name="id") long id)
    {
        PostDto postResponse=postService.updatePost(postDto,id);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name="id") long id)
    {
        postService.deletePost(id);

        return new ResponseEntity<>("Post data  deleted",HttpStatus.OK);
    }
}
