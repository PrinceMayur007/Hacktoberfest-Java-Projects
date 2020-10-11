package com.exploreandshare.example;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Controller implements Initializable {
	@FXML
	public GridPane rootGridPane;

	@FXML
	public Pane insertedDiscPane;

	@FXML
	public Label playerTurnLabel;

	@FXML
	public TextField playerOneTextField, playerTwoTextField;

	@FXML
	public Button setNamesButton;

	private boolean isAllowedToInsert = true;

	public static final int COLUMNS = 7;
	public static final int ROWS = 6;
	public static final int CIRCLE_DIAMETER = 80; //IN PIXEL

	public static final String discColor1 = "#24303E";
	public static final String discColor2 = "#4CAA88";

	public static String PLAYER_ONE = "Player One";
	public static String PLAYER_TWO = "Player Two";

	private boolean isPlayerOneTurn = true;

	private Disc[][] insertedDiscArray = new Disc[ROWS][COLUMNS]; //For Structural Changes / For the Developers

	public void createPlayground()
	{
		Shape rectangleWithHoles = createGameStructuralGrid();
		rootGridPane.add(rectangleWithHoles, 0, 1);

		List<Rectangle> rectangleList = createClickableColumns();
		for(Rectangle rectangle:rectangleList)
		{
			rootGridPane.add(rectangle, 0, 1);
		}
		setNamesButton.setOnAction(event -> {
			PLAYER_ONE = playerOneTextField.getText();
			PLAYER_TWO = playerTwoTextField.getText();
			playerTurnLabel.setText(isPlayerOneTurn? PLAYER_ONE : PLAYER_TWO);
		});
	}

	public Shape createGameStructuralGrid()
	{
		Shape rectangleWithHoles = new Rectangle((COLUMNS+1) * CIRCLE_DIAMETER, (ROWS+1) * CIRCLE_DIAMETER);
		//+1 for getting extra row and column which can be used for padding for inner circles. column top, row front.
		for(int row=0;row<ROWS;row++)
		{
			for(int columns=0;columns<COLUMNS;columns++)
			{
				Circle circle = new Circle();
				circle.setRadius(CIRCLE_DIAMETER/2);
				circle.setCenterX(CIRCLE_DIAMETER/2); //setting center point of circle x
				circle.setCenterX(CIRCLE_DIAMETER/2);//setting center point of circle y
				circle.setSmooth(true);

				circle.setTranslateX(columns * (CIRCLE_DIAMETER+5) + (CIRCLE_DIAMETER/4));   //position of each circle
				circle.setTranslateY(row * (CIRCLE_DIAMETER+5) + (65));    //CD/4 for padding for each circle

				rectangleWithHoles = Shape.subtract(rectangleWithHoles, circle);

			}
		}
		rectangleWithHoles.setFill(Color.WHITE);
		return rectangleWithHoles;
	}

	public List<Rectangle> createClickableColumns()    //Hovering rectangles on circle columns each
	{
		List<Rectangle> rectangleList = new ArrayList<Rectangle>();
		for(int columns=0;columns<COLUMNS;columns++)
		{
			Rectangle rectangle = new Rectangle(CIRCLE_DIAMETER, (ROWS+1) * CIRCLE_DIAMETER);
			rectangle.setFill(Color.TRANSPARENT);
			rectangle.setTranslateX(columns * (CIRCLE_DIAMETER+5) + (CIRCLE_DIAMETER/4));
			/*
			rectangle.setOnMouseClicked(event -> rectangle.setFill(Color.RED)); //when we hover over rectangle it will become red then revert back to blue with next event.
			rectangle.setOnMouseExited(event -> rectangle.setFill(Color.BLUE));
			*/
			rectangle.setOnMouseEntered(event -> rectangle.setFill(Color.valueOf("#eeeeee2e"))); //using above we created hover effect.
			rectangle.setOnMouseExited(event -> rectangle.setFill(Color.TRANSPARENT));

			final int col = columns;
			rectangle.setOnMouseClicked(event -> {   // when clicked on rectangle; insert disc
				if(isAllowedToInsert) {
					isAllowedToInsert = false;  //When disc is being dropped no more disc will be inserted.
					insertDisc(new Disc(isPlayerOneTurn), col);
				}
			});

			rectangleList.add(rectangle);
		}
		return rectangleList;
	}

	private void insertDisc(Disc disc, int column){

		int row = ROWS - 1;
		while(row>=0)
		{
			if(getDiscIfPresent(row,column) == null)  //checking emptiness of Array
			break;   //if row filled  break

			row--;
		}
		if(row< 0)     //if rows are filled
			return;    //do nothing

		insertedDiscArray[row][column] = disc;      //For StructuralChanges: For Developers
		insertedDiscPane.getChildren().add(disc);  //visually adding disc in Pane.

		disc.setTranslateX(column * (CIRCLE_DIAMETER+5) + (CIRCLE_DIAMETER/4));   //getting disc placed in respective columns
		//now we have to get the disc to bottom.where there is space for it.
		//animate fall from top to bottom so we increase duration to transition

		int currentRow = row;
		TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), disc);
		//disc.setTranslateY(5 * (CIRCLE_DIAMETER+6) + (CIRCLE_DIAMETER/4));      //copied from above y transform but after fixed positions by experimenting
		//replacing above line for translate transition
		translateTransition.setToY(row * (CIRCLE_DIAMETER+6) + (CIRCLE_DIAMETER/4));
		//we have to toggle between players i.e. colors
		translateTransition.setOnFinished(event -> {

			isAllowedToInsert = true;   //Finally, when disc is dropped allow next player to insert a disc.
			if(gameEnded(currentRow, column))
			{
				gameOver();
				return; //end do nothing after this.
			}

			isPlayerOneTurn = !isPlayerOneTurn;  //now is turn of player two
			playerTurnLabel.setText(isPlayerOneTurn? PLAYER_ONE : PLAYER_TWO); // changing names in Label field for turns
		});  //when animation finishes to toggle between two players color


		translateTransition.play();
		//now when we click bottom row get filled with disc but after that animation runs and disc goes to bottom, it does fill top where circle is empty.
		//for this we will make a loop of rows filled.
	}

	private boolean gameEnded(int row, int column)
	{
		// Vertical Points. A small example: Player has inserted his last disc at row = 2, column = 3
		// range of row values = 0,1,2,3,4,5
		//index of each present in column [row][column] : 0,3  1,3  2,3  4,3  5,3 -> Point2D class in java in terms of x,y
		List<Point2D> verticalPoints = IntStream.rangeClosed(row - 3, row + 3)  // If, row = 3, column = 3, then row = 0,1,2,3,4,5,6
				.mapToObj(r -> new Point2D(r, column))  // 0,3  1,3  2,3  3,3  4,3  5,3  6,3 [ Just an example for better understanding ]
				.collect(Collectors.toList());

		List<Point2D> horizontalPoints = IntStream.rangeClosed(column - 3, column + 3)
				.mapToObj(col -> new Point2D(row, col))
				.collect(Collectors.toList());

		Point2D startPoint1 = new Point2D(row - 3, column + 3);
		List<Point2D> diagonal1Points = IntStream.rangeClosed(0, 6)
				.mapToObj(i -> startPoint1.add(i, -i))
				.collect(Collectors.toList());

		Point2D startPoint2 = new Point2D(row - 3, column - 3);
		List<Point2D> diagonal2Points = IntStream.rangeClosed(0, 6)
				.mapToObj(i -> startPoint2.add(i, i))
				.collect(Collectors.toList());

		boolean isEnded = checkCombinations(verticalPoints) || checkCombinations(horizontalPoints)
				|| checkCombinations(diagonal1Points) || checkCombinations(diagonal2Points);

		return isEnded;
	}

	private boolean checkCombinations(List<Point2D> points) {
		int chain =0; //need to create chain of 4

		for(Point2D point : points)
		{


			int rowIndexForArray = (int) point.getX();
			int colIndexForArray = (int) point.getY();

			//instead of this Disc disc = insertedDiscArray[rowIndexForArray][colIndexForArray]; //we cann get exception of array out of bound here
			// we have to check if the numbers actually having disc or no t so create bottom method next to this.
			Disc disc = getDiscIfPresent(rowIndexForArray,colIndexForArray);

			if(disc != null && disc.isPlayerOneMove == isPlayerOneTurn){
				chain++;
				if(chain ==4)
				{
					return true;
				}
			}else{
				chain = 0;
			}
		}
		return false;
	}






	public Disc getDiscIfPresent(int row, int column)  //check if Array is out of Bound
	{
		if(row>= ROWS || row<0 || column >= COLUMNS || column< 0)
		{
			return null;
		}
		return insertedDiscArray[row][column];
	}

	private void gameOver()
	{
		String winner = isPlayerOneTurn? PLAYER_ONE : PLAYER_TWO; //if winner is that who inserted last disc
		System.out.println("The winner is : "+ winner);

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Connect Four");
		alert.setHeaderText("Winner");
		alert.setContentText("The winner is : "+ winner);

		ButtonType yesBtn = new ButtonType("Yes");
		ButtonType noBtn = new ButtonType("No, Exit");
		alert.getButtonTypes().addAll(yesBtn, noBtn);
		alert.getButtonTypes().removeAll(ButtonType.OK);

		Platform.runLater(() ->{   // only run if animation has ended required to mitigate the error.
			Optional<ButtonType> btnclicked = alert.showAndWait();
			if(btnclicked.isPresent() && btnclicked.get() == (yesBtn))
			{
				resetGame();
			}else {
				Platform.exit();
				System.exit(0);
			}
		});
	}

	public void resetGame() {
		insertedDiscPane.getChildren().clear(); // Remove all the inserted disc from pane. visually

		for(int row = 0; row < insertedDiscArray.length; row++) // Structurally, make all the elements in insertedDiscArray back to null
		{
			for(int col = 0; col < insertedDiscArray[row].length; col++)
			{
				insertedDiscArray[row][col] = null;
			}
		}

		isPlayerOneTurn = true; //make sure it starts from Player one

		playerTurnLabel.setText(PLAYER_ONE);

		createPlayground(); // create a fresh Playground
	}


	private static class Disc extends Circle
	{
		private final boolean isPlayerOneMove;

		public Disc(boolean isPlayerOneMove)
		{
			this.isPlayerOneMove = isPlayerOneMove;
			setRadius(CIRCLE_DIAMETER/2);
			setCenterX(CIRCLE_DIAMETER/2);
			setCenterY(CIRCLE_DIAMETER/2);
			setFill(isPlayerOneMove? Color.valueOf(discColor1) : Color.valueOf(discColor2));
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {


	}
}
