package com.example.BlogApp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private List<PostDto> content;
    private int PageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private Boolean last;

}
