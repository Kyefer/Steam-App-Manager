package com.kyefer.manager.view;

import com.kyefer.manager.model.Game;
import com.kyefer.manager.model.Genre;
import com.kyefer.manager.model.SteamProfile;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.*;
import java.util.stream.Collectors;


public class GenreOverviewController {
    @FXML
    private TableView<Genre> genreTable;
    @FXML
    private TableColumn<Genre, String> nameColumn;
    @FXML
    private Label nameLabel;
    @FXML
    private GridPane gameGrid;
    @FXML
    private Label minLabel;
    @FXML
    private Slider minSlider;

    private SteamProfile profile;


    public GenreOverviewController() {
    }

    @FXML
    private void initialize() {
        genreTable.setPlaceholder(new Label("No genres to display"));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        genreTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showGames(newValue));

        showGames(null);

        minSlider.valueProperty().addListener((observable, oldValue, newValue) -> {minLabel.setText(String.valueOf(newValue.intValue()));loadGenres();});
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
            minSlider.setMax(profile.getGenres().size());
            minSlider.setMajorTickUnit(profile.getGenres().size()/4);
            minSlider.setMinorTickCount(profile.getGenres().size()/16);
            minSlider.setBlockIncrement(1);

            showGames(null);
            profile.generateGenres();
            loadGenres();
        }
    }

    private void loadGenres() {
        List<Genre> validGenres = new ArrayList<>();
        List<Game> potentialNoGenreGames = new ArrayList<>();
        List<Game> hasAGenre = new ArrayList<>();

        for (Genre genre : profile.getGenres()) {
            if (genre.getGames().size() >= Integer.parseInt(minLabel.getText())) {
                validGenres.add(genre);
                genre.getGames().forEach(hasAGenre::add);
            } else {
                genre.getGames().forEach(potentialNoGenreGames::add);
            }
        }

        Genre noGenre = new Genre("NO GENRE");
        potentialNoGenreGames.stream().filter(game -> !hasAGenre.contains(game)).forEach(noGenre::addGame);

        if (noGenre.getGames().size() > 0) {
            validGenres.add(noGenre);
        }

        genreTable.setItems(FXCollections.observableArrayList(validGenres));
        genreTable.setPlaceholder(new Label("No genres to display"));

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

    @FXML
    public void onMinSliderDropped() {
        System.out.println("Dropped");
        loadGenres();
    }
}