# @kduma-sunmi/capacitor-sunmi-uhf

Binding for Sunmi's UHF module SDK

## Install

```bash
npm install @kduma-sunmi/capacitor-sunmi-uhf
npx cap sync
```

## API

<docgen-index>

* [`getScanModel()`](#getscanmodel)
* [`startScanning(...)`](#startscanning)
* [`setTagReadCallback(...)`](#settagreadcallback)
* [`clearTagReadCallback()`](#cleartagreadcallback)
* [`setInventoryScanCompletedCallback(...)`](#setinventoryscancompletedcallback)
* [`clearInventoryScanCompletedCallback()`](#clearinventoryscancompletedcallback)
* [`stopScanning()`](#stopscanning)
* [`readTag(...)`](#readtag)
* [`writeTag(...)`](#writetag)
* [`lockTag(...)`](#locktag)
* [`killTag(...)`](#killtag)
* [`setAccessEpcMatch(...)`](#setaccessepcmatch)
* [`cancelAccessEpcMatch()`](#cancelaccessepcmatch)
* [`getAccessEpcMatch()`](#getaccessepcmatch)
* [`setImpinjFastTid(...)`](#setimpinjfasttid)
* [`getImpinjFastTid()`](#getimpinjfasttid)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getScanModel()

```typescript
getScanModel() => Promise<{ model: "UHF_R2000" | "INNER" | "NONE" | "UNKNOWN"; available: boolean; }>
```

Get RFID type

**Returns:** <code>Promise&lt;{ model: 'UHF_R2000' | 'INNER' | 'NONE' | 'UNKNOWN'; available: boolean; }&gt;</code>

--------------------


### startScanning(...)

```typescript
startScanning(options?: { repeat_times?: number | undefined; } | undefined) => Promise<void>
```

| Param         | Type                                    |
| ------------- | --------------------------------------- |
| **`options`** | <code>{ repeat_times?: number; }</code> |

--------------------


### setTagReadCallback(...)

```typescript
setTagReadCallback(callback: (data: { epc: string; pc: string; frequency: string; rrsi: string; antenna: number; last_updated: number; read_count: number; }) => void) => Promise<CallbackID>
```

| Param          | Type                                                                                                                                                     |
| -------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`callback`** | <code>(data: { epc: string; pc: string; frequency: string; rrsi: string; antenna: number; last_updated: number; read_count: number; }) =&gt; void</code> |

**Returns:** <code>Promise&lt;string&gt;</code>

--------------------


### clearTagReadCallback()

```typescript
clearTagReadCallback() => Promise<void>
```

--------------------


### setInventoryScanCompletedCallback(...)

```typescript
setInventoryScanCompletedCallback(callback: (data: { rate: number; tags_read: number; details: { start_time: number; end_time: number; }; }) => void) => Promise<CallbackID>
```

| Param          | Type                                                                                                                     |
| -------------- | ------------------------------------------------------------------------------------------------------------------------ |
| **`callback`** | <code>(data: { rate: number; tags_read: number; details: { start_time: number; end_time: number; }; }) =&gt; void</code> |

**Returns:** <code>Promise&lt;string&gt;</code>

--------------------


### clearInventoryScanCompletedCallback()

```typescript
clearInventoryScanCompletedCallback() => Promise<void>
```

--------------------


### stopScanning()

```typescript
stopScanning() => Promise<void>
```

--------------------


### readTag(...)

```typescript
readTag(options: { bank: "RESERVED" | "EPC" | "TID" | "USER"; address: number; length: number; password?: string; }) => Promise<{ crc: string; pc: string; epc: string; data: string; details: { data_length: number; antenna: number; tag_read_count: number; start_time: number; end_time: number; }; }>
```

Tag operation – read tags

| Param         | Type                                                                                                               |
| ------------- | ------------------------------------------------------------------------------------------------------------------ |
| **`options`** | <code>{ bank: 'RESERVED' \| 'EPC' \| 'TID' \| 'USER'; address: number; length: number; password?: string; }</code> |

**Returns:** <code>Promise&lt;{ crc: string; pc: string; epc: string; data: string; details: { data_length: number; antenna: number; tag_read_count: number; start_time: number; end_time: number; }; }&gt;</code>

--------------------


### writeTag(...)

```typescript
writeTag(options: { bank: "RESERVED" | "EPC" | "TID" | "USER"; address: number; data: string; password?: string; }) => Promise<{ crc: string; pc: string; epc: string; details: { antenna: number; tag_read_count: number; start_time: number; end_time: number; }; }>
```

Tag operation – write tags

| Param         | Type                                                                                                             |
| ------------- | ---------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ bank: 'RESERVED' \| 'EPC' \| 'TID' \| 'USER'; address: number; data: string; password?: string; }</code> |

**Returns:** <code>Promise&lt;{ crc: string; pc: string; epc: string; details: { antenna: number; tag_read_count: number; start_time: number; end_time: number; }; }&gt;</code>

--------------------


### lockTag(...)

```typescript
lockTag(options: { bank: "USER" | "TID" | "EPC" | "ACCESS_PASSWORD" | "KILL_PASSWORD"; type: "OPEN" | "LOCK" | "PERM_OPEM" | "PERM_LOCK"; password: string; }) => Promise<{ crc: string; pc: string; epc: string; details: { antenna: number; tag_read_count: number; start_time: number; end_time: number; }; }>
```

Tag operation – lock tags

| Param         | Type                                                                                                                                                             |
| ------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ bank: 'EPC' \| 'TID' \| 'USER' \| 'ACCESS_PASSWORD' \| 'KILL_PASSWORD'; type: 'OPEN' \| 'LOCK' \| 'PERM_OPEM' \| 'PERM_LOCK'; password: string; }</code> |

**Returns:** <code>Promise&lt;{ crc: string; pc: string; epc: string; details: { antenna: number; tag_read_count: number; start_time: number; end_time: number; }; }&gt;</code>

--------------------


### killTag(...)

```typescript
killTag(options: { password: string; }) => Promise<{ crc: string; pc: string; epc: string; details: { antenna: number; tag_read_count: number; start_time: number; end_time: number; }; }>
```

Tag operation – kill tags

| Param         | Type                               |
| ------------- | ---------------------------------- |
| **`options`** | <code>{ password: string; }</code> |

**Returns:** <code>Promise&lt;{ crc: string; pc: string; epc: string; details: { antenna: number; tag_read_count: number; start_time: number; end_time: number; }; }&gt;</code>

--------------------


### setAccessEpcMatch(...)

```typescript
setAccessEpcMatch(options: { epc: string; }) => Promise<{ details: { start_time: number; end_time: number; }; }>
```

Tag operation – set the matched EPC to be accessed (EPC match is valid until the next refresh)

| Param         | Type                          |
| ------------- | ----------------------------- |
| **`options`** | <code>{ epc: string; }</code> |

**Returns:** <code>Promise&lt;{ details: { start_time: number; end_time: number; }; }&gt;</code>

--------------------


### cancelAccessEpcMatch()

```typescript
cancelAccessEpcMatch() => Promise<{ details: { start_time: number; end_time: number; }; }>
```

Tag operation – clear the matched EPC to be accessed

**Returns:** <code>Promise&lt;{ details: { start_time: number; end_time: number; }; }&gt;</code>

--------------------


### getAccessEpcMatch()

```typescript
getAccessEpcMatch() => Promise<{ epc_match: string; details: { start_time: number; end_time: number; }; }>
```

Tag operation – get EPC match

**Returns:** <code>Promise&lt;{ epc_match: string; details: { start_time: number; end_time: number; }; }&gt;</code>

--------------------


### setImpinjFastTid(...)

```typescript
setImpinjFastTid(options: { enable: boolean; save?: boolean; }) => Promise<{ details: { start_time: number; end_time: number; }; }>
```

Tag operation – set FastTID (only valid to some models of Impinj Monza tags)

| Param         | Type                                              |
| ------------- | ------------------------------------------------- |
| **`options`** | <code>{ enable: boolean; save?: boolean; }</code> |

**Returns:** <code>Promise&lt;{ details: { start_time: number; end_time: number; }; }&gt;</code>

--------------------


### getImpinjFastTid()

```typescript
getImpinjFastTid() => Promise<{ status: number; details: { start_time: number; end_time: number; }; }>
```

Tag operation – inquire FastTID

**Returns:** <code>Promise&lt;{ status: number; details: { start_time: number; end_time: number; }; }&gt;</code>

--------------------


### Type Aliases


#### CallbackID

<code>string</code>

</docgen-api>
