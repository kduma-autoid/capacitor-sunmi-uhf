package dev.duma.capacitor.sunmiuhf;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.sunmi.rfid.constant.ParamCts;

import java.util.Objects;

public class SunmiUHFBroadcastReceiver {
    public interface ScanCallback {
        void onReaderConnected();
        void onReaderDisconnected();
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (callback == null)
                return;

            switch (Objects.requireNonNull(intent.getAction())) {
                case ParamCts.BROADCAST_ON_LOST_CONNECT, ParamCts.BROADCAST_ON_DISCONNECT -> callback.onReaderDisconnected();

                case ParamCts.BROADCAST_READER_BOOT, ParamCts.BROADCAST_ON_CONNECT -> callback.onReaderConnected();
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
