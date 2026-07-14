import { Component, inject, signal } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';

import { DriverService } from '../../../core/services/driver.service';
import {
  DRIVER_VEHICLE_OPTIONS,
  DRIVER_WORK_MODES,
  DriverRegistration,
} from '../../../shared/models/driver.model';

type Status = 'idle' | 'submitting' | 'success' | 'error';

@Component({
  selector: 'app-driver-registration-page',
  imports: [ReactiveFormsModule],
  templateUrl: './driver-registration-page.html',
})
export class DriverRegistrationPage {
  private readonly fb = inject(FormBuilder);
  private readonly driverService = inject(DriverService);

  readonly vehicleOptions = DRIVER_VEHICLE_OPTIONS;
  readonly workModes = DRIVER_WORK_MODES;
  readonly status = signal<Status>('idle');
  readonly resultCode = signal<string | null>(null);
  readonly errorMessage = signal<string | null>(null);

  readonly form = this.fb.nonNullable.group({
    fullName: ['', [Validators.required, Validators.minLength(2)]],
    phone: ['', [Validators.required, Validators.pattern(/^0\d{8,10}$/)]],
    area: ['', [Validators.required]],
    vehicleType: [DRIVER_VEHICLE_OPTIONS[0] as string, [Validators.required]],
    plateNumber: ['', [Validators.required, Validators.minLength(6)]],
    seats: [4, [Validators.required, Validators.min(2), Validators.max(45)]],
    workMode: [DRIVER_WORK_MODES[0] as string, [Validators.required]],
    note: [''],
  });

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
    const payload = this.form.getRawValue() as DriverRegistration;
    this.driverService.register(payload).subscribe({
      next: (res) => {
        this.resultCode.set(res.code);
        this.status.set('success');
        window.scrollTo({ top: 0, behavior: 'smooth' });
      },
      error: () => {
        this.status.set('error');
        this.errorMessage.set('Không gửi được hồ sơ. Vui lòng thử lại sau ít phút.');
      },
    });
  }

  reset(): void {
    this.form.reset({
      vehicleType: DRIVER_VEHICLE_OPTIONS[0],
      workMode: DRIVER_WORK_MODES[0],
      seats: 4,
    });
    this.resultCode.set(null);
    this.status.set('idle');
  }
}
