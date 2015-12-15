package com.kyefer.manager.view;

import com.kyefer.manager.model.Game;
import com.kyefer.manager.model.SteamProfile;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;


public class GameOverviewController {
    @FXML
    private TableView<Game> gameTable;
    @FXML
    private TableColumn<Game, String> nameColumn;
    @FXML
    private Label nameLabel;
    @FXML
    private GridPane genreGrid;

    @FXML
    private ScrollPane scrollPane;

    private SteamProfile profile;

    @FXML
    private void initialize() {
        gameTable.setPlaceholder(new Label("No games to display"));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        gameTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showGenres(newValue));
        showGenres(null);
    }

    public void startLoading(){
        gameTable.setItems(FXCollections.emptyObservableList());
        ProgressIndicator progressIndicator = new ProgressIndicator(-1);
        progressIndicator.setMaxSize(30,30);
        gameTable.setPlaceholder(progressIndicator);
    }

    public void loadProfile(SteamProfile profile) {
        this.profile = profile;
        if (profile != null) {
            gameTable.setItems(FXCollections.observableArrayList(profile.getGames()));
        }
        gameTable.setPlaceholder(new Label("No games to display"));
    }

    private void showGenres(Game game) {
        genreGrid.getChildren().clear();
        if (game != null) {
            nameLabel.setText(game.getName());
            int row = 0;
            for (String genre : game.getGenresNames()) {
                Label genreLabel = new Label(genre);
                genreLabel.getStyleClass().add("label-grid");
                genreGrid.add(genreLabel, 0, row++);
            }
        } else {
            nameLabel.setText("");
        }
    }
}
