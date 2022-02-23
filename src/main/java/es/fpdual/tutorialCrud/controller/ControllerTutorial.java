package es.fpdual.tutorialCrud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.fpdual.tutorialCrud.model.Company;
import es.fpdual.tutorialCrud.service.ServiceTutorial;
import es.fpdual.tutorialCrud.service.exception.ValidateExceptionTutorial;

@RestController
@RequestMapping("/api/v1")
public class ControllerTutorial {

	@Autowired
	private ServiceTutorial tutorialService;

	@GetMapping("/tutorials")
	public ResponseEntity<?> getAllTutorial(@RequestParam(required = false) String title) {
		try {
			List<Company> companies = new ArrayList<Company>();
			if (title == null) {
				this.tutorialService.readAllTutorials().forEach(companies::add);
			} else {
				this.tutorialService.readTutorialsByTitle(title).forEach(companies::add);
			}

			if (companies.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(companies, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/tutorials/{id}")
	public ResponseEntity<?> getTutorialById(@PathVariable("id") long id) {
		Company company = tutorialService.readTutorialById(id);

		if (company == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(company, HttpStatus.OK);
	}

	@PostMapping("/tutorials")
	public ResponseEntity<?> createTutorial(@RequestBody Company company) {
		Map<Integer, Object> body = null;
		HttpStatus status = HttpStatus.OK;

		try {
			this.tutorialService.saveTutorial(company);
		} catch (ValidateExceptionTutorial e) {
			e.printStackTrace();
			status = HttpStatus.PRECONDITION_FAILED;
			body = new HashMap<Integer, Object>();
			body.put(0, e.getErrors());
		}

		return new ResponseEntity<>(body, status);
	}

	@PutMapping("/tutorials/{id}")
	public ResponseEntity<?> updateTutorialById(@PathVariable long id, @RequestBody Company newTutorial) {
		Map<Integer, Object> body = null;
		HttpStatus status = HttpStatus.OK;
		
		try {
			this.tutorialService.updateTutorial(id, newTutorial);
		} catch (ValidateExceptionTutorial e) {
			e.printStackTrace();
			status = HttpStatus.PRECONDITION_FAILED;
			body = new HashMap<Integer, Object>();
			body.put(0, e.getErrors());
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(body, status);
	}
	
	@DeleteMapping("/tutorials/{id}")
	public ResponseEntity<?> deleteTutorialById(@PathVariable long id) {
		
		Company company = this.tutorialService.readTutorialById(id);
		
		if (company == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		this.tutorialService.deleteTutorial(id);
		
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
}
