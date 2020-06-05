package App;

import Domain.Room;
import Enum.*;

public class DecreaseTemp implements Runnable{
    private Room room;

    public DecreaseTemp(Room room) {
        this.room = room;
    }

    @Override
    public void run() {
        while (true){
            System.out.println("回温线程运行中");
            if(room.getState() == State.OFF || room.getState() == State.DISCONNECT){
                System.out.println("回温线程死了");
                room.setCurrentTemp(room.getOutTemp());
                return;
            }
            if(room.getState() == State.HOLDON){
                System.out.println("回温！！！");
                int index;
                if(room.getTargetTemp() > room.getOutTemp()) index = 1;
                else index = -1;
                room.setCurrentTemp(room.getCurrentTemp() - index*0.5/40);
            }
            try {
                Thread.sleep(60000/40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
