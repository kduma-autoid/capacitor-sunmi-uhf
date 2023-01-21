import { SplashScreen } from '@capacitor/splash-screen';
import { Camera } from '@capacitor/camera';
import { SunmiUHF } from '../../../src';

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
        <h1>@kduma-sunmi/capacitor-sunmi-uhf</h1>
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
        <button id='start_scan'>Start</button>
        <button id='stop_scan'>Stop</button>
        
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
        const output = self.shadowRoot.querySelector('#output');
        output.innerHTML = "";
        const list = self.shadowRoot.querySelector('#list');
        list.innerHTML = "";
        this.tags = [];
        this.reads = 0;

        await SunmiUHF.startScanning();
      });

      self.shadowRoot.querySelector('#stop_scan').addEventListener('click', async function (e) {
        await SunmiUHF.stopScanning();
      });

      window.addEventListener('sunmi_uhf', (e) => {
        const output = self.shadowRoot.querySelector('#output');
        output.innerHTML = "<b>sunmi_uhf:</b><br><pre>" + JSON.stringify(e, null, 3) + "</pre><hr>" + output.innerHTML;

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


      window.addEventListener('sunmi_shortcut_key', async (e) => {
        if (e.pressed) {
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
      }, false);
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
