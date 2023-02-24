interface Window {
  addEventListener(type: "sunmi_uhf_tag_read", listener: (ev: { epc: string, pc: string, frequency: string, rrsi: string, antenna: number, last_updated: number, read_count: number }) => any, useCapture?: boolean): void;
  addEventListener(type: "sunmi_uhf_read_completed", listener: (ev: { rate: number, tags_read: number, details: { start_time: number, end_time: number } }) => any, useCapture?: boolean): void;
  addEventListener(type: "sunmi_uhf_debug", listener: (ev: { action: string, cmd: string, state?: string, tag?: string, params?: string, errorCode?: string, msg?: string }) => any, useCapture?: boolean): void;
}