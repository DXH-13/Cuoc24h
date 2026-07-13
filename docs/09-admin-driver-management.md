# Admin And Driver Management

## Muc Tieu

Admin can co cong cu don gian de quan ly booking va tai xe trong giai doan MVP, trong khi tai xe co trang rieng de dang ky tham gia he thong.

## Admin Features

### Dashboard Tong Quan

Chi so de xuat:

- So booking moi hom nay.
- So booking dang lien he.
- So booking da hoan thanh.
- So tai xe cho duyet.
- So notification Zalo that bai.

### Quan Ly Booking

Chuc nang:

- Xem danh sach booking.
- Filter theo trang thai, ngay tao, so dien thoai.
- Xem chi tiet booking.
- Cap nhat trang thai.
- Gan tai xe neu can.
- Ghi chu noi bo.
- Xem notification log.

### Quan Ly Tai Xe

Chuc nang:

- Xem danh sach tai xe.
- Filter theo trang thai.
- Xem chi tiet ho so.
- Duyet hoac tu choi ho so.
- Chuyen tai xe sang inactive neu tam ngung.
- Ghi chu noi bo.

### Quan Ly Admin

Cho MVP co the khoi tao admin dau tien bang seed data hoac script noi bo.

Can co:

- Dang nhap.
- Dang xuat.
- Doi mat khau o giai doan sau.

## Driver Registration

Trang dang ky tai xe can co:

- Form thong tin ca nhan.
- Form thong tin xe.
- Khu vuc hoat dong.
- Ghi chu them.
- Thong bao "ho so dang cho duyet" sau khi gui.

## Driver Status

| Trang thai | Y nghia |
| --- | --- |
| `PENDING` | Tai xe moi dang ky, cho admin duyet. |
| `APPROVED` | Tai xe da duoc duyet. |
| `REJECTED` | Ho so bi tu choi. |
| `INACTIVE` | Tai xe tam ngung hoat dong. |

## Bao Mat

- Khong public danh sach tai xe day du.
- So dien thoai tai xe chi hien trong admin.
- API cap nhat tai xe phai yeu cau admin token.

