# User Flows

## Customer Booking Flow

1. Khach hang truy cap website.
2. Khach hang xem thong tin dich vu va bam nut dat xe.
3. Khach hang nhap thong tin chuyen di.
4. Frontend validate du lieu bat buoc.
5. Backend tao booking voi trang thai `NEW`.
6. Backend gui thong bao len nhom Zalo.
7. Khach hang thay man hinh xac nhan.
8. Admin/tai xe lien he khach hang de dieu phoi.

## Driver Registration Flow

1. Tai xe truy cap trang dang ky tai xe.
2. Tai xe nhap thong tin ca nhan va thong tin xe.
3. Backend tao driver profile voi trang thai `PENDING`.
4. Admin xem ho so trong dashboard.
5. Admin duyet hoac tu choi.
6. Tai xe duoc them vao danh sach co the dieu phoi neu duoc duyet.

## Admin Booking Management Flow

1. Admin dang nhap.
2. Admin xem danh sach chuyen moi.
3. Admin mo chi tiet chuyen.
4. Admin lien he khach hoac phan cong tai xe theo quy trinh thu cong.
5. Admin cap nhat trang thai chuyen.
6. He thong luu lich su cap nhat co ban.

## Admin Driver Management Flow

1. Admin dang nhap.
2. Admin xem danh sach tai xe.
3. Admin filter theo trang thai `PENDING`, `APPROVED`, `REJECTED`, `INACTIVE`.
4. Admin xem chi tiet ho so tai xe.
5. Admin cap nhat trang thai, ghi chu noi bo.

## Error Flow: Zalo Notification Failed

1. Khach hang gui form dat chuyen.
2. Backend luu booking thanh cong.
3. Backend goi kenh thong bao Zalo nhung that bai.
4. He thong ghi log loi va danh dau notification status la `FAILED`.
5. Khach hang van nhan thong bao dat chuyen thanh cong.
6. Admin co the kiem tra booking trong dashboard.

