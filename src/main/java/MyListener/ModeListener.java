package MyListener;

import Domain.Room;
import Enum.*;
import MyGui.GuiModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModeListener implements ActionListener{
    private JComboBox<String> modeComboBox;
    private JButton modeButton;
    private GuiModel guiModel;
    private Room room;

    public ModeListener(JComboBox<String> modeComboBox, JButton modeButton, GuiModel guiModel, Room room) {
        this.modeComboBox = modeComboBox;
        this.modeButton = modeButton;
        this.guiModel = guiModel;
        this.room = room;
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("设置模式")){
            int index = modeComboBox.getSelectedIndex();
            if(room.getMode() != Mode.values()[index]){
                room.setMode(Mode.values()[index]);
                System.out.println("设置模式" + Mode.values()[index]);
            }

        }
    }
}
