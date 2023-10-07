package dev.duma.capacitor.sunmiuhf;

import android.os.RemoteException;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.text.MessageFormat;

import dev.duma.capacitor.sunmiuhf.internals.models.BatteryChargingStateEnum;

@CapacitorPlugin(name = "SunmiUHF")
public class SunmiUHFPlugin extends Plugin {
    private SunmiUHF implementation;

    @Override
    public void load() {
        boolean sdkDebugOutput = getConfig().getBoolean("SdkDebugOutput", false);

        implementation = new SunmiUHF(this, sdkDebugOutput);

        implementation.getBroadcastReceiver().setCallback(new SunmiUHFBroadcastReceiver.ScanCallback() {
            @Override
            public void onReaderBoot() {
                notifyListeners("onReaderBoot", new JSObject());
                notifyListeners("onReaderBootOrConnected", new JSObject());
            }

            @Override
            public void onReaderConnected() {
                notifyListeners("onReaderConnected", new JSObject());
                notifyListeners("onReaderBootOrConnected", new JSObject());
            }

            @Override
            public void onReaderDisconnected() {
                notifyListeners("onReaderDisconnected", new JSObject());
                notifyListeners("onReaderDisconnectedOrLostConnection", new JSObject());
            }

            @Override
            public void onReaderLostConnection() {
                notifyListeners("onReaderLostConnection", new JSObject());
                notifyListeners("onReaderDisconnectedOrLostConnection", new JSObject());
            }

            @Override
            public void onBatteryRemainingPercent(int charge_level) {
                JSObject ret = new JSObject();
                ret.put("charge_level", charge_level);
                notifyListeners("onBatteryRemainingPercent", ret);
                notifyListeners("onBatteryRemainingPercentOrLowElectricity", ret);
            }

            @Override
            public void onBatteryLowElectricity(int charge_level) {
                JSObject ret = new JSObject();
                ret.put("charge_level", charge_level);
                notifyListeners("onBatteryLowElectricity", ret);
                notifyListeners("onBatteryRemainingPercentOrLowElectricity", ret);
            }

            @Override
            public void onBatteryChargeNumTimes(int battery_cycles) {
                JSObject ret = new JSObject();
                ret.put("battery_cycles", battery_cycles);
                notifyListeners("onBatteryChargeNumTimes", ret);
            }

            @Override
            public void onBatteryVoltage(int voltage) {
                JSObject ret = new JSObject();
                ret.put("battery_cycles", voltage);
                notifyListeners("onBatteryVoltage", ret);
            }

            @Override
            public void onFirmwareVersion(int major, int minor) {
                JSObject ret = new JSObject();
                ret.put("version", MessageFormat.format("{0}.{1}", major, minor));
                ret.put("major", major);
                ret.put("minor", minor);
                notifyListeners("onFirmwareVersion", ret);
            }

            @Override
            public void onReaderSN(String sn, String region, int band_low, int band_high) {
                JSObject ret = new JSObject();
                ret.put("sn", sn);
                ret.put("region", region);
                ret.put("band_low", band_low);
                ret.put("band_high", band_high);
                notifyListeners("onFirmwareVersion", ret);
            }

            @Override
            public void onBatteryChargeState(BatteryChargingStateEnum state) {
                JSObject ret = new JSObject();
                ret.put("state", switch (state) {
                    case Unknown -> "Unknown";
                    case NotCharging -> "NotCharging";
                    case PreCharging -> "PreCharging";
                    case QuickCharging -> "QuickCharging";
                    case Charged -> "Charged";
                });
                notifyListeners("onBatteryChargeState", ret);
            }
        });

        implementation.getBroadcastReceiver().register();
    }

    // OnTerminate -> RFIDManager.getInstance().disconnect();

    @PluginMethod
    public void getScanModel(PluginCall call) throws RemoteException {
        try {
            implementation.basicInformation().getScanModel((model, available) -> {
                JSObject ret = new JSObject();
                ret.put("model", model);
                ret.put("available", available);
                call.resolve(ret);
            });
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void getBatteryChargeState(PluginCall call) throws RemoteException {
        try {
            implementation.basicInformation().getBatteryChargeState();
            call.resolve();
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void getBatteryRemainingPercent(PluginCall call) throws RemoteException {
        try {
            implementation.basicInformation().getBatteryRemainingPercent();
            call.resolve();
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void getBatteryChargeNumTimes(PluginCall call) throws RemoteException {
        try {
            implementation.basicInformation().getBatteryChargeNumTimes();
            call.resolve();
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void getBatteryVoltage(PluginCall call) throws RemoteException {
        try {
            implementation.basicInformation().getBatteryVoltage();
            call.resolve();
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void getFirmwareVersion(PluginCall call) throws RemoteException {
        try {
            implementation.basicInformation().getFirmwareVersion();
            call.resolve();
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void getReaderSN(PluginCall call) throws RemoteException {
        try {
            implementation.basicInformation().getReaderSN();
            call.resolve();
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void startScanning(PluginCall call) {
        try {
            implementation.tagInventory().startScanning(call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    public void setTagReadCallback(PluginCall call) {
        try {
            implementation.tagInventory().setTagReadCallback(call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void clearTagReadCallback(PluginCall call) {
        try {
            implementation.tagInventory().clearTagReadCallback(bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    public void setInventoryScanCompletedCallback(PluginCall call) {
        try {
            implementation.tagInventory().setInventoryScanCompletedCallback(call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void clearInventoryScanCompletedCallback(PluginCall call) {
        try {
            implementation.tagInventory().clearInventoryScanCompletedCallback(bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void stopScanning(PluginCall call) {
        try {
            implementation.tagInventory().stopScanning(call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void getAccessEpcMatch(PluginCall call) {
        try {
            implementation.tagOperations().getAccessEpcMatch(call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void setAccessEpcMatch(PluginCall call) {
        try {
            implementation.tagOperations().setAccessEpcMatch(call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void cancelAccessEpcMatch(PluginCall call) {
        try {
            implementation.tagOperations().cancelAccessEpcMatch(call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void readTag(PluginCall call) {
        try {
            implementation.tagOperations().readTag(call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void writeTag(PluginCall call) {
        try {
            implementation.tagOperations().writeTag(call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void lockTag(PluginCall call) {
        try {
            implementation.tagOperations().lockTag(call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void killTag(PluginCall call) {
        try {
            implementation.tagOperations().killTag(call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void getImpinjFastTid(PluginCall call) {
        try {
            implementation.tagOperations().getImpinjFastTid(call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void setImpinjFastTid(PluginCall call) {
        try {
            implementation.tagOperations().setImpinjFastTid(call, bridge);
        } catch (RuntimeException e) {
            call.reject(e.getMessage(), e);
        }
    }
}
