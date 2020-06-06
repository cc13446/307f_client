package MyListener;

import App.RequestDisconnect;
import App.RequestOff;
import Domain.Room;
import MyGui.GuiModel;
import Enum.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
                if (room.getRoomId() != roomId) {
                    room.setRoomId(roomId);
                    System.out.println("设置房间号" + roomId);
                    JOptionPane.showMessageDialog(jFrame, "设置成功", "恭喜", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            if (e.getSource() == currentTempButton) {
                double currentTemp = ((Number) currentTempTextField.getValue()).doubleValue();
                if (room.getCurrentTemp() != currentTemp) {
                    room.setCurrentTemp(currentTemp);
                    room.setOutTemp(currentTemp);
                    System.out.println("设置室温" + currentTemp);
                    JOptionPane.showMessageDialog(jFrame, "设置成功", "恭喜", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }catch (NullPointerException ignored){
            JOptionPane.showMessageDialog(jFrame, "输入有误", "警告", JOptionPane.ERROR_MESSAGE);
        }
        if(e.getSource() == confirmButton){
            System.out.println("确认");
            int roomId = ((Number) roomIdTextField.getValue()).intValue();
            if (room.getRoomId() != roomId) {
                room.setRoomId(roomId);
                System.out.println("设置房间号" + roomId);
            }
            double currentTemp = ((Number) currentTempTextField.getValue()).doubleValue();
            if (room.getCurrentTemp() != currentTemp) {
                room.setCurrentTemp(currentTemp);
                room.setOutTemp(currentTemp);
                System.out.println("设置室温" + currentTemp);
            }
            jFrame.setVisible(false);
            room.setState(State.DISCONNECT);
            GuiModel guiModel = new GuiModel(room);
            guiModel.getRoomLabel().setText("房间ID为: " + room.getRoomId());
            guiModel.getCurrentTempLabel().setText("当前温度为: " + String.format("%.4f", room.getCurrentTemp()) + "度");
            guiModel.getStateLabel().setText("空调状态为: " + room.getState());
            guiModel.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    if(room.getState() != State.DISCONNECT){
                        new RequestDisconnect(room, guiModel).disConnect();
                    }
                    System.exit(0);
                }
            });
            guiModel.setVisible(true);

        }
    }
}
