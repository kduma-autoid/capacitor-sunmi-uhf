export interface SunmiUHFPlugin {
  /**
   * Get RFID type
   *
   * @see 2.4.1.1. in docs
   */
  getScanModel(): Promise<{ model: "UHF_R2000"|"INNER"|"NONE"|"UNKNOWN", available: boolean }>;

  startScanning(options?: { repeat_times?: number }): Promise<void>;

  stopScanning(): Promise<void>;

  /**
   * Tag operation – read tags
   *
   * @see 2.4.3.1. in docs
   */
  readTag(options: { bank: "RESERVED"|"EPC"|"TID"|"USER", address: number, length: number, password?: string }): Promise<{ crc: string, pc: string, epc: string, data: string, details: { data_length: number, antenna: number, tag_read_count: number, start_time: number, end_time: number } }>;

  /**
   * Tag operation – write tags
   *
   * @see 2.4.3.2. in docs
   */
  writeTag(options: { bank: "RESERVED"|"EPC"|"TID"|"USER", address: number, data: string, password?: string }): Promise<{ crc: string, pc: string, epc: string, details: { antenna: number, tag_read_count: number, start_time: number, end_time: number } }>;

  /**
   * Tag operation – set the matched EPC to be accessed (EPC match is valid until the next refresh)
   *
   * @see 2.4.3.5. in docs
   */
  setAccessEpcMatch(options: { epc: string }): Promise<{ details: { start_time: number, end_time: number } }>;

  /**
   * Tag operation – clear the matched EPC to be accessed
   *
   * @see 2.4.3.6. in docs
   */
  cancelAccessEpcMatch(): Promise<{ details: { start_time: number, end_time: number } }>;

  /**
   * Tag operation – get EPC match
   *
   * @see 2.4.3.7. in docs
   */
  getAccessEpcMatch(): Promise<{ epc_match: string, details: { start_time: number, end_time: number } }>;
}
