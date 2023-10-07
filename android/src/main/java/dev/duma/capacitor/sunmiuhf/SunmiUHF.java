package dev.duma.capacitor.sunmiuhf;

import android.content.Context;
import android.util.Log;

import com.getcapacitor.Bridge;
import com.getcapacitor.Plugin;
import com.sunmi.rfid.RFIDHelper;
import com.sunmi.rfid.RFIDManager;

import dev.duma.capacitor.sunmiuhf.internals.RFID6CTagInventory;
import dev.duma.capacitor.sunmiuhf.internals.RFID6CTagOperations;
import dev.duma.capacitor.sunmiuhf.internals.RFIDBasicInformation;

public class SunmiUHF {
    private final RFID6CTagInventory tagInventory;
    private final RFID6CTagOperations tagOperations;
    private final RFIDBasicInformation basicInformation;

    public SunmiUHF(Plugin plugin) {
        tagInventory = new RFID6CTagInventory(plugin.getBridge());
        tagOperations = new RFID6CTagOperations();
        basicInformation = new RFIDBasicInformation();

        RFIDManager.getInstance().setPrintLog(true);
        RFIDManager.getInstance().connect(plugin.getContext());
    }

    public RFID6CTagInventory tagInventory() {
        return tagInventory;
    }

    public RFID6CTagOperations tagOperations() {
        return tagOperations;
    }

    public RFIDBasicInformation basicInformation() {
        return basicInformation;
    }

    public static RFIDHelper getRfidHelper() {
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
