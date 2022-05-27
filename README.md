# ReserveAPoolTable Application

Celem projektu jest stworzenie aplikacji webowej służącej do rezerwacji stołów przeznaczonej dla klubów bilardowych


## W projekcie zostały wykorzystane następujące technologie:

* Spring Boot,
* Spring Security,
* Hibernate,
* SQL,
* JSP

## W projekcie zostały zaprojektowane następujące finkcjonalności:
### Od strony użytkownika:
* rejestracja użytkownika,
* logowanie,
* możliwość reserwacji dla niezarejestrowanego użytkownika
* zniżka dla stałych klientów,

### Od strony dokonywania rezerwacji:
* sprawdzenie dostępnych godzin i stołów dla wybranego dnia,
* rezerwacja dostępnych terminów,
* możliwość płatności online lub na miejscu,

### Od strony administratora:
* dodawanie dostępnych rezerwacji dla wskazanego terminu, z uwzględnieniem aktualnie usawionych cen i dni świątecznych-pracujących,
* podgląd i edycja ceny rezerwacji za godzinę w zależności od grupy cenowej,
* podgląd i edycja dni świątecznych, w które klub jest otwarty,
