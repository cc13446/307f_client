package MyListener;

import App.RequestOff;
import App.RequestOn;
import Domain.Room;
import MyGui.GuiModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TurnListener implements ActionListener{
    private JRadioButton turnOn;
    private JRadioButton turnOff;
    private GuiModel guiModel;
    private Room room;
    private RequestOn requestOn;
    private RequestOff requestOff;

    public TurnListener(JRadioButton turnOn, JRadioButton turnOff, GuiModel guiModel, Room room) {
        this.turnOn = turnOn;
        this.turnOff = turnOff;
        this.guiModel = guiModel;
        this.room = room;
        requestOn = new RequestOn(room,guiModel);
        requestOff = new RequestOff(room,guiModel);

    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == turnOn){
            boolean flag = true;
            if(turnOn.isSelected()){
                System.out.println("开机");
                flag = requestOn.turnOn();
            }
            if(flag){
                turnOn.setSelected(true);
                turnOff.setSelected(false);
            }
        }
        else if(e.getSource() == turnOff){
            boolean flag = true;
            if(turnOff.isSelected()){
                System.out.println("关机");
                flag = requestOff.turnOff();
            }
            if(flag){
                turnOn.setSelected(false);
                turnOff.setSelected(true);
            }
        }
    }
}
