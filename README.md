# Library Management System API

## 📝 O projekcie
Kompleksowy system backendowy typu REST API do zarządzania biblioteką. Projekt został stworzony z myślą o profesjonalnej architekturze, wysokiej czytelności kodu oraz skalowalności. System obsługuje pełen cykl życia wypożyczeń, zarządzanie bazą książek oraz autorów.

## 🚀 Technologie i Narzędzia
*   **Java 17/21**
*   **Spring Boot 3** (Data JPA, Web, Validation)
*   **H2 Database** (Baza in-memory do celów deweloperskich i testowych)
*   **MapStruct** (Automatyczne i wydajne mapowanie obiektów Entity -> DTO)
*   **Lombok** (Redukcja boilerplate code)
*   **Maven** (Zarządzanie zależnościami i cyklem życia projektu)

## ✨ Kluczowe Funkcjonalności
*   **Architektura Warstwowa:** Wyraźny podział na Controller -> Service -> Repository.
*   **Zaawansowane Mapowanie (DTO):** Zastosowanie wzorca Data Transfer Object z podziałem na widoki dla Użytkownika i Administratora (bezpieczeństwo danych).
*   **Relacje JPA:** Obsługa relacji `@ManyToOne` oraz `@OneToMany` pomiędzy książkami, autorami a wypożyczeniami.
*   **Paginacja i Sortowanie:** Wszystkie punkty końcowe zwracające listy obsługują parametr `Pageable` (wydajność przy dużej ilości danych).
*   **Logika Biznesowa:** Automatyczna aktualizacja statusów książek przy wypożyczeniu i zwrocie.
*   **Wyszukiwanie Analityczne:** Możliwość filtrowania danych po autorach, statusach, datach oraz cenach.

## 🛠️ Architektura API (Przykładowe Endpointy)

### Administrator:
* `POST /admin/loans` - Rejestracja nowego wypożyczenia.
* `GET /admin/loans/search/book/type` - Filtrowanie wypożyczeń według gatunku książki.
* `GET /admin/loans/all?page=0&size=5&sort=loanDate,desc` - Paginowana lista wszystkich akcji.

### Użytkownik:
* `GET /books/all` - Przeglądanie dostępnego księgozbioru (widok uproszczony DTO).
* `GET /books/search/author/last-name` - Wyszukiwanie książek po nazwisku autora.

## 🏁 Jak uruchomić projekt?
1. Sklonuj repozytorium: `git clone [URL-Twojego-Repo]`
2. Wejdź do folderu projektu: `cd library-system`
3. Uruchom aplikację za pomocą Maven: `./mvnw spring-boot:run`
4. Aplikacja wystartuje na porcie `8080`.
5. Konsola bazy danych H2 dostępna pod: `http://localhost:8080/h2-console`
   * **JDBC URL:** `jdbc:h2:mem:testdb`
   * **User:** `sa`
   * **Password:** (puste)

## 📁 Struktura Projektu
* `src/main/java/.../model` - Encje bazy danych.
* `src/main/java/.../dto` - Obiekty transferu danych (DTO).
* `src/main/java/.../mapper` - Interfejsy MapStruct.
* `src/main/java/.../service` - Logika biznesowa.
* `src/main/java/.../controller` - Punkty końcowe REST API.
