package com.exploreandshare.test;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
//implements initialize and alt+enter to implement methods
public class MyController implements Initializable {

	@FXML                           //annotation to state that it belongs to FXML
	public Label welcomeLabel;     //name should be same as given in Label id in fxml layout for label
	@FXML
	public ChoiceBox<String> choiceBox;   //<?> to removed warning of "raw use of parameterized class"
	//<> here we have to put what type of items choice box will have , string or int ot object etc.
	@FXML
	public TextField inputText;
	@FXML
	public Button convertButton;

	public static final String C_to_F_Text = "Celsius to Fahrenheit";  //go down to choiceBox down to Values to know more
	public static final String F_to_C_Text = "Fahrenheit to Celsius";

	public Boolean isC_to_F_selected = true;  //to know what is selected to implement in other components.


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		choiceBox.getItems().add(C_to_F_Text);
		choiceBox.getItems().add(F_to_C_Text); //Adding items to choiceBox

		//setting default value to choiceBox
		//choiceBox.setValue("Celsius to Fahrenheit"); using same string as above so we declare public static final String type and use them
		choiceBox.setValue(C_to_F_Text);

		//now to setchoiceBox on action, here we have something new, use Change listener
		//we have get Selection mode then selected Item Property then add listener to it
		/*
		choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println(newValue);   //printing to give example
			}
		});
		*/
		//we can convert about as lambda like this: note here are three parameters given in (o,oV,nV)
		choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.equals(C_to_F_Text)) {
				isC_to_F_selected = true;    //we have specify all values explicitly else doest work here
			}
			if(newValue.equals(F_to_C_Text)) //we have specify all values explicitly else doest work here
			{
				isC_to_F_selected = false;
			}
		});
		//observable get the existing choice property,
		//oldValue get old value selected
		//newValue get new value selected


		convertButton.setOnAction(event -> convert());
	}
	//we are making app for converting temperature c to f and f to c
	private void convert() {
		//first we have to get the input from textField to covert it
		String input = inputText.getText(); //caz getText() returns anything in String only.
		float enteredTemperature = 0.0f;
		try {
			enteredTemperature = Float.parseFloat(input);  //converting string in float type.
		}catch (Exception e){
			warnUser(); // here we warn, user to enter valid temperature but the info shows value for default
			// we have to add retrun; to end the code here only.
			return;
		}
		float newConvertedTemperature = 0.0f;
		if(isC_to_F_selected)    //IF user selected C_to_F_Text then do this
		{
			newConvertedTemperature = (enteredTemperature * 9/5) + 32;
		}else{                                                              //IF user selected F_to_C_Text then do this
			newConvertedTemperature = (enteredTemperature - 32) * 5/9;
		}
		display(newConvertedTemperature);
	}

	private void warnUser() {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Error Occurred");
		alert.setHeaderText("Invalid Temperature Entered!");
		alert.setContentText("Please enter valid temperature");
		alert.show();
	}

	private void display(float newConvertedTemperature) {
		//doesn't display the unit so we add string with ternary operator
		String unit = isC_to_F_selected? "F" : "C";


		//System.out.println("The new temperature is: "+ newConvertedTemperature +" " + unit );
		//but we have to show an alert box
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Result");
		alert.setContentText("The new temperature is: "+ newConvertedTemperature +" " + unit);
		alert.show();
	}
}
