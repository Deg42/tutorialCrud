package es.fpdual.tutorialCrud.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.fpdual.tutorialCrud.model.Company;
import es.fpdual.tutorialCrud.repository.RepositoryTutorial;
import es.fpdual.tutorialCrud.service.exception.ValidateExceptionTutorial;

@Service
public class ServiceTutorialImpl implements ServiceTutorial {

	RepositoryTutorial tutorialRepository = null;

	@Autowired
	public ServiceTutorialImpl(RepositoryTutorial tutorialRepository) {
		this.tutorialRepository = tutorialRepository;
	}

	@Override
	public void saveTutorial(Company company) throws ValidateExceptionTutorial {
		Map<Integer, String> errors = this.validate(company);

		Company tutorialToSave = new Company(company.getTitle(), company.getDescription(), company.isPublished());

		if (!errors.isEmpty()) {
			throw new ValidateExceptionTutorial(errors);
		}

		this.tutorialRepository.saveAndFlush(tutorialToSave);
	}

	@Override
	public List<Company> readAllTutorials() {
		return this.tutorialRepository.findAll();
	}

	@Override
	public Company readTutorialById(Long id) {
		return this.tutorialRepository.findById(id).orElse(null);
		
	}

	@Override
	public void updateTutorial(Long id, Company newTutorial) {
		Map<Integer, String> errors = this.validate(newTutorial);
					
		Optional<Company> oldTutorial = this.tutorialRepository.findById(id);

		if (oldTutorial.isPresent() && errors.isEmpty()) {
			Company oldPresentTutorial = oldTutorial.get();
			oldPresentTutorial.setTitle(newTutorial.getTitle());
			oldPresentTutorial.setDescription(newTutorial.getDescription());
			oldPresentTutorial.setPublished(newTutorial.isPublished());
			
			this.tutorialRepository.saveAndFlush(oldPresentTutorial);
		}

	}

	@Override
	public void deleteTutorial(Long id) {
		this.tutorialRepository.deleteById(id);
	}

	@Override
	public void deleteAllTutorials() {
		this.tutorialRepository.deleteAll();
	}

	@Override
	public List<Company> readTutorialsByPublished(Boolean published) {
		return this.tutorialRepository.findByPublished(true);
	}

	@Override
	public List<Company> readTutorialsByTitle(String title) {
		return this.tutorialRepository.findByTitleContaining(title);
	}

	public Map<Integer, String> validate(Company company) {
		Map<Integer, String> result = new HashMap<Integer, String>();

		if (company.getTitle() == null || company.getTitle().isEmpty()) {
			result.put(1, "Name is empty");
		}
		if (company.getDescription() == null || company.getDescription().isEmpty()) {
			result.put(2, "Description is empty");
		}

		return result;
	}

}
