package MyListener;

import Domain.Room;
import MyHttp.HttpRequestModel;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiListener implements ActionListener {
    private JFrame frame;
    private HttpRequestModel httpRequestModel;
    private Room room;
    public GuiListener(JFrame frame, Room room){
        super();
        httpRequestModel = new HttpRequestModel();
    }
    public void actionPerformed(ActionEvent e) {
        // 利用getActionCommand获得按钮名称
        // 也可以利用getSource()来实现
        // if (e.getSource() ==button1)

        String buttonName = e.getActionCommand();
        if (buttonName.equals("开关")) {
            try {

                JSONObject json = new JSONObject();
                json.put("age", 20);
                json.put("name", "cc");
                httpRequestModel.send(json);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else if (buttonName.equals("按钮2"))
            JOptionPane.showMessageDialog(frame, "按钮2 被点击");
        else
            JOptionPane.showMessageDialog(frame, "Unknown event");
    }
}
