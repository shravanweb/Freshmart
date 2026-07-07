import { ChannelConstants } from "./D3ETemplate";
import WSReader from "./WSReader";

export default class Channels {
  public static onMessage(reader: WSReader): void {
    let channel: number = reader.readInteger();

    switch (channel) {
    }
  }
  public static reset(): void {}
}
