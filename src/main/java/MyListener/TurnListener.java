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


            }
            turnOn.setSelected(true);
            turnOff.setSelected(false);
        }
        else if(e.getSource() == turnOff){
            if(turnOff.isSelected()){
                System.out.println("关机");
            }
            turnOn.setSelected(false);
            turnOff.setSelected(true);
        }
    }
}
