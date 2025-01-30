# Entity Ordner: Anleitung

## **Was ist eine Entity?**
Eine **Entity** ist eine Klasse, die eine Tabelle in der Datenbank repräsentiert. Sie wird verwendet, um die Datenbankstruktur in einer objektorientierten Sprache wie Java abzubilden. Jede Instanz einer Entity entspricht einem Datensatz in der Tabelle.

---

## **Warum Entities verwenden?**
- **Objekt-Relationale Abbildung (ORM):**
    - Mit Frameworks wie Hibernate oder JPA werden Entities genutzt, um Datenbanken ohne direkten SQL-Code zu verwenden.
- **Strukturierte Datenmodellierung:**
    - Entities definieren klar die Datenstruktur und Beziehungen zwischen Tabellen.
- **Automatische Generierung:**
    - Tabellen können basierend auf den Entity-Klassen automatisch erstellt werden (z. B. durch Hibernate).

---

## **Bestandteile einer Entity**
Eine Entity-Klasse enthält:
1. **Annotationen:**
    - `@Entity`: Markiert die Klasse als Datenbank-Entität.
    - `@Table`: (optional) Gibt den Tabellennamen an.
2. **Felder:**
    - Repräsentieren die Spalten der Datenbanktabelle.
    - Annotationen wie `@Id`, `@GeneratedValue`, `@Column` spezifizieren Details.
3. **Getter und Setter:**
    - Erlauben den Zugriff und die Modifikation der Daten.
4. **Konstruktoren:**
    - Ein leerer Konstruktor ist erforderlich.
5. **Optional: Beziehungen:**
    - `@OneToMany`, `@ManyToOne`, `@OneToOne`, `@ManyToMany` definieren Beziehungen zwischen Tabellen.

---

## **Entity-Ordner Inhalte**

1. **`Citizen`**
    - Repräsentiert einen Bürger in der Datenbank.
    - Felder: `id`, `firstName`, `lastName`, `email`, `password`, etc.

2. **`Application`**
    - Repräsentiert einen Antrag eines Bürgers.
    - Felder: `id`, `citizenId`, `status`, `submissionDate`, etc.

---

## **Best Practices**
- **Konsistenz:**
    - Benenne Klassen und Felder klar und nach Konventionen (z. B. CamelCase).
- **Validation:**
    - Nutze Annotationen wie `@NotNull`, `@Size`, `@Pattern`, um Datenintegrität sicherzustellen.
- **Beziehungen modellieren:**
    - Nutze die richtigen JPA-Annotationen (`@OneToMany`, `@ManyToOne`, etc.), um Datenbankbeziehungen zu definieren.
- **Keine Geschäftslogik:**
    - Entitäten sollten nur Daten enthalten, keine Logik.


---

## **Zusammenfassung**
Entities sind das Rückgrat der Persistenz in Anwendungen mit ORMs wie JPA. Sie stellen eine Verbindung zwischen der Datenbank und der Anwendung her, indem sie Tabellen in objektorientierten Klassen abbilden. Entwirf sie sorgfältig, um eine saubere und effiziente Datenstruktur zu gewährleisten.

