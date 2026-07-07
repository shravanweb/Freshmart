import React, { ReactNode } from "react";

const IMG = "/images/landing/";

interface HeroSlide {
  key: string;
  badge: string;
  title: string;
  subtitle: string;
  image: string;
  imageAlt: string;
  highlights: string[];
  cta: string;
  tone: "green" | "teal" | "amber" | "yellow";
}

const HERO_SLIDES: HeroSlide[] = [
  {
    key: "groceries",
    badge: "Same-Day Delivery",
    title: "Fresh Groceries Delivered to Your Doorstep",
    subtitle:
      "Shop farm-fresh produce, daily essentials, and household favourites with express delivery across Hyderabad.",
    image: IMG + "hero-groceries.jpg",
    imageAlt: "Fresh groceries at FreshMart",
    highlights: ["15–30 min delivery", "Free above ₹499", "100% quality assured"],
    cta: "Shop Now",
    tone: "green",
  },
  {
    key: "pet-care",
    badge: "Pet Care",
    title: "Pet Care Supplies at Your Door",
    subtitle:
      "Food, treats, grooming essentials and more for your furry friends — delivered fast to your doorstep.",
    image: IMG + "featured-pharmacyslide.png",
    imageAlt: "Pet care products at FreshMart",
    highlights: ["Pet food", "Treats & toys", "Grooming essentials"],
    cta: "Shop Pet Care",
    tone: "yellow",
  },
  {
    key: "pharmacy",
    badge: "Health & Wellness",
    title: "Pharmacy at Your Doorstep",
    subtitle:
      "Medicines, pain relief sprays, vitamins and daily health essentials delivered to your door fast.",
    image: IMG + "pharmacy.png",
    imageAlt: "Pharmacy products at FreshMart",
    highlights: ["OTC medicines", "Pain relief", "Vitamins & supplements"],
    cta: "Order Medicines",
    tone: "teal",
  },
];

export interface LandingHeroCarouselProps {
  onCtaClick: () => void;
}

interface LandingHeroCarouselState {
  slideIndex: number;
}

function heroCopyToneClass(tone: HeroSlide["tone"]): string {
  if (tone === "teal") {
    return " landingHeroCopyTeal";
  }
  if (tone === "amber") {
    return " landingHeroCopyAmber";
  }
  if (tone === "yellow") {
    return " landingHeroCopyYellow";
  }
  return "";
}

function preloadHeroImages(): void {
  if (typeof window === "undefined") {
    return;
  }

  HERO_SLIDES.forEach((slide) => {
    const img = new window.Image();
    img.src = slide.image;
  });
}

export default class LandingHeroCarousel extends React.PureComponent<
  LandingHeroCarouselProps,
  LandingHeroCarouselState
> {
  private heroTimer: ReturnType<typeof setInterval> | null = null;

  public constructor(props: LandingHeroCarouselProps) {
    super(props);
    this.state = { slideIndex: 0 };
  }

  public componentDidMount(): void {
    preloadHeroImages();
    this.heroTimer = setInterval(() => {
      this.setState((prev) => ({
        slideIndex: (prev.slideIndex + 1) % HERO_SLIDES.length,
      }));
    }, 5500);
  }

  public componentWillUnmount(): void {
    if (this.heroTimer) {
      clearInterval(this.heroTimer);
      this.heroTimer = null;
    }
  }

  private goToSlide = (index: number): void => {
    if (index === this.state.slideIndex) {
      return;
    }
    this.setState({ slideIndex: index });
  };

  public render(): ReactNode {
    return React.createElement(
      "section",
      { className: "landingSection landingHeroSection" },
      React.createElement(
        "div",
        { className: "landingHeroBanner" },
        React.createElement(
          "div",
          { className: "landingHeroSlideStack" },
          HERO_SLIDES.map((slide, index) =>
            React.createElement(
              "div",
              {
                className:
                  "landingHeroSlide" +
                  (index === this.state.slideIndex ? " landingHeroSlideActive" : ""),
                "aria-hidden": index !== this.state.slideIndex,
                key: slide.key,
              },
              React.createElement(
                "div",
                { className: "landingHeroCopy" + heroCopyToneClass(slide.tone) },
                React.createElement("span", { className: "landingHeroBadge" }, slide.badge),
                React.createElement("h2", { className: "landingHeroTitle" }, slide.title),
                React.createElement("p", { className: "landingHeroSubtitle" }, slide.subtitle),
                React.createElement(
                  "div",
                  { className: "landingHeroHighlights" },
                  slide.highlights.map((item, highlightIndex) =>
                    React.createElement(
                      "span",
                      {
                        className: "landingHeroHighlight",
                        key: slide.key + "-hl-" + highlightIndex,
                      },
                      item
                    )
                  )
                ),
                React.createElement(
                  "button",
                  {
                    className: "landingHeroCtaBtn",
                    type: "button",
                    onClick: this.props.onCtaClick,
                    tabIndex: index === this.state.slideIndex ? 0 : -1,
                  },
                  slide.cta
                )
              ),
              React.createElement(
                "div",
                { className: "landingHeroMedia" },
                React.createElement("img", {
                  src: slide.image,
                  alt: slide.imageAlt,
                  className: "landingHeroPhoto",
                  loading: "eager",
                  decoding: "async",
                })
              )
            )
          )
        ),
        React.createElement(
          "div",
          { className: "landingHeroDots", "aria-label": "Hero slides" },
          HERO_SLIDES.map((item, index) =>
            React.createElement("button", {
              className:
                "landingHeroDot" +
                (index === this.state.slideIndex ? " landingHeroDotActive" : ""),
              type: "button",
              key: "dot-" + item.key,
              "aria-label": "Go to slide " + (index + 1),
              "aria-current": index === this.state.slideIndex ? "true" : undefined,
              onClick: () => this.goToSlide(index),
            })
          )
        )
      )
    );
  }
}
