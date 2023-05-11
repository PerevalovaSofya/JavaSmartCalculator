package com.example.calc;

import com.example.calc.controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DialogX implements Initializable {

    @FXML
    Button close;
    @FXML
    private Spinner<Double> mySpinner = new Spinner<>();
    @FXML
    private Pane header;

    Double xWindow, yWindow;

    @FXML
    private ImageView closeButton;

    public Spinner<Double> getMySpinner() {
        return mySpinner;
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();

    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(-1000.0, 1000.0);
        valueFactory.setValue(2.0);
        mySpinner.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        mySpinner.setValueFactory(valueFactory);
    }

    @FXML
    public void closeDialog(Stage stage) {
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
