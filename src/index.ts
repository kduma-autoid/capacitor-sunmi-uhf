import { registerPlugin } from '@capacitor/core';

import type { SunmiUHFPlugin } from './definitions';

const SunmiUHF = registerPlugin<SunmiUHFPlugin>('SunmiUHF', {
  web: () => import('./web').then(m => new m.SunmiUHFWeb()),
});

export * from './definitions';
export { SunmiUHF };
