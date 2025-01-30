# DTO Ordner: Anleitung

## **Was ist ein DTO?**
DTO (**Data Transfer Object**) ist eine Datenstruktur, die Daten sicher und effizient zwischen Schichten (z. B. Service, Controller) oder Systemen überträgt. Sie abstrahiert interne Datenbank-Entitäten und reduziert die übertragenen Daten auf das Nötigste.

---

## **Warum DTOs?**
- **Sicherheit:** Verhindert, dass sensible Informationen (z. B. Passwörter) übertragen werden.
- **Effizienz:** Überträgt nur relevante Daten.
- **Entkopplung:** Trennt interne Datenstruktur von der externen Darstellung.
- **Flexibilität:** Erlaubt unterschiedliche Datenformate je nach Anwendungsfall.

---

## **DTO-Ordner Inhalte**

1. **`CitizenDTO`**
    - Felder: `firstName`, `lastName`, `email`, etc.
    - Verwendung: Vollständige Bürgerinformationen.

2. **`CitizenSummaryDTO`**
    - Felder: `id`, `firstName`, `email`.
    - Verwendung: Kompakte Daten für Listenansichten.

---

## **Best Practices**
- **Service-Schicht:** Verantwortlich für die Konvertierung von Entitäten zu DTOs.
- **Keine direkten Entitäten:** Gib keine Datenbank-Entitäten im Controller zurück.
- **Mapper verwenden:** Automatisiere Konvertierung (z. B. mit MapStruct).
- **DTOs anpassen:** Erstelle spezifische DTOs für jeden Anwendungsfall.


---

## **Zusammenfassung**
DTOs trennen interne Daten von externen Schnittstellen, erhöhen Sicherheit und Effizienz. Nutze sie, um modular und flexibel zu arbeiten.

