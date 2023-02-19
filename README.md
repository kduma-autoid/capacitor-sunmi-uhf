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
* [`stopScanning()`](#stopscanning)
* [`setAccessEpcMatch(...)`](#setaccessepcmatch)
* [`cancelAccessEpcMatch()`](#cancelaccessepcmatch)
* [`getAccessEpcMatch()`](#getaccessepcmatch)
* [`readTag(...)`](#readtag)
* [`writeTag(...)`](#writetag)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getScanModel()

```typescript
getScanModel() => Promise<{ model: "UHF_R2000" | "INNER" | "NONE" | "UNKNOWN"; available: boolean; }>
```

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


### stopScanning()

```typescript
stopScanning() => Promise<void>
```

--------------------


### setAccessEpcMatch(...)

```typescript
setAccessEpcMatch(options: { epc: string; }) => Promise<{ details: { start_time: number; end_time: number; }; }>
```

| Param         | Type                          |
| ------------- | ----------------------------- |
| **`options`** | <code>{ epc: string; }</code> |

**Returns:** <code>Promise&lt;{ details: { start_time: number; end_time: number; }; }&gt;</code>

--------------------


### cancelAccessEpcMatch()

```typescript
cancelAccessEpcMatch() => Promise<{ details: { start_time: number; end_time: number; }; }>
```

**Returns:** <code>Promise&lt;{ details: { start_time: number; end_time: number; }; }&gt;</code>

--------------------


### getAccessEpcMatch()

```typescript
getAccessEpcMatch() => Promise<{ epc_match: string; details: { start_time: number; end_time: number; }; }>
```

**Returns:** <code>Promise&lt;{ epc_match: string; details: { start_time: number; end_time: number; }; }&gt;</code>

--------------------


### readTag(...)

```typescript
readTag(options: { bank: "RESERVED" | "EPC" | "TID" | "USER"; address: number; length: number; password?: string; }) => Promise<{ crc: string; pc: string; epc: string; data: string; details: { data_length: number; antenna: number; tag_read_count: number; start_time: number; end_time: number; }; }>
```

| Param         | Type                                                                                                               |
| ------------- | ------------------------------------------------------------------------------------------------------------------ |
| **`options`** | <code>{ bank: 'RESERVED' \| 'EPC' \| 'TID' \| 'USER'; address: number; length: number; password?: string; }</code> |

**Returns:** <code>Promise&lt;{ crc: string; pc: string; epc: string; data: string; details: { data_length: number; antenna: number; tag_read_count: number; start_time: number; end_time: number; }; }&gt;</code>

--------------------


### writeTag(...)

```typescript
writeTag(options: { bank: "RESERVED" | "EPC" | "TID" | "USER"; address: number; data: string; password?: string; }) => Promise<{ crc: string; pc: string; epc: string; details: { antenna: number; tag_read_count: number; start_time: number; end_time: number; }; }>
```

| Param         | Type                                                                                                             |
| ------------- | ---------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ bank: 'RESERVED' \| 'EPC' \| 'TID' \| 'USER'; address: number; data: string; password?: string; }</code> |

**Returns:** <code>Promise&lt;{ crc: string; pc: string; epc: string; details: { antenna: number; tag_read_count: number; start_time: number; end_time: number; }; }&gt;</code>

--------------------

</docgen-api>
