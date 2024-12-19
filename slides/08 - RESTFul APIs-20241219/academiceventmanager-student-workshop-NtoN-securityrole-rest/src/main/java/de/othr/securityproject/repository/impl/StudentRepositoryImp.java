package de.othr.securityproject.repository.impl;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.othr.securityproject.model.Student;
import de.othr.securityproject.repository.StudentRepositoryI;

@Repository
public interface StudentRepositoryImp extends  StudentRepositoryI, CrudRepository<Student, Long>{

}
