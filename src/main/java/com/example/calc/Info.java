package com.example.calc;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Info {
    @FXML
    private ImageView closeButton;
    @FXML
    private Pane pane;
    @FXML
    private Pane header;

    Double xWindow, yWindow;

    @FXML
    public void setInfo() {
        Label label = new Label("""
                The project implements a calculator 
                with the ability to calculate arithmetic 
                operations and mathematical functions.
                The calculation is performed using the 
                reverse Polish notation.
                You can enter numbers and operators by 
                pressing buttons and using the keyboard.
                If the arithmetic expression is composed 
                incorrectly, an error will be output.
                If "x" is used in the assignment, a window 
                will appear for substituting the value.
                By clicking on the ⇲ button, you can plot 
                the desired expression.
                Successful calculations!""");
        label.setFont(new Font("Arial", 23));
        pane.getChildren().add(label);

    }

    @FXML
    public void closeInfo(Stage stage) {
        closeButton.setOnMouseClicked(mouseEvent -> stage.close());
    }
    @FXML
    public void movingCalc(Stage stage) {
        header.setOnMousePressed(mouseEvent -> {
            xWindow = mouseEvent.getSceneX();
            yWindow = mouseEvent.getSceneY();
        });
        header.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - xWindow);
            stage.setY(mouseEvent.getScreenY() - yWindow);
        });
    }
}
