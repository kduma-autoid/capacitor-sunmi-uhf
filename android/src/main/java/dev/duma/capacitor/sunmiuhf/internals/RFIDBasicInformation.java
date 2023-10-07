package dev.duma.capacitor.sunmiuhf.internals;

import android.os.RemoteException;

import com.getcapacitor.Bridge;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.sunmi.rfid.RFIDHelper;
import com.sunmi.rfid.RFIDManager;

import dev.duma.capacitor.sunmiuhf.SunmiUHF;

public class RFIDBasicInformation {
    private final SunmiUHF uhf;

    public RFIDBasicInformation(SunmiUHF uhf) {
        this.uhf = uhf;
    }

    public void getScanModel(PluginCall call, Bridge bridge) throws RemoteException {
        RFIDHelper helper = SunmiUHF.getRfidHelper();

        int scanModel = helper.getScanModel();
        JSObject ret = new JSObject();

        switch (scanModel) {
            case RFIDManager.UHF_S7100 -> {
                ret.put("model", "UHF_S7100");
                ret.put("available", true);
            }
            case RFIDManager.UHF_R2000 -> {
                ret.put("model", "UHF_R2000");
                ret.put("available", true);
            }
            case RFIDManager.INNER -> {
                ret.put("model", "INNER");
                ret.put("available", true);
            }
            case RFIDManager.NONE -> {
                ret.put("model", "NONE");
                ret.put("available", false);
            }
            default -> {
                ret.put("model", "UNKNOWN");
                ret.put("available", false);
            }
        }

        call.resolve(ret);
    }
}
