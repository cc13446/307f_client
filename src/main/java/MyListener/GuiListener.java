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
    private Room room;
    public GuiListener(JFrame frame, Room room){
        super();
        this.room = room;
        this.frame = frame;
    }
    public void actionPerformed(ActionEvent e) {
        try {
            String buttonName = e.getActionCommand();
            JSONObject json = new JSONObject();
            HttpRequestModel httpRequestModel = null;
            if (buttonName.equals("开关")) {
                if(room.getState() == State.OFF){
                    httpRequestModel = new HttpRequestModel("/room/serve", "POST");
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
                    httpRequestModel = new HttpRequestModel("/room/serve", "PUT");
                    room.setState(State.OFF);
                    json.put("scheduleType", ScheduleType.CLOSE);
                    json.put("state", room.getState());
                }

            }
            else if (buttonName.equals("温度") && room.getState() == State.ON){
                httpRequestModel = new HttpRequestModel("/room/temp", "POST");
                room.setTargetTemp(26.0);
                json.put("scheduleType", ScheduleType.CHANGE_TEMP);
                json.put("targetTemp", room.getTargetTemp());
            }
            else if (buttonName.equals("风速") && room.getState() == State.ON){
                room.setFanSpeed(FanSpeed.LOW);
                json.put("scheduleType", ScheduleType.CHANGE_FAN_SPEED);
                json.put("fanSpeed", room.getFanSpeed());
                httpRequestModel = new HttpRequestModel("/room/fan", "POST");
            }
            else if (buttonName.equals("模式") && room.getState() == State.ON){
                JOptionPane.showMessageDialog(frame,"你要相信：模式改变了" );
                httpRequestModel = new HttpRequestModel("/room/mode", "POST");
            }
            else{
                JOptionPane.showMessageDialog(frame,"先开机哦" );
                httpRequestModel = new HttpRequestModel("/room/mode", "POST");
            }
            JSONObject temp = httpRequestModel.send(json);
            if(temp != null){
                System.out.println(temp);
            }
            else{
                System.out.println("发送失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
