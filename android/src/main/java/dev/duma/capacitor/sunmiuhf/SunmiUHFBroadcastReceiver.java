package dev.duma.capacitor.sunmiuhf;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.sunmi.rfid.constant.ParamCts;

import java.util.Objects;

import dev.duma.capacitor.sunmiuhf.internals.models.BatteryChargingStateEnum;

public class SunmiUHFBroadcastReceiver {
    public interface ScanCallback {
        void onReaderConnected();
        void onReaderDisconnected();
        void onBatteryState(int charge_level);

        void onBatteryCharging(BatteryChargingStateEnum state);

        void onBatteryChargingNumTimes(int battery_cycles);
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (callback == null)
                return;

            switch (Objects.requireNonNull(intent.getAction())) {
                case ParamCts.BROADCAST_ON_LOST_CONNECT, ParamCts.BROADCAST_ON_DISCONNECT -> callback.onReaderDisconnected();

                case ParamCts.BROADCAST_READER_BOOT, ParamCts.BROADCAST_ON_CONNECT -> callback.onReaderConnected();

                case ParamCts.BROADCAST_BATTER_LOW_ELEC, ParamCts.BROADCAST_BATTERY_REMAINING_PERCENTAGE -> {
                    callback.onBatteryState(
                            intent.getIntExtra(ParamCts.BATTERY_REMAINING_PERCENT, 100)
                    );
                }

                case ParamCts.BROADCAST_BATTER_CHARGING -> {
                    callback.onBatteryCharging(
                            switch (intent.getByteExtra(ParamCts.BATTERY_CHARGING, (byte) 0)) {
                                case (byte) 0x00 -> BatteryChargingStateEnum.NotCharging;
                                case (byte) 0x01 -> BatteryChargingStateEnum.PreCharging;
                                case (byte) 0x02 -> BatteryChargingStateEnum.QuickCharging;
                                case (byte) 0x03 -> BatteryChargingStateEnum.Charged;
                                default -> BatteryChargingStateEnum.Unknown;
                            }
                    );
                }

                case ParamCts.BROADCAST_BATTER_CHARGING_NUM_TIMES -> {
                    callback.onBatteryChargingNumTimes(
                            intent.getIntExtra(ParamCts.BATTERY_CHARGING_NUM_TIMES, 0)
                    );
                }
            }
        }
    };

    private final SunmiUHF uhf;

    public SunmiUHFBroadcastReceiver(SunmiUHF uhf) {
        this.uhf = uhf;
    }

    private ScanCallback callback = null;
    public void setCallback(ScanCallback callback) {
        this.callback = callback;
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    public void register() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ParamCts.BROADCAST_READER_BOOT);
        filter.addAction(ParamCts.BROADCAST_ON_CONNECT);

        filter.addAction(ParamCts.BROADCAST_ON_LOST_CONNECT);
        filter.addAction(ParamCts.BROADCAST_ON_DISCONNECT);

        filter.addAction(ParamCts.BROADCAST_BATTER_LOW_ELEC);
        filter.addAction(ParamCts.BROADCAST_BATTERY_REMAINING_PERCENTAGE);

        filter.addAction(ParamCts.BROADCAST_BATTER_CHARGING);

        filter.addAction(ParamCts.BROADCAST_BATTER_CHARGING_NUM_TIMES);

        uhf.getPlugin().getContext().registerReceiver(receiver, filter);
    }

    public void unregister() {
        try {
            uhf.getPlugin().getContext().unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
