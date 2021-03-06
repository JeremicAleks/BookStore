package com.internship.bookstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CategoryListDTO {
    List<CategoryDTO> categories;

    public CategoryListDTO(){
        this.categories = new ArrayList<>();
    }

    public CategoryListDTO(List<CategoryDTO> categories) {
        this.categories = categories;
    }
}
