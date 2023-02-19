import { WebPlugin } from '@capacitor/core';

import type { SunmiUHFPlugin } from './definitions';

export class SunmiUHFWeb extends WebPlugin implements SunmiUHFPlugin {
  cancelAccessEpcMatch(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  echo(): Promise<{ value: string }> {
    throw this.unimplemented('Not implemented on web.');
  }

  getAccessEpcMatch(): Promise<{ epc: string }> {
    throw this.unimplemented('Not implemented on web.');
  }

  getScanModel(): Promise<{ model: "UHF_R2000" | "INNER" | "NONE" | "UNKNOWN"; available: boolean }> {
    throw this.unimplemented('Not implemented on web.');
  }

  readTag(): Promise<{ pc: string; crc: string; epc: string; data: string }> {
    throw this.unimplemented('Not implemented on web.');
  }

  setAccessEpcMatch(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  startScanning(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  stopScanning(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

}
