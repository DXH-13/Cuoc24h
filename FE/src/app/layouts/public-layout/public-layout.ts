import { Component, HostListener, signal } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-public-layout',
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './public-layout.html',
})
export class PublicLayout {
  readonly menuOpen = signal(false);
  readonly year = new Date().getFullYear();

  toggleMenu(): void {
    this.setMenu(!this.menuOpen());
  }

  closeMenu(): void {
    this.setMenu(false);
  }

  private setMenu(open: boolean): void {
    this.menuOpen.set(open);
    try {
      document.body.classList.toggle('menu-open', open);
    } catch {
      /* SSR / no document */
    }
  }

  @HostListener('document:keydown.escape')
  onEscape(): void {
    this.closeMenu();
  }

  @HostListener('window:resize')
  onResize(): void {
    if (typeof window !== 'undefined' && window.matchMedia('(min-width: 1024px)').matches) {
      this.closeMenu();
    }
  }
}
