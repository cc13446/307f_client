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
            if(room.getState() == State.OFF || room.getState() == State.DISCONNECT){
                room.setCurrentTemp(room.getOutTemp());
                return;
            }
            if(room.getState() == State.SERVE){
                if(room.getFanSpeed() == FanSpeed.LOW){
                    room.setCurrentTemp(room.getCurrentTemp() + (double)1/40);
                }
                else if(room.getFanSpeed() == FanSpeed.MEDIUM){
                    room.setCurrentTemp(room.getCurrentTemp() + (double)2/40);
                }
                else if(room.getFanSpeed() == FanSpeed.HIGH){
                    room.setCurrentTemp(room.getCurrentTemp() + (double)3/40);
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
