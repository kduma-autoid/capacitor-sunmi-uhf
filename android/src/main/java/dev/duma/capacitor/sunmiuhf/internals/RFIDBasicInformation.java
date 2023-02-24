package dev.duma.capacitor.sunmiuhf.internals;

import android.os.RemoteException;

import com.getcapacitor.Bridge;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.sunmi.rfid.RFIDHelper;
import com.sunmi.rfid.RFIDManager;

public class RFIDBasicInformation {
    public void getScanModel(RFIDHelper helper, PluginCall call, Bridge bridge) throws RemoteException {
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
