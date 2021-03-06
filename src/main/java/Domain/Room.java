package Domain;

import Enum.*;

public class Room {
    private int customId;
    private int roomId;
    private State state;
    private Mode mode;
    private double outTemp;
    private double currentTemp;
    private double tempHighLimit;
    private double tempLowLimit;
    private double targetTemp;
    private double defaultTargetTemp;
    private FanSpeed fanSpeed;
    private FanSpeed defaultFanSpeed;
    private double fee;
    private double feeRate;

    public double getDefaultTargetTemp() {
        return defaultTargetTemp;
    }

    public void setDefaultTargetTemp(double defaultTargetTemp) {
        this.defaultTargetTemp = defaultTargetTemp;
    }

    public FanSpeed getDefaultFanSpeed() {
        return defaultFanSpeed;
    }

    public void setDefaultFanSpeed(FanSpeed defaultFanSpeed) {
        this.defaultFanSpeed = defaultFanSpeed;
    }

    public double getOutTemp() {
        return outTemp;
    }

    public void setOutTemp(double outTemp) {
        this.outTemp = outTemp;
    }

    public int getCustomId() {
        return customId;
    }

    public void setCustomId(int customId) {
        this.customId = customId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(double currentTemp) {
        this.currentTemp = currentTemp;
    }

    public double getTempHighLimit() {
        return tempHighLimit;
    }

    public void setTempHighLimit(double tempHighLimit) {
        this.tempHighLimit = tempHighLimit;
    }

    public double getTempLowLimit() {
        return tempLowLimit;
    }

    public void setTempLowLimit(double tempLowLimit) {
        this.tempLowLimit = tempLowLimit;
    }

    public double getTargetTemp() {
        return targetTemp;
    }

    public void setTargetTemp(double targetTemp) {
        this.targetTemp = targetTemp;
    }

    public FanSpeed getFanSpeed() {
        return fanSpeed;
    }

    public void setFanSpeed(FanSpeed fanSpeed) {
        this.fanSpeed = fanSpeed;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(double feeRate) {
        this.feeRate = feeRate;
    }

    @Override
    public String toString() {
        return "Room{" +
                "customId=" + customId +
                ", roomId=" + roomId +
                ", state=" + state +
                ", mode=" + mode +
                ", outTemp=" + outTemp +
                ", currentTemp=" + currentTemp +
                ", tempHighLimit=" + tempHighLimit +
                ", tempLowLimit=" + tempLowLimit +
                ", targetTemp=" + targetTemp +
                ", fanSpeed=" + fanSpeed +
                ", fee=" + fee +
                ", feeRate=" + feeRate +
                '}';
    }
}
