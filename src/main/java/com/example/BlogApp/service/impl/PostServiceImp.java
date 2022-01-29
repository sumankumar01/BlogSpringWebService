package com.example.BlogApp.service.impl;

import com.example.BlogApp.entity.Post;
import com.example.BlogApp.exception.ResourceNotFoundException;
import com.example.BlogApp.payload.PostDto;
import com.example.BlogApp.payload.PostResponse;
import com.example.BlogApp.repository.PostRepository;
import com.example.BlogApp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImp implements PostService {

   private PostRepository postRepository;

   private ModelMapper mapper;

    public PostServiceImp(PostRepository postRepository,ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper=mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        /*Post post=new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());*/

        Post post=mapToEntity(postDto);

        Post newPost=postRepository.save(post);

        PostDto postResponse=mapToDTO(newPost);

        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) {

        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending()
                 :Sort.by(sortBy).descending();
        //Pageable pageable= PageRequest.of(pageNo,pageSize);
       // Pageable pageable= PageRequest.of(pageNo,pageSize, Sort.by(sortBy));
        Pageable pageable= PageRequest.of(pageNo,pageSize, sort);
      // List<Post> posts= postRepository.findAll();
        Page<Post> posts= postRepository.findAll(pageable);
        List<Post> listOfPosts=posts.getContent();

      // return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
       // return listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        List<PostDto> content=listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {

        Post post= postRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Post","id",id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {

        Post post= postRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Post","id",id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatePost=postRepository.save(post);

        return mapToDTO(updatePost);
    }

    @Override
    public void deletePost(long id) {

        Post post= postRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);


    }

    //convert entity to DTO
    private PostDto mapToDTO(Post post)
    {

        PostDto postDto=mapper.map(post,PostDto.class);
       /* PostDto postDto=new PostDto();
       postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());*/

        return postDto;
    }
    //convert entity to DTO
    private Post mapToEntity(PostDto postDto)
    {

        Post post=mapper.map(postDto,Post.class);
 /*Post post=new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());*/

        return post;
    }
}
