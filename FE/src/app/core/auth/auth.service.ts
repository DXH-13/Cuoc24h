import { Injectable, signal } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { delay, tap } from 'rxjs/operators';

export interface AdminSession {
  token: string;
  username: string;
}

const STORAGE_KEY = 'cuoc24h.admin.session';

/**
 * Admin authentication (FR-04). Mocked for the MVP: any BE integration should
 * replace `login` with POST /api/auth/login and keep the same token handling.
 * Demo credentials: admin / cuoc24h
 */
@Injectable({ providedIn: 'root' })
export class AuthService {
  readonly session = signal<AdminSession | null>(this.readStoredSession());

  isLoggedIn(): boolean {
    return this.session() !== null;
  }

  login(username: string, password: string): Observable<AdminSession> {
    const ok = username.trim() === 'admin' && password === 'cuoc24h';
    if (!ok) {
      return throwError(() => new Error('Tài khoản hoặc mật khẩu không đúng.')).pipe(delay(900));
    }
    const session: AdminSession = {
      username: username.trim(),
      token: 'demo.' + Math.random().toString(36).slice(2),
    };
    return of(session).pipe(
      delay(900),
      tap((s) => this.persist(s)),
    );
  }

  logout(): void {
    this.session.set(null);
    try {
      localStorage.removeItem(STORAGE_KEY);
    } catch {
      /* storage unavailable */
    }
  }

  private persist(session: AdminSession): void {
    this.session.set(session);
    try {
      localStorage.setItem(STORAGE_KEY, JSON.stringify(session));
    } catch {
      /* storage unavailable */
    }
  }

  private readStoredSession(): AdminSession | null {
    try {
      const raw = localStorage.getItem(STORAGE_KEY);
      return raw ? (JSON.parse(raw) as AdminSession) : null;
    } catch {
      return null;
    }
  }
}
