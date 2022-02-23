package es.fpdual.tutorialCrud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.fpdual.tutorialCrud.model.Company;

// @Repository (not needed because extends from JpaRepository)
public interface RepositoryTutorial extends JpaRepository<Company, Long> {

	/* Name Queries
	 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
	*/
	
	List<Company> findByPublished(boolean published);
	
	List<Company> findByTitleContaining(String title);
	
}
