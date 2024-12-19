package de.othr.securityproject.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="registration")
public class Registration implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    Workshop workshop;

    @DateTimeFormat(pattern="yyyy-MM-dd")
	LocalDate registeredAt;

    public Registration() {
    	
    	this.registeredAt = LocalDate.now();
    	this.id = (long) -1;
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Workshop getWorkshop() {
		return workshop;
	}

	public LocalDate getRegisteredAt() {
		return registeredAt;
	}

	public void setRegisteredAt(LocalDate registeredAt) {
		this.registeredAt = registeredAt;
	}

	public void setWorkshop(Workshop workshop) {
		this.workshop = workshop;
	}

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}    
	

}
