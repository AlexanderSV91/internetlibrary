package com.faceit.example.internetlibrary.mapper.mysql;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageMapper {

    default <R, T> PageImpl<R> pageEntityToPageResponse(Page<T> page, List<R> list) {
        return new PageImpl<>(list, page.getPageable(), page.getTotalElements());
    }

/*
    default <R, T> PageImpl<R> pageEntityToPageResponse(Page<T> page) {
        return new PageImpl<>(
                entitiesToEntitiesResponse(page.getContent()),
                page.getPageable(),
                page.getTotalElements());
    }

    <R, T> List<R> entitiesToEntitiesResponse(List<T> books);
*/
}
