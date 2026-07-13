// Spotlight carousel — center card in focus, seamless one-direction infinite loop
function initSpotlightCarousel(carousel) {
  const viewport = carousel.querySelector("[data-viewport]");
  const track = carousel.querySelector("[data-track]");
  const prevButton = carousel.querySelector("[data-prev]");
  const nextButton = carousel.querySelector("[data-next]");
  const dotsContainer = carousel.querySelector("[data-dots]");
  if (!track || !viewport) {
    return;
  }

  const reals = Array.from(track.children);
  const count = reals.length;
  if (count === 0) {
    return;
  }

  // Clone the whole set before and after so looping never snaps back visibly
  const beforeFrag = document.createDocumentFragment();
  const afterFrag = document.createDocumentFragment();
  reals.forEach((slide) => {
    const cloneBefore = slide.cloneNode(true);
    const cloneAfter = slide.cloneNode(true);
    [cloneBefore, cloneAfter].forEach((clone) => {
      clone.classList.add("is-clone");
      clone.setAttribute("aria-hidden", "true");
    });
    beforeFrag.appendChild(cloneBefore);
    afterFrag.appendChild(cloneAfter);
  });
  track.appendChild(afterFrag);
  track.insertBefore(beforeFrag, track.firstChild);

  const allSlides = Array.from(track.children); // count * 3
  let phys = count; // index of the first real slide
  let timerId;

  const dots = reals.map((_, index) => {
    const dot = document.createElement("button");
    dot.type = "button";
    dot.className = "carousel-dot";
    dot.setAttribute("aria-label", `Xem mục ${index + 1}`);
    dot.addEventListener("click", () => {
      go(count + index);
      restartTimer();
    });
    dotsContainer.appendChild(dot);
    return dot;
  });

  function apply(animate) {
    const activeSlide = allSlides[phys];
    // Center the active slide exactly, using its real layout position (handles any widths/gaps)
    const translate = viewport.clientWidth / 2 - (activeSlide.offsetLeft + activeSlide.offsetWidth / 2);

    if (animate) {
      track.style.transform = `translateX(${translate}px)`;
    } else {
      track.style.transition = "none";
      track.style.transform = `translateX(${translate}px)`;
      track.getBoundingClientRect(); // force reflow so the next change animates
      track.style.transition = "";
    }

    allSlides.forEach((slide, index) => slide.classList.toggle("is-center", index === phys));

    const logical = (((phys - count) % count) + count) % count;
    dots.forEach((dot, index) => {
      const isActive = index === logical;
      dot.classList.toggle("is-active", isActive);
      dot.setAttribute("aria-current", isActive ? "true" : "false");
    });
  }

  function go(nextPhys) {
    phys = nextPhys;
    apply(true);
  }

  function next() {
    go(phys + 1);
  }

  function previous() {
    go(phys - 1);
  }

  // When we land on a cloned edge, silently jump to the identical real slide
  track.addEventListener("transitionend", (event) => {
    if (event.target !== track || event.propertyName !== "transform") {
      return;
    }
    if (phys >= count * 2) {
      phys -= count;
      apply(false);
    } else if (phys < count) {
      phys += count;
      apply(false);
    }
  });

  function restartTimer() {
    window.clearInterval(timerId);
    timerId = window.setInterval(next, 4500);
  }

  if (nextButton) {
    nextButton.addEventListener("click", () => {
      next();
      restartTimer();
    });
  }
  if (prevButton) {
    prevButton.addEventListener("click", () => {
      previous();
      restartTimer();
    });
  }

  window.addEventListener("resize", () => apply(false));
  window.addEventListener("load", () => apply(false));
  carousel.addEventListener("mouseenter", () => window.clearInterval(timerId));
  carousel.addEventListener("mouseleave", restartTimer);

  apply(false);
  // Re-measure once layout has settled (fonts/images can shift widths on load)
  window.requestAnimationFrame(() => apply(false));
  restartTimer();
}

document.querySelectorAll("[data-carousel]").forEach(initSpotlightCarousel);

// Mobile navigation menu
const navToggle = document.querySelector("[data-nav-toggle]");
const mobileMenu = document.querySelector("[data-mobile-menu]");
const navOverlay = document.querySelector("[data-nav-overlay]");

if (navToggle && mobileMenu) {
  const navLinks = mobileMenu.querySelectorAll("[data-nav-link]");

  function setMenu(open) {
    navToggle.classList.toggle("is-open", open);
    mobileMenu.classList.toggle("is-open", open);
    if (navOverlay) {
      navOverlay.classList.toggle("is-open", open);
    }
    document.body.classList.toggle("menu-open", open);
    navToggle.setAttribute("aria-expanded", open ? "true" : "false");
    navToggle.setAttribute("aria-label", open ? "Đóng menu điều hướng" : "Mở menu điều hướng");
  }

  navToggle.addEventListener("click", () => {
    setMenu(!mobileMenu.classList.contains("is-open"));
  });

  if (navOverlay) {
    navOverlay.addEventListener("click", () => setMenu(false));
  }

  navLinks.forEach((link) => link.addEventListener("click", () => setMenu(false)));

  document.addEventListener("keydown", (event) => {
    if (event.key === "Escape") {
      setMenu(false);
    }
  });

  window.addEventListener("resize", () => {
    if (window.matchMedia("(min-width: 1024px)").matches) {
      setMenu(false);
    }
  });
}

// Hero carousel — full-bleed slides, seamless one-direction infinite loop
const heroCarousel = document.querySelector("[data-hero-carousel]");

if (heroCarousel) {
  const heroTrack = heroCarousel.querySelector("[data-hero-track]");
  const heroPrev = heroCarousel.querySelector("[data-hero-prev]");
  const heroNext = heroCarousel.querySelector("[data-hero-next]");
  const heroDotsContainer = heroCarousel.querySelector("[data-hero-dots]");
  const heroReals = heroTrack ? Array.from(heroTrack.children) : [];
  const heroCount = heroReals.length;

  if (heroCount > 0) {
    // Clone the whole set before and after so the loop never snaps back
    const heroBefore = document.createDocumentFragment();
    const heroAfter = document.createDocumentFragment();
    heroReals.forEach((slide) => {
      const cloneBefore = slide.cloneNode(true);
      const cloneAfter = slide.cloneNode(true);
      [cloneBefore, cloneAfter].forEach((clone) => {
        clone.classList.add("is-clone");
        clone.setAttribute("aria-hidden", "true");
      });
      heroBefore.appendChild(cloneBefore);
      heroAfter.appendChild(cloneAfter);
    });
    heroTrack.appendChild(heroAfter);
    heroTrack.insertBefore(heroBefore, heroTrack.firstChild);

    const heroAll = Array.from(heroTrack.children); // heroCount * 3
    let heroPhys = heroCount; // first real slide
    let heroTimer;

    const heroDots = heroReals.map((_, i) => {
      const dot = document.createElement("button");
      dot.type = "button";
      dot.className = "hero-dot";
      dot.setAttribute("aria-label", `Chuyển tới slide ${i + 1}`);
      dot.addEventListener("click", () => {
        heroGo(heroCount + i);
        heroRestart();
      });
      heroDotsContainer.appendChild(dot);
      return dot;
    });

    function heroApply(animate) {
      if (animate) {
        heroTrack.style.transform = `translateX(${-heroPhys * 100}%)`;
      } else {
        heroTrack.style.transition = "none";
        heroTrack.style.transform = `translateX(${-heroPhys * 100}%)`;
        heroTrack.getBoundingClientRect();
        heroTrack.style.transition = "";
      }

      const logical = (((heroPhys - heroCount) % heroCount) + heroCount) % heroCount;
      heroDots.forEach((dot, i) => {
        const isActive = i === logical;
        dot.classList.toggle("is-active", isActive);
        dot.setAttribute("aria-current", isActive ? "true" : "false");
      });
      heroAll.forEach((slide, i) => slide.setAttribute("aria-hidden", i === heroPhys ? "false" : "true"));
    }

    function heroGo(nextPhys) {
      heroPhys = nextPhys;
      heroApply(true);
    }

    heroTrack.addEventListener("transitionend", (event) => {
      if (event.target !== heroTrack || event.propertyName !== "transform") {
        return;
      }
      if (heroPhys >= heroCount * 2) {
        heroPhys -= heroCount;
        heroApply(false);
      } else if (heroPhys < heroCount) {
        heroPhys += heroCount;
        heroApply(false);
      }
    });

    function heroRestart() {
      window.clearInterval(heroTimer);
      heroTimer = window.setInterval(() => heroGo(heroPhys + 1), 6000);
    }

    if (heroNext) {
      heroNext.addEventListener("click", () => {
        heroGo(heroPhys + 1);
        heroRestart();
      });
    }
    if (heroPrev) {
      heroPrev.addEventListener("click", () => {
        heroGo(heroPhys - 1);
        heroRestart();
      });
    }

    heroCarousel.addEventListener("mouseenter", () => window.clearInterval(heroTimer));
    heroCarousel.addEventListener("mouseleave", heroRestart);

    heroApply(false);
    heroRestart();
  }
}
