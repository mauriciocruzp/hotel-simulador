package controllers;

import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import threads.RoomService;
import threads.CreateGuests;
import monitors.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;


import java.util.Observable;
import java.util.Observer;


public class HotelController implements Observer {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button startButton;
    @FXML
    private ImageView roomService0;

    @FXML
    void startSimulation(ActionEvent event) {
        Hotel hotel = new Hotel();
        startButton.setDisable(true);
        hotel.addObserver(this);

        RoomService roomService = new RoomService(anchorPane, hotel);
        CreateGuests createGuests = new CreateGuests(anchorPane, hotel, this);

        Thread roomServiceThread = new Thread(roomService);
        Thread createGuestsThread = new Thread(createGuests);

        roomServiceThread.setDaemon(true);
        roomServiceThread.start();
        createGuestsThread.setDaemon(true);
        createGuestsThread.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        synchronized (this) {
            if (((String) arg).contains("roomService")) {
                String[] cadena = ((String) arg).split(" ");
                int x = Integer.parseInt(cadena[1]);
                int y = Integer.parseInt(cadena[2]);
                try {
                    sendRoomService(x, y);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void sendRoomService(int x, int y) throws InterruptedException {
        Boolean[] avalibleRoomService = {true, true, true, true, true, true};

        int actualX = ((int)roomService0.getLayoutX());
        int actualY = ((int)roomService0.getLayoutY());


        x = x - ((int)roomService0.getLayoutX());
        y = y - ((int)roomService0.getLayoutY());

        TranslateTransition transition0 = new TranslateTransition(Duration.seconds(0.5), roomService0);
        transition0.setToX(x);
        transition0.setToY(y);
        transition0.play();

        Thread.sleep(2000);

        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(0.5), roomService0);
        transition1.setToX(actualX-actualX);
        transition1.setToY(actualY-actualY);
        transition1.play();
    }
}
