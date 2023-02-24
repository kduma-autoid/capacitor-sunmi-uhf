package dev.duma.capacitor.sunmiuhf.internals;

import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.getcapacitor.Bridge;
import com.getcapacitor.PluginCall;
import com.sunmi.rfid.RFIDHelper;
import com.sunmi.rfid.RFIDManager;
import com.sunmi.rfid.ReaderCall;
import com.sunmi.rfid.constant.CMD;
import com.sunmi.rfid.constant.ParamCts;
import com.sunmi.rfid.entity.DataParameter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import dev.duma.capacitor.sunmiuhf.StrTools;

public class RFID6CTagInventory {
    public Bridge bridge;

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
                    try {
                        JSONObject json = new JSONObject();

                        int rate = params.getInt(ParamCts.READ_RATE, -1);
                        if (rate == 0) rate = -1;
                        json.put("rate", rate);

                        int tags_read = params.getInt(ParamCts.DATA_COUNT);
                        json.put("tags_read", tags_read);

                        long start_time = params.getLong(ParamCts.START_TIME);
                        json.put("start_time", start_time);

                        long end_time = params.getLong(ParamCts.END_TIME);
                        json.put("end_time", end_time);

                        bridge.triggerWindowJSEvent("sunmi_uhf_read_completed", json.toString());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
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

            try {
                JSONObject json = new JSONObject();

                if(cmd == CMD.REAL_TIME_INVENTORY) {
                    String epc = tag.getString(ParamCts.TAG_EPC);
                    if(epc == null) epc = "";
                    json.put("epc", epc);

                    String pc = tag.getString(ParamCts.TAG_PC);
                    if(pc == null) pc = "";
                    json.put("pc", pc);

                    String frequency = tag.getString(ParamCts.TAG_FREQ);
                    if(frequency == null) frequency = "";
                    json.put("frequency", frequency);

                    String rrsi = tag.getString(ParamCts.TAG_RSSI);
                    if(rrsi == null) rrsi = "";
                    json.put("rrsi", rrsi);

                    int antenna = (int) tag.getByte(ParamCts.ANT_ID);
                    json.put("antenna", antenna);

                    long last_updated = tag.getLong(ParamCts.TAG_TIME);
                    json.put("last_updated", last_updated);

                    int read_count = tag.getInt(ParamCts.TAG_READ_COUNT);
                    json.put("read_count", read_count);

                    bridge.triggerWindowJSEvent("sunmi_uhf_tag_read", json.toString());
                } else {
                    json.put("action", "onTag");
                    json.put("cmd", cmd);
                    json.put("state", state);
                    json.put("tag", tag != null ? StrTools.normalizeHexStr(tag.toString(), true) : null);

                    bridge.triggerWindowJSEvent("sunmi_uhf_debug", json.toString());
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
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

    public void stopScanning(RFIDHelper helper, PluginCall call, Bridge bridge) {
        state = false;
        stop(helper);
        call.resolve();
    }

    public void startScanning(RFIDHelper helper, PluginCall call, Bridge bridge) {
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
    }
}
