package es.fpdual.tutorialCrud.service;

import java.util.List;

import es.fpdual.tutorialCrud.model.Company;
import es.fpdual.tutorialCrud.service.exception.ValidateExceptionTutorial;

public interface ServiceTutorial {

	void saveTutorial(Company company) throws ValidateExceptionTutorial;

	List<Company> readAllTutorials();
	
	Company readTutorialById(Long id);
	
	void updateTutorial(Long id, Company company) throws ValidateExceptionTutorial;
	
	void deleteTutorial(Long id);
	
	void deleteAllTutorials();
	
	List<Company> readTutorialsByPublished(Boolean published);
	
	List<Company> readTutorialsByTitle(String title);

}
