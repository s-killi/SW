package de.othr.easytownhall.models.entities;

//Model im Backend auch im Frontend zur Verfügung stellen:

//In Angular dann:
//export interface TestModel{
//    id: string;
//    name: string;
//    age: number;
// einen typ kann man mit '?' nullable, also Feld muss nicht gesetzt sein, machen: street?: string;
//}
import jakarta.persistence.*;

@Entity
@Table(name="TestUsers")
public class TestUserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;

    public TestUserModel() {
    }

    public TestUserModel( String name, int age) {
        this.name = name;
        this.age = age;
    }

    //IMMER GETTER ERSTELLEN. SPRINGBOOT HOLT SICH SO DIE DATEN VOM OBJEKT
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
