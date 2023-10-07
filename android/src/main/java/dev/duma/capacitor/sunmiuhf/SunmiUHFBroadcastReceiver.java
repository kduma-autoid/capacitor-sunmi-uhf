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
        void onReaderBoot();
        void onReaderConnected();
        void onReaderDisconnected();
        void onReaderLostConnection();
        void onBatteryRemainingPercent(int charge_level);
        void onBatteryLowElectricity(int charge_level);
        void onBatteryChargeState(BatteryChargingStateEnum state);
        void onBatteryChargeNumTimes(int battery_cycles);
        void onBatteryVoltage(int voltage);
        void onFirmwareVersion(int major, int minor);
        void onReaderSN(String sn, String region, int band_low, int band_high);
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (callback == null)
                return;

            switch (Objects.requireNonNull(intent.getAction())) {
                case ParamCts.BROADCAST_ON_LOST_CONNECT -> callback.onReaderLostConnection();

                case ParamCts.BROADCAST_ON_DISCONNECT -> callback.onReaderDisconnected();

                case ParamCts.BROADCAST_ON_CONNECT -> callback.onReaderConnected();

                case ParamCts.BROADCAST_READER_BOOT -> callback.onReaderBoot();

                case ParamCts.BROADCAST_BATTER_LOW_ELEC -> callback.onBatteryLowElectricity(
                        intent.getIntExtra(ParamCts.BATTERY_REMAINING_PERCENT, 100)
                );

                case ParamCts.BROADCAST_BATTERY_REMAINING_PERCENTAGE -> callback.onBatteryRemainingPercent(
                        intent.getIntExtra(ParamCts.BATTERY_REMAINING_PERCENT, 100)
                );

                case ParamCts.BROADCAST_BATTER_CHARGING -> callback.onBatteryChargeState(
                        switch (intent.getByteExtra(ParamCts.BATTERY_CHARGING, (byte) 0)) {
                            case (byte) 0x00 -> BatteryChargingStateEnum.NotCharging;
                            case (byte) 0x01 -> BatteryChargingStateEnum.PreCharging;
                            case (byte) 0x02 -> BatteryChargingStateEnum.QuickCharging;
                            case (byte) 0x03 -> BatteryChargingStateEnum.Charged;
                            default -> BatteryChargingStateEnum.Unknown;
                        }
                );

                case ParamCts.BROADCAST_BATTER_CHARGING_NUM_TIMES -> callback.onBatteryChargeNumTimes(
                        intent.getIntExtra(ParamCts.BATTERY_CHARGING_NUM_TIMES, 0)
                );

                case ParamCts.BROADCAST_FIRMWARE_VERSION -> callback.onFirmwareVersion(
                        intent.getIntExtra(ParamCts.FIRMWARE_MAIN_VERSION, 0),
                        intent.getIntExtra(ParamCts.FIRMWARE_MIN_VERSION, 0)
                );

                case ParamCts.BROADCAST_SN -> {
                    String sn = intent.getStringExtra(ParamCts.SN);
                    if(sn == null) {
                        return;
                    }

                    int[] rfBand = ParamCts.INSTANCE.getRFFrequencyBand(sn);

                    String region;

                    if (rfBand[0] == 1) {
                        region = switch(rfBand[3]) {
                            default -> "America"; // 0x01
                            case 0x02 -> "Europe";
                            case 0x03 -> "China";
                        };
                    } else {
                        region = "Unknown";
                    }


                    callback.onReaderSN(
                            sn, region, rfBand[1], rfBand[2]
                    );
                }

                case ParamCts.BROADCAST_BATTERY_VOLTAGE -> callback.onBatteryVoltage(
                        intent.getIntExtra(ParamCts.BATTERY_VOLTAGE, 0)
                );
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
