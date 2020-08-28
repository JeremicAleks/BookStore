package com.internship.bookstore.serviceImpl;

import com.internship.bookstore.dto.AddCategoryDTO;
import com.internship.bookstore.dto.CategoryDTO;
import com.internship.bookstore.dto.CategoryListDTO;
import com.internship.bookstore.exception.StoreException;
import com.internship.bookstore.mapper.BookMapper;
import com.internship.bookstore.mapper.CategoryMapper;
import com.internship.bookstore.model.Book;
import com.internship.bookstore.model.Category;
import com.internship.bookstore.repository.BookRepository;
import com.internship.bookstore.repository.CategoryRepository;
import com.internship.bookstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	BookRepository bookRepository;
	@Autowired
	CategoryMapper categoryMapper;
	@Autowired
	BookMapper bookMapper;

	@Override
	public CategoryListDTO findAllCategories() {
		CategoryListDTO categoryListDTO = new CategoryListDTO();
		List<Category> categories = categoryRepository.findAll();
		if (!categories.isEmpty()) {
			for (Category category : categories) {
				if (!category.isDeleted())
					categoryListDTO.getCategories().add(categoryMapper.map(category));
			}
		} else
			throw new StoreException(HttpStatus.NOT_FOUND, "Category doesn't exist!");
		return categoryListDTO;
	}

	@Override
	public CategoryDTO getOne(Long id) {
		return categoryMapper.map(categoryRepository.getOne(id));
	}

	@Override
	public CategoryDTO addCategory(AddCategoryDTO addCategoryDTO) {
		Category category = new Category();
		category.setName(addCategoryDTO.getName());
		category.setDeleted(addCategoryDTO.getIsDeleted());
		category = categoryRepository.save(category);
		return categoryMapper.map(category);
	}

	@Override
	public Boolean updateCategory(AddCategoryDTO addCategoryDTO, Long id) {
		Category category = categoryRepository.getOne(id);
		if (category == null)
			throw new StoreException(HttpStatus.NOT_FOUND, "Category doesn't exist!");

		category.setName(addCategoryDTO.getName());
		category.setDeleted(addCategoryDTO.getIsDeleted());
		categoryRepository.save(category);
		return true;
	}

	@Override
	public Boolean deleteCategory(Long id) {
		Category category = categoryRepository.getOne(id);
		if (category == null)
			throw new StoreException(HttpStatus.NOT_FOUND, "Category doesn't exist!");

		Set<Book> books = category.getBooks();
		if (!books.isEmpty()) {
			throw new StoreException(HttpStatus.BAD_REQUEST, "You need to delete books with this category first.");
		}

		category.setDeleted(true);
		return true;
	}
	
}
