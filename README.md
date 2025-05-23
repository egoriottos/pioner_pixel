# User Management Service

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.1-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-orange)
![Redis](https://img.shields.io/badge/Redis-7-red)
![Elasticsearch](https://img.shields.io/badge/Elasticsearch-7.17-yellow)
![Docker](https://img.shields.io/badge/Docker-✓-blue)
![JWT](https://img.shields.io/badge/JWT-✓-brightgreen)

## 🚀 Технологии

**Основной стек:**
- **Java 17** - Основной язык разработки
- **Spring Boot 3.1** - Бэкенд-фреймворк
  - Spring Security - Аутентификация и авторизация
  - Spring Web
  - Spring Data JPA - Работа с PostgreSQL
  - Spring Data Redis - Кэширование
  - Spring Data Elasticsearch - Полнотекстовый поиск
  - Flyway
  - Swagger
  - modelmapper

**Базы данных:**
- **PostgreSQL 15** - Основное хранилище данных
- **Redis 7** - Кэширование и сессии
- **Elasticsearch 7.17** - Поиск пользователей

**Безопасность:**
- JWT (JSON Web Tokens) - Аутентификация

## 🛠️ Требования для запуска

- **Docker** + **Docker Compose** (версия 3.8+)
- Свободные порты:
  - 8080 (приложение)
  - 5432 (PostgreSQL)
  - 6379 (Redis)
  - 9200 (Elasticsearch)
  - В контроллере /auth/login получаете токен введя почту и пароль и ходите с этим токеном во все ручки 
 
  ## Описание приложения
  - **AuthController** - контроллер для аутентификации, логика в **AuthService**
  - **EmailDataController** - контроллер для работы с почтой(добавление,обновление,удаление), логика в **EmailService**
  - **PhoneDataController** - контроллер для работы с номерами телефонов(добавление,обновление,удаление), логика в **PhoneService**
  - **TransferController** - контроллер для переводов, логика в **TransferService**
  - **UserController** - контроллер для работы с пользователем(поиск), логика в **UserServcie**
  - **JwtService** - сервис содержащий логику выдачи токена, генерации токена, валидации токена, извлечения userID из токена
  - **PercentServcie** - сервис для увеличения баланса пользователя каждые 30 секунд
  - **UserDataSyncService** - сервис для синхронизации индекса в Elastic с БД, чтобы поиск был быстрым и актуальным
  - **JwtAuthFilter** - кастомный фильтр для проверки токена
  - **CustomUserDetailsService** - кастомный UserDetailsService, чтобы загружать пользователя в контекст безопасности не по имени, а по его ID
    Логирование минимальное (присутствует в **AuthController** например)
 
  
