![example workflow](https://github.com/icerstop/projekt/actions/workflows/ci.yml/badge.svg)

# JsonTransformer REST API

**JsonTransformer** to aplikacja REST API stworzona w języku Java z wykorzystaniem frameworka Spring Boot. Umożliwia manipulację i analizę danych w formacie JSON, oferując funkcjonalności takie jak minimalizacja, upiększanie, filtrowanie oraz porównywanie danych JSON.

---

## Funkcjonalności

1. **Minimalizacja JSON**  
   Usuwa zbędne białe znaki, aby zredukować rozmiar danych.  
   Endpoint: `POST /json/minify`

2. **Upiększanie JSON**  
   Dodaje wcięcia i formatowanie, aby poprawić czytelność.  
   Endpoint: `POST /json/prettify`

3. **Filtrowanie JSON - Właściwości do zachowania**  
   Zachowuje jedynie wskazane właściwości w danych JSON.  
   Endpoint: `POST /json/filter/include`

4. **Filtrowanie JSON - Właściwości do usunięcia**  
   Usuwa wskazane właściwości z danych JSON.  
   Endpoint: `POST /json/filter/exclude`

5. **Porównywanie JSON**  
   Porównuje dwa obiekty JSON i zwraca różnice.  
   Endpoint: `POST /json/compare`

---

## Wymagania

- **Java**: 11 lub nowsza
- **Maven**: 3.6 lub nowszy
- **Spring Boot**: 2.5+ (wbudowany w projekcie)

---

## Działanie
1. Minify
   POST http://localhost:8080/json/minify
   {
       "name": "Alice",
       "age": 25
   }

2. Prettify
   POST http://localhost:8080/json/minify
   {
       "name": "Alice",
       "age": 25
   }

3. Filter
   POST http://localhost:8080/json/filter/include
   {
       "json": {
           "name": "Alice",
           "age": 25,
           "city": "Poznań"
       },
       "properties": ["name", "city"]
   }

4. Compare
   POST http://localhost:8080/json/compare
   {
       "json1": {"name": "Alice", "age": 25},
       "json2": {"name": "Alice", "age": 30}
   }

Aplikacji można użyć przy pomocy aplikacji Postman
   


UML Class Diagram
![462648830_1917276605467350_4755050984266043955_n](https://github.com/user-attachments/assets/8872756e-77c2-493a-869d-ff6cbeda0522)
