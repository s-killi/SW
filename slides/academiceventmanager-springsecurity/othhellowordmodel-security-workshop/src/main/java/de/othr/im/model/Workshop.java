package de.othr.im.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="workshop")
public class Workshop {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
			
	private String title;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@javax.validation.constraints.NotNull (message="Please, inform a valid date!")
    LocalDate date;

	@ManyToOne
    @JoinColumn(name = "idacademicevent")
	AcademicEvent academicevent;
	
	@ManyToOne
    @JoinColumn(name = "idprofessor")
	Professor professor;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@OneToMany(mappedBy = "workshop")
	Set<WorkshopSubscription> workshopSubscriptions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Set<WorkshopSubscription> getWorkshopSubscriptions() {
		return workshopSubscriptions;
	}

	public void setWorkshopSubscriptions(Set<WorkshopSubscription> workshopSubscriptions) {
		this.workshopSubscriptions = workshopSubscriptions;
	}

	public AcademicEvent getAcademicevent() {
		return academicevent;
	}

	public void setAcademicevent(AcademicEvent academicevent) {
		this.academicevent = academicevent;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	
}
