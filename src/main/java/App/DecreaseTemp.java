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
            if(room.getState() == State.OFF || room.getState() == State.DISCONNECT){
                room.setCurrentTemp(room.getOutTemp());
                return;
            }
            if(room.getState() == State.HOLDON){
                room.setCurrentTemp(room.getCurrentTemp() - (double)0.5/40);
            }
            try {
                Thread.sleep(60000/40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
