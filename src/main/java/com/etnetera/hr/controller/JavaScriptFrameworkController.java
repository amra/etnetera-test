package com.etnetera.hr.controller;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

/**
 * Simple REST controller for accessing application logic.
 *  
 * @author Etnetera
 *
 * Consider: user rights check
 */
@RestController
public class JavaScriptFrameworkController {

	private final JavaScriptFrameworkRepository repository;

	@Autowired
	public JavaScriptFrameworkController(JavaScriptFrameworkRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/framework/list")
	public Iterable<JavaScriptFramework> frameworks() {
		return repository.findAll();
	}

	@GetMapping("/framework/show/{id}")
	public Optional<JavaScriptFramework> show(@PathVariable(value = "id") Long id) {
		return repository.findById(id);
	}

	@GetMapping("/framework/delete/{id}")
	public void delete(@PathVariable(value = "id") Long id) {
		repository.deleteById(id);
	}

	@PostMapping("/framework/save")
	public void save(@RequestBody JavaScriptFramework framework) {
		repository.save(framework);
	}

	@PostMapping("/framework/update")
	public void update(@RequestBody JavaScriptFramework framework) {
		repository.save(framework);
	}

	@ResponseStatus(value=HttpStatus.CONFLICT, reason="Data integrity violation")
	@ExceptionHandler({ Exception.class})
	public String handleException(RuntimeException ex, WebRequest request) {
		return ex.getMessage();
	}

}
