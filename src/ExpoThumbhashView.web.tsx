import * as React from 'react';

import { ExpoThumbhashViewProps } from './ExpoThumbhash.types';

export default function ExpoThumbhashView(props: ExpoThumbhashViewProps) {
  return (
    <div>
      <span>{props.name}</span>
    </div>
  );
}
