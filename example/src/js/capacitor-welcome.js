import { SplashScreen } from '@capacitor/splash-screen';
import { SunmiUHF } from '../../../src';
import { SunmiKeyboardHandler, HandleableKey, KeyEvent } from '@kduma-autoid/capacitor-sunmi-keyboard-handler';
import { WebViewWatchDog } from "@kduma-autoid/capacitor-webview-watchdog";

window.customElements.define(
  'capacitor-welcome',
  class extends HTMLElement {

    tags = [];
    reads = 0;

    constructor() {
      super();

      SplashScreen.hide();
      WebViewWatchDog.ping();

      const root = this.attachShadow({ mode: 'open' });

      root.innerHTML = `
    <style>
      :host {
        font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
        display: block;
        width: 100%;
        height: 100%;
      }
      h1, h2, h3, h4, h5 {
        text-transform: uppercase;
      }
      .button {
        display: inline-block;
        padding: 10px;
        background-color: #73B5F6;
        color: #fff;
        font-size: 0.9em;
        border: 0;
        border-radius: 3px;
        text-decoration: none;
        cursor: pointer;
      }
      main {
        padding: 15px;
      }
      main hr { height: 1px; background-color: #eee; border: 0; }
      main h1 {
        font-size: 1.4em;
        text-transform: uppercase;
        letter-spacing: 1px;
      }
      main h2 {
        font-size: 1.1em;
      }
      main h3 {
        font-size: 0.9em;
      }
      main p {
        color: #333;
      }
      main pre {
        white-space: pre-line;
      }
    </style>
    <div>
      <capacitor-welcome-titlebar>
        <h1>@kduma-autoid/capacitor-sunmi-uhf</h1>
      </capacitor-welcome-titlebar>
      <main>
        <div>
          Read Rate: <span id='rate'>0</span> tag(s)/second
        </div>
        <div>
          Tag Count: <span id='total'>0</span> tag(s)
        </div>
        <div>
          Cumulative Read Count: <span id='cumulative'>0</span> tag(s)
        </div>
        <hr>
        <button id='start_scan'>startScanning()</button>
        <button id='start_scan_255'>startScanning(0xFF)</button>
        <button id='start_scan_callback'>startScanning(callback)</button>
        <button id='stop_scan'>stopScanning()</button>
        <hr>
        <button id='getScanModel'>getScanModel()</button>
        <button id='getBatteryChargeState'>getBatteryChargeState()</button>
        <button id='getBatteryRemainingPercent'>getBatteryRemainingPercent()</button>
        <button id='getBatteryChargeNumTimes'>getBatteryChargeNumTimes()</button>
        <button id='getBatteryVoltage'>getBatteryVoltage()</button>
        <button id='getFirmwareVersion'>getFirmwareVersion()</button>
        <hr>
        <button id='setAccessEpcMatch'>setAccessEpcMatch(<span id='first_epc'>first</span>')</button>
        <button id='cancelAccessEpcMatch'>cancelAccessEpcMatch()</button>
        <button id='getAccessEpcMatch'>getAccessEpcMatch()</button>
        <hr>
        <button id='readTag'>readTag()</button>
        <button id='writeTag00'>writeTag(00)</button>
        <button id='writeTagFF'>writeTag(FF)</button>
        <hr>
        <button id='writeTagPsw'>writeTag(Psw)</button>
        <button id='writeTagCls'>writeTag(Cls)</button>
        <hr>
        <button id='lockTag'>lockTag()</button>
        <button id='unlockTag'>(un)lockTag()</button>
        <button id='killTag' disabled>killTag()</button>
        <hr>
        <button id='setImpinjFastTid'>setImpinjFastTid(true)</button>
        <button id='setImpinjFastTidOff'>setImpinjFastTid(false)</button>
        <button id='getImpinjFastTid'>getImpinjFastTid()</button>
        
        <h2>List</h2>
      
        <ol id='list'></ol>
        
        <h2>Events</h2>
        <p id="output"></p>
      </main>
    </div>
    `;
    }
    connectedCallback() {
      const self = this;

      function printToOutput(key, content) {
        const output = self.shadowRoot.querySelector('#output');
        output.innerHTML = "<b>" + key + ":</b><br><pre><code>" + JSON.stringify(content, null, 3) + "</code></pre><hr>" + output.innerHTML;
      }

      self.shadowRoot.querySelector('#start_scan').addEventListener('click', async function (e) {
        try {
          const first_epc = self.shadowRoot.querySelector('#first_epc');
          first_epc.innerHTML = "first";
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "";
          const list = self.shadowRoot.querySelector('#list');
          list.innerHTML = "";
          this.tags = [];
          this.reads = 0;

          await SunmiUHF.startScanning();
        } catch (error) {
          printToOutput('startScanning() - ERROR', { code: error.code, message: error.message });
        }
      });

      self.shadowRoot.querySelector('#start_scan_255').addEventListener('click', async function (e) {
        try {
          const first_epc = self.shadowRoot.querySelector('#first_epc');
          first_epc.innerHTML = "first";
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "";
          const list = self.shadowRoot.querySelector('#list');
          list.innerHTML = "";
          this.tags = [];
          this.reads = 0;

          await SunmiUHF.startScanning({ repeat_times: 255});
        } catch (error) {
          printToOutput('startScanning(255) - ERROR', { code: error.code, message: error.message });
        }
      });

      self.shadowRoot.querySelector('#start_scan_callback').addEventListener('click', async function (e) {
        try {
          const first_epc = self.shadowRoot.querySelector('#first_epc');
          first_epc.innerHTML = "first";
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "";
          const list = self.shadowRoot.querySelector('#list');
          list.innerHTML = "";
          this.tags = [];
          this.reads = 0;

          await SunmiUHF.setTagReadCallback((tag) => printToOutput('setTagReadCallback()', tag));
          await SunmiUHF.setInventoryScanCompletedCallback((scan) => printToOutput('setInventoryScanCompletedCallback()', scan));
          await SunmiUHF.startScanning();
        } catch (error) {
          printToOutput('startScanning(255) - ERROR', { code: error.code, message: error.message });
        }
      });

      self.shadowRoot.querySelector('#setAccessEpcMatch').addEventListener('click', async function (e) {
        const first_epc = self.shadowRoot.querySelector('#first_epc');

        if (first_epc.innerHTML == "first") {
          return;
        }

        try {
          const data = await SunmiUHF.setAccessEpcMatch({ epc: first_epc.innerHTML });
          printToOutput('setAccessEpcMatch('+first_epc.innerHTML+')', data);
        } catch (error) {
          printToOutput('setAccessEpcMatch('+first_epc.innerHTML+') - ERROR', { code: error.code, message: error.message });
        }
      });

      self.shadowRoot.querySelector('#readTag').addEventListener('click', async function (e) {
        let data = "";

        try {
          data = await SunmiUHF.readTag({bank: 'EPC', address: 0, length: 8});
          printToOutput('readTag(\'EPC\', 0, 8)', data);
        } catch (error) {
          printToOutput('readTag(\'EPC\', 0, 8) - ERROR', { code: error.code, message: error.message });
        }

        try {
          data = await SunmiUHF.readTag({bank: 'TID', address: 0, length: 6});
          printToOutput('readTag(\'TID\', 0, 6)', data);
        } catch (error) {
          printToOutput('readTag(\'TID\', 0, 6) - ERROR', { code: error.code, message: error.message });
        }

        try {
          data = await SunmiUHF.readTag({bank: 'USER', address: 0, length: 2});
          printToOutput('readTag(\'USER\', 0, 2', data);
        } catch (error) {
          printToOutput('readTag(\'USER\', 0, 2) - ERROR', { code: error.code, message: error.message });
        }

        try {
          data = await SunmiUHF.readTag({bank: 'RESERVED', address: 0, length: 4});
          printToOutput('readTag(\'RESERVED\', 0, 4)', data);
        } catch (error) {
          printToOutput('readTag(\'RESERVED\', 0, 4) - ERROR', { code: error.code, message: error.message });
        }
      });

      const handlers = {
        'stop_scan': {
          'tag': 'stopScanning()',
          'handler': async () => await SunmiUHF.stopScanning()
        },
        'cancelAccessEpcMatch': {
          'tag': 'cancelAccessEpcMatch()',
          'handler': async () => await SunmiUHF.cancelAccessEpcMatch()
        },
        'getAccessEpcMatch': {
          'tag': 'getAccessEpcMatch()',
          'handler': async () => await SunmiUHF.getAccessEpcMatch()
        },
        'writeTag00': {
          'tag': 'writeTag(00 00)',
          'handler': async () => await SunmiUHF.writeTag({bank: 'USER', address: 0, data: '00 00'})
        },
        'writeTagFF': {
          'tag': 'writeTag(FF FF)',
          'handler': async () => await SunmiUHF.writeTag({bank: 'USER', address: 0, data: 'FF FF'})
        },
        'writeTagPsw': {
          'tag': 'writeTag(Psw)',
          'handler': async () => await SunmiUHF.writeTag({bank: 'RESERVED', address: 0, data: '1234567812345678'})
        },
        'writeTagCls': {
          'tag': 'writeTag(Cls)',
          'handler': async () => await SunmiUHF.writeTag({bank: 'RESERVED', address: 0, data: '0000000000000000', password: '12345678'})
        },
        'lockTag': {
          'tag': 'lockTag()',
          'handler': async () => await SunmiUHF.lockTag({bank: 'USER', type: 'LOCK', password: '12345678'})
        },
        'unlockTag': {
          'tag': '(un)lockTag()',
          'handler': async () => await SunmiUHF.lockTag({bank: 'USER', type: 'OPEN', password: '12345678'})
        },
        'killTag': {
          'tag': 'killTag()',
          'handler': async () => await SunmiUHF.killTag({password: '12345678'})
        },
        'getScanModel': {
          'tag': 'getScanModel()',
          'handler': async () => await SunmiUHF.getScanModel()
        },
        'getBatteryChargeState': {
          'tag': 'getBatteryChargeState()',
          'handler': async () => await SunmiUHF.getBatteryChargeState()
        },
        'getBatteryRemainingPercent': {
          'tag': 'getBatteryRemainingPercent()',
          'handler': async () => await SunmiUHF.getBatteryRemainingPercent()
        },
        'getBatteryChargeNumTimes': {
          'tag': 'getBatteryChargeNumTimes()',
          'handler': async () => await SunmiUHF.getBatteryChargeNumTimes()
        },
        'getBatteryVoltage': {
          'tag': 'getBatteryVoltage()',
          'handler': async () => await SunmiUHF.getBatteryVoltage()
        },
        'getFirmwareVersion': {
          'tag': 'getFirmwareVersion()',
          'handler': async () => await SunmiUHF.getFirmwareVersion()
        },
        'setImpinjFastTid': {
          'tag': 'setImpinjFastTid(true)',
          'handler': async () => await SunmiUHF.setImpinjFastTid({enable: true})
        },
        'setImpinjFastTidOff': {
          'tag': 'setImpinjFastTid(false)',
          'handler': async () => await SunmiUHF.setImpinjFastTid({enable: false})
        },
        'getImpinjFastTid': {
          'tag': 'getImpinjFastTid()',
          'handler': async () => await SunmiUHF.setImpinjFastTid({enable: false})
        }
      }

      for (const [key, value] of Object.entries(handlers)) {
        self.shadowRoot.querySelector('#' + key).addEventListener('click', async function (e) {
          try {
            const data = await value.handler();
            printToOutput(value.tag, data);
          } catch (error) {
            printToOutput(value.tag + ' - ERROR', { code: error.code, message: error.message });
          }
        });
      }

      window.addEventListener('sunmi_uhf_debug', (e) => {
        printToOutput('sunmi_uhf_debug', e);
      }, false);

      window.addEventListener('sunmi_uhf_read_completed', (e) => {
        printToOutput('sunmi_uhf_read_completed', e);

        const rate = self.shadowRoot.querySelector('#rate');
        rate.innerHTML = e.rate;

        console.log(e);
      }, false);



      window.addEventListener('sunmi_uhf_tag_read', (e) => {
        printToOutput('sunmi_uhf_tag_read', e);

        const first_epc = self.shadowRoot.querySelector('#first_epc');
        if(first_epc.innerHTML == "first"){
          first_epc.innerHTML = e.epc;
        }

        let epc = e.epc;
        if (self.tags.indexOf(epc) === -1) {
          self.tags.push(epc);
          const list = self.shadowRoot.querySelector('#list');
          list.innerHTML += "<li>" + epc + "</li>";

          let total = self.shadowRoot.querySelector('#total');
          total.innerHTML = self.tags.length.toString();
        }

        let cumulative = self.shadowRoot.querySelector('#cumulative');
        cumulative.innerHTML = (++self.reads).toString();

        console.log(e);
      }, false);

      let handler = async (e) => {
        if (e.type === KeyEvent.KeyDown) {
          const first_epc = self.shadowRoot.querySelector('#first_epc');
          first_epc.innerHTML = "first";
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "";
          const list = self.shadowRoot.querySelector('#list');
          list.innerHTML = "";
          this.tags = [];
          this.reads = 0;

          await SunmiUHF.startScanning();
        } else {
          await SunmiUHF.stopScanning();
        }
      };

      SunmiKeyboardHandler.setKeyHandler({ key: HandleableKey.RFID }, handler);

      SunmiUHF.addListener("onReaderDisconnected", () => printToOutput('onReaderDisconnected', {}));
      SunmiUHF.addListener("onReaderLostConnection", () => printToOutput('onReaderLostConnection', {}));
      SunmiUHF.addListener("onReaderDisconnectedOrLostConnection", () => printToOutput('onReaderDisconnectedOrLostConnection', {}));

      SunmiUHF.addListener("onReaderConnected", () => printToOutput('onReaderConnected', {}));
      SunmiUHF.addListener("onReaderBoot", () => printToOutput('onReaderBoot', {}));
      SunmiUHF.addListener("onReaderBootOrConnected", () => printToOutput('onReaderBootOrConnected', {}));

      SunmiUHF.addListener("onBatteryRemainingPercent", (e) => printToOutput('onBatteryRemainingPercent', e));
      SunmiUHF.addListener("onBatteryLowElectricity", (e) => printToOutput('onBatteryLowElectricity', e));
      SunmiUHF.addListener("onBatteryRemainingPercentOrLowElectricity", (e) => printToOutput('onBatteryRemainingPercentOrLowElectricity', e));

      SunmiUHF.addListener("onBatteryChargeState", (e) => printToOutput('onBatteryChargeState', e));

      SunmiUHF.addListener("onBatteryChargeNumTimes", (e) => printToOutput('onBatteryChargeNumTimes', e));

      SunmiUHF.addListener("onBatteryVoltage", (e) => printToOutput('onBatteryVoltage', e));

      SunmiUHF.addListener("onFirmwareVersion", (e) => printToOutput('onFirmwareVersion', e));
    }
  }
);

window.customElements.define(
  'capacitor-welcome-titlebar',
  class extends HTMLElement {
    constructor() {
      super();
      const root = this.attachShadow({ mode: 'open' });
      root.innerHTML = `
    <style>
      :host {
        position: relative;
        display: block;
        padding: 15px 15px 15px 15px;
        text-align: center;
        background-color: #73B5F6;
      }
      ::slotted(h1) {
        margin: 0;
        font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
        font-size: 0.9em;
        font-weight: 600;
        color: #fff;
      }
    </style>
    <slot></slot>
    `;
    }
  }
);
