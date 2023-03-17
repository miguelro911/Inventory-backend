package com.company.inventory.services;
import org.springframework.http.ResponseEntity;

import com.company.inventory.model.Category;
import com.company.inventory.response.*;

public interface ICategoryService {
	public ResponseEntity<CategoryResponseRest> search();
	public ResponseEntity<CategoryResponseRest> searchById(Long id);
	public ResponseEntity<CategoryResponseRest> save(Category category);
	
	
}
