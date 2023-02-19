export interface SunmiUHFPlugin {
  getScanModel(): Promise<{ model: "UHF_R2000"|"INNER"|"NONE"|"UNKNOWN", available: boolean }>;
  startScanning(options?: { repeat_times?: number }): Promise<void>;
  stopScanning(): Promise<void>;

  setAccessEpcMatch(options: { epc: string }): Promise<{ details: { start_time: number, end_time: number } }>;
  cancelAccessEpcMatch(): Promise<{ details: { start_time: number, end_time: number } }>;
  getAccessEpcMatch(): Promise<{ epc_match: string, details: { start_time: number, end_time: number } }>;
  readTag(options: { bank: "RESERVED"|"EPC"|"TID"|"USER", address: number, length: number, password?: string }): Promise<{ crc: string, pc: string, epc: string, data: string, details: { data_length: number, antenna: number, tag_read_count: number, start_time: number, end_time: number } }>;
  writeTag(options: { bank: "RESERVED"|"EPC"|"TID"|"USER", address: number, data: string, password?: string }): Promise<{ crc: string, pc: string, epc: string, details: { antenna: number, tag_read_count: number, start_time: number, end_time: number } }>;
}
