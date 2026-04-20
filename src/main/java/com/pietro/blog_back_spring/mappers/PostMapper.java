package com.pietro.blog_back_spring.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.pietro.blog_back_spring.dtos.PostDto;
import com.pietro.blog_back_spring.entities.Post;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    
    Post toPost(PostDto postDto);

}
