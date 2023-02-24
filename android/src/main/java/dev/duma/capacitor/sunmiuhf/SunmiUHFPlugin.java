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

import dev.duma.capacitor.sunmiuhf.internals.RFID6CTagInventory;
import dev.duma.capacitor.sunmiuhf.internals.RFID6CTagOperations;
import dev.duma.capacitor.sunmiuhf.internals.RFIDBasicInformation;

@CapacitorPlugin(name = "SunmiUHF")
public class SunmiUHFPlugin extends Plugin {
    private final RFID6CTagInventory tagInventory = new RFID6CTagInventory();
    private final RFID6CTagOperations tagOperations = new RFID6CTagOperations();
    private final RFIDBasicInformation basicInformation = new RFIDBasicInformation();

    @Override
    public void load() {
        super.load();
        tagInventory.bridge = bridge;

        RFIDManager.getInstance().setPrintLog(true);
        RFIDManager.getInstance().connect(getContext());
    }

    // OnTerminate -> RFIDManager.getInstance().disconnect();

    @PluginMethod
    public void getScanModel(PluginCall call) throws RemoteException {
        try {
            RFIDHelper helper = getRfidHelper();

            basicInformation.getScanModel(helper, call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void startScanning(PluginCall call) {
        try {
            RFIDHelper helper = getRfidHelper();

            tagInventory.startScanning(helper, call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void stopScanning(PluginCall call) {
        try {
            RFIDHelper helper = getRfidHelper();

            tagInventory.stopScanning(helper, call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void getAccessEpcMatch(PluginCall call) {
        try {
            RFIDHelper helper = getRfidHelper();

            tagOperations.getAccessEpcMatch(helper, call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void setAccessEpcMatch(PluginCall call) {
        try {
            RFIDHelper helper = getRfidHelper();

            tagOperations.setAccessEpcMatch(helper, call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void cancelAccessEpcMatch(PluginCall call) {
        try {
            RFIDHelper helper = getRfidHelper();

            tagOperations.cancelAccessEpcMatch(helper, call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void readTag(PluginCall call) {
        try {
            RFIDHelper helper = getRfidHelper();

            tagOperations.readTag(helper, call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void writeTag(PluginCall call) {
        try {
            RFIDHelper helper = getRfidHelper();

            tagOperations.writeTag(helper, call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void lockTag(PluginCall call) {
        try {
            RFIDHelper helper = getRfidHelper();

            tagOperations.lockTag(helper, call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void killTag(PluginCall call) {
        try {
            RFIDHelper helper = getRfidHelper();

            tagOperations.killTag(helper, call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void getImpinjFastTid(PluginCall call) {
        try {
            RFIDHelper helper = getRfidHelper();

            tagOperations.getImpinjFastTid(helper, call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void setImpinjFastTid(PluginCall call) {
        try {
            RFIDHelper helper = getRfidHelper();

            tagOperations.setImpinjFastTid(helper, call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    private RFIDHelper getRfidHelper() {
        RFIDManager rfidManager = RFIDManager.getInstance();
        if(!rfidManager.isConnect()) {
            throw new RuntimeException("RFIDManager not connected!");
        }

        RFIDHelper helper = rfidManager.getHelper();
        if(helper == null) {
            throw new RuntimeException("RFIDHelper is not available!");
        }

        return helper;
    }
}
