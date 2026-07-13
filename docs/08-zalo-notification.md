# Zalo Notification

## Muc Tieu

Khi khach hang tao booking moi, he thong can gui thong tin tom tat len nhom Zalo de admin/tai xe co the tiep nhan nhanh.

## Nguyen Tac

- Booking phai duoc luu thanh cong truoc khi gui Zalo.
- Loi gui Zalo khong lam that bai booking.
- Moi lan gui can co log de truy vet.
- Token, webhook hoac thong tin tich hop khong duoc hard-code trong source code.

## Noi Dung Thong Bao De Xuat

```text
Cuoc moi: C24H-20260713-0001
Khach: Nguyen Van A - 0900000000
Don: Quan 1, TP.HCM
Den: San bay Tan Son Nhat
Thoi gian: 13/07/2026 10:30
Loai xe: 4 cho
Ghi chu: Co hanh ly
```

## Phuong An Tich Hop

### Phuong An 1: Webhook Trung Gian

Backend goi mot webhook trung gian co nhiem vu day tin sang Zalo.

Uu diem:

- De tach logic tich hop khoi backend chinh.
- Co the thay doi cach gui Zalo ma khong anh huong booking service.

Nhuoc diem:

- Can van hanh them mot endpoint/service.

### Phuong An 2: Zalo Official API Neu Phu Hop

Su dung API chinh thuc neu dap ung duoc nhu cau gui thong bao vao nhom.

Uu diem:

- Chinh thong, on dinh hon.

Nhuoc diem:

- Can kiem tra gioi han API, quyen gui vao nhom va quy trinh xet duyet.

### Phuong An 3: Xu Ly Thu Cong Cho MVP Rat Som

Neu chua co tich hop ky thuat ngay, admin dashboard van hien booking moi va admin copy noi dung sang Zalo.

Uu diem:

- Khong chan tien do MVP.

Nhuoc diem:

- Ton thao tac thu cong, de cham thong tin.

## Notification Status

- `PENDING`: da tao log, chua gui.
- `SENT`: gui thanh cong.
- `FAILED`: gui that bai.

## Retry

Admin nen co chuc nang retry notification that bai trong giai doan sau MVP hoac cuoi MVP.

## Cau Hinh

Bien moi truong de xuat:

```env
ZALO_NOTIFICATION_ENABLED=true
ZALO_WEBHOOK_URL=
ZALO_WEBHOOK_SECRET=
```

Ten bien co the dieu chinh theo phuong an tich hop cuoi cung.

