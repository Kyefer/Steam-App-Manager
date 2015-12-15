package com.kyefer.manager.view;

import com.kyefer.manager.model.Game;
import com.kyefer.manager.model.Genre;
import com.kyefer.manager.model.SteamProfile;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

import java.util.*;


public class GenreOverviewController {
    @FXML
    private TableView<Genre> genreTable;
    @FXML
    private TableColumn<Genre, String> nameColumn;
    @FXML
    private Label nameLabel;
    @FXML
    private GridPane gameGrid;

    private SteamProfile profile;


    public GenreOverviewController() {
    }

    @FXML
    private void initialize() {
        genreTable.setPlaceholder(new Label("No genres to display"));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        genreTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showGames(newValue));

        showGames(null);
    }

    public void startLoading() {
        genreTable.setItems(FXCollections.emptyObservableList());
        ProgressIndicator progressIndicator = new ProgressIndicator(-1);
        progressIndicator.setMaxSize(30, 30);
        genreTable.setPlaceholder(progressIndicator);
    }

    public void loadProfile(SteamProfile profile) {
        this.profile = profile;
        if (profile != null) {
            showGames(null);
            profile.generateGenres();
            genreTable.setItems(FXCollections.observableArrayList(profile.getGenres()));
            genreTable.setPlaceholder(new Label("No genres to display"));
        }
    }

    private void showGames(Genre genre) {
        gameGrid.getChildren().clear();
        if (genre != null) {
            nameLabel.setText(genre.getName());
            int row = 0;
            for (Game game : genre.getGames()) {
                Label genreLabel = new Label(game.getName());
                genreLabel.getStyleClass().add("label-grid");
                gameGrid.add(genreLabel, 0, row++);
            }
        } else {
            nameLabel.setText("");
        }
    }
}