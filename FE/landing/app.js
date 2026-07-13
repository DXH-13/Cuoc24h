const carousel = document.querySelector("[data-carousel]");

if (carousel) {
  const track = carousel.querySelector("[data-track]");
  const slides = Array.from(track.children);
  const previousButton = carousel.querySelector("[data-prev]");
  const nextButton = carousel.querySelector("[data-next]");
  const dotsContainer = carousel.querySelector("[data-dots]");
  let activeIndex = 0;
  let timerId;
  let dots = [];

  function visibleSlideCount() {
    if (window.matchMedia("(min-width: 1040px)").matches) {
      return 3;
    }
    if (window.matchMedia("(min-width: 720px)").matches) {
      return 2;
    }
    return 1;
  }

  function maxIndex() {
    return Math.max(slides.length - visibleSlideCount(), 0);
  }

  function slideStep() {
    const firstSlide = slides[0];
    const styles = window.getComputedStyle(track);
    const gap = Number.parseFloat(styles.columnGap || styles.gap || "0");
    return firstSlide.getBoundingClientRect().width + gap;
  }

  function goToSlide(index) {
    activeIndex = Math.min(Math.max(index, 0), maxIndex());
    track.style.transform = `translateX(${-activeIndex * slideStep()}px)`;

    dots.forEach((dot, dotIndex) => {
      const isActive = dotIndex === activeIndex;
      dot.classList.toggle("is-active", isActive);
      dot.setAttribute("aria-current", isActive ? "true" : "false");
    });
  }

  function renderDots() {
    dotsContainer.replaceChildren();
    dots = Array.from({ length: maxIndex() + 1 }, (_, index) => {
      const dot = document.createElement("button");
      dot.type = "button";
      dot.className = "carousel-dot";
      dot.setAttribute("aria-label", `Xem nhóm dịch vụ ${index + 1}`);
      dot.addEventListener("click", () => {
        goToSlide(index);
        restartTimer();
      });
      dotsContainer.appendChild(dot);
      return dot;
    });
    goToSlide(activeIndex);
  }

  function nextSlide() {
    goToSlide(activeIndex >= maxIndex() ? 0 : activeIndex + 1);
  }

  function previousSlide() {
    goToSlide(activeIndex <= 0 ? maxIndex() : activeIndex - 1);
  }

  function restartTimer() {
    window.clearInterval(timerId);
    timerId = window.setInterval(nextSlide, 5200);
  }

  previousButton.addEventListener("click", () => {
    previousSlide();
    restartTimer();
  });

  nextButton.addEventListener("click", () => {
    nextSlide();
    restartTimer();
  });

  window.addEventListener("resize", renderDots);
  carousel.addEventListener("mouseenter", () => window.clearInterval(timerId));
  carousel.addEventListener("mouseleave", restartTimer);

  renderDots();
  restartTimer();
}
