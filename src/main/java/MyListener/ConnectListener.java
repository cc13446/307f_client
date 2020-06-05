package MyListener;

import Domain.Room;
import MyGui.GuiModel;
import MyHttp.HttpRequestModel;
import net.sf.json.JSONObject;
import Enum.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectListener implements ActionListener{
    private JRadioButton connect;
    private JRadioButton disconnect;
    private GuiModel guiModel;
    private Room room;
    private HttpRequestModel connectHttpRequestModel;
    private HttpRequestModel disconnectHttpRequestModel;
    private HttpRequestModel turnOffHttpRequestModel;

    public ConnectListener(JRadioButton connect, JRadioButton disconnect, GuiModel guiModel, Room room) {
        this.connect = connect;
        this.disconnect = disconnect;
        this.guiModel = guiModel;
        this.room = room;
        connectHttpRequestModel = new HttpRequestModel("/room/initial", "POST");
        disconnectHttpRequestModel = new HttpRequestModel("/room/exit", "PUT");
        turnOffHttpRequestModel = new HttpRequestModel("/room/service", "PUT");
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == connect){
            if(connect.isSelected()){
                System.out.println("去连接");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("roomId", room.getRoomId());
                jsonObject.put("currentTemperature", room.getCurrentTemp());
                try {
                    jsonObject = connectHttpRequestModel.send(jsonObject, "?roomId=" + room.getRoomId() + "&currentTemperature=" + room.getCurrentTemp());
                    if(jsonObject.getInt("status") == 0){
                        JSONObject data = JSONObject.fromObject(jsonObject.get("data"));
                        room.setState(State.CONNECT);
                        room.setCustomId(data.getInt("id"));
                        room.setTempHighLimit(data.getDouble("highestTemperature"));
                        room.setTempLowLimit(data.getDouble("lowestTemperature"));
                        room.setFanSpeed(FanSpeed.values()[data.getInt("defaultFanSpeed")]);
                        room.setTargetTemp(data.getInt("defaultTargetTemperature"));
                        if (room.getTempLowLimit() == 25 && room.getTempHighLimit()==28){
                            room.setMode(Mode.COLD);
                            guiModel.getModeComboBox().setSelectedIndex(1);
                        }else{
                            room.setMode(Mode.HOT);
                            guiModel.getModeComboBox().setSelectedIndex(0);
                        }
                        guiModel.getModeComboBox().setEnabled(false);
                        System.out.println(room);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(guiModel, "连接失败", "警告", JOptionPane.ERROR_MESSAGE);
                    connect.setSelected(false);
                    disconnect.setSelected(true);
                    return;
                }
                guiModel.getTurnOn().setEnabled(true);
                guiModel.getTurnOff().setEnabled(true);
                guiModel.getTargetTempTextField().setValue(room.getTargetTemp());
                guiModel.getIdLabel().setText("用户ID为: " + room.getCustomId());
                guiModel.getRoomLabel().setText("房间ID为: " + room.getRoomId());
                guiModel.getStateLabel().setText("空调状态为: " + room.getState());
                guiModel.getCurrentTempLabel().setText("当前温度为: " + room.getCurrentTemp());
                guiModel.getFeeLabel().setText("当前费用为: " + room.getFee());
                guiModel.getFeeRateLabel().setText("当前费用为: " + room.getFeeRate());
            }
            connect.setSelected(true);
            disconnect.setSelected(false);
        }
        else if(e.getSource() == disconnect){
            if(disconnect.isSelected()){
                System.out.println("断连接");
                if(room.getState() != State.OFF){
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
                        JOptionPane.showMessageDialog(guiModel, "断开连接自动关机失败", "警告", JOptionPane.ERROR_MESSAGE);
                        guiModel.getTurnOn().setSelected(true);
                        guiModel.getTurnOff().setSelected(false);
                        return;
                    }
                    guiModel.getStateLabel().setText("空调状态为: " + room.getState());
                    guiModel.getFanComboBox().setEnabled(false);
                    guiModel.getFanButton().setEnabled(false);
                    guiModel.getTargetTempTextField().setEnabled(false);
                    guiModel.getTargetTempButton().setEnabled(false);
                    guiModel.getModeButton().setEnabled(false);
                    // 停止轮询
                    // 停止温控
                    guiModel.getTurnOn().setSelected(false);
                    guiModel.getTurnOff().setSelected(true);
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
                    connect.setSelected(true);
                    disconnect.setSelected(false);
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
                guiModel.getCurrentTempLabel().setText("当前温度为: " + room.getCurrentTemp());
                guiModel.getFeeLabel().setText("当前费用为: " + room.getFee());
                guiModel.getFeeRateLabel().setText("当前费用为: " + room.getFeeRate());
            }
            connect.setSelected(false);
            disconnect.setSelected(true);
        }
    }
}
