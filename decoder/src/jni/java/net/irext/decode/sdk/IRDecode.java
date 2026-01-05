package net.irext.decode.sdk;

import net.irext.decode.sdk.bean.ACStatus;
import net.irext.decode.sdk.bean.TemperatureRange;
import net.irext.decode.sdk.utils.Constants;

/**
 * Filename:       IRDecode.java
 * Revised:        Date: 2017-04-22
 * Revision:       Revision: 1.0
 * <p>
 * Description:    Wrapper-sdk of IR decode
 * <p>
 * Revision log:
 * 2017-04-23: created by strawmanbobi
 */
public class IRDecode {

    private static Object mSync = new Object();

    private native String irGetVersion();

    private native int irOpen(int category, int subCate, String fileName);

    private native int irOpenBinary(int category, int subCate, byte[] binaries, int binLength);

    private native int[] irDecode(int keyCode, ACStatus acStatus);

    private native void irClose();

    private native TemperatureRange irACGetTemperatureRange(int acMode);

    private native int irACGetSupportedMode();

    private native int irACGetSupportedWindSpeed(int acMode);

    private native int irACGetSupportedSwing(int acMode);

    private native int irACGetSupportedWindDirection(int acMode);

    private static IRDecode mInstance;
    public static IRDecode getInstance() {
        if (null == mInstance) {
            mInstance = new IRDecode();
        }
        return mInstance;
    }

    private IRDecode() {
        String libPath = "/data/irext/libirdecode_jni.so";
        System.out.println("loading decode library " + libPath);
        System.load(libPath);
    }
    public String getVersion() {
        return irGetVersion();
    }

    public int openFile(int category, int subCate, String fileName) {
        return irOpen(category, subCate, fileName);
    }

    public int openBinary(int category, int subCate, byte[] binaries, int binLength) {
        return irOpenBinary(category, subCate, binaries, binLength);
    }

    public int[] decodeBinary(int keyCode, ACStatus acStatus) {
        int []decoded;
        synchronized (mSync) {
            if (null == acStatus) {
                acStatus = new ACStatus();
            }
            // validate ac status
            if (!validateAcStatus(acStatus, keyCode)) {
                return new int[0];
            }
            decoded = irDecode(keyCode, acStatus);
        }
        return decoded;
    }

    public void closeBinary() {
        irClose();
    }

    public TemperatureRange getTemperatureRange(int acMode) {
        return irACGetTemperatureRange(acMode);
    }

    public int[] getACSupportedMode() {
        // cool, heat, auto, fan, de-humidification
        int []retSupportedMode = {0, 0, 0, 0, 0};
        int supportedMode = irACGetSupportedMode();
        for (int i = Constants.ACMode.MODE_COOL.getValue(); i <=
                Constants.ACMode.MODE_DEHUMIDITY.getValue(); i++) {
            retSupportedMode[i] = (supportedMode >>> 1) & 1;
        }
        return retSupportedMode;
    }

    public int[] getACSupportedWindSpeed(int acMode) {
        // auto, low, medium, high
        int []retSupportedWindSpeed = {0, 0, 0, 0};
        int supportedWindSpeed = irACGetSupportedWindSpeed(acMode);
        for (int i = Constants.ACWindSpeed.SPEED_AUTO.getValue();
             i <= Constants.ACWindSpeed.SPEED_HIGH.getValue();
             i++) {
            retSupportedWindSpeed[i] = (supportedWindSpeed >>> 1) & 1;
        }
        return retSupportedWindSpeed;
    }

    public int[] getACSupportedSwing(int acMode) {
        // swing-on, swing-off
        int []retSupportedSwing= {0, 0};
        int supportedSwing = irACGetSupportedSwing(acMode);
        for (int i = Constants.ACSwing.SWING_ON.getValue();
             i <= Constants.ACSwing.SWING_OFF.getValue();
             i++) {
            retSupportedSwing[i] = (supportedSwing >>> 1) & 1;
        }
        return retSupportedSwing;
    }

    public int getACSupportedWindDirection(int acMode) {
        // how many directions supported by specific AC
        return irACGetSupportedWindDirection(acMode);
    }

    private boolean validateAcStatus(ACStatus acStatus, int keyCode) {
        if (acStatus.getAcPower() != Constants.ACPower.POWER_ON.getValue() &&
            acStatus.getAcPower() != Constants.ACPower.POWER_OFF.getValue()) {
            return false;
        }
        if (acStatus.getAcMode() < Constants.ACMode.MODE_COOL.getValue() ||
                acStatus.getAcMode() > Constants.ACMode.MODE_DEHUMIDITY.getValue()) {
            return false;
        }
        if (acStatus.getAcTemp() < Constants.ACTemperature.TEMP_16.getValue() ||
                acStatus.getAcTemp() > Constants.ACTemperature.TEMP_30.getValue()) {
            return false;
        }
        if (acStatus.getAcWindSpeed() < Constants.ACWindSpeed.SPEED_AUTO.getValue() ||
                acStatus.getAcWindSpeed() > Constants.ACWindSpeed.SPEED_HIGH.getValue()) {
            return false;
        }
        if (acStatus.getAcWindDir() < Constants.ACSwing.SWING_ON.getValue() ||
                acStatus.getAcWindDir() > Constants.ACSwing.SWING_OFF.getValue()) {
            return false;
        }
        return true;
    }
}
