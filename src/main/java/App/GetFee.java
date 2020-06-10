package App;

import Domain.Room;
import Enum.*;
import MyGui.GuiModel;
import MyHttp.HttpRequestModel;
import net.sf.json.JSONObject;

import javax.swing.*;

public class GetFee implements Runnable{
    private Room room;
    private HttpRequestModel getFeeHttpRequestModel;
    private GuiModel guiModel;

    public GetFee(Room room, GuiModel guiModel) {
        this.room = room;
        this.guiModel = guiModel;
        getFeeHttpRequestModel = new HttpRequestModel("/room/fee", "GET");
    }

    @Override
    public void run() {
        double preTemperature = room.getCurrentTemp();
        while(true){
            System.out.println("费用线程运行中");
            if(room.getState() == State.OFF || room.getState() == State.DISCONNECT){
                System.out.println("费用线程死了");
                return;
            }
            if(room.getState() == State.SERVE || room.getState() == State.ON || room.getState() == State.HOLDON || room.getState() == State.WAIT){
                System.out.println("获取费用 上传温度");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", room.getCustomId());
                jsonObject.put("currentTemperature", room.getCurrentTemp());
                jsonObject.put("changeTemperature", room.getCurrentTemp() - preTemperature);
                try {
                    jsonObject = getFeeHttpRequestModel.send(jsonObject, "?id=" + room.getCustomId() + "&currentTemperature=" + room.getCurrentTemp() + "&changeTemperature=" + (room.getCurrentTemp() - preTemperature));
                    if(jsonObject.getInt("status") == 0){
                        JSONObject data = JSONObject.fromObject(jsonObject.get("data"));
                        int state = data.getInt("roomState");
                        switch (state){
                            case 0:room.setState(State.SERVE); break;
                            case 1:room.setState(State.WAIT); break;
                            case 2:room.setState(State.HOLDON); break;
                        }
                        if(data.getInt("id") == room.getCustomId()){
                            room.setFee(data.getDouble("fee"));
                        }
                        guiModel.getIdLabel().setText("用户ID为: " + room.getCustomId());
                        guiModel.getRoomLabel().setText("房间ID为: " + room.getRoomId());
                        guiModel.getStateLabel().setText("空调状态为: " + room.getState());
                        guiModel.getCurrentTempLabel().setText("当前温度为: " +  String.format("%.4f", room.getCurrentTemp()) + "度");
                        guiModel.getFeeLabel().setText("当前费用为: " + String.format("%.4f", room.getFee()) + "元");
                        guiModel.getFeeRateLabel().setText("当前费率为: " + String.format("%.2f", room.getFeeRate()) + "元/分钟");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(guiModel, "远端服务器已经关机", "警告", JOptionPane.ERROR_MESSAGE);
                    // getFee失败 代表远端服务器关闭，断开连接
                    // 房间初始化
                    room.setTargetTemp(room.getDefaultTargetTemp());
                    room.setFanSpeed(room.getDefaultFanSpeed());
                    room.setFeeRate(0.5);
                    room.setState(State.DISCONNECT);
                    room.setCurrentTemp(room.getOutTemp());
                    room.setFee(0);
                    // 状态初始化
                    guiModel.getFanComboBox().setSelectedIndex(room.getFanSpeed().ordinal());
                    guiModel.getTargetTempTextField().setValue(room.getTargetTemp());
                    guiModel.getTurnOn().setEnabled(false);
                    guiModel.getTurnOff().setEnabled(false);
                    guiModel.getModeComboBox().setEnabled(false);
                    guiModel.getModeButton().setEnabled(false);
                    guiModel.getFanComboBox().setEnabled(false);
                    guiModel.getFanButton().setEnabled(false);
                    guiModel.getTargetTempTextField().setEnabled(false);
                    guiModel.getTargetTempButton().setEnabled(false);
                    guiModel.getIdLabel().setText("用户ID为: " + room.getCustomId());
                    guiModel.getRoomLabel().setText("房间ID为: " + room.getRoomId());
                    guiModel.getStateLabel().setText("空调状态为: " + room.getState());
                    guiModel.getCurrentTempLabel().setText("当前温度为: " + String.format("%.4f", room.getCurrentTemp()) + "度");
                    guiModel.getFeeLabel().setText("当前费用为: " + String.format("%.4f", room.getFee()) + "元");
                    guiModel.getFeeRateLabel().setText("当前费率为: " + String.format("%.2f", room.getFeeRate()) + "元/分钟");
                    guiModel.getTurnOff().setSelected(true);
                    guiModel.getTurnOn().setSelected(false);
                    guiModel.getConnect().setSelected(false);
                    guiModel.getDisconnect().setSelected(true);
                    return;
                }
                preTemperature = room.getCurrentTemp();
            }
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
