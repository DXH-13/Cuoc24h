# Database Design

## Nguyen Tac

- Dung MySQL cho MVP.
- Uu tien schema don gian, de truy van va de mo rong.
- Dung enum/status ro rang cho booking va driver.
- Luu timestamp `created_at`, `updated_at` cho cac bang chinh.

## Bang `bookings`

Luu thong tin yeu cau chuyen di cua khach hang.

| Cot | Kieu du lieu | Mo ta |
| --- | --- | --- |
| id | BIGINT PK | ID noi bo. |
| public_code | VARCHAR(32) | Ma tham chieu hien cho khach/admin. |
| customer_name | VARCHAR(120) | Ten khach hang. |
| customer_phone | VARCHAR(30) | So dien thoai khach. |
| pickup_address | VARCHAR(255) | Diem don. |
| dropoff_address | VARCHAR(255) | Diem den. |
| pickup_time | DATETIME | Thoi gian don mong muon. |
| vehicle_type | VARCHAR(50) | Loai xe mong muon. |
| note | TEXT | Ghi chu cua khach. |
| internal_note | TEXT | Ghi chu noi bo cua admin. |
| status | VARCHAR(30) | `NEW`, `CONTACTING`, `ASSIGNED`, `COMPLETED`, `CANCELLED`. |
| assigned_driver_id | BIGINT NULL | Tai xe duoc gan neu co. |
| created_at | DATETIME | Thoi diem tao. |
| updated_at | DATETIME | Thoi diem cap nhat. |

## Bang `drivers`

Luu ho so tai xe.

| Cot | Kieu du lieu | Mo ta |
| --- | --- | --- |
| id | BIGINT PK | ID noi bo. |
| full_name | VARCHAR(120) | Ho ten tai xe. |
| phone | VARCHAR(30) | So dien thoai. |
| operating_area | VARCHAR(255) | Khu vuc hoat dong. |
| vehicle_type | VARCHAR(50) | Loai xe. |
| license_plate | VARCHAR(30) | Bien so xe. |
| seat_count | INT | So ghe. |
| service_type | VARCHAR(80) | Hinh thuc chay xe. |
| note | TEXT | Ghi chu tai xe gui. |
| admin_note | TEXT | Ghi chu noi bo cua admin. |
| status | VARCHAR(30) | `PENDING`, `APPROVED`, `REJECTED`, `INACTIVE`. |
| created_at | DATETIME | Thoi diem tao. |
| updated_at | DATETIME | Thoi diem cap nhat. |

## Bang `admin_users`

Luu tai khoan admin.

| Cot | Kieu du lieu | Mo ta |
| --- | --- | --- |
| id | BIGINT PK | ID noi bo. |
| username | VARCHAR(80) | Ten dang nhap. |
| password_hash | VARCHAR(255) | Mat khau da hash. |
| display_name | VARCHAR(120) | Ten hien thi. |
| role | VARCHAR(30) | Vai tro admin. |
| active | BOOLEAN | Trang thai tai khoan. |
| created_at | DATETIME | Thoi diem tao. |
| updated_at | DATETIME | Thoi diem cap nhat. |

## Bang `notification_logs`

Luu ket qua gui thong bao.

| Cot | Kieu du lieu | Mo ta |
| --- | --- | --- |
| id | BIGINT PK | ID noi bo. |
| booking_id | BIGINT | Booking lien quan. |
| channel | VARCHAR(30) | Vi du: `ZALO`. |
| recipient | VARCHAR(255) | Nhom/endpoint nhan thong bao. |
| message_preview | TEXT | Noi dung tom tat. |
| status | VARCHAR(30) | `PENDING`, `SENT`, `FAILED`. |
| error_message | TEXT | Loi neu gui that bai. |
| sent_at | DATETIME NULL | Thoi diem gui thanh cong. |
| created_at | DATETIME | Thoi diem tao. |

## Bang `booking_status_history`

Luu lich su cap nhat trang thai chuyen di.

| Cot | Kieu du lieu | Mo ta |
| --- | --- | --- |
| id | BIGINT PK | ID noi bo. |
| booking_id | BIGINT | Booking lien quan. |
| old_status | VARCHAR(30) | Trang thai cu. |
| new_status | VARCHAR(30) | Trang thai moi. |
| note | TEXT | Ghi chu khi cap nhat. |
| changed_by_admin_id | BIGINT NULL | Admin thuc hien. |
| created_at | DATETIME | Thoi diem cap nhat. |

## Index De Xuat

- `bookings(status, created_at)`
- `bookings(customer_phone)`
- `drivers(status, created_at)`
- `drivers(phone)`
- `notification_logs(booking_id, channel)`

