package App;

import Domain.Room;
import MyGui.GuiModel;
import MyHttp.HttpRequestModel;
import net.sf.json.JSONObject;
import Enum.*;
import javax.swing.*;

public class RequestOn {
    private Room room;
    private HttpRequestModel turnOnHttpRequestModel;
    private GuiModel guiModel;

    public RequestOn(Room room, GuiModel guiModel) {
        this.room = room;
        this.guiModel = guiModel;
        turnOnHttpRequestModel = new HttpRequestModel("/room/service", "POST");
    }

    public void turnOn(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", room.getCustomId());
        jsonObject.put("targetTemperature", room.getTargetTemp());
        jsonObject.put("fanSpeed", room.getFanSpeed().ordinal());

        try {
            jsonObject = turnOnHttpRequestModel.send(jsonObject, "?id=" + room.getCustomId() + "&targetTemperature=" + room.getTargetTemp() + "&fanSpeed=" + room.getFanSpeed().ordinal());
            if(jsonObject.getInt("status") == 0){
                room.setState(State.ON);
                System.out.println(room);
                System.out.println(jsonObject);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(guiModel, "开机失败", "警告", JOptionPane.ERROR_MESSAGE);
            guiModel.getTurnOn().setSelected(false);
            guiModel.getTurnOff().setSelected(true);
            return;
        }
        guiModel.getStateLabel().setText("空调状态为: " + room.getState());
        guiModel.getFanComboBox().setEnabled(true);
        guiModel.getFanButton().setEnabled(true);
        guiModel.getTargetTempTextField().setEnabled(true);
        guiModel.getTargetTempButton().setEnabled(true);
        guiModel.getModeButton().setEnabled(true);
        // 状态初始化
        guiModel.getFanComboBox().setSelectedIndex(room.getFanSpeed().ordinal());
        guiModel.getTargetTempTextField().setValue(room.getTargetTemp());
        guiModel.getTurnOff().setSelected(false);
        guiModel.getTurnOn().setSelected(true);

        GetFee getFee = new GetFee(room, guiModel);
        new Thread(getFee).start();
        IncreaseTemp increaseTemp = new IncreaseTemp(guiModel, room);
        new Thread(increaseTemp).start();
        DecreaseTemp decreaseTemp = new DecreaseTemp(room);
        new Thread(decreaseTemp).start();
    }
}
