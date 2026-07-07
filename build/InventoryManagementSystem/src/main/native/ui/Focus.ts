import React, { ReactNode } from "react";
import { FocusNode } from "../classes/FocusNode";
import BaseUIProps from "./BaseUIProps";

interface FocusProps extends BaseUIProps {
  focusNode?: FocusNode;
  child: ReactNode;
}

export default function Focus(props: FocusProps): ReactNode {
  return props.child;
}
