package App;

import Domain.Room;
import MyGui.GuiModel;
import Enum.*;

public class IncreaseTemp implements Runnable{
    private GuiModel guiModel;
    private Room room;

    public IncreaseTemp(GuiModel guiModel, Room room) {
        this.guiModel = guiModel;
        this.room = room;
    }

    @Override
    public void run() {
        while (true){
            System.out.println("服务程序运行中");
            if(room.getState() == State.OFF || room.getState() == State.DISCONNECT){
                // 空调关闭 回温线程关闭
                System.out.println("服务程序死了");
                room.setCurrentTemp(room.getOutTemp());
                guiModel.getCurrentTempLabel().setText("当前温度为: " +  String.format("%.4f", room.getCurrentTemp()) + "度");
                return;
            }
            if(room.getState() == State.SERVE){
                System.out.println("服务！！！！");
                int index = 0;
                if(room.getTargetTemp() > room.getCurrentTemp()) index = 1;
                else index = -1;
                if(room.getFanSpeed() == FanSpeed.LOW){
                    room.setCurrentTemp(room.getCurrentTemp() + (double)index*0.33/40);
                }
                else if(room.getFanSpeed() == FanSpeed.MEDIUM){
                    room.setCurrentTemp(room.getCurrentTemp() + (double)index*0.5/40);
                }
                else if(room.getFanSpeed() == FanSpeed.HIGH){
                    room.setCurrentTemp(room.getCurrentTemp() + (double)index*1/40);
                }
            }
            try {
                Thread.sleep(60000/40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
