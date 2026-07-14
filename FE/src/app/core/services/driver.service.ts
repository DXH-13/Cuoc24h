import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { delay } from 'rxjs/operators';

import {
  DriverRegistration,
  DriverRegistrationResult,
} from '../../shared/models/driver.model';

/**
 * Driver-registration API client. Simulates the FR-03 flow: create a driver
 * profile with status PENDING for admin review. Swap for POST /api/drivers later.
 */
@Injectable({ providedIn: 'root' })
export class DriverService {
  register(payload: DriverRegistration): Observable<DriverRegistrationResult> {
    const code = 'TX-' + Math.random().toString(36).slice(2, 8).toUpperCase();
    // eslint-disable-next-line no-console
    console.info('[DriverService] đăng ký tài xế (mock):', payload);
    return of<DriverRegistrationResult>({ code, status: 'PENDING' }).pipe(delay(1100));
  }
}
