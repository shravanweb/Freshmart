import React, { Component, ReactNode } from "react";
import LandingCatalogApi, { PublicCategoryRecord } from "../utils/LandingCatalogApi";

const FOOTER_USEFUL_LINKS: string[][] = [
  ["Blog", "Privacy", "Terms", "FAQs", "Security", "Contact"],
  ["Partner", "Franchise", "Seller", "Warehouse", "Careers", "Stores"],
  ["Track Order", "Returns", "Help Center", "About Us", "Offers", "IMS Platform"],
];

const FOOTER_SOCIAL = [
  { name: "Facebook", href: "https://facebook.com", key: "fb" },
  { name: "X", href: "https://x.com", key: "x" },
  { name: "Instagram", href: "https://instagram.com", key: "ig" },
  { name: "LinkedIn", href: "https://linkedin.com", key: "li" },
  { name: "Threads", href: "https://threads.net", key: "threads" },
];

function renderFooterLinkColumn(links: string[], key: string): ReactNode {
  return React.createElement(
    "ul",
    { className: "landingFooterLinkCol", key },
    links.map((link, index) =>
      React.createElement(
        "li",
        { key: key + "-link-" + index },
        React.createElement("a", { className: "landingFooterLink", href: "#" }, link)
      )
    )
  );
}

function renderFooterSocialIcon(key: string): ReactNode {
  const svgProps = {
    viewBox: "0 0 24 24",
    "aria-hidden": true,
    className: "landingFooterSocialSvg",
    fill: "currentColor",
  };
  if (key === "fb") {
    return React.createElement(
      "svg",
      svgProps,
      React.createElement("path", {
        d: "M22 12a10 10 0 10-11.6 9.9v-7h-2v-3h2v-2.3c0-2 1.2-3.1 3-3.1.9 0 1.8.1 1.8.1v2h-1c-1 0-1.3.6-1.3 1.2V12h2.3l-.4 3h-1.9v7A10 10 0 0022 12z",
      })
    );
  }
  if (key === "x") {
    return React.createElement(
      "svg",
      svgProps,
      React.createElement("path", {
        d: "M18.9 3H22l-6.8 7.8L23 21h-6.7l-4.2-5.5L7.5 21H3.4l7.3-8.4L2 3h6.9l3.8 5.1L18.9 3zm-1.2 16h1.7L7.1 5H5.3l12.4 14z",
      })
    );
  }
  if (key === "ig") {
    return React.createElement(
      "svg",
      svgProps,
      React.createElement("path", {
        d: "M12 7.5A4.5 4.5 0 1016.5 12 4.5 4.5 0 0012 7.5zm0-2.2c2.9 0 3.2 0 4.4.1a3.2 3.2 0 011.2.2 2.1 2.1 0 011.2 1.2 3.2 3.2 0 01.2 1.2c0 1.2.1 1.5.1 4.4s0 3.2-.1 4.4a3.2 3.2 0 01-.2 1.2 2.1 2.1 0 01-1.2 1.2 3.2 3.2 0 01-1.2.2c-1.2 0-1.5.1-4.4.1s-3.2 0-4.4-.1a3.2 3.2 0 01-1.2-.2 2.1 2.1 0 01-1.2-1.2 3.2 3.2 0 01-.2-1.2c0-1.2-.1-1.5-.1-4.4s0-3.2.1-4.4a3.2 3.2 0 01.2-1.2 2.1 2.1 0 011.2-1.2 3.2 3.2 0 011.2-.2c1.2-.1 1.5-.1 4.4-.1zM12 4.8c-2.8 0-3.1 0-4.2.1a2 2 0 00-.7.1 1 1 0 00-.6.6 2 2 0 00-.1.7c-.1 1.1-.1 1.4-.1 4.2s0 3.1.1 4.2a2 2 0 00.1.7 1 1 0 00.6.6 2 2 0 00.7.1c1.1.1 1.4.1 4.2.1s3.1 0 4.2-.1a2 2 0 00.7-.1 1 1 0 00.6-.6 2 2 0 00.1-.7c.1-1.1.1-1.4.1-4.2s0-3.1-.1-4.2a2 2 0 00-.1-.7 1 1 0 00-.6-.6 2 2 0 00-.7-.1c-1.1-.1-1.4-.1-4.2-.1z",
      }),
      React.createElement("circle", { cx: "17.4", cy: "6.6", r: "1.1" })
    );
  }
  if (key === "threads") {
    return React.createElement(
      "svg",
      svgProps,
      React.createElement("path", {
        d: "M12 2.2c-3.1 0-5.6 2.5-5.6 5.6 0 2.9 2.2 5.3 5 5.6v-2.5a3.1 3.1 0 112.2-3v6.4c0 2.5-2 4.5-4.5 4.5S4.6 17.7 4.6 15.2h2.2c0 1.3 1 2.3 2.3 2.3s2.3-1 2.3-2.3V7.8h2.2v.6a5.5 5.5 0 003.4-1.2A5.6 5.6 0 0012 2.2z",
      })
    );
  }
  return React.createElement(
    "svg",
    svgProps,
    React.createElement("path", {
      d: "M6.5 3A2.5 2.5 0 004 5.5v13A2.5 2.5 0 006.5 21h2A2.5 2.5 0 0011 18.5v-5.8c0-1 .8-1.8 1.8-1.8s1.8.8 1.8 1.8v5.8A2.5 2.5 0 0017.5 21h2A2.5 2.5 0 0022 18.5V9.8c0-2.8-2.2-5-5-5-1.4 0-2.6.6-3.5 1.5V5.5A2.5 2.5 0 0011 3H6.5z",
    })
  );
}

function getFooterCategoryColumns(categories: PublicCategoryRecord[]): string[][] {
  const names: string[] = [];
  for (const category of categories) {
    names.push(category.name);
  }
  if (names.length === 0) {
    return [];
  }
  const midpoint = Math.ceil(names.length / 2);
  return [names.slice(0, midpoint), names.slice(midpoint)];
}

export interface LandingFooterProps {
  categories?: PublicCategoryRecord[];
}

interface LandingFooterState {
  categories: PublicCategoryRecord[];
}

export default class LandingFooter extends Component<LandingFooterProps, LandingFooterState> {
  public constructor(props: LandingFooterProps) {
    super(props);
    this.state = { categories: props.categories ?? [] };
  }

  public componentDidMount(): void {
    if (!this.props.categories?.length) {
      this.loadCategories();
    }
  }

  public componentDidUpdate(prevProps: LandingFooterProps): void {
    if (this.props.categories && this.props.categories !== prevProps.categories) {
      this.setState({ categories: this.props.categories });
    }
  }

  private loadCategories = async (): Promise<void> => {
    try {
      const items = await LandingCatalogApi.getPublicCategories();
      this.setState({ categories: items });
    } catch {
      // Footer still renders without dynamic categories.
    }
  };

  public render(): ReactNode {
    const categoryColumns = getFooterCategoryColumns(this.state.categories);

    return React.createElement(
      "footer",
      { className: "landingFooter", key: "footer" },
      React.createElement(
        "div",
        { className: "landingContainer landingFooterInner" },
        React.createElement(
          "div",
          { className: "landingFooterBrand" },
          React.createElement("img", {
            src: "/images/freshmart.png",
            alt: "FreshMart",
            className: "landingFooterLogo",
          }),
          React.createElement(
            "p",
            { className: "landingFooterTagline" },
            "Fresh groceries & inventory — delivered fast across your city."
          )
        ),
        React.createElement(
          "div",
          { className: "landingFooterMainRow" },
          React.createElement(
            "div",
            { className: "landingFooterSection landingFooterSectionUseful" },
            React.createElement("h3", { className: "landingFooterHeading" }, "Useful Links"),
            React.createElement(
              "div",
              { className: "landingFooterLinkGrid" },
              FOOTER_USEFUL_LINKS.map((column, index) =>
                renderFooterLinkColumn(column, "useful-" + index)
              )
            )
          ),
          React.createElement(
            "div",
            { className: "landingFooterSection landingFooterSectionCategories" },
            React.createElement(
              "div",
              { className: "landingFooterHeadingRow" },
              React.createElement("h3", { className: "landingFooterHeading" }, "Categories"),
              React.createElement(
                "a",
                { className: "landingFooterSeeAll", href: "#" },
                "see all",
                React.createElement("span", { className: "landingFooterSeeAllIcon" }, "›")
              )
            ),
            categoryColumns.length > 0
              ? React.createElement(
                  "div",
                  { className: "landingFooterLinkGrid landingFooterCategoryGrid" },
                  categoryColumns.map((column, index) =>
                    renderFooterLinkColumn(column, "cat-" + index)
                  )
                )
              : null
          )
        )
      ),
      React.createElement(
        "div",
        { className: "landingFooterBottomBar" },
        React.createElement(
          "div",
          { className: "landingContainer landingFooterBottomInner" },
          React.createElement("p", { className: "landingFooterCopy" }, "© FreshMart Retail Group, 2026"),
          React.createElement(
            "div",
            { className: "landingFooterAppBlock" },
            React.createElement("span", { className: "landingFooterAppLabel" }, "Download App"),
            React.createElement(
              "div",
              { className: "landingFooterAppButtons" },
              React.createElement(
                "a",
                {
                  className: "landingFooterAppBtn",
                  href: "#",
                  "aria-label": "Download on the App Store",
                },
                React.createElement(
                  "svg",
                  { viewBox: "0 0 24 24", className: "landingFooterAppIcon", "aria-hidden": "true" },
                  React.createElement("path", {
                    d: "M16.5 12.2c-.1-2.1 1.7-3.1 1.8-3.2-1-.1-2-.6-2.5-1.4-.6-.8-.9-1.9-.8-3 .9-.1 1.8.5 2.3 1.3.5.8.7 1.8.6 2.8-.9.1-1.7-.4-2.4-1.5zm1.2 1.9c-1.3-.1-2.4.8-3 .8-.6 0-1.6-.7-2.7-.7-1.4 0-2.7.8-3.4 2.1-1.5 2.5-.4 6.2 1 8.2.7 1 1.5 2.1 2.6 2.1 1 0 1.4-.7 2.7-.7 1.3 0 1.6.7 2.7.7 1.1 0 1.8-1 2.5-2 .8-1.1 1.1-2.2 1.1-2.3-.1 0-2.2-.8-2.2-3.2zM14.7 5.5c.6-.7 1-1.7.9-2.7-1 .1-2.1.6-2.8 1.3-.6.6-1.1 1.6-.9 2.6 1.1.1 2.2-.5 2.8-1.2z",
                    fill: "currentColor",
                  })
                ),
                React.createElement(
                  "span",
                  { className: "landingFooterAppText" },
                  React.createElement("small", null, "Download on the"),
                  React.createElement("strong", null, "App Store")
                )
              ),
              React.createElement(
                "a",
                {
                  className: "landingFooterAppBtn",
                  href: "#",
                  "aria-label": "Get it on Google Play",
                },
                React.createElement(
                  "svg",
                  { viewBox: "0 0 24 24", className: "landingFooterAppIcon", "aria-hidden": "true" },
                  React.createElement("path", {
                    d: "M3.6 2.4l10.2 10.2-2.4 2.4L2 4.2c-.4-.7.1-1.8 1.6-1.8zm0 19.2c-1.5 0-2-1.1-1.6-1.8l9.4-10.8 2.4 2.4L3.6 21.6zM14.4 12.6l2.5-2.5 4.1 2.3c1.2.7 1.2 1.9 0 2.6l-4.1 2.3-2.5-2.5 2.5-2.2zM5.8 3.6l8.6 8.6-2.5 2.5L3.3 6.1c-.5-.8 0-1.8 2.5-2.5z",
                    fill: "currentColor",
                  })
                ),
                React.createElement(
                  "span",
                  { className: "landingFooterAppText" },
                  React.createElement("small", null, "Get it on"),
                  React.createElement("strong", null, "Google Play")
                )
              )
            )
          ),
          React.createElement(
            "div",
            { className: "landingFooterSocial" },
            FOOTER_SOCIAL.map((item) =>
              React.createElement(
                "a",
                {
                  className: "landingFooterSocialBtn",
                  href: item.href,
                  key: item.key,
                  target: "_blank",
                  rel: "noopener noreferrer",
                  "aria-label": item.name,
                },
                renderFooterSocialIcon(item.key)
              )
            )
          )
        )
      ),
      React.createElement(
        "div",
        { className: "landingFooterDisclaimerWrap" },
        React.createElement(
          "div",
          { className: "landingContainer" },
          React.createElement(
            "p",
            { className: "landingFooterDisclaimer" },
            '"FreshMart" is owned & managed by FreshMart Retail Group. Product names, trademarks and brands are property of their respective owners. FreshMart operates as an independent grocery retail and inventory management platform. All content, pricing and availability are subject to change without notice.'
          )
        )
      )
    );
  }
}
