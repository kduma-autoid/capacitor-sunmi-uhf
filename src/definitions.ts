export interface SunmiUHFPlugin {
  getScanModel(): Promise<{ model: "UHF_R2000"|"INNER"|"NONE"|"UNKNOWN", available: boolean }>;
  startScanning(options?: { repeat_times?: number }): Promise<void>;
  stopScanning(): Promise<void>;

  setAccessEpcMatch(options: { epc: string }): Promise<void>;
  cancelAccessEpcMatch(): Promise<void>;
  getAccessEpcMatch(): Promise<{ epc: string }>;
  readTag(options: { bank: "RESERVED"|"EPC"|"TID"|"USER", address: number, length: number, password?: string }): Promise<{pc: string, crc: string, epc: string, data: string }>;
}
