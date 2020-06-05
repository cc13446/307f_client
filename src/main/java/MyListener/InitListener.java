package MyListener;

import Domain.Room;
import MyGui.GuiModel;
import MyHttp.HttpRequestModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitListener implements ActionListener{
    private JFrame jFrame;
    private JFormattedTextField roomIdTextField;
    private JButton roomIdButton;
    private JFormattedTextField currentTempTextField;
    private JButton currentTempButton;
    private JButton confirmButton;
    private Room room;

    public InitListener(Room room, JFrame jFrame, JFormattedTextField roomIdTextField, JButton roomIdButton, JFormattedTextField currentTempTextField, JButton currentTempButton, JButton confirmButton) {
        this.room = room;
        this.jFrame = jFrame;
        this.roomIdTextField = roomIdTextField;
        this.roomIdButton = roomIdButton;
        this.currentTempTextField = currentTempTextField;
        this.currentTempButton = currentTempButton;
        this.confirmButton = confirmButton;
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == roomIdButton) {
                int roomId = ((Number) roomIdTextField.getValue()).intValue();
                if ("null".equals(String.valueOf(room.getRoomId())) || room.getRoomId() != roomId) {
                    room.setRoomId(roomId);
                    System.out.println("设置房间号" + roomId);
                    JOptionPane.showMessageDialog(jFrame, "设置成功", "恭喜", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            if (e.getSource() == currentTempButton) {
                double currentTemp = ((Number) currentTempTextField.getValue()).doubleValue();
                if ("null".equals(String.valueOf(room.getCurrentTemp())) || room.getCurrentTemp() != currentTemp) {
                    room.setCurrentTemp(currentTemp);
                    System.out.println("设置室温" + currentTemp);
                    JOptionPane.showMessageDialog(jFrame, "设置成功", "恭喜", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }catch (NullPointerException ignored){
            JOptionPane.showMessageDialog(jFrame, "输入有误", "警告", JOptionPane.ERROR_MESSAGE);
        }
        if(e.getSource() == confirmButton){
            System.out.println("确认");
            jFrame.setVisible(false);
            GuiModel guiModel = new GuiModel(room);
            guiModel.setVisible(true);

        }
    }
}
