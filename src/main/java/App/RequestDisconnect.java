package App;

import Domain.Room;
import MyGui.GuiModel;
import MyHttp.HttpRequestModel;
import net.sf.json.JSONObject;
import Enum.*;
import javax.swing.*;

public class RequestDisconnect {

    private Room room;
    private HttpRequestModel turnOffHttpRequestModel;
    private HttpRequestModel disconnectHttpRequestModel;
    private GuiModel guiModel;
    private RequestOff requestOff;

    public RequestDisconnect(Room room, GuiModel guiModel) {
        this.room = room;
        disconnectHttpRequestModel = new HttpRequestModel("/room/exit", "PUT");
        turnOffHttpRequestModel = new HttpRequestModel("/room/service", "PUT");
        this.guiModel = guiModel;
        this.requestOff = new RequestOff(room, guiModel);
    }

    public void disConnect(){
        if(room.getState() != State.OFF){
            System.out.println("关机");
            requestOff.turnOff();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", room.getCustomId());
        try {
            jsonObject = disconnectHttpRequestModel.send(jsonObject, "?id" + room.getCustomId());
            if(jsonObject.getInt("status") == 0){
                room.setState(State.DISCONNECT);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(guiModel, "断开连接失败", "警告", JOptionPane.ERROR_MESSAGE);
            guiModel.getConnect().setSelected(true);
            guiModel.getDisconnect().setSelected(false);
            return;
        }
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
        // 状态初始化
        guiModel.getModeComboBox().setSelectedIndex(room.getFanSpeed().ordinal());
        guiModel.getTargetTempTextField().setValue(room.getTargetTemp());
        guiModel.getConnect().setSelected(false);
        guiModel.getDisconnect().setSelected(true);
    }
}
