package MyGui;

import Domain.Room;
import MyListener.*;
import Enum.*;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.Enumeration;

public class GuiModel extends javax.swing.JFrame{

    private JPanel panel;
    private JLabel label;
    private JRadioButton connect;
    private JRadioButton disconnect;
    private JRadioButton turnOn;
    private JRadioButton turnOff;
    private JComboBox<String> modeComboBox;
    private JButton modeButton;
    private JFormattedTextField targetTempTextField;
    private JButton targetTempButton;
    private JComboBox<String> fanComboBox;
    private JButton fanButton;
    private JLabel roomLabel;
    private JLabel idLabel;
    private JLabel stateLabel;
    private JLabel currentTempLabel;
    private JLabel feeLabel;
    private JLabel feeRateLabel;
    private Room room;

    private ConnectListener connectListener;
    private TurnListener turnListener;
    private FanListener fanListener;
    private ModeListener modeListener;
    private TempListener tempListener;

    public GuiModel(Room room)
    {
        super("空调面板");
        this.room = room;
        this.setSize(360, 500);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        label = new JLabel("波普特酒店空调控制面板");
        label.setFont(new Font("微软雅黑",Font.BOLD, 26));
        roomLabel = new JLabel("房间号为：");
        connect = new JRadioButton("建立连接");
        disconnect = new JRadioButton("断开连接");
        disconnect.setSelected(true);
        turnOn = new JRadioButton("开启空调");
        turnOn.setEnabled(false);
        turnOff = new JRadioButton("关闭空调");
        turnOff.setEnabled(false);
        turnOff.setSelected(true);
        String[] mode = new String[]{"制热", "制冷", "送风"};
        modeComboBox = new JComboBox<String>(mode);
        modeComboBox.setEnabled(false);
        modeComboBox.setMaximumSize(new Dimension(200,35));
        modeComboBox.setMinimumSize(new Dimension(200,35));
        modeButton = new JButton("设置模式");
        modeButton.setEnabled(false);
        targetTempTextField = new JFormattedTextField(NumberFormat.getNumberInstance());
        targetTempTextField.setValue(room.getTargetTemp());
        targetTempTextField.setEnabled(false);
        targetTempTextField.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if(!Character.isDigit(c) && c != '.')e.consume();
            }
        });
        targetTempTextField.setMaximumSize(new Dimension(200,35));
        targetTempTextField.setMinimumSize(new Dimension(200,35));
        targetTempButton = new JButton("设置温度");
        targetTempButton.setEnabled(false);
        String[] fan = new String[]{"低", "中", "高"};
        fanComboBox = new JComboBox<String>(fan);
        fanComboBox.setEnabled(false);
        fanComboBox.setSelectedIndex(1);
        fanComboBox.setMaximumSize(new Dimension(200,35));
        fanComboBox.setMinimumSize(new Dimension(200,35));
        fanButton = new JButton("设置风速");
        fanButton.setEnabled(false);
        idLabel = new JLabel("用户ID为: ");
        roomLabel = new JLabel("房间ID为: ");
        stateLabel = new JLabel("空调状态为: ");
        currentTempLabel = new JLabel("当前温度为: ");
        feeLabel = new JLabel("当前费用为: ");
        feeRateLabel = new JLabel("当前费率为: ");
        panel = new JPanel();

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        // 自动创建组件之间的间隙
        layout.setAutoCreateGaps(true);
        // 自动创建容器与触到容器边框的组件之间的间隙
        layout.setAutoCreateContainerGaps(true);
        /*
         * 水平组（仅确定 X 轴方向的坐标/排列方式）
         *
         * 水平串行: 水平排列（左右排列）
         * 水平并行: 垂直排列（上下排列）
        */
        GroupLayout.SequentialGroup connectHSeqGroup = layout.createSequentialGroup().addComponent(connect).addComponent(disconnect);
        GroupLayout.SequentialGroup turnHSeqGroup = layout.createSequentialGroup().addComponent(turnOn).addComponent(turnOff);
        GroupLayout.SequentialGroup modeHSeqGroup = layout.createSequentialGroup().addComponent(modeComboBox).addComponent(modeButton);
        GroupLayout.SequentialGroup fanHSeqGroup = layout.createSequentialGroup().addComponent(fanComboBox).addComponent(fanButton);
        GroupLayout.SequentialGroup tempHSeqGroup = layout.createSequentialGroup().addComponent(targetTempTextField).addComponent(targetTempButton);
        GroupLayout.ParallelGroup infoHParallelGroup = layout.createParallelGroup()
                .addComponent(idLabel, GroupLayout.Alignment.LEADING)
                .addComponent(roomLabel, GroupLayout.Alignment.LEADING)
                .addComponent(stateLabel, GroupLayout.Alignment.LEADING)
                .addComponent(currentTempLabel, GroupLayout.Alignment.LEADING)
                .addComponent(feeLabel, GroupLayout.Alignment.LEADING)
                .addComponent(feeRateLabel, GroupLayout.Alignment.LEADING);
        GroupLayout.ParallelGroup hParallelGroup = layout.createParallelGroup()
                .addComponent(label, GroupLayout.Alignment.CENTER)
                .addGroup(GroupLayout.Alignment.CENTER,connectHSeqGroup)
                .addGroup(GroupLayout.Alignment.CENTER,turnHSeqGroup)
                .addGroup(GroupLayout.Alignment.CENTER,modeHSeqGroup)
                .addGroup(GroupLayout.Alignment.CENTER,fanHSeqGroup)
                .addGroup(GroupLayout.Alignment.CENTER,tempHSeqGroup)
                .addGroup(GroupLayout.Alignment.CENTER,infoHParallelGroup);
        layout.setHorizontalGroup(hParallelGroup);

        GroupLayout.ParallelGroup connectVParallelGroup = layout.createParallelGroup().addComponent(connect).addComponent(disconnect);
        GroupLayout.ParallelGroup turnVParallelGroup = layout.createParallelGroup().addComponent(turnOn).addComponent(turnOff);
        GroupLayout.ParallelGroup modeVParallelGroup = layout.createParallelGroup().addComponent(modeComboBox).addComponent(modeButton);
        GroupLayout.ParallelGroup fanVParallelGroup = layout.createParallelGroup().addComponent(fanComboBox).addComponent(fanButton);
        GroupLayout.ParallelGroup tempVParallelGroup = layout.createParallelGroup().addComponent(targetTempTextField).addComponent(targetTempButton);
        GroupLayout.SequentialGroup vSeqGroup = layout.createSequentialGroup()
                .addComponent(label)
                .addGap(5)
                .addGroup(connectVParallelGroup)
                .addGap(5)
                .addGroup(turnVParallelGroup)
                .addGap(5)
                .addGroup(modeVParallelGroup)
                .addGap(5)
                .addGroup(fanVParallelGroup)
                .addGap(5)
                .addGroup(tempVParallelGroup)
                .addGap(10)
                .addComponent(roomLabel)
                .addComponent(idLabel)
                .addComponent(stateLabel)
                .addComponent(currentTempLabel)
                .addComponent(feeLabel)
                .addComponent(feeRateLabel);

        layout.setVerticalGroup(vSeqGroup);
        this.setContentPane(panel);
        connectListener = new ConnectListener(connect, disconnect, this, room);
        connect.addActionListener(connectListener);
        disconnect.addActionListener(connectListener);
        turnListener = new  TurnListener(turnOn, turnOff, this, room);
        turnOn.addActionListener(turnListener);
        turnOff.addActionListener(turnListener);
        fanListener = new  FanListener(fanComboBox, fanButton, this, room);
        fanButton.addActionListener(fanListener);
        fanComboBox.addActionListener(fanListener);
        modeListener = new ModeListener(modeComboBox, modeButton, this, room);
        modeButton.addActionListener(modeListener);
        modeComboBox.addActionListener(modeListener);
        tempListener = new TempListener(targetTempTextField, targetTempButton, this, room);
        targetTempTextField.addActionListener(tempListener);
        targetTempButton.addActionListener(tempListener);
    }
    public static void initGlobalFontSetting(Font fnt){
        FontUIResource fontRes = new FontUIResource(fnt);
        for(Enumeration keys = UIManager.getDefaults().keys(); keys.hasMoreElements();){
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if(value instanceof FontUIResource)
                UIManager.put(key, fontRes);
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public JLabel getRoomLabel() {
        return roomLabel;
    }

    public void setRoomLabel(JLabel roomLabel) {
        this.roomLabel = roomLabel;
    }

    public JRadioButton getConnect() {
        return connect;
    }

    public void setConnect(JRadioButton connect) {
        this.connect = connect;
    }

    public JRadioButton getDisconnect() {
        return disconnect;
    }

    public void setDisconnect(JRadioButton disconnect) {
        this.disconnect = disconnect;
    }

    public JRadioButton getTurnOn() {
        return turnOn;
    }

    public void setTurnOn(JRadioButton turnOn) {
        this.turnOn = turnOn;
    }

    public JRadioButton getTurnOff() {
        return turnOff;
    }

    public void setTurnOff(JRadioButton turnOff) {
        this.turnOff = turnOff;
    }

    public JComboBox<String> getModeComboBox() {
        return modeComboBox;
    }

    public void setModeComboBox(JComboBox<String> modeComboBox) {
        this.modeComboBox = modeComboBox;
    }

    public JButton getModeButton() {
        return modeButton;
    }

    public void setModeButton(JButton modeButton) {
        this.modeButton = modeButton;
    }

    public JFormattedTextField getTargetTempTextField() {
        return targetTempTextField;
    }

    public void setTargetTempTextField(JFormattedTextField targetTempTextField) {
        this.targetTempTextField = targetTempTextField;
    }

    public JButton getTargetTempButton() {
        return targetTempButton;
    }

    public void setTargetTempButton(JButton targetTempButton) {
        this.targetTempButton = targetTempButton;
    }

    public JComboBox<String> getFanComboBox() {
        return fanComboBox;
    }

    public void setFanComboBox(JComboBox<String> fanComboBox) {
        this.fanComboBox = fanComboBox;
    }

    public JButton getFanButton() {
        return fanButton;
    }

    public void setFanButton(JButton fanButton) {
        this.fanButton = fanButton;
    }

    public JLabel getIdLabel() {
        return idLabel;
    }

    public void setIdLabel(JLabel idLabel) {
        this.idLabel = idLabel;
    }

    public JLabel getStateLabel() {
        return stateLabel;
    }

    public void setStateLabel(JLabel stateLabel) {
        this.stateLabel = stateLabel;
    }

    public JLabel getCurrentTempLabel() {
        return currentTempLabel;
    }

    public void setCurrentTempLabel(JLabel currentTempLabel) {
        this.currentTempLabel = currentTempLabel;
    }

    public JLabel getFeeLabel() {
        return feeLabel;
    }

    public void setFeeLabel(JLabel feeLabel) {
        this.feeLabel = feeLabel;
    }

    public JLabel getFeeRateLabel() {
        return feeRateLabel;
    }

    public void setFeeRateLabel(JLabel feeRateLabel) {
        this.feeRateLabel = feeRateLabel;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}