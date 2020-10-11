package com.exploreandshare.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //GridPane rootGridPane = FXMLLoader.load(getClass().getResource("game.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        GridPane rootGridPane = loader.load();
        controller = loader.getController();
        controller.createPlayground();
        Pane paneMenu = (Pane) rootGridPane.getChildren().get(0);                          //getChildren pane from grid pane index 0 because it is added first
        MenuBar menuBar = createMenus();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());                //to get menuBar display full on pane
        //otherwise it will only cover the area which acquired by its menus.
        paneMenu.getChildren().addAll(menuBar);


        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(rootGridPane));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    public MenuBar createMenus() {
        Menu fileMenu = new Menu("File");
        //Adding menu items to file menu
        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction(event -> controller.resetGame());
        MenuItem resetGame = new MenuItem("Reset Game");
        resetGame.setOnAction(event -> controller.resetGame());
        MenuItem exitGame = new MenuItem("Exit Game");
        exitGame.setOnAction(event -> { Platform.exit(); System.exit(0);}); //Platform will exit from platform
        //system will exit from threads created
        SeparatorMenuItem separator1 = new SeparatorMenuItem();
        fileMenu.getItems().addAll(newGame, resetGame, separator1, exitGame);

        Menu helpMenu = new Menu("Help");
        //Adding menu items to help menu
        MenuItem aboutGame = new MenuItem("About Connect4");
        //set action
        aboutGame.setOnAction(event -> aboutConnect4());
        MenuItem aboutMe = new MenuItem("About Me");
        //set action
        aboutMe.setOnAction(event -> aboutMine());
        SeparatorMenuItem separator2 = new SeparatorMenuItem();
        helpMenu.getItems().addAll(aboutGame, separator2, aboutMe); //one separator can be used multiple times

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, helpMenu);
        return menuBar;
    }

    private void resetGame() {

    }

    private void aboutMine() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About The Developer");
        alert.setHeight(400.0);
        alert.setHeaderText("Mr. Mayur G. Narkhede");
        alert.setContentText("I love to play around with code and create awesome new games. Connect4 is one of them. In free time, I like to spend time with nears and dears.");
        alert.show();
    }

    private void aboutConnect4() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Connect Four");
        alert.setHeaderText("How to Play?");
        alert.setContentText("Connect Four is a two - player connection game in which the players first choose a color and then take turns dropping colored discs from the top into a seven-column, six-row vertically suspended grid. The piece fall straight down occupying the next available space within the column. The objective of the game is to be the first to form a horizontal, vertical, or diagonal line of four of one's own discs. Connect Four is a solved game. The first player can always win by playing the right moves.");
        alert.show();
    }
}
