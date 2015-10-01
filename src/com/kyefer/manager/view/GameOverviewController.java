package com.kyefer.manager.view;

import com.kyefer.manager.Main;
import com.kyefer.manager.model.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;

import javax.xml.soap.Node;


public class GameOverviewController {
    @FXML
    private TableView<Game> gameTable;
    @FXML
    private TableColumn<Game, String> nameColumn;
    @FXML
    private Label nameLabel;
    @FXML
    private GridPane genreGrid;

    private Main main;


    public GameOverviewController() {
    }

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        showGenres(null);

        gameTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showGenres(newValue));
    }

    public void setMainApp(Main main) {
        this.main = main;

        gameTable.setItems(main.getGameData());
    }

    private void showGenres(Game game) {
        genreGrid.getChildren().clear();
        if (game != null) {
            nameLabel.setText(game.getName());
            int row = 0;
            for (String genre : game.getGenres()) {
                Label genreLabel = new Label(genre);
                genreLabel.getStyleClass().add("label-grid");
                genreGrid.add(genreLabel, 0, row++);
            }
        } else {
            nameLabel.setText("");
        }
    }
}
