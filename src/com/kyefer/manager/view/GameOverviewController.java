package com.kyefer.manager.view;

import com.kyefer.manager.Main;
import com.kyefer.manager.model.Game;
import com.kyefer.manager.model.Profile;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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

    private Profile profile;


    public GameOverviewController() {
    }

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        showGenres(null);

        gameTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showGenres(newValue));
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
        if (profile.getSteamID() != null) {
            profile.addAllGamesFromSteam();
            showGenres(null);
            gameTable.setItems(profile.getGames());
        }

        Service loadGames = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        return null;
                    }
                };
            }
        };
        loadGames.start();


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
