# @kduma-sunmi/capacitor-sunmi-uhf

Binding for Sunmi's UHF module SDK

## Install

```bash
npm install @kduma-sunmi/capacitor-sunmi-uhf
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`getScanModel()`](#getscanmodel)
* [`startScanning(...)`](#startscanning)
* [`stopScanning()`](#stopscanning)
* [`setAccessEpcMatch(...)`](#setaccessepcmatch)
* [`cancelAccessEpcMatch()`](#cancelaccessepcmatch)
* [`getAccessEpcMatch()`](#getaccessepcmatch)
* [`readTag(...)`](#readtag)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


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
setAccessEpcMatch(options: { epc: string; }) => Promise<void>
```

| Param         | Type                          |
| ------------- | ----------------------------- |
| **`options`** | <code>{ epc: string; }</code> |

--------------------


### cancelAccessEpcMatch()

```typescript
cancelAccessEpcMatch() => Promise<void>
```

--------------------


### getAccessEpcMatch()

```typescript
getAccessEpcMatch() => Promise<{ epc: string; }>
```

**Returns:** <code>Promise&lt;{ epc: string; }&gt;</code>

--------------------


### readTag(...)

```typescript
readTag(options: { bank: "RESERVED" | "EPC" | "TID" | "USER"; address: number; length: number; password?: string; }) => Promise<{ pc: string; crc: string; epc: string; data: string; }>
```

| Param         | Type                                                                                                               |
| ------------- | ------------------------------------------------------------------------------------------------------------------ |
| **`options`** | <code>{ bank: 'RESERVED' \| 'EPC' \| 'TID' \| 'USER'; address: number; length: number; password?: string; }</code> |

**Returns:** <code>Promise&lt;{ pc: string; crc: string; epc: string; data: string; }&gt;</code>

--------------------

</docgen-api>
