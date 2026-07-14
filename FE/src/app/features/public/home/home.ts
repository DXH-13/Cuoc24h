import { AfterViewInit, Component, ElementRef, OnDestroy, inject } from '@angular/core';
import { NgClass } from '@angular/common';
import { Router, RouterLink } from '@angular/router';

interface DriverCard {
  name: string;
  photo: string;
  badge: string;
  badgeClass: string;
  sponsored?: boolean;
  rating: string;
  trips: number;
  desc: string;
  area: string;
  vehicle: string;
}

interface Review {
  text: string;
  initials: string;
  name: string;
  location: string;
}

@Component({
  selector: 'app-home',
  imports: [NgClass, RouterLink],
  templateUrl: './home.html',
})
export class Home implements AfterViewInit, OnDestroy {
  private readonly host = inject(ElementRef<HTMLElement>);
  private readonly router = inject(Router);
  private readonly cleanups: Array<() => void> = [];

  readonly coverage = [
    'TP.Hồ Chí Minh',
    'Bình Dương',
    'Đồng Nai',
    'Long An',
    'Vũng Tàu',
    'Tỉnh Miền Tây',
  ];

  readonly drivers: DriverCard[] = [
    {
      name: 'Nguyễn Minh',
      photo: 'https://images.unsplash.com/photo-1560250097-0b93528c311a?auto=format&fit=crop&w=700&q=85',
      badge: 'Được đề xuất',
      badgeClass: 'driver-badge-sponsored',
      sponsored: true,
      rating: '4.9',
      trips: 214,
      desc: 'Chuyên nghiệp, thông thuộc đường đi, phục vụ chu đáo các tuyến đi tỉnh miền Tây.',
      area: 'TP.HCM / Miền Tây',
      vehicle: '7 Chỗ rộng rãi',
    },
    {
      name: 'Anh Khoa',
      photo: 'https://images.unsplash.com/photo-1547425260-76bcadfb4f2c?auto=format&fit=crop&w=700&q=85',
      badge: 'Top Lựa Chọn',
      badgeClass: 'driver-badge-top',
      rating: '4.8',
      trips: 168,
      desc: 'Xe gia đình sạch mát, không mùi. Lái xe điềm đạm, an toàn, phù hợp đi công tác.',
      area: 'Bình Dương / TP.HCM',
      vehicle: '4 Chỗ lịch sự',
    },
    {
      name: 'Chị Hạnh',
      photo: 'https://images.unsplash.com/photo-1607346256330-dee7af15f7c5?auto=format&fit=crop&w=700&q=85',
      badge: 'Thân Thiện',
      badgeClass: 'driver-badge-family',
      rating: '4.9',
      trips: 96,
      desc: 'Phục vụ chu đáo, thân thiện, ưu tiên đón khách nữ và gia đình có em nhỏ đi nội thành.',
      area: 'Đồng Nai / Sân Bay',
      vehicle: '4 Chỗ tiện nghi',
    },
    {
      name: 'Trần Bảo',
      photo: 'https://images.unsplash.com/photo-1568602471122-7832951cc4c5?auto=format&fit=crop&w=700&q=85',
      badge: 'Đánh giá cao',
      badgeClass: 'driver-badge-top',
      rating: '4.8',
      trips: 152,
      desc: 'Lái xe êm ái, đúng giờ, thông thạo các tuyến nội đô và đường dài miền Đông.',
      area: 'TP.HCM / Miền Đông',
      vehicle: '7 Chỗ rộng rãi',
    },
    {
      name: 'Đức Thịnh',
      photo: 'https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?auto=format&fit=crop&w=700&q=85',
      badge: 'Đúng giờ',
      badgeClass: 'driver-badge-family',
      rating: '4.7',
      trips: 128,
      desc: 'Xe đời mới sạch sẽ, hỗ trợ hành lý nhiệt tình, chuyên tuyến Vũng Tàu - sân bay.',
      area: 'Vũng Tàu / Sân Bay',
      vehicle: '4 Chỗ tiện nghi',
    },
    {
      name: 'Chị Thu',
      photo: 'https://images.unsplash.com/photo-1573497019940-1c28c88b4f3e?auto=format&fit=crop&w=700&q=85',
      badge: 'Được yêu thích',
      badgeClass: 'driver-badge-top',
      rating: '5.0',
      trips: 74,
      desc: 'Nhẹ nhàng, cẩn thận, được nhiều khách nữ và người lớn tuổi tin tưởng lựa chọn.',
      area: 'Bình Dương / TP.HCM',
      vehicle: '4 Chỗ lịch sự',
    },
  ];

  readonly reviews: Review[] = [
    {
      text: 'Đặt chuyến đi sân bay lúc rạng sáng, có tài xế gọi xác nhận siêu nhanh và báo giá cố định rõ ràng, tôi rất yên tâm.',
      initials: 'LA',
      name: 'Lan Anh',
      location: 'Quận 7, TP.HCM',
    },
    {
      text: 'Gia đình tôi đi Vũng Tàu chơi dịp cuối tuần, xe rất sạch sẽ thơm tho, tài xế lái đầm, lịch thiệp và hỗ trợ bưng bê đồ.',
      initials: 'MQ',
      name: 'Minh Quân',
      location: 'Thủ Đức, TP.HCM',
    },
    {
      text: 'Nhanh và tiện thực sự. Chỉ cần vào web điền lộ trình với số điện thoại là có lái xe khu vực liên hệ đón luôn.',
      initials: 'HN',
      name: 'Hoàng Nam',
      location: 'Bình Thạnh, TP.HCM',
    },
    {
      text: 'Tài xế đến sớm hơn giờ hẹn, xe thơm sạch. Giá báo trước đúng như lúc đi, không phát sinh gì thêm.',
      initials: 'TT',
      name: 'Thu Trang',
      location: 'Quận 3, TP.HCM',
    },
    {
      text: 'Đi từ Biên Hòa lên Đà Lạt, tài xế vững tay lái, nghỉ ngơi hợp lý, cả nhà đi rất thoải mái.',
      initials: 'ĐH',
      name: 'Đức Huy',
      location: 'Biên Hòa, Đồng Nai',
    },
    {
      text: 'Đặt xe lúc nửa đêm ra sân bay mà vẫn có tài xế nhận nhanh, phục vụ chu đáo, mình rất hài lòng.',
      initials: 'NB',
      name: 'Ngọc Bích',
      location: 'Tân Bình, TP.HCM',
    },
  ];

  /** Hero quick panel → carry values to the full booking page. */
  quickBook(pickup: string, dropoff: string, phone: string, vehicleType: string): void {
    this.router.navigate(['/dat-xe'], {
      queryParams: {
        pickup: pickup || null,
        dropoff: dropoff || null,
        phone: phone || null,
        vehicleType: vehicleType || null,
      },
    });
  }

  ngAfterViewInit(): void {
    const root = this.host.nativeElement as HTMLElement;
    root.querySelectorAll<HTMLElement>('[data-carousel]').forEach((c) => this.initSpotlight(c));
    const hero = root.querySelector<HTMLElement>('[data-hero-carousel]');
    if (hero) {
      this.initHero(hero);
    }
  }

  ngOnDestroy(): void {
    this.cleanups.forEach((fn) => fn());
  }

  // Spotlight carousel — center card in focus, seamless one-direction infinite loop
  private initSpotlight(carousel: HTMLElement): void {
    const viewport = carousel.querySelector<HTMLElement>('[data-viewport]');
    const track = carousel.querySelector<HTMLElement>('[data-track]');
    const prevButton = carousel.querySelector<HTMLElement>('[data-prev]');
    const nextButton = carousel.querySelector<HTMLElement>('[data-next]');
    const dotsContainer = carousel.querySelector<HTMLElement>('[data-dots]');
    if (!track || !viewport) {
      return;
    }

    const reals = Array.from(track.children);
    const count = reals.length;
    if (count === 0) {
      return;
    }

    const beforeFrag = document.createDocumentFragment();
    const afterFrag = document.createDocumentFragment();
    reals.forEach((slide) => {
      const cloneBefore = slide.cloneNode(true) as HTMLElement;
      const cloneAfter = slide.cloneNode(true) as HTMLElement;
      [cloneBefore, cloneAfter].forEach((clone) => {
        clone.classList.add('is-clone');
        clone.setAttribute('aria-hidden', 'true');
      });
      beforeFrag.appendChild(cloneBefore);
      afterFrag.appendChild(cloneAfter);
    });
    track.appendChild(afterFrag);
    track.insertBefore(beforeFrag, track.firstChild);

    const allSlides = Array.from(track.children) as HTMLElement[];
    let phys = count;
    let timerId: number | undefined;

    const dots = reals.map((_, index) => {
      const dot = document.createElement('button');
      dot.type = 'button';
      dot.className = 'carousel-dot';
      dot.setAttribute('aria-label', `Xem mục ${index + 1}`);
      dot.addEventListener('click', () => {
        go(count + index);
        restartTimer();
      });
      dotsContainer?.appendChild(dot);
      return dot;
    });

    const apply = (animate: boolean): void => {
      const activeSlide = allSlides[phys];
      const translate = viewport.clientWidth / 2 - (activeSlide.offsetLeft + activeSlide.offsetWidth / 2);
      if (animate) {
        track.style.transform = `translateX(${translate}px)`;
      } else {
        track.style.transition = 'none';
        track.style.transform = `translateX(${translate}px)`;
        track.getBoundingClientRect();
        track.style.transition = '';
      }
      allSlides.forEach((slide, index) => slide.classList.toggle('is-center', index === phys));
      const logical = (((phys - count) % count) + count) % count;
      dots.forEach((dot, index) => {
        const isActive = index === logical;
        dot.classList.toggle('is-active', isActive);
        dot.setAttribute('aria-current', isActive ? 'true' : 'false');
      });
    };

    const go = (nextPhys: number): void => {
      phys = nextPhys;
      apply(true);
    };
    const next = (): void => go(phys + 1);
    const previous = (): void => go(phys - 1);

    const onTransitionEnd = (event: TransitionEvent): void => {
      if (event.target !== track || event.propertyName !== 'transform') {
        return;
      }
      if (phys >= count * 2) {
        phys -= count;
        apply(false);
      } else if (phys < count) {
        phys += count;
        apply(false);
      }
    };
    track.addEventListener('transitionend', onTransitionEnd);

    const restartTimer = (): void => {
      window.clearInterval(timerId);
      timerId = window.setInterval(next, 4500);
    };

    nextButton?.addEventListener('click', () => {
      next();
      restartTimer();
    });
    prevButton?.addEventListener('click', () => {
      previous();
      restartTimer();
    });

    const onResize = (): void => apply(false);
    window.addEventListener('resize', onResize);
    carousel.addEventListener('mouseenter', () => window.clearInterval(timerId));
    carousel.addEventListener('mouseleave', restartTimer);

    apply(false);
    window.requestAnimationFrame(() => apply(false));
    restartTimer();

    this.cleanups.push(() => {
      window.clearInterval(timerId);
      window.removeEventListener('resize', onResize);
      track.removeEventListener('transitionend', onTransitionEnd);
    });
  }

  // Hero carousel — full-bleed slides, seamless one-direction infinite loop
  private initHero(heroCarousel: HTMLElement): void {
    const heroTrack = heroCarousel.querySelector<HTMLElement>('[data-hero-track]');
    const heroPrev = heroCarousel.querySelector<HTMLElement>('[data-hero-prev]');
    const heroNext = heroCarousel.querySelector<HTMLElement>('[data-hero-next]');
    const heroDotsContainer = heroCarousel.querySelector<HTMLElement>('[data-hero-dots]');
    const heroReals = heroTrack ? Array.from(heroTrack.children) : [];
    const heroCount = heroReals.length;
    if (!heroTrack || heroCount === 0) {
      return;
    }

    const heroBefore = document.createDocumentFragment();
    const heroAfter = document.createDocumentFragment();
    heroReals.forEach((slide) => {
      const cloneBefore = slide.cloneNode(true) as HTMLElement;
      const cloneAfter = slide.cloneNode(true) as HTMLElement;
      [cloneBefore, cloneAfter].forEach((clone) => {
        clone.classList.add('is-clone');
        clone.setAttribute('aria-hidden', 'true');
      });
      heroBefore.appendChild(cloneBefore);
      heroAfter.appendChild(cloneAfter);
    });
    heroTrack.appendChild(heroAfter);
    heroTrack.insertBefore(heroBefore, heroTrack.firstChild);

    const heroAll = Array.from(heroTrack.children) as HTMLElement[];
    let heroPhys = heroCount;
    let heroTimer: number | undefined;

    const heroDots = heroReals.map((_, i) => {
      const dot = document.createElement('button');
      dot.type = 'button';
      dot.className = 'hero-dot';
      dot.setAttribute('aria-label', `Chuyển tới slide ${i + 1}`);
      dot.addEventListener('click', () => {
        heroGo(heroCount + i);
        heroRestart();
      });
      heroDotsContainer?.appendChild(dot);
      return dot;
    });

    const heroApply = (animate: boolean): void => {
      if (animate) {
        heroTrack.style.transform = `translateX(${-heroPhys * 100}%)`;
      } else {
        heroTrack.style.transition = 'none';
        heroTrack.style.transform = `translateX(${-heroPhys * 100}%)`;
        heroTrack.getBoundingClientRect();
        heroTrack.style.transition = '';
      }
      const logical = (((heroPhys - heroCount) % heroCount) + heroCount) % heroCount;
      heroDots.forEach((dot, i) => {
        const isActive = i === logical;
        dot.classList.toggle('is-active', isActive);
        dot.setAttribute('aria-current', isActive ? 'true' : 'false');
      });
      heroAll.forEach((slide, i) => slide.setAttribute('aria-hidden', i === heroPhys ? 'false' : 'true'));
    };

    const heroGo = (nextPhys: number): void => {
      heroPhys = nextPhys;
      heroApply(true);
    };

    const onHeroTransitionEnd = (event: TransitionEvent): void => {
      if (event.target !== heroTrack || event.propertyName !== 'transform') {
        return;
      }
      if (heroPhys >= heroCount * 2) {
        heroPhys -= heroCount;
        heroApply(false);
      } else if (heroPhys < heroCount) {
        heroPhys += heroCount;
        heroApply(false);
      }
    };
    heroTrack.addEventListener('transitionend', onHeroTransitionEnd);

    const heroRestart = (): void => {
      window.clearInterval(heroTimer);
      heroTimer = window.setInterval(() => heroGo(heroPhys + 1), 6000);
    };

    heroNext?.addEventListener('click', () => {
      heroGo(heroPhys + 1);
      heroRestart();
    });
    heroPrev?.addEventListener('click', () => {
      heroGo(heroPhys - 1);
      heroRestart();
    });

    heroCarousel.addEventListener('mouseenter', () => window.clearInterval(heroTimer));
    heroCarousel.addEventListener('mouseleave', heroRestart);

    heroApply(false);
    heroRestart();

    this.cleanups.push(() => {
      window.clearInterval(heroTimer);
      heroTrack.removeEventListener('transitionend', onHeroTransitionEnd);
    });
  }
}
