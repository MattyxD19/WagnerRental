import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Main extends Application implements EventHandler<ActionEvent> {

    Scene scene1, scene2, scene3, endOfReservation;
    Button search;
    Button button2;
    Button saveScene2;
    Button endReservation;
    CheckBox insurance;
    TextField name;
    TextField address;
    TextField phone;
    TextField zipCode;
    TextField driverLicense;
    TextField extraDriver;
    TextField startWeek;
    TextField endWeek;
    TextField endMilage;
    TextField remainingGas;
    ChoiceBox categorySelection;
    TableView availableCampers;

    public String totalPrice;
    public String strName, strAddress, strPhone, strZipcode, strDriver, strExtraDriver, strStartWeek, strEndWeek;

    //Button reservation;
    public Pane root2 = new Pane();
    public Pane root3 = new Pane();
    public Pane root4 = new Pane();



    @Override
    public void start(Stage primaryStage) throws Exception {
//Scene 1
        Label label1= new Label("Welcome to Autocamper Rental");
        Button button1= new Button("Start reservation");
        endReservation = new Button("End your reservation");
        endReservation.setOnAction(event -> primaryStage.setScene(endOfReservation));
        button1.setOnAction(e -> primaryStage.setScene(scene2));
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, button1, endReservation);
        scene1= new Scene(layout1, 600, 600);

//Scene 2
        Label label2= new Label("Step 1");
        saveScene2 = new Button("Save information");
        saveScene2.setLayoutX(50);
        saveScene2.setLayoutY(350);
        saveScene2.setOnAction(this);

        name = new TextField();
        name.setPromptText("Enter name");
        name.setLayoutX(50);
        name.setLayoutY(50);

        address = new TextField();
        address.setPromptText("Enter Address");
        address.setLayoutX(50);
        address.setLayoutY(100);

        phone = new TextField();
        phone.setPromptText("Enter phone number");
        phone.setLayoutX(50);
        phone.setLayoutY(150);

        zipCode = new TextField();
        zipCode.setPromptText("Enter zipcode");
        zipCode.setLayoutX(50);
        zipCode.setLayoutY(200);

        driverLicense = new TextField();
        driverLicense.setPromptText("Enter driver license number");
        driverLicense.setLayoutX(50);
        driverLicense.setLayoutY(250);

        extraDriver = new TextField();
        extraDriver.setPromptText("Enter extra driver license number");
        extraDriver.setLayoutX(50);
        extraDriver.setLayoutY(300);


        button2= new Button("Continue");
        button2.setLayoutX(400);
        button2.setLayoutY(500);

        button2.setOnAction(e -> primaryStage.setScene(scene3));

        root2.getChildren().addAll(label2, button2, name, address, phone, zipCode, driverLicense,
                extraDriver, saveScene2);
        scene2= new Scene(root2,600,600);

        //Scene 3
        Label label3= new Label("Step 2");
        Label label4= new Label("Super insurance");
        label4.setLayoutX(250);
        label4.setLayoutY(100);

        Label label5= new Label("Price so far:");
        label5.setLayoutX(500);
        label5.setLayoutY(20);

        Label priceLabel= new Label(totalPrice);
        priceLabel.setLayoutX(500);
        priceLabel.setLayoutY(50);

        startWeek = new TextField();
        startWeek.setPromptText("Enter start week");
        startWeek.setLayoutX(50);
        startWeek.setLayoutY(100);

        endWeek = new TextField();
        endWeek.setPromptText("Enter end week");
        endWeek.setLayoutX(50);
        endWeek.setLayoutY(150);

        categorySelection = new ChoiceBox();
        categorySelection.setItems(FXCollections.observableArrayList("Luxury", "Standard", "Basic"));
        categorySelection.setLayoutX(50);
        categorySelection.setLayoutY(50);

        availableCampers = new TableView();
        //availableCampers.setItems();
        availableCampers.setLayoutX(50);
        availableCampers.setLayoutY(175);

        search = new Button("Search");
        search.setLayoutX(245);
        search.setLayoutY(150);

        Button button3= new Button("Continue");
        button3.setLayoutX(400);
        button3.setLayoutY(500);

        insurance = new CheckBox();
        insurance.setLayoutX(225);
        insurance.setLayoutY(100);

        scene3 = new Scene(root3, 600, 600);
        search.setOnAction(this);
        button3.setOnAction(e -> primaryStage.setScene(endOfReservation));
        root3.getChildren().addAll(startWeek, endWeek, categorySelection, availableCampers, search,
                button3, insurance, label3, label4, label5, priceLabel);

        //Scene 4

        endMilage = new TextField();
        endMilage.setPromptText("Enter current mileage");
        endMilage.setLayoutX(50);
        endMilage.setLayoutY(10);

        remainingGas = new TextField();
        remainingGas.setPromptText("Enter amount of gas");
        remainingGas.setLayoutX(50);
        remainingGas.setLayoutY(50);


        root4.getChildren().addAll(remainingGas, endMilage);
        endOfReservation = new Scene(root4, 600, 600);


        primaryStage.setTitle("Wagner autocampers");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(ActionEvent event) {

        if(event.getSource().equals(saveScene2)){
            strName = name.getText();
            strAddress = address.getText();
            strZipcode = zipCode.getText();
            strPhone = phone.getText();
            strDriver = driverLicense.getText();
            strExtraDriver = extraDriver.getText();

            int intPhone = Integer.parseInt(strPhone);
            int intZipcode = Integer.parseInt(strZipcode);
            int intDriver = Integer.parseInt(strDriver);
            int intExtraDriver = Integer.parseInt(strExtraDriver);

            DB.insertSQL("INSERT INTO tblCustomer VALUES ('"+ strName +"','"+ strAddress +"' ,"+ intPhone +" \n" +
                    "," + intZipcode + " ,"+ intDriver+" ,"+ intExtraDriver+")");

            System.out.println(strName);
            System.out.println(strAddress);

            System.out.println(intPhone);
            System.out.println(intZipcode);
            System.out.println(intDriver);
            System.out.println(intExtraDriver);


        }

        if (event.getSource().equals(search)){

            String strStartWeek = startWeek.getText();
            String strEndWeek = endWeek.getText();
            String strCategory = (String) categorySelection.getSelectionModel().getSelectedItem();
            int intStartWeek = Integer.parseInt(strStartWeek);
            int intEndWeek = Integer.parseInt(strEndWeek);

            System.out.println(insurance.isSelected());
            System.out.println(strCategory);
            System.out.println(intStartWeek);
            System.out.println(intEndWeek);


        }

    }
}