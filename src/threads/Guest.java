package threads;

import monitors.Hotel;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.Random;

public class Guest implements Runnable {
    private final AnchorPane anchorPane;
    private Hotel hotel;
    private static String[] roomPositions;
    private boolean roomService;

    public Guest(AnchorPane anchorPane, Hotel hotel) {
        this.anchorPane = anchorPane;
        this.hotel = hotel;
        roomPositions = new String[]{
                "114 456", "114 390", "114 324", "114 256",
                "114 187", "114 118", "114 50", "206 50",
                "297 50", "388 50", "478 50", "569 50",
                "660 50", "751 50", "751 118", "751 187",
                "751 256", "751 324", "751 390", "751 456"
        };
        this.roomService = Math.random() < 0.5;
    }

    @Override
    public void run() {
        Image guestImage = new Image(getClass().getResource("/principal/resources/assets/guest.png").toExternalForm());
        ImageView imageView = new ImageView(guestImage);

        imageView.setFitWidth(75);
        imageView.setFitHeight(55);
        Platform.runLater(() -> {
            imageView.setLayoutX(0);
            imageView.setLayoutY(600);
            anchorPane.getChildren().add(imageView);
        });

        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(1), imageView);
        transition1.setToX(224);
        transition1.play();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int roomId = hotel.enterAndGetRoom(Thread.currentThread().getName());
        String[] layout = roomPositions[roomId].split(" ");

        TranslateTransition transition2 = new TranslateTransition(Duration.seconds(0.5), imageView);
        transition2.setToX(Integer.parseInt(layout[0]));
        transition2.setToY(Integer.parseInt(layout[1]) - 600);
        transition2.play();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (roomService) {
            System.out.println("Solicitando servicio a la habitacion " + (roomId + 1));
            hotel.requestRoomService(roomId, layout);
        }

        try {
            int randomTime = new Random().nextInt(30) + 30;
            Thread.sleep(randomTime * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        hotel.leaveHotel(roomId, Thread.currentThread().getName());
        Platform.runLater(() -> {
            anchorPane.getChildren().remove(imageView);
        });
    }
}
