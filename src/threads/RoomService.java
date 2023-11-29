package threads;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import monitors.Hotel;
import javafx.scene.layout.AnchorPane;

public class RoomService implements Runnable {
    private Hotel hotel;
    private AnchorPane anchorPane;

    public RoomService(AnchorPane anchorPane, Hotel hotel) {
        this.hotel = hotel;
        this.anchorPane = anchorPane;
    }

    @Override
    public void run() {
        System.out.println("Servicio a cuarto disponible");
    }
}
