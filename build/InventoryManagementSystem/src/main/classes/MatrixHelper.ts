import { Matrix4, Offset } from "../native";

export class MatrixHelper {
  constructor() {}
  public static getMatrixData(offSet: Offset, data: number): Matrix4 {
    return Matrix4.identity().translate(offSet.dx, offSet.dy).scale(data);
  }

  public static rotateWithAngle(angle: number): Matrix4 {
    let val = (angle * Math.PI) / 180;
    return Matrix4.rotationZ(val);
  }
}
