package dev.duma.capacitor.sunmiuhf.internals;

import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.getcapacitor.Bridge;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.sunmi.rfid.RFIDHelper;
import com.sunmi.rfid.ReaderCall;
import com.sunmi.rfid.constant.CMD;
import com.sunmi.rfid.constant.ParamCts;
import com.sunmi.rfid.entity.DataParameter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import dev.duma.capacitor.sunmiuhf.StrTools;
import dev.duma.capacitor.sunmiuhf.SunmiUHF;

public class RFID6CTagOperations {
    private final SunmiUHF uhf;

    public RFID6CTagOperations(SunmiUHF uhf) {
        this.uhf = uhf;
    }

    public void setImpinjFastTid(PluginCall call, Bridge bridge) {
        RFIDHelper helper = SunmiUHF.getRfidHelper();

        boolean blnOpen = Boolean.TRUE.equals(call.getBoolean("enable", false));
        boolean blnSave = Boolean.TRUE.equals(call.getBoolean("save", false));

        helper.registerReaderCall(new ReaderCall() {
            @Override
            public void onSuccess(byte cmd, @Nullable DataParameter params) throws RemoteException {
                if (cmd == CMD.SET_IMPINJ_FAST_TID) {
                    helper.unregisterReaderCall();
                    JSObject ret = new JSObject();
                    assert params != null;

                    JSObject details = new JSObject();
                    details.put("start_time", params.getLong(ParamCts.START_TIME));
                    details.put("end_time", params.getLong(ParamCts.END_TIME));
                    ret.put("details", details);

                    call.resolve(ret);
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

                    bridge.triggerWindowJSEvent("sunmi_uhf_debug", json.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                call.reject(msg, "0x"+StrTools.byteToHexStr(errorCode));
            }
        });
        helper.setImpinjFastTid(blnOpen, blnSave);
    }

    public void getImpinjFastTid(PluginCall call, Bridge bridge) {
        RFIDHelper helper = SunmiUHF.getRfidHelper();

        helper.registerReaderCall(new ReaderCall() {
            @Override
            public void onSuccess(byte cmd, @Nullable DataParameter params) throws RemoteException {
                if (cmd == CMD.GET_IMPINJ_FAST_TID) {
                    helper.unregisterReaderCall();

                    JSObject ret = new JSObject();
                    assert params != null;
                    ret.put("status", StrTools.byteToHexStr(params.getByte(ParamCts.TAG_MONZA_STATUS)));

                    JSObject details = new JSObject();
                    details.put("start_time", params.getLong(ParamCts.START_TIME));
                    details.put("end_time", params.getLong(ParamCts.END_TIME));
                    ret.put("details", details);

                    call.resolve(ret);
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

                    bridge.triggerWindowJSEvent("sunmi_uhf_debug", json.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                call.reject(msg, "0x"+StrTools.byteToHexStr(errorCode));
            }
        });
        helper.getImpinjFastTid();
    }

    public void setAccessEpcMatch(PluginCall call, Bridge bridge) {
        RFIDHelper helper = SunmiUHF.getRfidHelper();

        String epc = call.getString("epc", "");
        byte[] epcBytes = StrTools.hexStrToByteArray(epc);

        assert epcBytes != null;
        helper.registerReaderCall(new ReaderCall() {
            @Override
            public void onSuccess(byte cmd, @Nullable DataParameter params) throws RemoteException {
                if (cmd == CMD.SET_ACCESS_EPC_MATCH) {
                    helper.unregisterReaderCall();
                    JSObject ret = new JSObject();
                    assert params != null;

                    JSObject details = new JSObject();
                    details.put("start_time", params.getLong(ParamCts.START_TIME));
                    details.put("end_time", params.getLong(ParamCts.END_TIME));
                    ret.put("details", details);

                    call.resolve(ret);
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

                    bridge.triggerWindowJSEvent("sunmi_uhf_debug", json.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                call.reject(msg, "0x"+StrTools.byteToHexStr(errorCode));
            }
        });
        helper.setAccessEpcMatch((byte) epcBytes.length, epcBytes);
    }

    public void getAccessEpcMatch(PluginCall call, Bridge bridge) {
        RFIDHelper helper = SunmiUHF.getRfidHelper();

        helper.registerReaderCall(new ReaderCall() {
            @Override
            public void onSuccess(byte cmd, @Nullable DataParameter params) throws RemoteException {
                if (cmd == CMD.GET_ACCESS_EPC_MATCH) {
                    helper.unregisterReaderCall();

                    JSObject ret = new JSObject();
                    assert params != null;
                    ret.put("epc_match", StrTools.normalizeHexStr(params.getString(ParamCts.TAG_ACCESS_EPC_MATCH), true));

                    JSObject details = new JSObject();
                    details.put("start_time", params.getLong(ParamCts.START_TIME));
                    details.put("end_time", params.getLong(ParamCts.END_TIME));
                    ret.put("details", details);

                    call.resolve(ret);
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

                    bridge.triggerWindowJSEvent("sunmi_uhf_debug", json.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                call.reject(msg, "0x"+StrTools.byteToHexStr(errorCode));
            }
        });
        helper.getAccessEpcMatch();
    }

    public void cancelAccessEpcMatch(PluginCall call, Bridge bridge) {
        RFIDHelper helper = SunmiUHF.getRfidHelper();

        helper.registerReaderCall(new ReaderCall() {
            @Override
            public void onSuccess(byte cmd, @Nullable DataParameter params) throws RemoteException {
                if (cmd == CMD.SET_ACCESS_EPC_MATCH) {
                    helper.unregisterReaderCall();

                    JSObject ret = new JSObject();
                    assert params != null;

                    JSObject details = new JSObject();
                    details.put("start_time", params.getLong(ParamCts.START_TIME));
                    details.put("end_time", params.getLong(ParamCts.END_TIME));
                    ret.put("details", details);

                    call.resolve(ret);
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

                    bridge.triggerWindowJSEvent("sunmi_uhf_debug", json.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                call.reject(msg, "0x"+StrTools.byteToHexStr(errorCode));
            }
        });
        helper.cancelAccessEpcMatch();
    }

    public void readTag(PluginCall call, Bridge bridge) {
        RFIDHelper helper = SunmiUHF.getRfidHelper();

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
                if (cmd == CMD.READ_TAG) {
                    helper.unregisterReaderCall();

                    JSObject ret = new JSObject();
                    assert params != null;
                    ret.put("crc", StrTools.normalizeHexStr(params.getString(ParamCts.TAG_CRC), true));
                    ret.put("pc", StrTools.normalizeHexStr(params.getString(ParamCts.TAG_PC), true));
                    ret.put("epc", StrTools.normalizeHexStr(params.getString(ParamCts.TAG_EPC), true));
                    ret.put("data", StrTools.normalizeHexStr(params.getString(ParamCts.TAG_DATA), true));

                    JSObject details = new JSObject();
                    details.put("data_length", params.getInt(ParamCts.TAG_DATA_LEN));
                    details.put("antenna", params.getByte(ParamCts.ANT_ID));
                    details.put("tag_read_count", params.getInt(ParamCts.TAG_READ_COUNT));
                    details.put("start_time", params.getLong(ParamCts.START_TIME));
                    details.put("end_time", params.getLong(ParamCts.END_TIME));
                    ret.put("details", details);

                    call.resolve(ret);
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

                    bridge.triggerWindowJSEvent("sunmi_uhf_debug", json.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                call.reject(msg, "0x"+StrTools.byteToHexStr(errorCode));
            }
        });
        helper.readTag(btMemBank, btWordAdd, btWordCnt, btAryPassWord);
    }

    public void writeTag(PluginCall call, Bridge bridge) {
        RFIDHelper helper = SunmiUHF.getRfidHelper();

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
        byte[] btAryPassWord = StrTools.hexStrToByteArray(Objects.requireNonNull(call.getString("password", "00000000")));
        String data = Objects.requireNonNull(call.getString("data"));
        byte[] btAryData = StrTools.hexStrToByteArray(data);
        byte btWordCnt = btAryData.length > 0 ? (byte) (btAryData.length / 2) : 0;

        if(btWordCnt==0 || btAryData.length % 2 != 0) {
            call.reject("Invalid data!");
            return;
        }

        helper.registerReaderCall(new ReaderCall() {
            @Override
            public void onSuccess(byte cmd, @Nullable DataParameter params) throws RemoteException {
                helper.unregisterReaderCall();

                if (cmd == CMD.WRITE_TAG) {
                    JSObject ret = new JSObject();
                    assert params != null;
                    ret.put("crc", StrTools.normalizeHexStr(params.getString(ParamCts.TAG_CRC), true));
                    ret.put("pc", StrTools.normalizeHexStr(params.getString(ParamCts.TAG_PC), true));
                    ret.put("epc", StrTools.normalizeHexStr(params.getString(ParamCts.TAG_EPC), true));

                    JSObject details = new JSObject();
                    details.put("antenna", params.getByte(ParamCts.ANT_ID));
                    details.put("tag_read_count", params.getInt(ParamCts.TAG_READ_COUNT));
                    details.put("start_time", params.getLong(ParamCts.START_TIME));
                    details.put("end_time", params.getLong(ParamCts.END_TIME));
                    ret.put("details", details);

                    call.resolve(ret);
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

                    bridge.triggerWindowJSEvent("sunmi_uhf_debug", json.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                call.reject(msg, "0x"+StrTools.byteToHexStr(errorCode));
            }
        });
        helper.writeTag(btAryPassWord, btMemBank, btWordAdd, btWordCnt, btAryData);
    }

    public void lockTag(PluginCall call, Bridge bridge) {
        RFIDHelper helper = SunmiUHF.getRfidHelper();

        byte btMemBank;
        switch(Objects.requireNonNull(call.getString("bank"))) {
            case "USER":
                btMemBank = 1;
                break;
            case "TID":
                btMemBank = 2;
                break;
            case "EPC":
                btMemBank = 3;
                break;
            case "ACCESS_PASSWORD":
                btMemBank = 4;
                break;
            case "KILL_PASSWORD":
                btMemBank = 5;
                break;
            default:
                call.reject("Invalid bank!");
                return;
        }

        byte btLockType;
        switch(Objects.requireNonNull(call.getString("type"))) {
            case "OPEN":
                btLockType = 0;
                break;
            case "LOCK":
                btLockType = 1;
                break;
            case "PERM_OPEM":
                btLockType = 2;
                break;
            case "PERM_LOCK":
                btLockType = 3;
                break;
            default:
                call.reject("Invalid lock type!");
                return;
        }


        byte[] btAryPassWord = StrTools.hexStrToByteArray(Objects.requireNonNull(call.getString("password", "00000000")));

        helper.registerReaderCall(new ReaderCall() {
            @Override
            public void onSuccess(byte cmd, @Nullable DataParameter params) throws RemoteException {
                helper.unregisterReaderCall();

                if (cmd == CMD.LOCK_TAG) {
                    JSObject ret = new JSObject();
                    assert params != null;
                    ret.put("crc", StrTools.normalizeHexStr(params.getString(ParamCts.TAG_CRC), true));
                    ret.put("pc", StrTools.normalizeHexStr(params.getString(ParamCts.TAG_PC), true));
                    ret.put("epc", StrTools.normalizeHexStr(params.getString(ParamCts.TAG_EPC), true));

                    JSObject details = new JSObject();
                    details.put("antenna", params.getByte(ParamCts.ANT_ID));
                    details.put("tag_read_count", params.getInt(ParamCts.TAG_READ_COUNT));
                    details.put("start_time", params.getLong(ParamCts.START_TIME));
                    details.put("end_time", params.getLong(ParamCts.END_TIME));
                    ret.put("details", details);

                    call.resolve(ret);
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

                    bridge.triggerWindowJSEvent("sunmi_uhf_debug", json.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                call.reject(msg, "0x"+StrTools.byteToHexStr(errorCode));
            }
        });
        helper.lockTag(btAryPassWord, btMemBank, btLockType);
    }

    public void killTag(PluginCall call, Bridge bridge) {
        RFIDHelper helper = SunmiUHF.getRfidHelper();

        byte[] btAryPassWord = StrTools.hexStrToByteArray(Objects.requireNonNull(call.getString("password", "00000000")));

        helper.registerReaderCall(new ReaderCall() {
            @Override
            public void onSuccess(byte cmd, @Nullable DataParameter params) throws RemoteException {
                helper.unregisterReaderCall();

                if (cmd == CMD.KILL_TAG) {
                    JSObject ret = new JSObject();
                    assert params != null;
                    ret.put("crc", StrTools.normalizeHexStr(params.getString(ParamCts.TAG_CRC), true));
                    ret.put("pc", StrTools.normalizeHexStr(params.getString(ParamCts.TAG_PC), true));
                    ret.put("epc", StrTools.normalizeHexStr(params.getString(ParamCts.TAG_EPC), true));

                    JSObject details = new JSObject();
                    details.put("antenna", params.getByte(ParamCts.ANT_ID));
                    details.put("tag_read_count", params.getInt(ParamCts.TAG_READ_COUNT));
                    details.put("start_time", params.getLong(ParamCts.START_TIME));
                    details.put("end_time", params.getLong(ParamCts.END_TIME));
                    ret.put("details", details);

                    call.resolve(ret);
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

                    bridge.triggerWindowJSEvent("sunmi_uhf_debug", json.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                call.reject(msg, "0x"+StrTools.byteToHexStr(errorCode));
            }
        });
        helper.killTag(btAryPassWord);
    }
}
