export type CallbackID = string;

import type { PluginListenerHandle } from '@capacitor/core';

export interface SunmiUHFPlugin {
  /**
   * Get RFID type
   *
   * **Returns:**
   *
   * - `model` - RFID type: `UHF_S7100` - UHF S7100, `UHF_R2000` - UHF R2000, `INNER` - Inner RFID, `NONE` - No RFID module, `UNKNOWN` - Unknown RFID module.
   * - `available` - Whether the RFID module is available.
   *
   * @see 2.4.1.1. in docs
   */
  getScanModel(): Promise<{ model: "UHF_S7100"|"UHF_R2000"|"INNER"|"NONE"|"UNKNOWN", available: boolean }>;

  /**
   * Starts scanning inventory operation
   *
   * **Parameters:**
   *
   * - `options.repeat_times` - The number of times the inventory to be repeated. If it is `0xFF`, the time used in this inventory round is the shortest time. If there is only one tag in the RF area, the time used in this inventory round is 30-50mS. This parameter value is normally used when quickly polling with several antennas in a four-channel device.
   *
   * **Please note:** When the parameter `options.repeat_times` is set as `255`(`0xFF`), the algorithm specially designed for reading a small amount of tags will be enabled. It is more efficient and sensitive to read a small amount of tags. However, this parameter is not suitable to be used to simultaneously read a large amount of tags.
   *
   * @see 2.4.2.2. in docs
   */
  startScanning(options?: { repeat_times?: number }): Promise<void>;

  /**
   * Sets TagReadCallback
   */
  setTagReadCallback(callback: (data: { epc: string, pc: string, frequency: string, rrsi: string, antenna: number, last_updated: number, read_count: number }) => void): Promise<CallbackID>;

  /**
   * Removes TagReadCallback
   */
  clearTagReadCallback(): Promise<void>;

  /**
   * Sets InventoryScanCompletedCallback
   */
  setInventoryScanCompletedCallback(callback: (data: { rate: number, tags_read: number, details: { start_time: number, end_time: number } }) => void): Promise<CallbackID>;

  /**
   * Removes InventoryScanCompletedCallback
   */
  clearInventoryScanCompletedCallback(): Promise<void>;

  /**
   * Stops scanning
   */
  stopScanning(): Promise<void>;

  /**
   * Tag operation – read tags
   *
   * **Parameters:**
   *
   * - `options.bank` - Tag memory bank: `RESERVED` - Reserved Memory, `EPC` - EPC Memory, `TID` - TID Memory, `USER` - User Memory.
   * - `options.address` - The first word address of the data read. For the value range, please refer to tag specs.
   * - `options.length` - The data length, word length or WORD (16 bits) length of the data read; For the value range, please refer to tag specs.
   * - `options.password` - Tag access password. 4 bytes. If the tag does not have a password, this parameter is not required.
   *
   * **Please note:** `setAccessEpcMatch` should be done first. Tags with the same EPC but different data read will be deemed as different tags.
   *
   * @see 2.4.3.1. in docs
   */
  readTag(options: { bank: "RESERVED"|"EPC"|"TID"|"USER", address: number, length: number, password?: string }): Promise<{ crc: string, pc: string, epc: string, data: string, details: { data_length: number, antenna: number, tag_read_count: number, start_time: number, end_time: number } }>;

  /**
   * Tag operation – write tags
   *
   * **Parameters:**
   *
   * - `options.bank` - Tag memory bank: `RESERVED` - Reserved Memory, `EPC` - EPC Memory, `TID` - TID Memory, `USER` - User Memory.
   * - `options.address` - The first word address of the data written. For the value range, please refer to tag specs; it usually starts from 02 if it was written into EPC memory bank, and PC + CRC are stored in the first four bytes of this area.
   * - `options.data` - The data written in Hex String Format.
   * - `options.password` - Tag access password. 4 bytes. If the tag does not have a password, this parameter is not required.
   *
   * **Please note:** `setAccessEpcMatch` should be done first. Tags with the same EPC but different data read will be deemed as different tags.
   *
   * @see 2.4.3.2. in docs
   */
  writeTag(options: { bank: "RESERVED"|"EPC"|"TID"|"USER", address: number, data: string, password?: string }): Promise<{ crc: string, pc: string, epc: string, details: { antenna: number, tag_read_count: number, start_time: number, end_time: number } }>;

  /**
   * Tag operation – lock tags
   *
   * **Parameters:**
   *
   * - `options.bank` - Tag memory bank: `USER` - User Memory, `TID` - TID Memory, `EPC` - EPC Memory, `ACCESS_PASSWORD` - Access Password, `KILL_PASSWORD` - Kill Password.
   * - `options.type` - The types of lock operation: `OPEN` - open, `LOCK` - lock, `PERM_OPEM` - permanently open, `PERM_LOCK` - permanently locked.
   * - `options.password` - Tag access password. 4 bytes.
   *
   * **Please note:** `setAccessEpcMatch` should be done first. Tags with the same EPC but different data read will be deemed as different tags.
   *
   * @see 2.4.3.3. in docs
   */
  lockTag(options: { bank: "USER"|"TID"|"EPC"|"ACCESS_PASSWORD"|"KILL_PASSWORD", type: "OPEN"|"LOCK"|"PERM_OPEM"|"PERM_LOCK", password: string }): Promise<{ crc: string, pc: string, epc: string, details: { antenna: number, tag_read_count: number, start_time: number, end_time: number } }>;

  /**
   * Tag operation – kill tags
   *
   * **Parameters:**
   *
   * - `options.password` - Tag access password. 4 bytes.
   *
   * **Please note:** `setAccessEpcMatch` should be done first. Tags with the same EPC but different data read will be deemed as different tags.
   *
   * @see 2.4.3.4. in docs
   */
  killTag(options: { password: string }): Promise<{ crc: string, pc: string, epc: string, details: { antenna: number, tag_read_count: number, start_time: number, end_time: number } }>;

  /**
   * Tag operation – set the matched EPC to be accessed (EPC match is valid until the next refresh)
   *
   * **Parameters:**
   *
   * - `options.epc` - EPC number in Hex String Format.
   *
   * **Please note:** Tags with the same EPC but different data read will be deemed as different tags.
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

  /**
   * Tag operation – set FastTID (only valid to some models of Impinj Monza tags)
   *
   * **Parameters:**
   *
   * - `options.enable` - FastTID on or off status.
   * - `options.save` - Save the configuration into the internal Flash, which will prevent it from being lost due to an outage. Default is `false`.
   *
   * **Please note:** This function is only effective to some models of Impinj Monza tags.
   *
   * **Please note:** This function recognizes EPC and TID at the same time, thus drastically enhancing the efficiency of reading TID.
   *
   * **Please note:** After turning on this function, tags of specific model will package TID into EPC during inventory. Therefore, the PC of a tag will be changed, and the original PC + EPC will become: the modified PC + EPC + (CRC of EPC) + TID.
   *
   * **Please note:** If there is something goes wrong when recognizing a TID, the original PC + EPC will be uploaded. ★Please turn off this function if you do not need it to avoid unnecessary time used.
   *
   * @see 2.4.3.8. in docs
   */
  setImpinjFastTid(options: { enable: boolean, save?: boolean }): Promise<{ details: { start_time: number, end_time: number } }>;

  /**
   * Tag operation – inquire FastTID
   *
   * @see 2.4.3.9. in docs
   */
  getImpinjFastTid(): Promise<{ status: number, details: { start_time: number, end_time: number } }>;

  /**
   * Refreshes the battery charging state. The resulting battery state will be returned in `onBatteryChargeState` events.
   *
   * @throws {Error} If the device does not support this feature.
   */
  getBatteryChargeState(): Promise<void>;

  /**
   * Refreshes the battery remaining percent. The resulting battery remaining percent will be returned in `onBatteryRemainingPercent` events.
   *
   * @throws {Error} If the device does not support this feature.
   */
  getBatteryRemainingPercent(): Promise<void>;


  /**
   * Refreshes the battery cycles. The resulting battery cycles will be returned in `onBatteryChargeNumTimes` events.
   *
   * @throws {Error} If the device does not support this feature.
   */
  getBatteryChargeNumTimes(): Promise<void>;

  /**
   * Listens for reader connected events.
   */
  addListener(
      eventName: 'onReaderConnected',
      listenerFunc: () => void,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;

  /**
   * Listens for reader booted events.
   */
  addListener(
      eventName: 'onReaderBoot',
      listenerFunc: () => void,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;

  /**
   * Listens for reader connected or booted events.
   */
  addListener(
      eventName: 'onReaderBootOrConnected',
      listenerFunc: () => void,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;

  /**
   * Listens for reader disconnected  events.
   */
  addListener(
      eventName: 'onReaderDisconnected',
      listenerFunc: () => void,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;

  /**
   * Listens for reader lost connection events.
   */
  addListener(
      eventName: 'onReaderLostConnection',
      listenerFunc: () => void,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;

  /**
   * Listens for reader disconnected or lost connection events.
   */
  addListener(
      eventName: 'onReaderDisconnectedOrLostConnection',
      listenerFunc: () => void,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;

  /**
   *  Listens for battery remaining percent events.
   */
  addListener(
      eventName: 'onBatteryRemainingPercent',
      listenerFunc: (event: { charge_level: number }) => void,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;

  /**
   * Listens for battery low electricity events.
   */
  addListener(
      eventName: 'onBatteryLowElectricity',
      listenerFunc: (event: { charge_level: number }) => void,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;

  /**
   * Listens for battery remaining percent or low electricity events.
   */
  addListener(
      eventName: 'onBatteryRemainingPercentOrLowElectricity',
      listenerFunc: (event: { charge_level: number }) => void,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;

  /**
   * Listens for battery charge state events.
   */
  addListener(
      eventName: 'onBatteryChargeState',
      listenerFunc: (event: { state: "Unknown"|"NotCharging"|"PreCharging"|"QuickCharging"|"Charged" }) => void,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;

  /**
   * Listens for battery charge num times events.
   */
  addListener(
      eventName: 'onBatteryChargeNumTimes',
      listenerFunc: (event: { battery_cycles: number  }) => void,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;

  /**
   * Removes all listeners
   */
  removeAllListeners(): Promise<void>;
}
