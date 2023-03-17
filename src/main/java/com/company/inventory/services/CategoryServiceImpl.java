package com.company.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> search() {
		CategoryResponseRest response = new CategoryResponseRest();
		try {
			List<Category> category = (List<Category>) categorydao.findAll();
			response.getCategory().setCategory(category);
			response.setMetadata("Resptesta ok", "00", "Respuesta Exitosa");
		} catch (Exception e) {
			response.setMetadata("Resptesta nok", "-1", "Respuesta Exitosa");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> searchById(Long id) {
		CategoryResponseRest response = new CategoryResponseRest();
		List<Category> list = new ArrayList<>();
		try {
			Optional<Category> category = categorydao.findById(id);
			if (category.isPresent()) {
				list.add(category.get());
				response.getCategory().setCategory(list);
				response.setMetadata("Respuesta ok", "00", "Categoria encontrada");

			} else {
				response.setMetadata("Respuesta nok", "-1", "Categoria no encontrada");

				return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);

			}
		} catch (Exception e) {
			response.setMetadata("Respuesta nok", "-1", "Error al consultar por id");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);

	}

	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> save(Category category) {
		CategoryResponseRest response = new CategoryResponseRest();
		List<Category> list = new ArrayList<>();
		try {
			Category categorySaved = categorydao.save(category);
			if(categorySaved != null) {
				list.add(categorySaved);
				response.getCategory().setCategory(list);
				response.setMetadata("Respuesta ok", "00", "Categoria grabada");
			}
			else {
				response.setMetadata("Respuesta nok", "-1", "Error al grabar la categoria");
				return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			response.setMetadata("Respuesta nok", "-1", "Error al grabar la categoria");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<CategoryResponseRest> update(Category category, Long id) {
		CategoryResponseRest response = new CategoryResponseRest();
		List<Category> list = new ArrayList<>();
		try {
			Optional<Category> categorysearch = categorydao.findById(id);
			if(categorysearch.isPresent()) {
				categorysearch.get().setNombre(category.getNombre());
				categorysearch.get().setDescription(category.getDescription());
				
				Category categoryToUpdate = categorydao.save(categorysearch.get());
				if(categoryToUpdate != null) {
					list.add(categoryToUpdate);
					response.getCategory().setCategory(list);
					response.setMetadata("Respuesta ok", "00", "Categoria Actualizada");
				}
				else {
					response.setMetadata("Respuesta nok", "-1", "Categoria no Actualizada");
					
					return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
				}
			}
			else {
				response.setMetadata("Respuesta nok", "-1", "Categoria no encontrada");
				
				return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			
			
		} catch (Exception e) {
			response.setMetadata("Respuesta nok", "-1", "Error al actualizar la categoria");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	
	}

	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> deleteById(Long id) {
		CategoryResponseRest response = new CategoryResponseRest();
		try {
			categorydao.deleteById(id);
			response.setMetadata("Resptesta ok", "00", "Registro eliminado");
		} catch (Exception e) {
			response.setMetadata("Resptesta nok", "-1", "Error al eliminar");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}

}
