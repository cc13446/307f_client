package MyGui;

import Domain.Room;
import MyListener.InitListener;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.Enumeration;

public class InitGuiModel extends JFrame {
    private JPanel panel;
    private JLabel label;
    private JFormattedTextField roomIdTextField;
    private JButton roomIdButton;
    private JFormattedTextField currentTempTextField;
    private JButton currentTempButton;
    private JButton confirmButton;

    private InitListener initListener;

    private Room room;

    public InitGuiModel(Room room){
        super();
        this.room = room;
        this.setSize(400, 250);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        label = new JLabel("波普特酒店空调控制面板初始化");
        label.setFont(new Font("微软雅黑",Font.BOLD, 24));
        roomIdTextField = new JFormattedTextField(NumberFormat.getNumberInstance());
        roomIdTextField.setValue(room.getRoomId());
        roomIdTextField.setMaximumSize(new Dimension(200,35));
        roomIdTextField.setMinimumSize(new Dimension(200,35));
        roomIdTextField.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if(!Character.isDigit(c))e.consume();
            }
        });
        roomIdButton = new JButton("设置房间号码");
        currentTempTextField = new JFormattedTextField(NumberFormat.getNumberInstance());
        currentTempTextField.setValue(room.getCurrentTemp());
        currentTempTextField.setMaximumSize(new Dimension(200,35));
        currentTempTextField.setMinimumSize(new Dimension(200,35));
        currentTempTextField.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if(!Character.isDigit(c) && c != '.')e.consume();
            }
        });
        currentTempButton = new JButton("设置当前室温");
        confirmButton = new JButton("确认");
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
        GroupLayout.SequentialGroup roomIdHSeqGroup = layout.createSequentialGroup().addComponent(roomIdTextField).addComponent(roomIdButton);
        GroupLayout.SequentialGroup tempHSeqGroup = layout.createSequentialGroup().addComponent(currentTempTextField).addComponent(currentTempButton);

        GroupLayout.ParallelGroup hParallelGroup = layout.createParallelGroup()
                .addComponent(label, GroupLayout.Alignment.CENTER)
                .addGroup(GroupLayout.Alignment.CENTER,roomIdHSeqGroup)
                .addGroup(GroupLayout.Alignment.CENTER,tempHSeqGroup)
                .addComponent(confirmButton, GroupLayout.Alignment.CENTER);
        layout.setHorizontalGroup(hParallelGroup);

        GroupLayout.ParallelGroup roomIdVParallelGroup = layout.createParallelGroup().addComponent(roomIdTextField).addComponent(roomIdButton);
        GroupLayout.ParallelGroup tempVParallelGroup = layout.createParallelGroup().addComponent(currentTempTextField).addComponent(currentTempButton);
        GroupLayout.SequentialGroup vSeqGroup = layout.createSequentialGroup()
                .addComponent(label)
                .addGap(10)
                .addGroup(roomIdVParallelGroup)
                .addGap(5)
                .addGroup(tempVParallelGroup)
                .addGap(10)
                .addComponent(confirmButton);
        layout.setVerticalGroup(vSeqGroup);
        this.setContentPane(panel);
        initListener = new InitListener(room, this, roomIdTextField, roomIdButton, currentTempTextField, currentTempButton, confirmButton);
        roomIdTextField.addActionListener(initListener);
        roomIdButton.addActionListener(initListener);
        currentTempTextField.addActionListener(initListener);
        currentTempButton.addActionListener(initListener);
        confirmButton.addActionListener(initListener);
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
}
