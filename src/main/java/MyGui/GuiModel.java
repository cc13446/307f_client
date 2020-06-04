package MyGui;

import Domain.Room;
import MyListener.GuiListener;
import javax.swing.*;
import Enum.*;

public class GuiModel {
    private JFrame frame;
    private JPanel myPanel;
    private JButton turnButton;
    private JButton defaultTempButton;
    private JButton modeButton;
    private JButton fanButton;
    private GuiListener guiListener;
    private Room room;

    GuiModel()
    {
        myPanel = new JPanel();
        room = new Room();
        room.setCurrentTemp(20.5);
        room.setFanSpeed(FanSpeed.HIGH);
        room.setFee(0.0);
        room.setFeeRate(1.1);
        room.setState(State.ON);
        room.setRoomId(1);
        room.setTargetTemp(30.0);
        room.setMode(Mode.HOT);
        turnButton = new JButton("开关");
        defaultTempButton = new JButton("温度");
        modeButton = new JButton("模式");
        fanButton = new JButton("风速");

        myPanel.add(turnButton); // 添加按钮到面板
        myPanel.add(defaultTempButton);
        myPanel.add(modeButton);
        myPanel.add(fanButton);

        frame = new JFrame("307f空调控制面板"); // 新建JFrame
        frame.getContentPane().add(myPanel);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        guiListener = new GuiListener(frame, room);
        turnButton.addActionListener(guiListener);
        defaultTempButton.addActionListener(guiListener);
        modeButton.addActionListener(guiListener);
        fanButton.addActionListener(guiListener);
    }

    public static void main(String s[]) {
        GuiModel guiModel = new GuiModel(); // 新建Simple1组件
        guiModel.frame.setVisible(true);

    }
}