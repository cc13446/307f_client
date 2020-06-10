package App;

import Domain.Room;
import MyGui.GuiModel;
import MyHttp.HttpRequestModel;
import net.sf.json.JSONObject;
import Enum.*;

import javax.swing.*;

public class RequestOff {
    private Room room;
    private HttpRequestModel turnOffHttpRequestModel;
    private GuiModel guiModel;

    public RequestOff(Room room, GuiModel guiModel) {
        this.room = room;
        turnOffHttpRequestModel = new HttpRequestModel("/room/service", "PUT");
        this.guiModel = guiModel;
    }

    public boolean turnOff(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", room.getCustomId());

        try {
            jsonObject = turnOffHttpRequestModel.send(jsonObject, "?id=" + room.getCustomId());
            if(jsonObject.getInt("status") == 0){
                room.setState(State.OFF);
                System.out.println(room);
                System.out.println(jsonObject);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(guiModel, "关机失败", "警告", JOptionPane.ERROR_MESSAGE);
            guiModel.getTurnOn().setSelected(true);
            guiModel.getTurnOff().setSelected(false);
            return false;
        }
        guiModel.getStateLabel().setText("空调状态为: " + room.getState());
        guiModel.getFanComboBox().setEnabled(false);
        guiModel.getFanButton().setEnabled(false);
        guiModel.getTargetTempTextField().setEnabled(false);
        guiModel.getTargetTempButton().setEnabled(false);
        guiModel.getModeButton().setEnabled(false);
        room.setTargetTemp(room.getDefaultTargetTemp());
        room.setFanSpeed(room.getDefaultFanSpeed());
        room.setFeeRate(0.5);
        // 状态初始化
        guiModel.getFanComboBox().setSelectedIndex(room.getFanSpeed().ordinal());
        guiModel.getTargetTempTextField().setValue(room.getTargetTemp());
        guiModel.getFeeRateLabel().setText("当前费率为: " + String.format("%.2f", room.getFeeRate()) + "元/分钟");
        guiModel.getTurnOff().setSelected(true);
        guiModel.getTurnOn().setSelected(false);
        return true;
    }
}
