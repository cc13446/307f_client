package MyListener;

import Domain.Room;
import MyGui.GuiModel;
import MyHttp.HttpRequestModel;
import net.sf.json.JSONObject;
import Enum.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TurnListener implements ActionListener{
    private JRadioButton turnOn;
    private JRadioButton turnOff;
    private GuiModel guiModel;
    private Room room;
    private HttpRequestModel turnOnHttpRequestModel;
    private HttpRequestModel turnOffHttpRequestModel;

    public TurnListener(JRadioButton turnOn, JRadioButton turnOff, GuiModel guiModel, Room room) {
        this.turnOn = turnOn;
        this.turnOff = turnOff;
        this.guiModel = guiModel;
        this.room = room;
        turnOnHttpRequestModel = new HttpRequestModel("/room/service", "POST");
        turnOffHttpRequestModel = new HttpRequestModel("/room/service", "PUT");
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == turnOn){
            if(turnOn.isSelected()){
                System.out.println("开机");
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
                    turnOn.setSelected(false);
                    turnOff.setSelected(true);
                    return;
                }
                guiModel.getStateLabel().setText("空调状态为: " + room.getState());
                // 开启轮询
                // 温度温控
            }
            turnOn.setSelected(true);
            turnOff.setSelected(false);
        }
        else if(e.getSource() == turnOff){
            if(turnOff.isSelected()){
                System.out.println("关机");
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
                    JOptionPane.showMessageDialog(guiModel, "开机失败", "警告", JOptionPane.ERROR_MESSAGE);
                    turnOn.setSelected(false);
                    turnOff.setSelected(true);
                    return;
                }
                guiModel.getStateLabel().setText("空调状态为: " + room.getState());
                // 停止轮询
                // 停止温控
            }
            turnOn.setSelected(false);
            turnOff.setSelected(true);
        }
    }
}
