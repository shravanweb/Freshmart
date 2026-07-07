import * as ui from "../native/index";

type ImageLoaded = (url: string, img: ui.Image) => void;

export default class ImageLoader {
  listener: ImageLoaded;
  images: Map<String, ui.Image> = new Map();
  headers: Map<String, String>;
  constructor(listener: ImageLoaded, headers: Map<String, String>) {
    this.listener = listener;
    this.headers = headers;
  }

  loadImage(imageAssetPath: string): Promise<ui.Image> {
    //TODO data: ByteData = await rootBundle.load(imageAssetPath);
    // completer: Completer<UI.Image> = Completer();
    // UI.decodeImageFromList(Uint8List.view(data.buffer), (UI.Image img) {
    //   return completer.complete(img);
    // });
    // return completer.future;
    return null;
  }

  getImage(url: string, onLoad: () => void): ui.Image {
    //TODO if (images.containsKey(url)) {
    //   return images[url];
    // }
    // let provider = ui.NetworkImage(
    //   url,
    //   headers: headers,
    //   scale: 1,
    // );
    // ImageStream stream = provider.resolve(ImageConfiguration.empty);
    // stream.addListener(ImageStreamListener((info, isSync) {
    //   images[url] = info.image;
    //   listener(url, info.image);
    //   onLoad();
    // }, onError: (e, s) {}));
    return null;
  }
}
