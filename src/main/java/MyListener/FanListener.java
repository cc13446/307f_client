package MyListener;

import Domain.Room;
import MyGui.GuiModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Enum.*;

public class FanListener implements ActionListener{
    private JComboBox<String> fanComboBox;
    private JButton fanButton;
    private GuiModel guiModel;
    private Room room;

    public FanListener(JComboBox<String> fanComboBox, JButton fanButton, GuiModel guiModel, Room room) {
        this.fanComboBox = fanComboBox;
        this.fanButton = fanButton;
        this.guiModel = guiModel;
        this.room = room;
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("设置风速")){
            int index = fanComboBox.getSelectedIndex();
            if(room.getFanSpeed() != FanSpeed.values()[index]){
                room.setFanSpeed(FanSpeed.values()[index]);
                System.out.println("设置风速" + FanSpeed.values()[index]);
            }

        }
    }
}
