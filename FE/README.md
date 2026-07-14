# FE

Thu muc frontend cho ung dung Angular + Tailwind CSS cua Cuoc24h.

## Cau Truc

```text
FE/
  src/
    app/
      core/
        auth/              # Auth state, login/logout helpers
        guards/            # Route guards
        interceptors/      # HTTP interceptors
        services/          # API clients va singleton services
      features/
        public/
          home/            # Landing page
          booking/         # Customer booking flow
          driver-registration/
        admin/
          dashboard/
          bookings/
          drivers/
          notifications/
      layouts/
        public-layout/
        admin-layout/
      shared/
        components/
        models/
        pipes/
        validators/
    assets/
      icons/
      images/
    environments/
    styles/
```

## Stack Du Kien

- Angular
- Tailwind CSS
- Angular Router
- Reactive Forms

## Huong UI

- Mobile-first cho khach hang va tai xe.
- Desktop-friendly cho admin dashboard.
- Public pages gom landing page, booking flow, driver registration.
- Admin pages gom login, dashboard, bookings, drivers, notification logs.

## Chay Ung Dung

Angular app da duoc scaffold (Angular 22 standalone + Tailwind v4).

```bash
cd FE
npm install
npm start        # ng serve -> http://localhost:4200
npm run build    # build production vao dist/
```

Cac route hien co:

- `/` — landing page (feature `public/home`)
- `/dat-xe` — form dat xe khach hang (FR-02)
- `/dang-ky-tai-xe` — dang ky tai xe, trang thai PENDING (FR-03)
- `/admin/dang-nhap` — admin dang nhap (FR-04, demo: `admin` / `cuoc24h`)

API hien tai duoc mock trong `core/services` + `core/auth`; thay bang HttpClient khi BE san sang.

## Prototype (cu)

- `landing/index.html` la ban prototype tinh ban dau. Thiet ke da duoc port vao Angular
  (`src/styles/landing.css` + feature `public/home`). Giu lai de tham chieu.
