package org.openjfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.openjfx.App;
import org.openjfx.management.APIRequest;
import org.openjfx.model.Genre;
import org.openjfx.model.Movie;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DashboardController{
    private int count;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label recommendedMoviesLabel;

    @FXML
    private Label topRatedLabel;

    @FXML
    private Label wishListLabel;

    @FXML
    private Label ratedMoviesLabel;

    @FXML
    private Label searchLabel;

    @FXML
    private Label profileSettingLabel;

    @FXML
    private Label headingLabel;

    @FXML
    private Label logOutLabel;

    @FXML
    private ImageView imageView1;

    @FXML
    private Label movieLabel1;

    @FXML
    private Label genreLabel1;

    @FXML
    private Label voteLabel1;

    @FXML
    private Button moreButton1;

    @FXML
    private ImageView imageView2;

    @FXML
    private Label movieLabel2;

    @FXML
    private Label genreLabel2;

    @FXML
    private Label voteLabel2;

    @FXML
    private Button moreButton2;

    @FXML
    private ImageView imageView3;

    @FXML
    private Label movieLabel3;

    @FXML
    private Label genreLabel3;

    @FXML
    private Label voteLabel3;

    @FXML
    private Button moreButton3;

    @FXML
    private ImageView imageView4;

    @FXML
    private Label movieLabel4;

    @FXML
    private Label genreLabel4;

    @FXML
    private Label voteLabel4;

    @FXML
    private Button moreButton4;

    @FXML
    private Button nextButton;

    @FXML
    private Button previousButton;

    @FXML
    private VBox cardsVbox = new VBox();

    @FXML
    public void initialize() throws IOException {
        this.count = 0;
        App.indexOfChosenMovie = 0;
        this.usernameLabel.setText(App.username);
        DatabaseController dbController = new DatabaseController();
        APIRequest apiRequest = new APIRequest();

        // TODO
        // check if user has any rated movies ?
        if (dbController.checkIfUserHasRatedAnyMovie()) {
            String movieId = dbController.getRatedMovieIdFromTableUSERS_MOVIE();
            App.recommendedMovies = apiRequest.getRecommendedMoviesByMovieId(movieId);
        }

        // if user has not rated any movies and from initialization layout
        if (App.recommendedMovies == null) {

            if (!dbController.getChosenGenreIDFromTableUSERS_GENRE(App.username).isEmpty()) {
                App.recommendedMovies = apiRequest.getMoviesFromGenres();
            }
        }

        this.setMovieBriefDetailsOnDashBoard();
    }

    @FXML
    void logOutOnMouseClicked(MouseEvent event) throws IOException{
        App.resetGlobalVariables();
        App.setRoot("login");
    }

    @FXML
    void profileSettingOnMouseClicked(MouseEvent event) throws IOException{
        App.setRoot("profile");
    }

    @FXML
    void ratedMoviesOnMouseClicked(MouseEvent event) {
        DatabaseController dbController = new DatabaseController();
        APIRequest apiRequest = new APIRequest();
        this.count = 0;
        App.indexOfChosenMovie = 0;

        this.setMovieBriefDetailsOnDashBoard();
    }

    @FXML
    void recommendedMoviesOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void searchOnMouseClicked(MouseEvent event) throws IOException{
        App.setRoot("search");
    }

    @FXML
    void topRatedOnMouseClicked(MouseEvent event) {
        this.count = 0;
        App.indexOfChosenMovie = 0;
        APIRequest apiRequest = new APIRequest();
        App.recommendedMovies = apiRequest.getTopRatedMovies();
        this.setMovieBriefDetailsOnDashBoard();
    }

    @FXML
    void usernameOnMouseClicked(MouseEvent event) throws IOException{
        App.setRoot("profile");
    }

    @FXML
    void wishListOnMouseClicked(MouseEvent event) {
        DatabaseController dbController = new DatabaseController();
        APIRequest apiRequest = new APIRequest();
        this.count = 0;
        App.indexOfChosenMovie = 0;

        List<String> movieIds = dbController.getMovieIDFromTableUSERS_WISHLISTS();
        System.out.println(movieIds);
        App.recommendedMovies = new ArrayList<>();
        System.out.println(apiRequest.getMovieByMovieId("5763"));

    }

    @FXML
    void moreButton1OnAction(ActionEvent event) throws IOException{
        App.indexOfChosenMovie += this.count;
        App.setRoot("movie-profile");
    }

    @FXML
    void moreButton2OnAction(ActionEvent event) throws IOException{
        App.indexOfChosenMovie += this.count + 1;
        App.setRoot("movie-profile");
    }

    @FXML
    void moreButton3OnAction(ActionEvent event) throws IOException{
        App.indexOfChosenMovie += this.count + 2;
        App.setRoot("movie-profile");
    }

    @FXML
    void moreButton4OnAction(ActionEvent event) throws IOException{
        App.indexOfChosenMovie += this.count + 3;
        App.setRoot("movie-profile");
    }

    @FXML
    void nextButtonOnAction(ActionEvent event) {
        if (count + 4 < App.recommendedMovies.size()) {
            this.count += 4;
            this.setMovieBriefDetailsOnDashBoard();
        }
    }

    @FXML
    void previousButtonOnAction(ActionEvent event) {
        if (this.count - 4 >= 0) {
            this.count -= 4;
            this.setMovieBriefDetailsOnDashBoard();
        }
    }

    private void setMovieBriefDetailsOnDashBoard() {
        List<Genre> genres = new APIRequest().getGenreAndId();
        this.movieLabel1.setText(App.recommendedMovies.get(count).getTitle());
        String genres1 = "";
        int i1 = 0;
        for (Integer each: App.recommendedMovies.get(count).getGenres()) {
            if(i1++ == App.recommendedMovies.get(count).getGenres().size() - 1){
                genres1 += getGenreNameFromId(genres, each);
            }else{
                genres1 += getGenreNameFromId(genres, each) + " / ";
            }

        }
        this.genreLabel1.setText(genres1);
        this.voteLabel1.setText(App.recommendedMovies.get(count).getVote_average());
        Image image1 = new Image(App.recommendedMovies.get(count).getPoster_path());
        this.imageView1.setImage(image1);

        this.movieLabel2.setText(App.recommendedMovies.get(count + 1).getTitle());
        String genres2 = "";
        int i2 = 0;
        for (Integer each: App.recommendedMovies.get(count + 1).getGenres()) {
            if(i2++ == App.recommendedMovies.get(count + 1).getGenres().size() - 1){
                genres2 += getGenreNameFromId(genres, each);
            }else{
                genres2 += getGenreNameFromId(genres, each) + " / ";
            }
        }
        this.genreLabel2.setText(genres2);
        this.voteLabel2.setText(App.recommendedMovies.get(count + 1).getVote_average());
        Image image2 = new Image(App.recommendedMovies.get(count + 1).getPoster_path());
        this.imageView2.setImage(image2);

        this.movieLabel3.setText(App.recommendedMovies.get(count + 2).getTitle());
        String genres3 = "";
        int i3 = 0;
        for (Integer each: App.recommendedMovies.get(count + 2).getGenres()) {
            if(i3++ == App.recommendedMovies.get(count + 2).getGenres().size() - 1){
                genres3 += getGenreNameFromId(genres, each);
            }else{
                genres3 += getGenreNameFromId(genres, each) + " / ";
            }

        }
        this.genreLabel3.setText(genres3);
        this.voteLabel3.setText(App.recommendedMovies.get(count + 2).getVote_average());
        Image image3 = new Image(App.recommendedMovies.get(count + 2).getPoster_path());
        this.imageView3.setImage(image3);

        this.movieLabel4.setText(App.recommendedMovies.get(count + 3).getTitle());
        String genres4 = "";
        int i4 = 0;
        for (Integer each: App.recommendedMovies.get(count + 3).getGenres()) {
            if(i4++ == App.recommendedMovies.get(count + 3).getGenres().size() - 1){
                genres4 += getGenreNameFromId(genres, each);
            }else{
                genres4 += getGenreNameFromId(genres, each) + " / ";
            }

        }
        this.genreLabel4.setText(genres4);
        this.voteLabel4.setText(App.recommendedMovies.get(count + 3).getVote_average());
        Image image4 = new Image(App.recommendedMovies.get(count + 3).getPoster_path());
        this.imageView4.setImage(image4);
    }

    private String getGenreNameFromId(List<Genre> list, Integer genreId) {
        for (Genre each: list) {
            if (each.getId().equals(String.valueOf(genreId))) {
                return each.getName();
            }
        }
        return null;
    }
}
