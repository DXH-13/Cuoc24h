# Product Requirements

## Muc Tieu

Xay dung MVP cho website Cuoc24h, tap trung vao ba luong chinh:

- Khach hang dat xe nhanh.
- Tai xe dang ky tham gia he thong.
- Admin quan ly va nhan thong bao chuyen di qua Zalo.

## Vai Tro Nguoi Dung

| Vai tro | Mo ta |
| --- | --- |
| Guest/Customer | Khach hang khong can dang ky, co the tao yeu cau chuyen di. |
| Driver | Tai xe dang ky thong tin, cho admin duyet. |
| Admin | Nguoi quan ly he thong, tai xe va chuyen di. |

## Yeu Cau Chuc Nang

### FR-01: Trang Gioi Thieu Dich Vu

Khach hang co the xem thong tin ve dich vu, loi ich, khu vuc ho tro va nut dat xe.

Tieu chi chap nhan:

- Co CTA dat xe noi bat tren mobile.
- Co thong tin lien he nhanh.
- Co noi dung tao niem tin: khu vuc phuc vu, loai xe, quy trinh dat xe.

### FR-02: Form Dat Chuyen

Khach hang co the gui thong tin chuyen di.

Du lieu toi thieu:

- Ten khach hang
- So dien thoai
- Diem don
- Diem den
- Thoi gian don
- Loai xe mong muon
- Ghi chu

Tieu chi chap nhan:

- Form validate cac truong bat buoc.
- Tao record chuyen di trong database.
- Gui thong bao len Zalo sau khi tao thanh cong.
- Hien thi man hinh cam on/xac nhan cho khach.

### FR-03: Dang Ky Tai Xe

Tai xe co the gui thong tin dang ky tham gia.

Du lieu toi thieu:

- Ho ten
- So dien thoai
- Khu vuc hoat dong
- Loai xe
- Bien so xe
- So ghe
- Hinh thuc chay xe
- Ghi chu

Tieu chi chap nhan:

- Tai xe dang ky voi trang thai `PENDING`.
- Admin co the xem danh sach tai xe dang cho duyet.
- Admin co the duyet hoac tu choi ho so.

### FR-04: Admin Authentication

Admin co the dang nhap vao trang quan tri.

Tieu chi chap nhan:

- API admin duoc bao ve bang authentication.
- Mat khau duoc hash, khong luu plain text.
- Frontend luu token theo cach phu hop va co logout.

### FR-05: Quan Ly Chuyen Di

Admin co the xem va cap nhat trang thai chuyen di.

Trang thai de xuat:

- `NEW`
- `CONTACTING`
- `ASSIGNED`
- `COMPLETED`
- `CANCELLED`

Tieu chi chap nhan:

- Danh sach co filter theo trang thai va ngay tao.
- Admin xem chi tiet mot chuyen.
- Admin cap nhat trang thai va ghi chu noi bo.

### FR-06: Thong Bao Zalo

Khi co chuyen moi, he thong gui noi dung tom tat len nhom Zalo.

Tieu chi chap nhan:

- Noi dung thong bao co ten, so dien thoai, diem don, diem den, thoi gian, loai xe.
- Neu gui that bai, record chuyen van duoc luu.
- Loi gui thong bao duoc log de admin kiem tra.

## Yeu Cau Phi Chuc Nang

### NFR-01: Mobile First

Giao dien uu tien su dung tren dien thoai, sau do responsive cho desktop.

### NFR-02: Bao Mat Thong Tin Ca Nhan

So dien thoai, dia chi diem don/diem den va thong tin tai xe can duoc bao ve o muc phu hop cho MVP.

### NFR-03: Do Tin Cay

Khach hang gui form khong bi mat du lieu neu Zalo notification loi.

### NFR-04: Kha Nang Mo Rong

Thiet ke backend theo module de sau nay them tinh nang tai khoan khach hang, dieu phoi tai xe, thanh toan va realtime tracking.

## Gia Dinh MVP

- Khach hang khong can dang ky tai khoan.
- Chuyen di duoc dieu phoi thu cong qua admin/nhom Zalo.
- Tai xe chua nhan chuyen truc tiep tren web trong giai doan dau.
- He thong chua tinh gia tu dong trong MVP, co the them truong uoc tinh/gia thoa thuan sau.

