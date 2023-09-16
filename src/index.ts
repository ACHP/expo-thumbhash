import { NativeModulesProxy, EventEmitter, Subscription } from 'expo-modules-core';

// Import the native module. On web, it will be resolved to ExpoThumbhash.web.ts
// and on native platforms to ExpoThumbhash.ts
import ExpoThumbhashModule from './ExpoThumbhashModule';
import ExpoThumbhashView from './ExpoThumbhashView';
import { ChangeEventPayload, ExpoThumbhashViewProps } from './ExpoThumbhash.types';

// Get the native constant value.
export const PI = ExpoThumbhashModule.PI;

export function hello(): string {
  return ExpoThumbhashModule.hello();
}

export async function setValueAsync(value: string) {
  return await ExpoThumbhashModule.setValueAsync(value);
}

const emitter = new EventEmitter(ExpoThumbhashModule ?? NativeModulesProxy.ExpoThumbhash);

export function addChangeListener(listener: (event: ChangeEventPayload) => void): Subscription {
  return emitter.addListener<ChangeEventPayload>('onChange', listener);
}

export { ExpoThumbhashView, ExpoThumbhashViewProps, ChangeEventPayload };
