import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { delay } from 'rxjs/operators';

import { BookingRequest, BookingResult } from '../../shared/models/booking.model';

/**
 * Customer booking API client. Backend is not wired yet (MVP), so this simulates
 * the FR-02 flow: create a booking with status NEW and return a reference code.
 * Replace the body with a real HttpClient POST /api/bookings when BE is ready.
 */
@Injectable({ providedIn: 'root' })
export class BookingService {
  createBooking(payload: BookingRequest): Observable<BookingResult> {
    const code = 'C24-' + Math.random().toString(36).slice(2, 8).toUpperCase();
    // eslint-disable-next-line no-console
    console.info('[BookingService] tạo booking (mock):', payload);
    return of<BookingResult>({ code, status: 'NEW' }).pipe(delay(1100));
  }
}
