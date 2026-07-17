# BE — Cuoc24h API

Backend Java **Spring Boot 3.5 / Java 21** cho ung dung dat xe Cuoc24h.
Hien thuc theo `docs/04-system-architecture.md`, `docs/05-database-design.md`,
`docs/06-api-spec.md`, `docs/08-zalo-notification.md`.

## Stack

- Spring Boot (Web, Data JPA, Security, Validation)
- MySQL (production) + Flyway migration; H2 in-memory cho dev
- JWT (JJWT) cho admin authentication
- Maven (co san Maven wrapper `./mvnw`)

## Chay Nhanh (khong can cai gi ngoai JDK 21)

Profile mac dinh la `dev`: dung H2 in-memory, khong can MySQL.

```bash
cd BE
./mvnw spring-boot:run        # Windows: mvnw.cmd spring-boot:run
# -> http://localhost:8080
```

Admin mac dinh duoc seed lan chay dau: **admin / cuoc24h** (doi mat khau sau khi dang nhap).
Console H2 (chi dev): http://localhost:8080/h2-console (JDBC URL `jdbc:h2:mem:cuoc24h`, user `sa`).

## Chay Voi MySQL

```bash
export SPRING_PROFILES_ACTIVE=mysql
export SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/cuoc24h?useSSL=false&serverTimezone=Asia/Ho_Chi_Minh"
export SPRING_DATASOURCE_USERNAME=cuoc24h
export SPRING_DATASOURCE_PASSWORD=yourpassword
./mvnw spring-boot:run
```

Flyway se tao schema tu `src/main/resources/db/migration/V1__init_schema.sql`.

## Build & Test

```bash
./mvnw test         # unit + integration test (chay tren H2)
./mvnw package      # tao target/cuoc24h-api-*.jar
java -jar target/cuoc24h-api-0.0.1-SNAPSHOT.jar
```

## Bien Moi Truong

| Bien | Mac dinh | Y nghia |
| --- | --- | --- |
| `SPRING_PROFILES_ACTIVE` | `dev` | `dev` (H2) hoac `mysql` |
| `SPRING_DATASOURCE_URL/USERNAME/PASSWORD` | localhost | Ket noi MySQL |
| `APP_JWT_SECRET` | (dev placeholder) | Khoa ky JWT, >= 32 byte — **bat buoc doi o production** |
| `APP_JWT_EXPIRATION_MINUTES` | `720` | Thoi han access token |
| `APP_ADMIN_USERNAME/PASSWORD/DISPLAY_NAME` | admin/cuoc24h | Tai khoan admin seed lan dau |
| `APP_CORS_ALLOWED_ORIGINS` | `http://localhost:4200` | Origin FE duoc phep (CSV) |
| `ZALO_NOTIFICATION_ENABLED` | `false` | Bat/tat goi Zalo webhook |
| `ZALO_WEBHOOK_URL` / `ZALO_WEBHOOK_SECRET` | — | Endpoint trung gian day tin Zalo |
| `ZALO_RECIPIENT` | Nhom dieu phoi Cuoc24h | Ten nhom nhan thong bao |

Khi `ZALO_NOTIFICATION_ENABLED=false`, moi booking van tao log notification (trang thai `SENT`, no-op)
de kiem thu duoc ma khong can Zalo that.

## API

Response chuan: `{ "data": ..., "message": ... }`. Loi validation tra `400` kem `errors[]`.

### Public (khong can auth)

- `POST /api/bookings` — tao booking (FR-02), tra `publicCode` dang `C24H-YYYYMMDD-0001`.
- `POST /api/drivers/register` — dang ky tai xe (FR-03), trang thai `PENDING`.

### Admin auth

- `POST /api/admin/auth/login` — tra `accessToken` (JWT). Gui kem header `Authorization: Bearer <token>`.
- `GET /api/admin/auth/me`

### Admin booking (yeu cau token)

- `GET /api/admin/bookings?status=&from=&to=&page=&size=`
- `GET /api/admin/bookings/{id}`
- `PATCH /api/admin/bookings/{id}/status` — kiem tra chuyen trang thai hop le, ghi `booking_status_history`.
- `PATCH /api/admin/bookings/{id}/assign-driver` — chi gan tai xe da `APPROVED`.
- `GET /api/admin/bookings/{id}/notifications`

### Admin driver / notification (yeu cau token)

- `GET /api/admin/drivers?status=&page=&size=`
- `GET /api/admin/drivers/{id}`
- `PATCH /api/admin/drivers/{id}/status`
- `POST /api/admin/notifications/{id}/retry`

## Cau Truc Package

```text
com.cuoc24h.api
  booking/        # entity, repo, service, public controller, DTO, status history, event
  driver/         # entity, repo, service, public controller, DTO
  admin/          # AdminBooking/Driver/Notification controllers (/api/admin)
  auth/           # AdminUser, JWT service/filter, AuthController, DTO
  notification/   # NotificationService (AFTER_COMMIT listener), Zalo webhook client, log
  common/         # ApiResponse, PageResponse, ErrorResponse, GlobalExceptionHandler
  config/         # SecurityConfig, CORS/JWT/Admin properties, DataInitializer (seed admin)
```

## Nguyen Tac Da Ap Dung

- Booking duoc luu **truoc**; notification chay sau khi transaction commit (`@TransactionalEventListener`
  `AFTER_COMMIT`) nen loi Zalo khong lam that bai booking.
- Controller mong, business logic o service, DTO cho request/response, repository chi lo persistence.
- Global exception handler — khong tra stack trace ra client.
- Mat khau admin duoc hash bang BCrypt; khong log secret/token.
- `internal_note` (booking) va `admin_note` (driver) khong lo qua API public.
