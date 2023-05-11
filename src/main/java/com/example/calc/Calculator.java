package com.example.calc;

import com.example.calc.controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Arrays;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import java.io.IOException;


public class Calculator {

    Controller controller = new Controller();
    String calcText;
    Double xWindow, yWindow;
    @FXML
    Pane header;
    @FXML
    private ImageView closeButton;
    @FXML
    private TextArea calcString;

    Preferences pref = Preferences.userRoot().node("prefexample");


    @FXML
    protected void onClicked(MouseEvent event) {
        String text = ((Button) event.getSource()).getText();
        calcString.setText(calcString.getText() + text);
    }

    public TextArea getCalcString() {
        return calcString;
    }

    @FXML
    protected void onClickedFunct(MouseEvent event) {
        String text = ((Button) event.getSource()).getText();
        calcString.setText(calcString.getText() + text + "(");
    }

    @FXML
    protected void onClickedAC(MouseEvent event) {
        calcString.setText("");
    }

    @FXML
    protected void onClickedRemoveSymbol(MouseEvent event) {
        if (!calcString.getText().isEmpty()) {
            calcString.setText(calcString.getText().substring(0, calcString.getText().length() - 1));
        }
    }

    @FXML
    protected void onClickedEqual(MouseEvent event) throws IOException {
        calcText = calcString.getText();
        pref.put(calcText, "");
        if (calcText.indexOf('x') == -1) {
            calculate(0);
        } else {
            Stage dialogStage = new Stage();
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("x-window.fxml"));
            Scene scene = new Scene(loader.load());
            dialogStage.setScene(scene);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            DialogX childrenDialog = loader.getController();
            childrenDialog.closeDialog(dialogStage);
            childrenDialog.movingCalc(dialogStage);
            dialogStage.showAndWait();
            calculate(childrenDialog.getMySpinner().getValue());
        }
    }

    @FXML
    private void calculate(double xDouble) {
        try {
            double answer = controller.calculateAnswer(controller.findPolishString(calcText), xDouble);
            calcString.setText(Double.toString(answer));
        } catch (Exception e) {
            calcString.setText(e.getMessage());
        }
    }


    @FXML
    protected void onClickedGraph(MouseEvent event) throws IOException {
        Stage graphStage = new Stage();
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("graph.fxml"));
        Scene scene = new Scene(loader.load());
        graphStage.setScene(scene);
        graphStage.initModality(Modality.APPLICATION_MODAL);
        graphStage.initStyle(StageStyle.TRANSPARENT);
        graphStage.show();
        Graph childrenGraph = loader.getController();
        childrenGraph.setController(controller);
        childrenGraph.closeGraph(graphStage);
        childrenGraph.movingCalc(graphStage);
        childrenGraph.setCalcText(calcString.getText());
    }

    @FXML
    public void closeCalc(Stage stage) {
        closeButton.setOnMouseClicked(mouseEvent -> stage.close());
    }

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

    @FXML
    public void onInfoClicked() throws IOException {
        Stage infoStage = new Stage();
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("info.fxml"));
        Scene scene = new Scene(loader.load());
        infoStage.setScene(scene);
        infoStage.initModality(Modality.APPLICATION_MODAL);
        infoStage.initStyle(StageStyle.TRANSPARENT);
        infoStage.show();
        Info childrenInfo = loader.getController();
        childrenInfo.closeInfo(infoStage);
        childrenInfo.movingCalc(infoStage);
        childrenInfo.setInfo();
    }

    @FXML
    public void onClearHistory() throws BackingStoreException {
        pref.clear();
    }

    public void setTextToCalcString(String text) {
        calcString.setText(text);
    }

    @FXML
    public void onShowHistory() throws IOException, BackingStoreException {
        ObservableList<String> historyList = FXCollections.observableArrayList();
        historyList.addAll(Arrays.asList(pref.keys()));
        Stage historyStage = new Stage();
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("history.fxml"));
        Scene scene = new Scene(loader.load());
        historyStage.setScene(scene);
        historyStage.initModality(Modality.APPLICATION_MODAL);
        historyStage.initStyle(StageStyle.TRANSPARENT);
        historyStage.show();
        History childrenHistory = loader.getController();
        childrenHistory.closeHistory(historyStage);
        childrenHistory.movingCalc(historyStage);
        childrenHistory.setHistory(historyList);
        childrenHistory.setParent(this);
    }


}