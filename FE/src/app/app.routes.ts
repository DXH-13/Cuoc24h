import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./layouts/public-layout/public-layout').then((m) => m.PublicLayout),
    children: [
      {
        path: '',
        loadComponent: () => import('./features/public/home/home').then((m) => m.Home),
        title: 'Cuốc24h — Đặt xe dịch vụ không cần app',
      },
      {
        path: 'dat-xe',
        loadComponent: () =>
          import('./features/public/booking/booking-page').then((m) => m.BookingPage),
        title: 'Đặt xe — Cuốc24h',
      },
      {
        path: 'dang-ky-tai-xe',
        loadComponent: () =>
          import('./features/public/driver-registration/driver-registration-page').then(
            (m) => m.DriverRegistrationPage,
          ),
        title: 'Đăng ký tài xế — Cuốc24h',
      },
    ],
  },
  {
    path: 'admin/dang-nhap',
    loadComponent: () => import('./features/admin/login/admin-login').then((m) => m.AdminLogin),
    title: 'Đăng nhập quản trị — Cuốc24h',
  },
  { path: '**', redirectTo: '' },
];
