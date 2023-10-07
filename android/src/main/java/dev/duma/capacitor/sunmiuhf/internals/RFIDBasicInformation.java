package dev.duma.capacitor.sunmiuhf.internals;

import android.os.RemoteException;

import com.sunmi.rfid.RFIDManager;

import dev.duma.capacitor.sunmiuhf.SunmiUHF;

public class RFIDBasicInformation {
    private final SunmiUHF uhf;

    public RFIDBasicInformation(SunmiUHF uhf) {
        this.uhf = uhf;
    }

    public interface ScanModelCallback {
        void response(String model, boolean available);
    }

    public void getScanModel(ScanModelCallback callback) throws RemoteException {
        switch (uhf.RfidHelper().getScanModel()) {
            case RFIDManager.UHF_S7100 -> callback.response("UHF_S7100", true);
            case RFIDManager.UHF_R2000 -> callback.response("UHF_R2000", true);
            case RFIDManager.INNER -> callback.response("INNER", true);
            case RFIDManager.NONE -> callback.response("NONE", false);
            default -> callback.response("UNKNOWN", false);
        }
    }
}
