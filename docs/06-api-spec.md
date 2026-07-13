# API Specification

## Nguyen Tac Chung

- Base path public: `/api`
- Base path admin: `/api/admin`
- Request/response dung JSON.
- Admin API yeu cau authentication token.
- Loi validation tra ve HTTP 400 voi danh sach field loi.

## Response Format De Xuat

Thanh cong:

```json
{
  "data": {},
  "message": "Success"
}
```

Loi:

```json
{
  "message": "Validation failed",
  "errors": [
    {
      "field": "customerPhone",
      "message": "Phone is required"
    }
  ]
}
```

## Public APIs

### Tao Booking

`POST /api/bookings`

Request:

```json
{
  "customerName": "Nguyen Van A",
  "customerPhone": "0900000000",
  "pickupAddress": "Quan 1, TP.HCM",
  "dropoffAddress": "San bay Tan Son Nhat",
  "pickupTime": "2026-07-13T10:30:00",
  "vehicleType": "4_SEATS",
  "note": "Co hanh ly"
}
```

Response:

```json
{
  "data": {
    "publicCode": "C24H-20260713-0001",
    "status": "NEW"
  },
  "message": "Booking created"
}
```

### Dang Ky Tai Xe

`POST /api/drivers/register`

Request:

```json
{
  "fullName": "Tran Van B",
  "phone": "0911111111",
  "operatingArea": "TP.HCM",
  "vehicleType": "7_SEATS",
  "licensePlate": "51A-12345",
  "seatCount": 7,
  "serviceType": "TAXI_SERVICE",
  "note": "Co the chay san bay"
}
```

Response:

```json
{
  "data": {
    "status": "PENDING"
  },
  "message": "Driver registration submitted"
}
```

## Admin Auth APIs

### Dang Nhap

`POST /api/admin/auth/login`

Request:

```json
{
  "username": "admin",
  "password": "secret"
}
```

Response:

```json
{
  "data": {
    "accessToken": "jwt-token",
    "displayName": "Admin"
  },
  "message": "Login successful"
}
```

### Thong Tin Admin Hien Tai

`GET /api/admin/auth/me`

## Admin Booking APIs

### Lay Danh Sach Booking

`GET /api/admin/bookings?status=NEW&from=2026-07-01&to=2026-07-31&page=0&size=20`

### Lay Chi Tiet Booking

`GET /api/admin/bookings/{id}`

### Cap Nhat Trang Thai Booking

`PATCH /api/admin/bookings/{id}/status`

Request:

```json
{
  "status": "CONTACTING",
  "internalNote": "Da goi khach luc 10:45"
}
```

### Gan Tai Xe Cho Booking

`PATCH /api/admin/bookings/{id}/assign-driver`

Request:

```json
{
  "driverId": 123,
  "internalNote": "Tai xe nhan chuyen"
}
```

## Admin Driver APIs

### Lay Danh Sach Tai Xe

`GET /api/admin/drivers?status=PENDING&page=0&size=20`

### Lay Chi Tiet Tai Xe

`GET /api/admin/drivers/{id}`

### Cap Nhat Trang Thai Tai Xe

`PATCH /api/admin/drivers/{id}/status`

Request:

```json
{
  "status": "APPROVED",
  "adminNote": "Da xac minh thong tin"
}
```

## Admin Notification APIs

### Xem Log Notification Theo Booking

`GET /api/admin/bookings/{id}/notifications`

### Gui Lai Notification

`POST /api/admin/notifications/{id}/retry`

