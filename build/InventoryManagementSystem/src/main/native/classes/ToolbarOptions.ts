export default class ToolbarOptions {
  copy?: boolean;
  cut?: boolean;
  paste?: boolean;
  selectAll?: boolean;
  constructor(props: Partial<ToolbarOptions>) {
    Object.assign(this, props);
  }
}
