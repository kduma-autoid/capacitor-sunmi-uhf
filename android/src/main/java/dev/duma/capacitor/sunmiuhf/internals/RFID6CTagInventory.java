package dev.duma.capacitor.sunmiuhf.internals;

import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.getcapacitor.Bridge;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.sunmi.rfid.RFIDHelper;
import com.sunmi.rfid.RFIDManager;
import com.sunmi.rfid.ReaderCall;
import com.sunmi.rfid.constant.CMD;
import com.sunmi.rfid.constant.ParamCts;
import com.sunmi.rfid.entity.DataParameter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import dev.duma.capacitor.sunmiuhf.StrTools;
import dev.duma.capacitor.sunmiuhf.SunmiUHF;

public class RFID6CTagInventory {
    private Bridge bridge;

    public RFID6CTagInventory(Bridge bridge) {
        this.bridge = bridge;
    }

    private byte repeat_times = (byte) 3;
    boolean state = false;

    private final ReaderCall readerCall = new ReaderCall() {
        @Override
        public void onSuccess(byte cmd, @Nullable DataParameter params) throws RemoteException {
            if(state) {
                start(Objects.requireNonNull(RFIDManager.getInstance().getHelper()));
            }

            if (cmd == CMD.REAL_TIME_INVENTORY) {
                if (params != null) {
                    JSObject ret = new JSObject();

                    int rate = params.getInt(ParamCts.READ_RATE, -1);
                    if (rate == 0) rate = -1;
                    ret.put("rate", rate);

                    ret.put("tags_read", params.getInt(ParamCts.DATA_COUNT));

                    JSObject details = new JSObject();
                    details.put("start_time", params.getLong(ParamCts.START_TIME));
                    details.put("end_time", params.getLong(ParamCts.END_TIME));
                    ret.put("details", details);

                    PluginCall call = getInventoryScanCompletedCallback();
                    if (call != null) {
                        call.resolve(ret);
                    } else {
                        bridge.triggerWindowJSEvent("sunmi_uhf_read_completed", ret.toString());
                    }
                }
            } else {
                try {
                    JSONObject json = new JSONObject();
                    json.put("action", "onSuccess");
                    json.put("cmd", cmd);
                    json.put("params", params != null ? params.toString() : null);

                    bridge.triggerWindowJSEvent("sunmi_uhf_debug", json.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @Override
        public void onTag(byte cmd, byte state, @Nullable DataParameter tag) throws RemoteException {
            if (tag == null) return;

            JSObject ret = new JSObject();

            if(cmd == CMD.REAL_TIME_INVENTORY) {
                String epc = tag.getString(ParamCts.TAG_EPC);
                if(epc == null) epc = "";
                ret.put("epc", epc);

                String pc = tag.getString(ParamCts.TAG_PC);
                if(pc == null) pc = "";
                ret.put("pc", pc);

                String frequency = tag.getString(ParamCts.TAG_FREQ);
                if(frequency == null) frequency = "";
                ret.put("frequency", frequency);

                String rrsi = tag.getString(ParamCts.TAG_RSSI);
                if(rrsi == null) rrsi = "";
                ret.put("rrsi", rrsi);

                int antenna = (int) tag.getByte(ParamCts.ANT_ID);
                ret.put("antenna", antenna);

                long last_updated = tag.getLong(ParamCts.TAG_TIME);
                ret.put("last_updated", last_updated);

                int read_count = tag.getInt(ParamCts.TAG_READ_COUNT);
                ret.put("read_count", read_count);

                PluginCall call = getTagReadCallback();
                if (call != null) {
                    call.resolve(ret);
                } else {
                    bridge.triggerWindowJSEvent("sunmi_uhf_tag_read", ret.toString());
                }
            } else {
                ret.put("action", "onTag");
                ret.put("cmd", cmd);
                ret.put("state", state);
                ret.put("tag", tag != null ? StrTools.normalizeHexStr(tag.toString(), true) : null);

                bridge.triggerWindowJSEvent("sunmi_uhf_debug", ret.toString());
            }
        }

        @Override
        public void onFailed(byte cmd, byte errorCode, @Nullable String msg) throws RemoteException {
            try {
                JSONObject json = new JSONObject();
                json.put("action", "onFailed");
                json.put("cmd", cmd);
                json.put("errorCode", errorCode);
                json.put("msg", msg);

                bridge.triggerWindowJSEvent("sunmi_uhf_debug", json.toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    };

    private String tagReadCallbackId = null;
    private String inventoryScanCompletedCallbackId = null;

    public void stopScanning(PluginCall call, Bridge bridge) {
        RFIDHelper helper = SunmiUHF.getRfidHelper();

        state = false;
        stop(helper);
        call.resolve();
    }

    public void startScanning(PluginCall call, Bridge bridge) {
        RFIDHelper helper = SunmiUHF.getRfidHelper();

        state = true;
        repeat_times = Objects.requireNonNull(call.getInt("call", 3)).byteValue();
        start(helper);

        call.resolve();
    }

    private void start(RFIDHelper helper) {
        helper.registerReaderCall(readerCall);
        helper.realTimeInventory(repeat_times);
    }

    private void stop(RFIDHelper helper) {
        helper.inventory((byte) 1);
        helper.unregisterReaderCall();
        clearTagReadCallback(bridge);
        clearInventoryScanCompletedCallback(bridge);
    }

    @Nullable
    protected PluginCall getTagReadCallback() {
        if(tagReadCallbackId == null) {
            return null;
        }

        return bridge.getSavedCall(tagReadCallbackId);
    }

    public void setTagReadCallback(PluginCall call, Bridge bridge) {
        RFIDHelper helper = SunmiUHF.getRfidHelper();

        if(tagReadCallbackId != null) {
            clearTagReadCallback(bridge);
        }

        call.setKeepAlive(true);
        tagReadCallbackId = call.getCallbackId();
        bridge.saveCall(call);
    }

    public void clearTagReadCallback(Bridge bridge) {
        if(tagReadCallbackId == null) {
            return;
        }

        bridge.releaseCall(tagReadCallbackId);
        tagReadCallbackId = null;
    }

    @Nullable
    protected PluginCall getInventoryScanCompletedCallback() {
        if(inventoryScanCompletedCallbackId == null) {
            return null;
        }

        return bridge.getSavedCall(inventoryScanCompletedCallbackId);
    }

    public void setInventoryScanCompletedCallback(PluginCall call, Bridge bridge) {
        RFIDHelper helper = SunmiUHF.getRfidHelper();

        if(inventoryScanCompletedCallbackId != null) {
            clearInventoryScanCompletedCallback(bridge);
        }

        call.setKeepAlive(true);
        inventoryScanCompletedCallbackId = call.getCallbackId();
        bridge.saveCall(call);
    }

    public void clearInventoryScanCompletedCallback(Bridge bridge) {
        if(inventoryScanCompletedCallbackId == null) {
            return;
        }

        bridge.releaseCall(inventoryScanCompletedCallbackId);
        inventoryScanCompletedCallbackId = null;
    }
}
