package monitors;

import java.util.Arrays;
import java.util.Observable;

public class Hotel extends Observable {
    public int maxNumGuests;
    public boolean[] rooms;
    public int roomsAux;
    public int numGuests;

    public Hotel() {
        rooms = new boolean[20];
        Arrays.fill(rooms, false);
        maxNumGuests = 20;
        numGuests = 0;
        roomsAux = -1;
    }

    public synchronized int enterAndGetRoom(String guestId) {
        int roomId = 0;
        try {
            while (numGuests >= maxNumGuests) {
                wait();
            }
            numGuests++;
            for (int i = 0; i < 20; i++) {
                if (!rooms[i]) {
                    roomId = i;
                    roomsAux = i;
                    rooms[i] = true;
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Habitación no. " + (roomId + 1) + " asignada al " + guestId);
        return roomId;
    }

    public void requestRoomService(int roomId, String[] layout) {
        synchronized (this) {
            String x = layout[0];
            String y = layout[1];
            setChanged();
            notifyObservers("roomService " + x + " " + y);
        }
    }

    public void leaveHotel(int roomId, String guestId) {
        synchronized (this) {
            numGuests--;
            System.out.println(guestId + " ha salido del hotel.");
            rooms[roomId] = false;
            notifyAll();
        }
    }
}
