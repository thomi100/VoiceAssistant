package net.thomi100.Desktop;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
 * Created by thomi100 on 02.02.2018.
 */
public class newApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {

            Text text = new Text();
            text.setFont(new Font(45));
            text.setX(50);
            text.setY(150);
            text.setFill(Color.WHITE);
            text.setText("Willkommen");

            Line line = new Line();
            line.setStartX(50);
            line.setEndX(275);
            line.setStartY(165);
            line.setEndY(165);
            line.setStroke(Color.WHITE);

            Button knopf = new Button();
            knopf.setText("Klick mich");
            knopf.setTextFill(Color.WHITE);
            knopf.setLayoutX(100);
            knopf.setLayoutY(100);

            Rectangle rect = new Rectangle();
            rect.setHeight(40);
            rect.setWidth(100);
            rect.setX(50);
            rect.setY(190);
            rect.setFill(Color.BLUE);
            rect.setStroke(Color.WHITE);

            rect.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {



                }
            });
            rect.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    rect.setFill(Color.LIGHTBLUE);
                }
            });
            rect.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    rect.setFill(Color.BLUE);
                }
            });

            Text button = new Text();
            button.setFont(new Font(20));
            button.setX(rect.getX() + 5);
            button.setY(rect.getY() + 5);
            button.setFill(Color.WHITE);
            button.setText("browse");

            Group two = new Group();
            ObservableList list = two.getChildren();
            list.add(text);
            list.add(line);
            list.add(button);
            list.add(rect);
            list.add(knopf);

            Scene scene = new Scene(two, 600, 300);
            stage.setTitle("Sample application");
            scene.setFill(Color.DARKBLUE);

            scene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.F11) {
                    stage.setFullScreen(!stage.isFullScreen());
                }
                if (e.getCode() == KeyCode.F4) {
                    System.exit(0);
                }
            });

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setFullScreenExitHint("");
            stage.setFullScreen(true);

            stage.show();
        } catch(Exception e) {

        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
