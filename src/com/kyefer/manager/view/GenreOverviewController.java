package com.kyefer.manager.view;

import com.kyefer.manager.model.Game;
import com.kyefer.manager.model.Profile;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    private Profile profile;
    private ObservableList<Genre> genres;


    public GenreOverviewController() {
    }

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        showGames(null);

        genreTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showGames(newValue));
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
        genres = FXCollections.observableArrayList();
        if (profile.getSteamID() != null) {
            profile.addAllGamesFromSteam();
            showGames(null);
        }

        for (Game game : profile.getGames()) {
            for (String genreString : game.getGenres()) {
                Optional<Genre> genreOptional = genres.stream().filter(genre -> genre.getName().equals(genreString)).findFirst();
                if (genreOptional.isPresent())
                    genreOptional.get().addGame(game);
                else {
                    Genre newGenre = new Genre(genreString);
                    newGenre.addGame(game);
                    genres.add(newGenre);
                }
            }
        }
        genreTable.setItems(genres);

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

class Genre {
    private StringProperty nameProperty;
    private ArrayList<Game> games;

    public Genre(String name) {
        nameProperty = new SimpleStringProperty(name);
        games = new ArrayList<>();
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public String getName() {
        return nameProperty.get();
    }

    public List<Game> getGames() {
        Collections.sort(games, (g1, g2) -> g1.getName().compareToIgnoreCase(g2.getName()));
        return games;
    }

    public void addGame(Game newGame) {
        games.add(newGame);
    }
}
