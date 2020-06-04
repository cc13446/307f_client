package MyListener;

import Domain.Room;
import MyHttp.HttpRequestModel;
import net.sf.json.JSONObject;
import Enum.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiListener implements ActionListener {
    private JFrame frame;
    private HttpRequestModel httpRequestModel;
    private Room room;
    public GuiListener(JFrame frame, Room room){
        super();
        this.room = room;
        this.frame = frame;
        httpRequestModel = new HttpRequestModel();
    }
    public void actionPerformed(ActionEvent e) {
        // 利用getActionCommand获得按钮名称
        // 也可以利用getSource()来实现
        // if (e.getSource() ==button1)
        try {
            String buttonName = e.getActionCommand();
            if (buttonName.equals("开关")) {
                JSONObject json = new JSONObject();
                if(room.getState() == State.OFF){
                    room.setState(State.ON);
                    json.put("scheduleType", ScheduleType.NEW_REQUEST);
                    json.put("roomId", room.getRoomId());
                    json.put("state", room.getState());
                    json.put("mode", room.getMode());
                    json.put("currentTemp", room.getCurrentTemp());
                    json.put("targetTemp", room.getTargetTemp());
                    json.put("fanSpeed", room.getFanSpeed());
                }
                else if(room.getState() == State.ON){
                    room.setState(State.OFF);
                    json.put("scheduleType", ScheduleType.CLOSE);
                    json.put("state", room.getState());
                }
                System.out.println(httpRequestModel.send(json));
            }
            else if (buttonName.equals("温度") && room.getState() == State.ON){
                JSONObject json = new JSONObject();
                room.setTargetTemp(26.0);
                json.put("scheduleType", ScheduleType.CHANGE_TEMP);
                json.put("targetTemp", room.getTargetTemp());
                System.out.println(httpRequestModel.send(json));
            }
            else if (buttonName.equals("风速") && room.getState() == State.ON){
                JSONObject json = new JSONObject();
                room.setFanSpeed(FanSpeed.LOW);
                json.put("scheduleType", ScheduleType.CHANGE_FAN_SPEED);
                json.put("fanSpeed", room.getFanSpeed());
                System.out.println(httpRequestModel.send(json));
            }
            else if (buttonName.equals("模式") && room.getState() == State.ON){
                JOptionPane.showMessageDialog(frame,"你要相信：模式改变了" );
            }
            else{
                JOptionPane.showMessageDialog(frame,"先开机哦" );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
