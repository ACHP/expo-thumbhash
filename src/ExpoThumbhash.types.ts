import { type ViewProps } from 'react-native';

export type ChangeEventPayload = {
  value: string;
};

export type OnLoadEvent = {
  url: string;
};

export type ExpoThumbhashViewProps = {
  url?: string;
  onLoad?: (event: { nativeEvent: OnLoadEvent }) => void;
} & ViewProps;
