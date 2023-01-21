import { WebPlugin } from '@capacitor/core';

import type { SunmiUHFPlugin } from './definitions';

export class SunmiUHFWeb extends WebPlugin implements SunmiUHFPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  startScanning(): Promise<void> {
    return Promise.resolve(undefined);
  }

  stopScanning(): Promise<void> {
    return Promise.resolve(undefined);
  }
}
