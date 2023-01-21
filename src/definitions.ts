export interface SunmiUHFPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;

  startScanning(): Promise<void>;
  stopScanning(): Promise<void>;
}
