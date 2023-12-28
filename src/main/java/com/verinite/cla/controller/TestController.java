package com.verinite.cla.controller;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/auth/test")
public class TestController {

	@GetMapping
	public ResponseEntity<String> get() {
		return ResponseEntity.ok("Test");
	}

	@PostMapping("/execute")
	public String executeJs(@RequestBody String jsCode) {
		try (Context context = Context.create()) {
			Value result = context.eval("js", jsCode);

			Object javaObject = result.as(Object.class);
			if (javaObject != null) {
				return "Result of JavaScript Execution: " + javaObject.toString();
			} else {
				return "Result of JavaScript Execution is null or undefined.";
			}
		}
	}
}
