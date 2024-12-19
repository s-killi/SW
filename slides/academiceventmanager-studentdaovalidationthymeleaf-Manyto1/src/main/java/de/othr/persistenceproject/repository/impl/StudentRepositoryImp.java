package de.othr.persistenceproject.repository.impl;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.othr.persistenceproject.model.Student;
import de.othr.persistenceproject.repository.StudentRepositoryI;

@Repository
public interface StudentRepositoryImp extends  StudentRepositoryI, CrudRepository<Student, Long>{

}
