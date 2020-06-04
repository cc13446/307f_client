package MyGui;

import Domain.Room;
import MyHttp.HttpRequestModel;
import MyListener.GuiListener;

import java.awt.event.*;
import javax.swing.*;

public class GuiModel {
    private static JFrame frame;
    private static JPanel myPanel;
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
        turnButton = new JButton("开关");
        defaultTempButton = new JButton("26度");
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


        frame.setVisible(true);
    }
}