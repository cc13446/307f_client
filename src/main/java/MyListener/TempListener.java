package MyListener;

import Domain.Room;
import Enum.FanSpeed;
import MyGui.GuiModel;
import MyHttp.HttpRequestModel;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TempListener implements ActionListener{
    private JFormattedTextField targetTempTextField;
    private JButton targetTempButton;
    private GuiModel guiModel;
    private Room room;
    private HttpRequestModel changeTempHttpRequestModel;

    public TempListener(JFormattedTextField targetTempTextField, JButton targetTempButton, GuiModel guiModel, Room room) {
        this.targetTempTextField = targetTempTextField;
        this.targetTempButton = targetTempButton;
        this.guiModel = guiModel;
        this.room = room;
        changeTempHttpRequestModel = new HttpRequestModel("/room/temp", "POST");
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("设置温度")){
            double index = ((Number)targetTempTextField.getValue()).doubleValue();
            if(index >= room.getTempLowLimit() && index <= room.getTempHighLimit()){
                if(room.getTargetTemp() != index ){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", room.getCustomId());
                    jsonObject.put("targetTemperature", index);
                    try {
                        jsonObject = changeTempHttpRequestModel.send(jsonObject, "?id=" + room.getCustomId() + "&targetTemperature=" + index);
                        if(jsonObject.getInt("status") == 0){
                            room.setTargetTemp(index);
                            System.out.println("设置温度" + index);
                            System.out.println(room);
                            System.out.println(jsonObject);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(guiModel, "设置温度失败", "警告", JOptionPane.ERROR_MESSAGE);
                        targetTempTextField.setValue(room.getTargetTemp());
                        return;
                    }
                }
            }
            else{
                targetTempTextField.setValue(null);
                JOptionPane.showMessageDialog(guiModel, "温度不再区间内", "警告！", JOptionPane.ERROR_MESSAGE);
            }

        }
    }
}
