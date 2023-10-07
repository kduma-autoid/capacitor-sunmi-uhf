package dev.duma.capacitor.sunmiuhf;

import android.os.RemoteException;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import dev.duma.capacitor.sunmiuhf.internals.models.BatteryChargingStateEnum;

@CapacitorPlugin(name = "SunmiUHF")
public class SunmiUHFPlugin extends Plugin {
    private SunmiUHF implementation;

    @Override
    public void load() {
        implementation = new SunmiUHF(this);

        implementation.getBroadcastReceiver().setCallback(new SunmiUHFBroadcastReceiver.ScanCallback() {
            @Override
            public void onReaderConnected() {
                notifyListeners("onReaderConnected", new JSObject());
            }

            @Override
            public void onReaderDisconnected() {
                notifyListeners("onReaderDisconnected", new JSObject());
            }

            @Override
            public void onBatteryState(int charge_level) {
                JSObject ret = new JSObject();
                ret.put("charge_level", charge_level);
                notifyListeners("onBatteryState", ret);
            }

            @Override
            public void onBatteryChargingNumTimes(int battery_cycles) {
                JSObject ret = new JSObject();
                ret.put("battery_cycles", battery_cycles);
                notifyListeners("onBatteryChargingNumTimes", ret);
            }

            @Override
            public void onBatteryCharging(BatteryChargingStateEnum state) {
                JSObject ret = new JSObject();
                ret.put("state", switch (state) {
                    case Unknown -> "Unknown";
                    case NotCharging -> "NotCharging";
                    case PreCharging -> "PreCharging";
                    case QuickCharging -> "QuickCharging";
                    case Charged -> "Charged";
                });
                notifyListeners("onBatteryCharging", ret);
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
    public void refreshBatteryState(PluginCall call) throws RemoteException {
        try {
            implementation.basicInformation().refreshBatteryState();
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
