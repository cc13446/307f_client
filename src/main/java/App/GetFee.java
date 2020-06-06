package App;

import Domain.Room;
import Enum.*;
import MyHttp.HttpRequestModel;
import net.sf.json.JSONObject;

import javax.swing.*;

public class GetFee implements Runnable{
    private Room room;
    private HttpRequestModel getFeeHttpRequestModel;

    public GetFee(Room room) {
        this.room = room;
        getFeeHttpRequestModel = new HttpRequestModel("/room/fee", "GET");
    }

    @Override
    public void run() {
        double preTemperature = room.getCurrentTemp();
        while(true){
            System.out.println("费用线程运行中");
            if(room.getState() == State.OFF || room.getState() == State.DISCONNECT){
                System.out.println("费用线程死了");
                return;
            }
            if(room.getState() == State.SERVE || room.getState() == State.ON || room.getState() == State.HOLDON || room.getState() == State.WAIT){
                System.out.println("获取费用 上传温度");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", room.getCustomId());
                jsonObject.put("currentTemperature", room.getCurrentTemp());
                jsonObject.put("changeTemperature", room.getCurrentTemp() - preTemperature);
                try {
                    jsonObject = getFeeHttpRequestModel.send(jsonObject, "?id=" + room.getCustomId() + "&currentTemperature=" + room.getCurrentTemp() + "&changeTemperature=" + (room.getCurrentTemp() - preTemperature));
                    if(jsonObject.getInt("status") == 0){
                        JSONObject data = JSONObject.fromObject(jsonObject.get("data"));
                        int state = data.getInt("roomState");
                        switch (state){
                            case 0:room.setState(State.SERVE); break;
                            case 1:room.setState(State.WAIT); break;
                            case 2:room.setState(State.HOLDON); break;
                        }
                        if(data.getInt("id") == room.getCustomId()){
                            room.setFee(data.getDouble("fee"));
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("get Fee Error");
                    return;
                }
                preTemperature = room.getCurrentTemp();
            }
            try {
                Thread.sleep(4500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
