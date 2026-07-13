# System Architecture

## Tong Quan

Cuoc24h duoc thiet ke theo mo hinh web application truyen thong:

- Angular frontend phuc vu khach hang, tai xe va admin.
- Spring Boot backend xu ly API, validation, authentication va business logic.
- MySQL luu tru booking, driver, admin user va notification log.
- Zalo notification service gui thong tin chuyen di moi len nhom Zalo.

## Thanh Phan Chinh

### Frontend

Cong nghe:

- Angular
- Tailwind CSS
- Angular Router
- Reactive Forms

Module de xuat:

- `public`: home page, booking form, driver registration.
- `admin`: login, dashboard, booking management, driver management.
- `shared`: UI components, validators, API client helpers.

### Backend

Cong nghe:

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- MySQL Driver

Module/package de xuat:

- `booking`: tao va quan ly chuyen di.
- `driver`: dang ky va quan ly tai xe.
- `admin`: authentication va admin operations.
- `notification`: gui thong bao Zalo va log ket qua.
- `common`: exception handling, response format, validation helpers.

### Database

Database MySQL luu cac nhom du lieu:

- Booking/request cua khach.
- Driver profile.
- Admin account.
- Notification log.
- Audit/update history co ban.

### Zalo Notification

Backend goi lop `NotificationService` sau khi booking duoc tao. Viec gui thong bao nen tach khoi core booking flow de tranh truong hop loi Zalo lam mat booking.

## Luong Xu Ly Dat Chuyen

1. Angular gui `POST /api/bookings`.
2. Spring Boot validate request.
3. Backend luu booking voi trang thai `NEW`.
4. Backend tao notification log voi trang thai `PENDING`.
5. Backend gui thong bao Zalo.
6. Backend cap nhat notification log thanh `SENT` hoac `FAILED`.
7. API tra ve booking reference cho frontend.

## Nguyen Tac Thiet Ke

- Booking la du lieu quan trong nhat, phai duoc luu truoc khi gui notification.
- Admin API can authentication.
- Public API can rate limit/captcha o giai doan sau neu spam tang.
- Khong de frontend truy cap truc tiep database hay thong tin token Zalo.
- Tach ro public customer flow va admin flow.

## Cau Hinh Moi Truong

Bien moi truong de xuat:

```env
SPRING_DATASOURCE_URL=
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=
JWT_SECRET=
ZALO_NOTIFICATION_ENABLED=
ZALO_WEBHOOK_URL=
```

Ten bien Zalo co the thay doi tuy giai phap tich hop thuc te.

