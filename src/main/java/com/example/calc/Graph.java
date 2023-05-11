package com.example.calc;

import com.example.calc.controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Graph implements Initializable {
    private Controller controller;
    private String calcText;
    private double x, y;

    @FXML
    private ImageView closeButton;

    @FXML
    private Label status;
    @FXML
    private Spinner<Double> spinnerXStart = new Spinner<>();

    @FXML
    private Spinner<Double> spinnerXEnd = new Spinner<>();

    @FXML
    private Pane header;

    Double xWindow, yWindow;
    @FXML
    private ScatterChart<Number, Number> scatterChat;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setCalcText(String calcText) {
        this.calcText = calcText;
    }

    @FXML
    public void onBuildGraph(MouseEvent event) {
        try {
            if (!calcText.isEmpty()) {
                XYChart.Series series = new XYChart.Series<>();
                var polishString = controller.findPolishString(calcText);
                double start = spinnerXStart.getValue();
                double end = spinnerXEnd.getValue();
                double step = (end - start) / 1000;
                for (double x = start; x < end; x += step) {
                    double y = controller.calculateAnswer(polishString, x);
                    if (Double.toString(y).equals("NaN") || Double.toString(y).equals("Infinity") || Double.toString(y).equals("-Infinity"))
                        continue;
                    series.getData().add(new XYChart.Data(x, y));
                }
                scatterChat.getData().addAll(series);
                status.setText("Graph is build");
            }
        } catch (Exception e) {
            status.setText(e.getMessage());
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        SpinnerValueFactory<Double> valueFactoryXS = new SpinnerValueFactory.DoubleSpinnerValueFactory(-1000000.0, 1000000.0);
        valueFactoryXS.setValue(-10.0);
        SpinnerValueFactory<Double> valueFactoryXE = new SpinnerValueFactory.DoubleSpinnerValueFactory(-1000000.0, 1000000.0);
        valueFactoryXE.setValue(10.0);
        spinnerXStart.setValueFactory(valueFactoryXS);
        spinnerXEnd.setValueFactory(valueFactoryXE);

    }

    @FXML
    public void closeGraph(Stage stage) {
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
