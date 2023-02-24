import { WebPlugin } from '@capacitor/core';

import type { CallbackID, SunmiUHFPlugin } from './definitions';

export class SunmiUHFWeb extends WebPlugin implements SunmiUHFPlugin {
  cancelAccessEpcMatch(): Promise<{ details: { start_time: number; end_time: number } }> {
      throw this.unimplemented('Not implemented on web.');
  }

  getAccessEpcMatch(): Promise<{ epc_match: string; details: { start_time: number; end_time: number } }> {
      throw this.unimplemented('Not implemented on web.');
  }

  getScanModel(): Promise<{ model: "UHF_R2000" | "INNER" | "NONE" | "UNKNOWN"; available: boolean }> {
      throw this.unimplemented('Not implemented on web.');
  }

  readTag(): Promise<{ crc: string; pc: string; epc: string; data: string; details: { data_length: number; antenna: number; tag_read_count: number; start_time: number; end_time: number } }> {
    throw this.unimplemented('Not implemented on web.');
  }

  setAccessEpcMatch(): Promise<{ details: { start_time: number; end_time: number } }> {
    throw this.unimplemented('Not implemented on web.');
  }

  startScanning(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  stopScanning(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  writeTag(): Promise<{ crc: string; pc: string; epc: string; details: { antenna: number; tag_read_count: number; start_time: number; end_time: number } }> {
    throw this.unimplemented('Not implemented on web.');
  }

  killTag(): Promise<{ crc: string; pc: string; epc: string; details: { antenna: number; tag_read_count: number; start_time: number; end_time: number } }> {
    throw this.unimplemented('Not implemented on web.');
  }

  lockTag(): Promise<{ crc: string; pc: string; epc: string; details: { antenna: number; tag_read_count: number; start_time: number; end_time: number } }> {
    throw this.unimplemented('Not implemented on web.');
  }

  getImpinjFastTid(): Promise<{ status: number; details: { start_time: number; end_time: number } }> {
    throw this.unimplemented('Not implemented on web.');
  }

  setImpinjFastTid(): Promise<{ details: { start_time: number; end_time: number } }> {
    throw this.unimplemented('Not implemented on web.');
  }

  clearInventoryScanCompletedCallback(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  clearTagReadCallback(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  setInventoryScanCompletedCallback(): Promise<CallbackID> {
    throw this.unimplemented('Not implemented on web.');
  }

  setTagReadCallback(): Promise<CallbackID> {
    throw this.unimplemented('Not implemented on web.');
  }

}
