package threads;

import monitors.Hotel;
import controllers.HotelController;
import javafx.scene.layout.AnchorPane;

import java.util.concurrent.ThreadLocalRandom;

public class CreateGuests implements Runnable {
    private AnchorPane anchor;
    private Hotel hotel;
    private HotelController hotelController;
    private Guest guest;

    public CreateGuests(AnchorPane anchor, Hotel hotel, HotelController hotelController) {
        this.anchor = anchor;
        this.hotel = hotel;
        this.hotelController = hotelController;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            guest = new Guest(anchor, hotel);
            Thread guestThread = new Thread(guest);
            guestThread.setName("Guest " + (i + 1));

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(4000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            guestThread.setDaemon(true);
            guestThread.start();
        }
    }
}
