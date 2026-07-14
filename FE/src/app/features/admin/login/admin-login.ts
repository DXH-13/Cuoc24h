import { Component, inject, signal } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { AuthService } from '../../../core/auth/auth.service';

type Status = 'idle' | 'submitting' | 'error';

@Component({
  selector: 'app-admin-login',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './admin-login.html',
})
export class AdminLogin {
  private readonly fb = inject(FormBuilder);
  private readonly auth = inject(AuthService);

  readonly status = signal<Status>('idle');
  readonly errorMessage = signal<string | null>(null);
  readonly showPassword = signal(false);
  readonly session = this.auth.session;

  readonly form = this.fb.nonNullable.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required]],
  });

  invalid(control: string): boolean {
    const c = this.form.get(control);
    return !!c && c.invalid && (c.touched || c.dirty);
  }

  togglePassword(): void {
    this.showPassword.update((v) => !v);
  }

  submit(): void {
    this.errorMessage.set(null);
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.status.set('submitting');
    const { username, password } = this.form.getRawValue();
    this.auth.login(username, password).subscribe({
      next: () => {
        this.status.set('idle');
        this.form.reset();
      },
      error: (err: Error) => {
        this.status.set('error');
        this.errorMessage.set(err.message);
      },
    });
  }

  logout(): void {
    this.auth.logout();
    this.status.set('idle');
  }
}
