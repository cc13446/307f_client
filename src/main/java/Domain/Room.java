package Domain;

import Enum.*;

public class Room {
    private int roomId;
    private State state;
    private Mode mode;
    private Double currentTemp;
    private Double targetTemp;
    private FanSpeed fanSpeed;
    private double fee;
    private double feeRate;

    public Mode getMode() {
        return mode;
    }
    public void setMode(Mode mode) {
        this.mode = mode;
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

    public Double getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(Double currentTemp) {
        this.currentTemp = currentTemp;
    }

    public Double getTargetTemp() {
        return targetTemp;
    }

    public void setTargetTemp(Double targetTemp) {
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
                "roomId=" + roomId +
                ", state=" + state +
                ", mode=" + mode +
                ", currentTemp=" + currentTemp +
                ", targetTemp=" + targetTemp +
                ", fanSpeed=" + fanSpeed +
                ", fee=" + fee +
                ", feeRate=" + feeRate +
                '}';
    }
}
