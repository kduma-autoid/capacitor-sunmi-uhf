export interface SunmiUHFPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
