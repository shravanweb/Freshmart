import React, { ReactNode, createRef } from "react";
import BaseUIProps, { processBaseUIProps } from "../native/ui/BaseUIProps";
import ObservableComponent from "./ObservableComponent";

interface VideoPlayerViewProps extends BaseUIProps {
  videoUrl: string;
  videoType?: string;
  videoWidth?: number;
  videoHeight?: number;
  autoPlay?: boolean;
}

class _VideoPlayerView extends ObservableComponent<VideoPlayerViewProps> {
  videoRef = createRef<HTMLVideoElement>();

  componentDidMount() {
    const videoEl = this.videoRef.current;

    if (videoEl) {
      videoEl.muted = true;

      if (this.props.autoPlay !== false) {
        videoEl.play().catch((err) => console.warn("Autoplay prevented:", err));
      }
    }
  }

  playVideo() {
    const videoEl = this.videoRef.current;
    if (videoEl) {
      videoEl.play().catch((err) => console.warn("Play prevented:", err));
    }
  }

  render(): ReactNode {
    const isAutoPlay = this.props.autoPlay !== false;

    return React.createElement(
      "video",
      {
        ref: this.videoRef,
        width: this.props.videoWidth ?? 300,
        height: this.props.videoHeight ?? 200,
        autoPlay: isAutoPlay,
        muted: true,
        playsInline: true,
        loop: isAutoPlay,
        controls: !isAutoPlay,
        ...processBaseUIProps(this.props),
      },
      React.createElement("source", {
        src: this.props.videoUrl,
        type: "video/mp4",
      })
    );
  }
}

export default function VideoPlayerView(props: VideoPlayerViewProps) {
  return React.createElement(_VideoPlayerView, {
    ...props,
  });
}
