package MyListener;

import Domain.Room;
import MyGui.GuiModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Enum.*;
import MyHttp.HttpRequestModel;
import net.sf.json.JSONObject;

public class FanListener implements ActionListener{
    private JComboBox<String> fanComboBox;
    private JButton fanButton;
    private GuiModel guiModel;
    private Room room;
    private HttpRequestModel changeFanHttpRequestModel;

    public FanListener(JComboBox<String> fanComboBox, JButton fanButton, GuiModel guiModel, Room room) {
        this.fanComboBox = fanComboBox;
        this.fanButton = fanButton;
        this.guiModel = guiModel;
        this.room = room;
        changeFanHttpRequestModel = new HttpRequestModel("/room/fan", "POST");
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("设置风速")){
            int index = fanComboBox.getSelectedIndex();
            if(room.getFanSpeed() != FanSpeed.values()[index]){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", room.getCustomId());
                jsonObject.put("fanSpeed", index);
                try {
                    jsonObject = changeFanHttpRequestModel.send(jsonObject, "?id=" + room.getCustomId() + "&fanSpeed=" + index);
                    if(jsonObject.getInt("status") == 0){
                        System.out.println(room);
                        System.out.println(jsonObject);
                        room.setFanSpeed(FanSpeed.values()[index]);
                        room.setFeeRate(1/(3-(double)index));
                        System.out.println("设置风速" + FanSpeed.values()[index]);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(guiModel, "设置风速失败", "警告", JOptionPane.ERROR_MESSAGE);
                    fanComboBox.setSelectedIndex(room.getFanSpeed().ordinal());
                    return;
                }
            }

        }
    }
}
