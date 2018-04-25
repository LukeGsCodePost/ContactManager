//Standard javafx imports.
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

//Imports for components in this application.
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

//Layout.
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

//To support application quitting.
import javafx.application.Platform;


//File support.
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class ContactManager extends Application {
	
	//Labels to identify controls.
	Label lblNames, lblEmail, lblAddress;
	
	//Textfield to show email.
	TextField txtfEmail;
	
	//Textarea to show address.
	TextArea txtAddress;
	
	//Button to close the application.
	Button btnClose;
	
	//Listview to show list of contact names.
	ListView <String> lvNames;
	

	public ContactManager() {
		// TODO Auto-generated constructor stub
	}//constructor

	@Override
	public void init(){
		
		//Instantiate controls.
		lblNames = new Label("Names:");
		lblEmail = new Label("Email:");
		lblAddress = new Label("Address:");
		
		lvNames = new ListView <String>();
		
		txtfEmail = new TextField();
		txtAddress = new TextArea();
		
		btnClose = new Button("Close");
		
		btnClose.setOnAction(ae -> Platform.exit());
		
		lvNames.setOnMouseClicked(ae -> {
			
			//Get the selected name.
			String selectedName = lvNames.getSelectionModel().getSelectedItem();
			
			//Call the method that populates the email and address fields.
			showEmailAndAddr("./contacts.csv", selectedName);
			
				
			
		});
							
	}//init()
	
	
	
	private void showEmailAndAddr(String fileName, String selection){
		//Read in the contact names from a file.		
				//try to open a file.
				try{
					String line;
					
					FileReader fr =  new FileReader(fileName);
					BufferedReader buf = new BufferedReader(fr);
					
					while((line = buf.readLine()) != null){
						
						if(line.startsWith(selection)){
						//A 3-item array to hold the split line contents.
						String [] contactDataArray = new String[3];
						
						//Split the string into 3 on ':' char.
						contactDataArray = line.split(":");
						
						//Add the email to the email textfield
						txtfEmail.setText(contactDataArray[1]);
						
						//Add the address to the address textarea.
						txtAddress.setText(contactDataArray[2]);
						
						}//if
										
					}//while()
					//Done iterating through file lines. Close the file.
					buf.close();
								
				}//try
				catch(IOException ioe){
					ioe.toString();
					
				}//catch		
		
	}//showEmailAndAddr
	
	
	
	
	private void readContactNames(String contactsFile){
		//Read in the contact names from a file.
		
		//try to open a file.
		try{
			String line;
			
			FileReader fr =  new FileReader(contactsFile);
			BufferedReader buf = new BufferedReader(fr);
			
			while((line = buf.readLine()) != null){
				
				//A 3-item array to hold the split line contents.
				String [] contactDataArray = new String[3];
				
				//Split the string into 3 on ':' char.
				contactDataArray = line.split(":");
				
				//Add just the name to the listview.
				lvNames.getItems().add(contactDataArray[0]);
								
			}//while()
			//Done iterating through file lines. Close the file.
			buf.close();
			
			//Sort the list of names in the listview.
			lvNames.getItems().sort(null);
									
		}//try
		
		catch(IOException ioe){
			ioe.toString();
			
		}//catch
		
		
	}//readContactNames()
	
	
	
	
	
	

	
	@Override
	public void start(Stage pStage) throws Exception {
		//Set the title.
		pStage.setTitle("Contact Manager");
		
		//Set the width and height.
		pStage.setWidth(450);
		pStage.setHeight(300);
		
		//Create a layout.
		VBox vbNames = new VBox();
		vbNames.setSpacing(5);
		
		VBox vbAddresses = new VBox();
		vbAddresses.setSpacing(5);
		
		HBox hbButton = new HBox();
		hbButton.getChildren().add(btnClose);
		hbButton.setAlignment(Pos.BASELINE_RIGHT);
		
		
		
		GridPane gpMain = new GridPane();
		gpMain.setPadding(new Insets(5));
		gpMain.setHgap(10);
		gpMain.setVgap(10);
			
		
		
		//Add components to the layout.
		vbNames.getChildren().addAll(lblNames, lvNames);
		vbAddresses.getChildren().addAll(lblEmail, txtfEmail, lblAddress, txtAddress);
		gpMain.add(vbNames, 0, 0);
		gpMain.add(vbAddresses, 1, 0);
		gpMain.add(hbButton, 1, 1);
	
		
		//Create a scene.
		Scene s = new Scene(gpMain);
		
		
		//Set the scene.
		pStage.setScene(s);
		
		//Populate the listview with names.
		readContactNames("./contacts.csv");
						
		//Show the stage.
		pStage.show();		

	}//start()

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Launch the application.
		launch();
		
	}//main()

}//class