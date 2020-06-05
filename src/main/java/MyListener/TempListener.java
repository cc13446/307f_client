package MyListener;

import Domain.Room;
import Enum.FanSpeed;
import MyGui.GuiModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TempListener implements ActionListener{
    private JFormattedTextField targetTempTextField;
    private JButton targetTempButton;
    private GuiModel guiModel;
    private Room room;

    public TempListener(JFormattedTextField targetTempTextField, JButton targetTempButton, GuiModel guiModel, Room room) {
        this.targetTempTextField = targetTempTextField;
        this.targetTempButton = targetTempButton;
        this.guiModel = guiModel;
        this.room = room;
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("设置温度")){
            double index = ((Number)targetTempTextField.getValue()).doubleValue();
            if(index >= room.getTempLowLimit() && index <= room.getTempHighLimit()){
                if(room.getTargetTemp() != index ){
                    room.setTargetTemp(index);
                    System.out.println("设置温度" + index);
                }
            }
            else{
                targetTempTextField.setValue(null);
                JOptionPane.showMessageDialog(guiModel, "温度不再区间内", "警告！", JOptionPane.ERROR_MESSAGE);
            }

        }
    }
}
