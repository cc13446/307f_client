package MyListener;

import App.RequestDisconnect;
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
    private RequestDisconnect requestDisconnect;

    public ConnectListener(JRadioButton connect, JRadioButton disconnect, GuiModel guiModel, Room room) {
        this.connect = connect;
        this.disconnect = disconnect;
        this.guiModel = guiModel;
        this.room = room;
        connectHttpRequestModel = new HttpRequestModel("/room/initial", "POST");
        requestDisconnect = new RequestDisconnect(room, guiModel);
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
                guiModel.getCurrentTempLabel().setText("当前温度为: " + String.format("%.4f", room.getCurrentTemp()) + "度");
                guiModel.getFeeLabel().setText("当前费用为: " + String.format("%.4f", room.getFee())+ "元");
                guiModel.getFeeRateLabel().setText("当前费率为: " + String.format("%.2f", room.getFeeRate()) + "元/分钟");
                // 状态初始化
                guiModel.getModeComboBox().setSelectedIndex(room.getFanSpeed().ordinal());
                guiModel.getTargetTempTextField().setValue(room.getTargetTemp());
            }
            connect.setSelected(true);
            disconnect.setSelected(false);
        }
        else if(e.getSource() == disconnect){
            if(disconnect.isSelected()){
                System.out.println("断连接");
                requestDisconnect.disConnect();
            }
            connect.setSelected(false);
            disconnect.setSelected(true);
        }
    }
}
