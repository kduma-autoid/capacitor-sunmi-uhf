package dev.duma.capacitor.sunmiuhf;

import com.getcapacitor.Plugin;
import com.sunmi.rfid.RFIDHelper;
import com.sunmi.rfid.RFIDManager;

import dev.duma.capacitor.sunmiuhf.internals.RFID6CTagInventory;
import dev.duma.capacitor.sunmiuhf.internals.RFID6CTagOperations;
import dev.duma.capacitor.sunmiuhf.internals.RFIDBasicInformation;

public class SunmiUHF {
    private Plugin plugin;
    private final RFID6CTagInventory tagInventory;
    private final RFID6CTagOperations tagOperations;
    private final RFIDBasicInformation basicInformation;
    private final SunmiUHFBroadcastReceiver broadcastReceiver;

    public SunmiUHF(Plugin plugin, boolean sdkDebugOutput) {
        this.plugin = plugin;

        tagInventory = new RFID6CTagInventory(this);
        tagOperations = new RFID6CTagOperations(this);
        basicInformation = new RFIDBasicInformation(this);
        broadcastReceiver = new SunmiUHFBroadcastReceiver(this);

        RFIDManager.getInstance().setPrintLog(sdkDebugOutput);
        RFIDManager.getInstance().connect(plugin.getContext());
    }

    public Plugin getPlugin() {
        return plugin;
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

    public SunmiUHFBroadcastReceiver getBroadcastReceiver() {
        return broadcastReceiver;
    }

    public RFIDHelper RfidHelper() {
        return SunmiUHF.getRfidHelper();
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
