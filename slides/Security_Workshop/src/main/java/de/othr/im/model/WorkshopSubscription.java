package de.othr.im.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinColumn;

@Entity
@Table(name="workshopsubscription")
public class WorkshopSubscription {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne
    @JoinColumn(name = "idstudent")
    Student student;
	
	@NotNull (message = "one workshop must be defined!")
	@ManyToOne
    @JoinColumn(name = "idworkshop")
	Workshop workshop;
	

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Workshop getWorkshop() {
		return workshop;
	}

	public void setWorkshop(Workshop workshop) {
		this.workshop = workshop;
	}
	
	
	
}
