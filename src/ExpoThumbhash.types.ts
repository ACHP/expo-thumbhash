import { type ViewProps } from 'react-native';

export type ChangeEventPayload = {
  value: string;
};

export type OnLoadEvent = {
  hash: string;
};

export type ExpoThumbhashViewProps = {
  hash?: string;
  onLoaded?: (event: { nativeEvent: OnLoadEvent }) => void;
} & ViewProps;
