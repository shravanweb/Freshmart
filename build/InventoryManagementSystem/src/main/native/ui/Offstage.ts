import React, { ReactNode } from "react";
import BaseUIProps from "./BaseUIProps";

interface OffstageProps extends BaseUIProps {
  offstage: boolean;
  child: ReactNode;
}

class _Offstage extends React.Component<OffstageProps, {}> {}

export default function Offstage(props: OffstageProps) {
  return React.createElement(_Offstage, props);
}
