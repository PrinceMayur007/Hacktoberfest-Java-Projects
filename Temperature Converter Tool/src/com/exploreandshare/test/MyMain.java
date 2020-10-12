package com.exploreandshare.test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;
//Menu and MenuBar is in java.awt also
//but we want for javafx so if ide have wrong import you will get an error.


//as in video, deleted all file in src, and created new one
//refactor "sample" with eg. "com.exploreandshare.test" as package
//extend class by Application class of javafx
//Alt+Enter to implement method then press okay
//create main func, it is optional but here created for study sake
//because we write launch() in main, but if not, the javafx creates it internally
//to run the program, s its optional
public class MyMain extends Application {

	public static void main(String[] args)
	{
		launch(args);
		System.out.println("main"); //this will print first but it not part of javafx lifecycle.
	}
	//launch(args) is by default present but for learning sake we are keeping for now.


	@Override
	public void init() throws Exception {
		System.out.println("init");
		super.init();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println("start");
		//code pasted from training
		FXMLLoader loader = new FXMLLoader(getClass().getResource("app_layout.fxml"));
		//connecting .java and .fxml
		VBox rootNode = loader.load();
		//initialing root node as our root node is pane, you can see it in fxml file

		MenuBar menuBar = createMenu();
		//rootNode.getChildren().addAll(menuBar);
		//menu bar should be on top so we add index to it.
		rootNode.getChildren().add(0, menuBar); //index starts from 0

		Scene scene = new Scene(rootNode);
		//we are making a pane in stage which contains all the components i.e. scene.
		primaryStage.setScene(scene);
		//scene is set to stage
		primaryStage.setTitle("Temperature Converter Tool");
		// primaryStage.setResizable(false); makes fix size of windows, but this not a good practise
		primaryStage.show();
		//to show the result
	}

	private MenuBar createMenu()
	{
		Menu fileMenu = new Menu("File");
		//fileMenu items
		MenuItem newMenuItem = new MenuItem("New");

		//set action on new item
		newMenuItem.setOnAction(event -> {
			System.out.println("New Menu Item Clicked");
			//newGame() or newProject()
		});
		//above code replaced with lambda

		MenuItem quitMenuItem = new MenuItem("Quit");
		//set action on quit item
		quitMenuItem.setOnAction(event -> Platform.exit()); //will get exited
		//above code replaced with expression lambda

		//adding separator to specify type in option of fileMenu
		SeparatorMenuItem separator = new SeparatorMenuItem();
		//adding to fileMenu
		fileMenu.getItems().addAll(newMenuItem, separator, quitMenuItem);

		Menu helpMenu = new Menu("Help");
		//helpMenu Items
		MenuItem aboutApp = new MenuItem("About");
		//set about on action transformed as lambda
		aboutApp.setOnAction(event -> aboutApp());
		//actual code
		/*
		aboutApp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				aboutApp();
			}
		});*/
		//adding to helpMenu
		helpMenu.getItems().addAll(aboutApp);


		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu, helpMenu);
		return menuBar;
	}

	private void aboutApp() {
		Alert alertDialog = new Alert(Alert.AlertType.INFORMATION);
		alertDialog.setTitle("My First Desktop App");
		alertDialog.setHeaderText("Learning Java FX");
		alertDialog.setContentText("I am a beginner in java but soon I will be pro and start to build awesome Java Games. ");
		//by default we get button i.e. okay. we can add custom buttons too.
		ButtonType yesButton = new ButtonType("Yes");
		ButtonType noButton = new ButtonType("No");
		//adding button to alert dialog
		alertDialog.getButtonTypes().addAll(yesButton,noButton);
		//remove okay button from dialog which is built-in
		alertDialog.getButtonTypes().removeAll(ButtonType.OK);

		//alertDialog.show(); just show the dialog
		//alertDialog.showAndWait(); //returns which key is pressed. wait for correct key. it returns class of Optional.
		//so we add optional object to it. optional released with java 8
		//so optional act as container which contains object of ButtonType
		Optional<ButtonType> clickedButton = alertDialog.showAndWait();

		if(clickedButton.isPresent() && clickedButton.get() == yesButton)
		{
			//code
			System.out.println("Yes button is clicked");
		} else{
			//code
			System.out.println("No button is clicked");
		}
	}

	@Override
	public void stop() throws Exception {
		System.out.println("stop");
		super.stop();
	}
}
//if you getting problem running project,please follow this
//1) "Build" menu -> "Rebuild Project". Sometimes Intellij doesn't rewrite the classes because they already exist, this way you ask Intellij to rewrite everything.
//
//2) "Run" menu -> "Edit configuration" -> delete the profile -> add back the profile ("Application" if it's a Java application), choose your main class from the "Main Class" dropdown menu.
//check if it runs
//3)"Build" menu -> "Rebuild Project".