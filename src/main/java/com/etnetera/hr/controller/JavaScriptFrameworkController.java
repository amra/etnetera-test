package com.etnetera.hr.controller;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger LOG = LoggerFactory.getLogger(JavaScriptFrameworkController.class);

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
		LOG.info("YYYY");
		repository.deleteById(id);
	}

	@PostMapping("/framework/save")
	public void save(@RequestBody JavaScriptFramework framework) {
		//TODO: check if framework doesn't exist
		repository.save(framework);
	}

	@PostMapping("/framework/update")
	public void update(@RequestBody JavaScriptFramework framework) {
		//TODO: check if framework exists
		repository.save(framework);
	}

	@ResponseStatus(value=HttpStatus.CONFLICT, reason="Data integrity violation")
	@ExceptionHandler({ Exception.class})
	public String handleException(RuntimeException ex, WebRequest request) {
		LOG.info("XXXX {}", ex.getMessage());
		return ex.getMessage();
	}

}
