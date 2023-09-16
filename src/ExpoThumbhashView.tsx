import { requireNativeViewManager } from 'expo-modules-core';
import * as React from 'react';

import { ExpoThumbhashViewProps } from './ExpoThumbhash.types';

const NativeView: React.ComponentType<ExpoThumbhashViewProps> =
  requireNativeViewManager('ExpoThumbhash');

export default function ExpoThumbhashView(props: ExpoThumbhashViewProps) {
  return <NativeView {...props} />;
}
