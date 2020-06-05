package MyListener;

import Domain.Room;
import MyGui.GuiModel;
import MyHttp.HttpRequestModel;
import net.sf.json.JSONObject;
import Enum.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectListener implements ActionListener{
    private JRadioButton connect;
    private JRadioButton disconnect;
    private GuiModel guiModel;
    private Room room;

    public ConnectListener(JRadioButton connect, JRadioButton disconnect, GuiModel guiModel, Room room) {
        this.connect = connect;
        this.disconnect = disconnect;
        this.guiModel = guiModel;
        this.room = room;
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == connect){
            if(connect.isSelected()){
                System.out.println("去连接");
            }
            connect.setSelected(true);
            disconnect.setSelected(false);
        }
        else if(e.getSource() == disconnect){
            if(disconnect.isSelected()){
                System.out.println("断连接");
            }
            connect.setSelected(false);
            disconnect.setSelected(true);
        }
    }
}
