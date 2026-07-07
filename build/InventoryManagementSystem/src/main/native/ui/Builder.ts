import { ReactNode } from "react";
import BaseUIProps from "./BaseUIProps";

interface BuilderProps extends BaseUIProps {
  builder: (context: any) => ReactNode;
}

export default function Builder(props: BuilderProps) {
  return props.builder({});
}
