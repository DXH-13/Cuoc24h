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

## Prototype

- Landing page static: `landing/index.html`
- Mo truc tiep file HTML trong trinh duyet de xem prototype khi chua scaffold Angular.
