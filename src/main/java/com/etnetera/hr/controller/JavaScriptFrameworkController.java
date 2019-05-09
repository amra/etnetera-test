package com.etnetera.hr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;

import java.util.Optional;

/**
 * Simple REST controller for accessing application logic.
 *  
 * @author Etnetera
 *
 */
@RestController
public class JavaScriptFrameworkController {

	private final JavaScriptFrameworkRepository repository;

	@Autowired
	public JavaScriptFrameworkController(JavaScriptFrameworkRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/frameworks")
	public Iterable<JavaScriptFramework> frameworks() {
		return repository.findAll();
	}

	@PostMapping("/framework/save")
	public void save(JavaScriptFramework framework) {
		repository.save(framework);
	}

	@GetMapping("/framework/show")
	public Optional<JavaScriptFramework> show(Long id) {
		return repository.findById(id);
	}

	@PostMapping("/framework/update")
	public void update(JavaScriptFramework framework) {
		repository.save(framework);
	}

	@GetMapping("/framework/delete")
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
