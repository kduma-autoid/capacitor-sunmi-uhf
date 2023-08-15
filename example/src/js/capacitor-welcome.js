import { SplashScreen } from '@capacitor/splash-screen';
import { SunmiUHF } from '../../../src';
import { SunmiKeyboardHandler, HandleableKey, KeyEvent } from '@kduma-autoid/capacitor-sunmi-keyboard-handler';

window.customElements.define(
  'capacitor-welcome',
  class extends HTMLElement {

    tags = [];
    reads = 0;

    constructor() {
      super();

      SplashScreen.hide();

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
        <button id='get_model'>getScanModel</button>
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
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>startScanning() - ERROR:</b><br><pre>" + JSON.stringify({ code: error.code, message: error.message }, null, 3) + "</pre><hr>" + output.innerHTML;
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
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>startScanning(255) - ERROR:</b><br><pre>" + JSON.stringify({ code: error.code, message: error.message }, null, 3) + "</pre><hr>" + output.innerHTML;
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

          await SunmiUHF.setTagReadCallback((tag) => {
            const output = self.shadowRoot.querySelector('#output');
            output.innerHTML = "<b>setTagReadCallback():</b><br><pre>" + JSON.stringify(tag, null, 3) + "</pre><hr>" + output.innerHTML;
          });
          await SunmiUHF.setInventoryScanCompletedCallback((scan) => {
            const output = self.shadowRoot.querySelector('#output');
            output.innerHTML = "<b>setInventoryScanCompletedCallback():</b><br><pre>" + JSON.stringify(scan, null, 3) + "</pre><hr>" + output.innerHTML;
          });
          await SunmiUHF.startScanning();
        } catch (error) {
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>startScanning(255) - ERROR:</b><br><pre>" + JSON.stringify({ code: error.code, message: error.message }, null, 3) + "</pre><hr>" + output.innerHTML;
        }
      });

      self.shadowRoot.querySelector('#stop_scan').addEventListener('click', async function (e) {
        try {
          await SunmiUHF.stopScanning();
        } catch (error) {
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>stopScanning() - ERROR:</b><br><pre>" + JSON.stringify({ code: error.code, message: error.message }, null, 3) + "</pre><hr>" + output.innerHTML;
        }
      });

      self.shadowRoot.querySelector('#setAccessEpcMatch').addEventListener('click', async function (e) {
        const first_epc = self.shadowRoot.querySelector('#first_epc');
        try {
          const data = await SunmiUHF.setAccessEpcMatch({ epc: first_epc.innerHTML });
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>setAccessEpcMatch("+first_epc.innerHTML+"):</b><br><pre>" + JSON.stringify(data, null, 3) + "</pre><hr>" + output.innerHTML;
        } catch (error) {
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>setAccessEpcMatch("+first_epc.innerHTML+") - ERROR:</b><br><pre>" + JSON.stringify({ code: error.code, message: error.message }, null, 3) + "</pre><hr>" + output.innerHTML;
        }
      });

      self.shadowRoot.querySelector('#cancelAccessEpcMatch').addEventListener('click', async function (e) {
        try {
          const data = await SunmiUHF.cancelAccessEpcMatch();
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>cancelAccessEpcMatch():</b><br><pre>" + JSON.stringify(data, null, 3) + "</pre><hr>" + output.innerHTML;
        } catch (error) {
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>cancelAccessEpcMatch() - ERROR:</b><br><pre>" + JSON.stringify({ code: error.code, message: error.message }, null, 3) + "</pre><hr>" + output.innerHTML;
        }
      });

      self.shadowRoot.querySelector('#getAccessEpcMatch').addEventListener('click', async function (e) {
        try {
          const data = await SunmiUHF.getAccessEpcMatch();
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>getAccessEpcMatch():</b><br><pre>" + JSON.stringify(data, null, 3) + "</pre><hr>" + output.innerHTML;
        } catch (error) {
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>getAccessEpcMatch() - ERROR:</b><br><pre>" + JSON.stringify({ code: error.code, message: error.message }, null, 3) + "</pre><hr>" + output.innerHTML;
        }
      });

      self.shadowRoot.querySelector('#readTag').addEventListener('click', async function (e) {
        const output = self.shadowRoot.querySelector('#output');
        let data = "";

        try {
          data = await SunmiUHF.readTag({bank: 'EPC', address: 0, length: 8});
          output.innerHTML = "<b>readTag('EPC', 0, 8):</b><br><pre>" + JSON.stringify(data, null, 3) + "</pre><hr>" + output.innerHTML;
        } catch (error) {
          output.innerHTML = "<b>readTag('EPC', 0, 8) - ERROR:</b><br><pre>" + JSON.stringify({ code: error.code, message: error.message }, null, 3) + "</pre><hr>" + output.innerHTML;
        }

        try {
          data = await SunmiUHF.readTag({bank: 'TID', address: 0, length: 6});
          output.innerHTML = "<b>readTag('TID', 0, 6):</b><br><pre>" + JSON.stringify(data, null, 3) + "</pre><hr>" + output.innerHTML;
        } catch (error) {
          output.innerHTML = "<b>readTag('TID', 0, 6) - ERROR:</b><br><pre>" + JSON.stringify({ code: error.code, message: error.message }, null, 3) + "</pre><hr>" + output.innerHTML;
        }

        try {
          data = await SunmiUHF.readTag({bank: 'USER', address: 0, length: 2});
          output.innerHTML = "<b>readTag('USER', 0, 2):</b><br><pre>" + JSON.stringify(data, null, 3) + "</pre><hr>" + output.innerHTML;
        } catch (error) {
          output.innerHTML = "<b>readTag('USER', 0, 2) - ERROR:</b><br><pre>" + JSON.stringify({ code: error.code, message: error.message }, null, 3) + "</pre><hr>" + output.innerHTML;
        }

        try {
          data = await SunmiUHF.readTag({bank: 'RESERVED', address: 0, length: 4});
          output.innerHTML = "<b>readTag('RESERVED', 0, 4):</b><br><pre>" + JSON.stringify(data, null, 3) + "</pre><hr>" + output.innerHTML;
        } catch (error) {
          output.innerHTML = "<b>readTag('RESERVED', 0, 4) - ERROR:</b><br><pre>" + JSON.stringify({ code: error.code, message: error.message }, null, 3) + "</pre><hr>" + output.innerHTML;
        }
      });

      self.shadowRoot.querySelector('#writeTag00').addEventListener('click', async function (e) {
        try {
          let data = await SunmiUHF.writeTag({bank: 'USER', address: 0, data: '00 00'});
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>writeTag(00 00):</b><br><pre>" + JSON.stringify(data, null, 3) + "</pre><hr>" + output.innerHTML;
        } catch (error) {
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>writeTag(00 00) - ERROR:</b><br><pre>" + JSON.stringify({ code: error.code, message: error.message }, null, 3) + "</pre><hr>" + output.innerHTML;
        }
      });

      self.shadowRoot.querySelector('#writeTagFF').addEventListener('click', async function (e) {
        try {
          let data = await SunmiUHF.writeTag({bank: 'USER', address: 0, data: 'FF FF'});
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>writeTag(FF FF):</b><br><pre>" + JSON.stringify(data, null, 3) + "</pre><hr>" + output.innerHTML;
        } catch (error) {
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>writeTag(FF FF) - ERROR:</b><br><pre>" + JSON.stringify({ code: error.code, message: error.message }, null, 3) + "</pre><hr>" + output.innerHTML;
        }
      });

      self.shadowRoot.querySelector('#writeTagPsw').addEventListener('click', async function (e) {
        try {
          let data = await SunmiUHF.writeTag({bank: 'RESERVED', address: 0, data: '1234567812345678'});
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>writeTag(Psw):</b><br><pre>" + JSON.stringify(data, null, 3) + "</pre><hr>" + output.innerHTML;
        } catch (error) {
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>writeTag(Psw) - ERROR:</b><br><pre>" + JSON.stringify({ code: error.code, message: error.message }, null, 3) + "</pre><hr>" + output.innerHTML;
        }
      });

      self.shadowRoot.querySelector('#writeTagCls').addEventListener('click', async function (e) {
        try {
          let data = await SunmiUHF.writeTag({bank: 'RESERVED', address: 0, data: '0000000000000000', password: '12345678'});
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>writeTag(Cls):</b><br><pre>" + JSON.stringify(data, null, 3) + "</pre><hr>" + output.innerHTML;
        } catch (error) {
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>writeTag(Cls) - ERROR:</b><br><pre>" + JSON.stringify({ code: error.code, message: error.message }, null, 3) + "</pre><hr>" + output.innerHTML;
        }
      });

      self.shadowRoot.querySelector('#lockTag').addEventListener('click', async function (e) {
        try {
          let data = await SunmiUHF.lockTag({bank: 'USER', type: 'LOCK', password: '12345678'});
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>lockTag():</b><br><pre>" + JSON.stringify(data, null, 3) + "</pre><hr>" + output.innerHTML;
        } catch (error) {
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>lockTag() - ERROR:</b><br><pre>" + JSON.stringify({ code: error.code, message: error.message }, null, 3) + "</pre><hr>" + output.innerHTML;
        }
      });

      self.shadowRoot.querySelector('#unlockTag').addEventListener('click', async function (e) {
        try {
          let data = await SunmiUHF.lockTag({bank: 'USER', type: 'OPEN', password: '12345678'});
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>(un)lockTag():</b><br><pre>" + JSON.stringify(data, null, 3) + "</pre><hr>" + output.innerHTML;
        } catch (error) {
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>(un)lockTag() - ERROR:</b><br><pre>" + JSON.stringify({ code: error.code, message: error.message }, null, 3) + "</pre><hr>" + output.innerHTML;
        }
      });

      self.shadowRoot.querySelector('#killTag').addEventListener('click', async function (e) {
        try {
          let data = await SunmiUHF.killTag({password: '12345678'});
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>killTag():</b><br><pre>" + JSON.stringify(data, null, 3) + "</pre><hr>" + output.innerHTML;
        } catch (error) {
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>killTag() - ERROR:</b><br><pre>" + JSON.stringify({ code: error.code, message: error.message }, null, 3) + "</pre><hr>" + output.innerHTML;
        }
      });

      self.shadowRoot.querySelector('#get_model').addEventListener('click', async function (e) {
        try {
          const data = await SunmiUHF.getScanModel();
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>getScanModel():</b><br><pre>" + JSON.stringify(data, null, 3) + "</pre><hr>" + output.innerHTML;
        } catch (error) {
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>getScanModel() - ERROR:</b><br><pre>" + JSON.stringify({ code: error.code, message: error.message }, null, 3) + "</pre><hr>" + output.innerHTML;
        }
      });

      self.shadowRoot.querySelector('#setImpinjFastTid').addEventListener('click', async function (e) {
        try {
          const data = await SunmiUHF.setImpinjFastTid({enable: true});
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>setImpinjFastTid(true):</b><br><pre>" + JSON.stringify(data, null, 3) + "</pre><hr>" + output.innerHTML;
        } catch (error) {
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>setImpinjFastTid(true) - ERROR:</b><br><pre>" + JSON.stringify({ code: error.code, message: error.message }, null, 3) + "</pre><hr>" + output.innerHTML;
        }
      });

      self.shadowRoot.querySelector('#setImpinjFastTidOff').addEventListener('click', async function (e) {
        try {
          const data = await SunmiUHF.setImpinjFastTid({enable: false});
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>setImpinjFastTid(false):</b><br><pre>" + JSON.stringify(data, null, 3) + "</pre><hr>" + output.innerHTML;
        } catch (error) {
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>setImpinjFastTid(false) - ERROR:</b><br><pre>" + JSON.stringify({ code: error.code, message: error.message }, null, 3) + "</pre><hr>" + output.innerHTML;
        }
      });

      self.shadowRoot.querySelector('#getImpinjFastTid').addEventListener('click', async function (e) {
        try {
          const data = await SunmiUHF.getImpinjFastTid();
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>getImpinjFastTid():</b><br><pre>" + JSON.stringify(data, null, 3) + "</pre><hr>" + output.innerHTML;
        } catch (error) {
          const output = self.shadowRoot.querySelector('#output');
          output.innerHTML = "<b>getImpinjFastTid() - ERROR:</b><br><pre>" + JSON.stringify({ code: error.code, message: error.message }, null, 3) + "</pre><hr>" + output.innerHTML;
        }
      });

      window.addEventListener('sunmi_uhf_debug', (e) => {
        const output = self.shadowRoot.querySelector('#output');
        output.innerHTML = "<b>sunmi_uhf_debug:</b><br><pre>" + JSON.stringify(e, null, 3) + "</pre><hr>" + output.innerHTML;

        console.log(e);
      }, false);

      window.addEventListener('sunmi_uhf_read_completed', (e) => {
        const output = self.shadowRoot.querySelector('#output');
        output.innerHTML = "<b>sunmi_uhf_read_completed:</b><br><pre>" + JSON.stringify(e, null, 3) + "</pre><hr>" + output.innerHTML;

        const rate = self.shadowRoot.querySelector('#rate');
        rate.innerHTML = e.rate;

        console.log(e);
      }, false);



      window.addEventListener('sunmi_uhf_tag_read', (e) => {
        const output = self.shadowRoot.querySelector('#output');
        output.innerHTML = "<b>sunmi_uhf_tag_read:</b><br><pre>" + JSON.stringify(e, null, 3) + "</pre><hr>" + output.innerHTML;

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
        if (e.type == KeyEvent.KeyDown) {
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

      SunmiKeyboardHandler.setKeyHandler({ key: HandleableKey.L2s_Shortcut_or_RFID }, handler);
      SunmiKeyboardHandler.setKeyHandler({ key: HandleableKey.L2k_RFID }, handler);
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
