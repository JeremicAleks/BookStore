package com.internship.bookstore.mapper;

import com.internship.bookstore.dto.CategoryDTO;
import com.internship.bookstore.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO map(Category category);
}
