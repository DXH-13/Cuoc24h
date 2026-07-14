import { Component, inject, signal } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { BookingService } from '../../../core/services/booking.service';
import { BookingRequest, VEHICLE_OPTIONS, VehicleType } from '../../../shared/models/booking.model';

type Status = 'idle' | 'submitting' | 'success' | 'error';

@Component({
  selector: 'app-booking-page',
  imports: [ReactiveFormsModule],
  templateUrl: './booking-page.html',
})
export class BookingPage {
  private readonly fb = inject(FormBuilder);
  private readonly bookingService = inject(BookingService);
  private readonly route = inject(ActivatedRoute);

  readonly vehicleOptions = VEHICLE_OPTIONS;
  readonly status = signal<Status>('idle');
  readonly resultCode = signal<string | null>(null);
  readonly errorMessage = signal<string | null>(null);

  readonly form = this.fb.nonNullable.group({
    customerName: ['', [Validators.required, Validators.minLength(2)]],
    phone: ['', [Validators.required, Validators.pattern(/^0\d{8,10}$/)]],
    pickup: ['', [Validators.required]],
    dropoff: ['', [Validators.required]],
    pickupTime: ['', [Validators.required]],
    vehicleType: ['4-cho' as VehicleType, [Validators.required]],
    note: [''],
  });

  constructor() {
    const q = this.route.snapshot.queryParamMap;
    this.form.patchValue({
      pickup: q.get('pickup') ?? '',
      dropoff: q.get('dropoff') ?? '',
      phone: q.get('phone') ?? '',
      vehicleType: (q.get('vehicleType') as VehicleType) ?? '4-cho',
    });
  }

  invalid(control: string): boolean {
    const c = this.form.get(control);
    return !!c && c.invalid && (c.touched || c.dirty);
  }

  submit(): void {
    this.errorMessage.set(null);
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.status.set('submitting');
    const payload = this.form.getRawValue() as BookingRequest;
    this.bookingService.createBooking(payload).subscribe({
      next: (res) => {
        this.resultCode.set(res.code);
        this.status.set('success');
        window.scrollTo({ top: 0, behavior: 'smooth' });
      },
      error: () => {
        this.status.set('error');
        this.errorMessage.set('Không gửi được yêu cầu. Vui lòng thử lại hoặc gọi hotline 0900 000 000.');
      },
    });
  }

  reset(): void {
    this.form.reset({ vehicleType: '4-cho' });
    this.resultCode.set(null);
    this.status.set('idle');
  }
}
