package dev.duma.capacitor.sunmiuhf;

import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
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

@CapacitorPlugin(name = "SunmiUHF")
public class SunmiUHFPlugin extends Plugin {
    private byte repeat_times = (byte) 3;
    Integer rate = -1;

    boolean state = false;
    ArrayList<String> tidList = new ArrayList<String>();
    ArrayList<DataParameter> tagList = new ArrayList<DataParameter>();

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

                    bridge.triggerWindowJSEvent("sunmi_uhf", json.toString());
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

                    bridge.triggerWindowJSEvent("sunmi_uhf", json.toString());
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

                bridge.triggerWindowJSEvent("sunmi_uhf", json.toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    };

    @Override
    public void load() {
        super.load();

        RFIDManager.getInstance().setPrintLog(true);
        RFIDManager.getInstance().connect(getContext());
    }

    // OnTerminate -> RFIDManager.getInstance().disconnect();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", value);
        call.resolve(ret);
    }

    @PluginMethod
    public void startScanning(PluginCall call) {
        RFIDManager rfidManager = RFIDManager.getInstance();
        if(!rfidManager.isConnect()) {
            call.reject("RFIDManager not connected!");
            return;
        }

        RFIDHelper helper = rfidManager.getHelper();
        if(helper == null) {
            call.reject("RFIDHelper is not available!");
            return;
        }

        state = true;
        repeat_times = Objects.requireNonNull(call.getInt("call", 3)).byteValue();
        start(helper);

        call.resolve();
    }

    @PluginMethod
    public void cancelAccessEpcMatch(PluginCall call) {
        RFIDManager rfidManager = RFIDManager.getInstance();
        if(!rfidManager.isConnect()) {
            call.reject("RFIDManager not connected!");
            return;
        }

        RFIDHelper helper = rfidManager.getHelper();
        if(helper == null) {
            call.reject("RFIDHelper is not available!");
            return;
        }

        helper.cancelAccessEpcMatch();

        call.resolve();
    }

    @PluginMethod
    public void readTag(PluginCall call) {
        RFIDManager rfidManager = RFIDManager.getInstance();
        if(!rfidManager.isConnect()) {
            call.reject("RFIDManager not connected!");
            return;
        }

        RFIDHelper helper = rfidManager.getHelper();
        if(helper == null) {
            call.reject("RFIDHelper is not available!");
            return;
        }

        byte btMemBank;
        switch(Objects.requireNonNull(call.getString("bank"))) {
            case "RESERVED":
                btMemBank = 0;
                break;
            case "EPC":
                btMemBank = 1;
                break;
            case "TID":
                btMemBank = 2;
                break;
            case "USER":
                btMemBank = 3;
                break;
            default:
                call.reject("Invalid bank!");
                return;
        }


        byte btWordAdd = Objects.requireNonNull(call.getInt("address")).byteValue();
        byte btWordCnt = Objects.requireNonNull(call.getInt("length")).byteValue();
        byte[] btAryPassWord = StrTools.hexStrToByteArray(Objects.requireNonNull(call.getString("password", "00000000")));
        helper.registerReaderCall(new ReaderCall() {
            @Override
            public void onSuccess(byte cmd, @Nullable DataParameter params) throws RemoteException {
                helper.unregisterReaderCall();

                if (cmd == CMD.READ_TAG) {
                    JSObject ret = new JSObject();
                    assert params != null;
                    ret.put("pc", StrTools.normalizeHexStr(params.getString(ParamCts.TAG_PC), true));
                    ret.put("crc", StrTools.normalizeHexStr(params.getString(ParamCts.TAG_CRC), true));
                    ret.put("epc", StrTools.normalizeHexStr(params.getString(ParamCts.TAG_EPC), true));
                    ret.put("data", StrTools.normalizeHexStr(params.getString(ParamCts.TAG_DATA), true));
                    call.resolve(ret);
                } else {
                    try {
                        JSONObject json = new JSONObject();
                        json.put("action", "onSuccess");
                        json.put("cmd", cmd);
                        json.put("params", params != null ? params.toString() : null);

                        bridge.triggerWindowJSEvent("sunmi_uhf", json.toString());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onTag(byte cmd, byte state, @Nullable DataParameter tag) throws RemoteException { }

            @Override
            public void onFailed(byte cmd, byte errorCode, @Nullable String msg) throws RemoteException {
                helper.unregisterReaderCall();
                try {
                    JSONObject json = new JSONObject();
                    json.put("action", "onFailed");
                    json.put("cmd", cmd);
                    json.put("errorCode", errorCode);
                    json.put("msg", msg);

                    bridge.triggerWindowJSEvent("sunmi_uhf", json.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                call.reject(msg);
            }
        });
        helper.readTag(btMemBank, btWordAdd, btWordCnt, btAryPassWord);
    }

    @PluginMethod
    public void getAccessEpcMatch(PluginCall call) {
        RFIDManager rfidManager = RFIDManager.getInstance();
        if(!rfidManager.isConnect()) {
            call.reject("RFIDManager not connected!");
            return;
        }

        RFIDHelper helper = rfidManager.getHelper();
        if(helper == null) {
            call.reject("RFIDHelper is not available!");
            return;
        }

        helper.registerReaderCall(new ReaderCall() {
            @Override
            public void onSuccess(byte cmd, @Nullable DataParameter params) throws RemoteException {
                helper.unregisterReaderCall();
                try {
                    JSONObject json = new JSONObject();
                    json.put("action", "onSuccess");
                    json.put("cmd", cmd);
                    json.put("params", params != null ? params.toString() : null);

                    bridge.triggerWindowJSEvent("sunmi_uhf", json.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                call.resolve();
            }

            @Override
            public void onTag(byte cmd, byte state, @Nullable DataParameter tag) throws RemoteException { }

            @Override
            public void onFailed(byte cmd, byte errorCode, @Nullable String msg) throws RemoteException {
                helper.unregisterReaderCall();
                try {
                    JSONObject json = new JSONObject();
                    json.put("action", "onFailed");
                    json.put("cmd", cmd);
                    json.put("errorCode", errorCode);
                    json.put("msg", msg);

                    bridge.triggerWindowJSEvent("sunmi_uhf", json.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                call.reject(msg);
            }
        });
        helper.getAccessEpcMatch();
    }

    @PluginMethod
    public void setAccessEpcMatch(PluginCall call) {
        RFIDManager rfidManager = RFIDManager.getInstance();
        if(!rfidManager.isConnect()) {
            call.reject("RFIDManager not connected!");
            return;
        }

        RFIDHelper helper = rfidManager.getHelper();
        if(helper == null) {
            call.reject("RFIDHelper is not available!");
            return;
        }

        String epc = call.getString("epc", "");
        byte[] epcBytes = StrTools.hexStrToByteArray(epc);

        assert epcBytes != null;
        helper.setAccessEpcMatch((byte) epcBytes.length, epcBytes);

        call.resolve();
    }

    public void start(RFIDHelper helper) {
        helper.registerReaderCall(readerCall);
        helper.realTimeInventory(repeat_times);
    }

    @PluginMethod
    public void stopScanning(PluginCall call) {
        RFIDManager rfidManager = RFIDManager.getInstance();
        if(!rfidManager.isConnect()) {
            call.reject("RFIDManager not connected!");
            return;
        }

        RFIDHelper helper = rfidManager.getHelper();
        if(helper == null) {
            call.reject("RFIDHelper is not available!");
            return;
        }

        state = false;
        stop(helper);

        call.resolve();
    }

    public void stop(RFIDHelper helper) {
        helper.inventory((byte) 1);
        helper.unregisterReaderCall();
    }

    @PluginMethod
    public void getScanModel(PluginCall call) throws RemoteException {
        RFIDManager rfidManager = RFIDManager.getInstance();
        if(!rfidManager.isConnect()) {
            call.reject("RFIDManager not connected!");
            return;
        }

        RFIDHelper helper = rfidManager.getHelper();
        if(helper == null) {
            call.reject("RFIDHelper is not available!");
            return;
        }

        int scanModel = helper.getScanModel();
        JSObject ret = new JSObject();

        switch(scanModel) {
            case RFIDManager.UHF_R2000:
                ret.put("model", "UHF_R2000");
                ret.put("available", true);
                break;

            case RFIDManager.INNER:
                ret.put("model", "INNER");
                ret.put("available", true);
                break;

            case RFIDManager.NONE:
                ret.put("model", "NONE");
                ret.put("available", false);
                break;

            default:
                ret.put("model", "UNKNOWN");
                ret.put("available", false);
                break;
        }

        call.resolve(ret);
    }
}
