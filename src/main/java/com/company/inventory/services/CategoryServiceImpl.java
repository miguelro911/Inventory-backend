package com.company.inventory.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.inventory.model.*;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.response.CategoryResponseRest;
@Service
public class CategoryServiceImpl implements ICategoryService {
	@Autowired
	private ICategoryDao categorydao;

	@Override
	@Transactional(readOnly=true)
	public ResponseEntity<CategoryResponseRest> search() {
		CategoryResponseRest response = new CategoryResponseRest();
		try {
			List<Category> category = (List<Category>) categorydao.findAll();
			response.getCategory().setCategory(category);
			response.setMetadata("Resptesta ok", "00", "Respuesta Exitosa");
		} catch(Exception e){
			response.setMetadata("Resptesta nok", "-1", "Respuesta Exitosa");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.OK);
	}

}
