package com.example.calc;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class History {

    Calculator parent;
    @FXML
    private ImageView closeButton;
    @FXML
    private Pane header;

    @FXML
    private TableView<String> table;

    Double xWindow, yWindow;


    @FXML
    public void closeHistory(Stage stage) {
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

    public void setHistory(ObservableList<String> historyList) {
        TableColumn<String, String> column = new TableColumn<>("History");
        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue()));
        column.setStyle("-fx-background-color:  bf88ef; -fx-font-size: 20");
        table.getColumns().add(column);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getSelectionModel().getSelectedCells().addListener(this::selectString);
        table.setItems(historyList);
    }

    public void setParent(Calculator parent) {
        this.parent = parent;
    }

    private void selectString(ListChangeListener.Change<? extends TablePosition> allString) {
        final String[] choose = {""};
        allString.getList().forEach(tablePosition -> choose[0] = (String) tablePosition.getTableColumn().getCellData(tablePosition.getRow()));
        if (choose[0] != null) {
            parent.setTextToCalcString(choose[0]);
        }
    }
}
