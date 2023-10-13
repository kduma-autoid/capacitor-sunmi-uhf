const fs = require('fs');
const path = require('path');

let workingDir = process.env.INIT_CWD;
if (!workingDir) {
    workingDir = '.';
}

if (!fs.existsSync(path.join(workingDir, 'android'))) {
    console.warn(
        'cannot install SunmiScannerSdk library: @capacitor/android not installed, or in wrong parent directory.',
    );
    return;
}

if (!fs.existsSync(path.join(workingDir, 'android/app/'))) {
    console.warn(
        'cannot install SunmiScannerSdk library: @capacitor/android is installed, but there is no app directory.',
    );
    return;
}

const releaseAARPath = path.join(
    'android/src/main/libs/SunmiScannerSdk-release-v1.1.8.aar',
);

const fullPath = path.join(workingDir, 'android/app/libs');
if (!fs.existsSync(fullPath)) {
    fs.mkdirSync(fullPath);
}
console.log(releaseAARPath);
fs.copyFileSync(
    releaseAARPath,
    path.join(fullPath, 'SunmiScannerSdk-release-v1.1.8.aar'),
);

console.log(`copied SunmiScannerSdk-release-v1.1.8.aar to ${fullPath}`);