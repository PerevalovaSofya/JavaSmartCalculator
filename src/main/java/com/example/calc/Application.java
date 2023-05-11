package com.example.calc;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("calculator.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Calculator controller = fxmlLoader.getController();
        try (InputStream iconStream = getClass().getResourceAsStream("calculate_icon.png")) {
            stage.getIcons().add(new Image(Objects.requireNonNull(iconStream)));
        } catch (Exception e) {
            controller.getCalcString().setText("Image not found");
        }
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        controller.closeCalc(stage);
        controller.movingCalc(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
