# BE

Thu muc backend cho ung dung Java Spring Boot cua Cuoc24h.

## Cau Truc

```text
BE/
  src/
    main/
      java/com/cuoc24h/api/
        admin/          # Admin APIs va dashboard operations
        auth/           # Authentication, token, password handling
        booking/        # Booking APIs va business logic
        common/         # Shared errors, response models, utilities
        config/         # Spring configuration
        driver/         # Driver registration va management
        notification/   # Zalo/webhook notification
      resources/
        db/migration/   # Database migrations
        static/         # Static resources neu can
        templates/      # Server-side templates neu can
    test/
      java/com/cuoc24h/api/
      resources/
```

## Stack Du Kien

- Java Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- MySQL
- Flyway hoac Liquibase cho migration

