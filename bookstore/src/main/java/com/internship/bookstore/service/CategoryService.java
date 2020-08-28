package com.internship.bookstore.service;


import com.internship.bookstore.dto.AddCategoryDTO;
import com.internship.bookstore.dto.CategoryDTO;
import com.internship.bookstore.dto.CategoryListDTO;

public interface CategoryService {

	CategoryListDTO findAllCategories();

	CategoryDTO getOne(Long id);

	CategoryDTO addCategory(AddCategoryDTO addCategoryDTO);

	Boolean updateCategory(AddCategoryDTO addCategoryDTO, Long id);

	Boolean deleteCategory(Long id);

}
