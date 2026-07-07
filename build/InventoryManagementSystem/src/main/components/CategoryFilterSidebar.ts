import React, { ReactElement, ReactNode } from "react";
import {
  CATEGORY_FILTER_GROUPS,
  CategoryFilterGroup,
  CategoryFilterOptions,
  CategoryFilterSelections,
} from "../utils/CategoryProductFilters";

export interface CategoryFilterSidebarProps {
  options: CategoryFilterOptions;
  selections: CategoryFilterSelections;
  onToggle: (group: CategoryFilterGroup, value: string) => void;
  onClear: () => void;
}

function renderFilterGroup(
  group: CategoryFilterGroup,
  label: string,
  items: string[],
  selections: CategoryFilterSelections,
  onToggle: (group: CategoryFilterGroup, value: string) => void
): ReactNode {
  if (items.length === 0) {
    return null;
  }

  const selected = selections[group];

  return React.createElement(
    "section",
    { className: "categoryFilterGroup", key: group },
    React.createElement("h3", { className: "categoryFilterGroupTitle" }, label),
    React.createElement(
      "div",
      { className: "categoryFilterPills" },
      items.map((item) => {
        const checked = selected.some((value) => value.toLowerCase() === item.toLowerCase());

        return React.createElement(
          "button",
          {
            className:
              "categoryFilterPill" + (checked ? " categoryFilterPillActive" : ""),
            key: group + "-" + item,
            type: "button",
            onClick: () => onToggle(group, item),
            "aria-pressed": checked,
          },
          item
        );
      })
    )
  );
}

export default function CategoryFilterSidebar(
  props: CategoryFilterSidebarProps
): ReactElement | null {
  const activeGroups = CATEGORY_FILTER_GROUPS.filter(
    (group) => props.options[group.key].length > 0
  );

  if (activeGroups.length === 0) {
    return null;
  }

  const hasSelection =
    props.selections.brands.length > 0 ||
    props.selections.flavours.length > 0 ||
    props.selections.packSizes.length > 0 ||
    props.selections.productTypes.length > 0 ||
    props.selections.namkeens.length > 0;

  return React.createElement(
    "aside",
    { className: "categoryProductsSidebar", "aria-label": "Product filters" },
    React.createElement(
      "div",
      { className: "categoryFilterPanel" },
      React.createElement(
        "div",
        { className: "categoryFilterPanelHead" },
        React.createElement(
          "div",
          { className: "categoryFilterPanelTitleRow" },
          React.createElement(
            "svg",
            {
              className: "categoryFilterPanelIcon",
              viewBox: "0 0 24 24",
              "aria-hidden": "true",
            },
            React.createElement("path", {
              d: "M3 5a1 1 0 011-1h16a1 1 0 01.8 1.6l-5.2 6.93V18a1 1 0 01-.55.9l-4 2A1 1 0 019 20v-6.47L3.2 6.6A1 1 0 013 5z",
              fill: "currentColor",
            })
          ),
          React.createElement("h2", { className: "categoryFilterPanelTitle" }, "Refine by")
        ),
        hasSelection
          ? React.createElement(
              "button",
              {
                className: "categoryFilterClearBtn",
                type: "button",
                onClick: props.onClear,
              },
              "Clear"
            )
          : null
      ),
      React.createElement(
        "div",
        { className: "categoryFilterPanelBody" },
        activeGroups.map((group) =>
          renderFilterGroup(
            group.key,
            group.label,
            props.options[group.key],
            props.selections,
            props.onToggle
          )
        )
      )
    )
  );
}
