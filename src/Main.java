import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;



public class Main extends Application implements EventHandler<ActionEvent> {

    Scene scene1, scene2, scene3, scene4, endOfReservation;
    Button search;
    Button button2;
    Button saveScene2;
    Button endReservation;
    Button button3;
    Button confirm;
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
    TextArea availableCampers;
    Label depositDue;
    Label paymentDue;
    Label orderID;
    Label priceLabel;
    Button checkWeek;
    ChoiceBox selectCamper;

    boolean superInsurance = false;






    public String strName, strAddress, strPhone, strZipcode, strDriver, strExtraDriver, strStartWeek, strEndWeek;

    //Button reservation;
    public Pane root2 = new Pane();
    public Pane root3 = new Pane();
    public Pane root4 = new Pane();
    public Pane rootReservation = new Pane();



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

        priceLabel = new Label();
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

        availableCampers = new TextArea();
        availableCampers.setLayoutX(50);
        availableCampers.setLayoutY(200);

        /*
        TableColumn category = new TableColumn("Category");
        TableColumn brand = new TableColumn("Brand");
        TableColumn model = new TableColumn("Model");
        TableColumn currentKilometer = new TableColumn("Current kilometer");
        TableColumn description = new TableColumn("Description");


        availableCampers.getColumns().addAll(category, brand, model, currentKilometer, description);*/

        search = new Button("Search");
        search.setLayoutX(245);
        search.setLayoutY(150);

        button3= new Button("Continue");
        button3.setLayoutX(500);
        button3.setLayoutY(500);

        insurance = new CheckBox();
        insurance.setLayoutX(225);
        insurance.setLayoutY(100);

        selectCamper = new ChoiceBox();
        selectCamper.setLayoutX(450);
        selectCamper.setLayoutY(100);

        checkWeek = new Button("Check week");
        checkWeek.setLayoutX(300);
        checkWeek.setLayoutY(150);

        depositDue = new Label();
        paymentDue = new Label();
        depositDue.setText("Deposit due: " + depositDate() );
        paymentDue.setText("Payment due: " + paymentDue() );

        depositDue.setLayoutX(50);
        depositDue.setLayoutY(400);

        paymentDue.setLayoutX(50);
        paymentDue.setLayoutY(450);

        orderID = new Label();
        orderID.setText("Order ID: " );
        orderID.setLayoutX(50);
        orderID.setLayoutY(500);

        confirm = new Button("Confirm reservation");
        confirm.setLayoutX(350);
        confirm.setLayoutY(500);
        confirm.setOnAction(event -> {

            String strStartWeek = startWeek.getText();
            String strEndWeek = endWeek.getText();
            String strCategory = (String) categorySelection.getSelectionModel().getSelectedItem();
            int intStartWeek = Integer.parseInt(strStartWeek);
            int intEndWeek = Integer.parseInt(strEndWeek);
            String autoID = (String) selectCamper.getValue();
            int intAutoID = Integer.parseInt(autoID);

            DB.insertSQL("Insert into tblOccupied VALUES ("+intAutoID+", "+intStartWeek+" , "+intEndWeek+", 2019)");
        });

        checkWeek.setOnAction(event -> {

                    ArrayList campers = new ArrayList();
                    selectCamper.getItems().clear();

                    try {
                        campers = DB.storedproc(Integer.parseInt(startWeek.getText()));

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    selectCamper.getItems().addAll(campers);
                }
        );

        scene3 = new Scene(root3, 600, 600);
        search.setOnAction(this);
        button3.setOnAction(e -> primaryStage.setScene(scene1));
        root3.getChildren().addAll(startWeek, endWeek, categorySelection,
                availableCampers, search, button3, insurance, label3, label4, label5,
                priceLabel, selectCamper, checkWeek, depositDue, paymentDue, confirm);


        //End reservation scene
        endMilage = new TextField();
        endMilage.setPromptText("Enter current mileage");
        endMilage.setLayoutX(50);
        endMilage.setLayoutY(10);

        Label finalprice = new Label();
        finalprice.setLayoutX(50);
        finalprice.setLayoutY(250);


        TextField autoID = new TextField();
        autoID.setPromptText("Enter auto ID");
        autoID.setLayoutX(50);
        autoID.setLayoutY(100);

        Button update = new Button("End payment");
        update.setLayoutX(300);
        update.setLayoutY(400);

        TextField previousMil = new TextField();
        previousMil.setPromptText("Previous mileage");
        previousMil.setLayoutX(250);
        previousMil.setLayoutY(50);

        update.setOnAction(event -> {

            String getID = autoID.getText();
            int intID = Integer.parseInt(getID);

            String getMileage = endMilage.getText();
            int newMileage = Integer.parseInt(getMileage);

            String getOldMileage = previousMil.getText();
            int oldMileage = Integer.parseInt(getOldMileage);

            double priceGas = newMileage - oldMileage - 1000;
            priceGas = priceGas * 0.3;
            System.out.println(priceGas);

            /*
            DB.selectSQL("SELECT tblCurrentKilometer FROM tblAutoCampers where fldAutoID = "+autoID+"");

            do{
                String data = DB.getDisplayData();
                if (data.equals(DB.NOMOREDATA)){
                    break;
                }else{
                    System.out.print(data);
                }
            } while(true);
            */


            DB.updateSQL("UPDATE tblAutoCampers SET fldCurrentKilometer = "+ newMileage +" where fldAutoID = "+intID+" ");







        });

        remainingGas = new TextField();
        remainingGas.setPromptText("Enter amount of gas");
        remainingGas.setLayoutX(50);
        remainingGas.setLayoutY(50);

        rootReservation.getChildren().addAll(remainingGas, endMilage, autoID, update, finalprice, previousMil);
        endOfReservation = new Scene(rootReservation, 600, 600);


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

            DB.insertSQL("INSERT INTO tblCustomer VALUES ("+ intPhone +",'"+ strName +"' ,'"+ strAddress +"' \n" +
                    "," + intZipcode + "," + 0 + " ,"+ intDriver+" ,"+ intExtraDriver+")");

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


            int week = intEndWeek - intStartWeek;

            double sum;

                if (categorySelection.getSelectionModel().getSelectedItem().equals("Luxury")) {
                   sum = 5000;
                   priceLabel.setText("5000");

                    if (intStartWeek >= 25 && intStartWeek <= 35) {
                        sum = sum * 1.1;
                        String price = String.format("%.2f", sum);

                        if (insurance.isSelected()) {
                            superInsurance = true;
                            sum = sum * 1.1;
                            price = String.format("%.2f", sum);
                            priceLabel.setText(price);
                        }
                    }

                    if (intStartWeek <=24 && intEndWeek <= 24 || intStartWeek >=36 && intEndWeek >=36) {

                        String price = String.format("%.2f", sum);
                        priceLabel.setText(price);

                        if (insurance.isSelected()) {
                            superInsurance = true;
                            sum = sum * 1.1;
                            price = String.format("%.2f", sum);
                            priceLabel.setText(price);
                        }
                    }

                }

                else if (categorySelection.getSelectionModel().getSelectedItem().equals("Standard")) {
                    priceLabel.setText("4000");
                    sum = 4000;

                    if (intStartWeek >= 25 && intStartWeek <= 35) {
                        sum = sum * 1.1;
                        String price = String.format("%.2f", sum);

                        if (insurance.isSelected()) {
                            superInsurance = true;
                            sum = sum * 1.1;
                            price = String.format("%.2f", sum);
                            priceLabel.setText(price);
                        }
                    }

                    else if (intStartWeek <=24 && intEndWeek <= 24 || intStartWeek >=36 && intEndWeek >=36) {

                        String price = String.format("%.2f", sum);
                        priceLabel.setText(price);

                        if (insurance.isSelected()) {
                            superInsurance = true;
                            sum = sum * 1.1;
                            price = String.format("%.2f", sum);
                            priceLabel.setText(price);
                        }
                    }

                }
                    else if (categorySelection.getSelectionModel().getSelectedItem().equals("Basic")) {
                    priceLabel.setText("3000");
                    sum = 3000;
                    if (intStartWeek >= 25 && intStartWeek <= 35) {
                        sum = sum * 1.1;
                        String price = String.format("%.2f", sum);

                        if (insurance.isSelected()) {
                            superInsurance = true;
                            sum = sum * 1.1;
                            price = String.format("%.2f", sum);
                            priceLabel.setText(price);
                        }
                    }

                    else if (intStartWeek <=24 && intEndWeek <= 24 || intStartWeek >=36 && intEndWeek >=36) {

                        String price = String.format("%.2f", sum);
                        priceLabel.setText(price);

                        if (insurance.isSelected()) {
                            superInsurance = true;
                            sum = sum * 1.1;
                            price = String.format("%.2f", sum);
                            priceLabel.setText(price);
                        }
                    }
                }

            DB.selectSQL("SELECT * FROM tblAutoCampers");
            availableCampers.clear();
            do{
                String data = DB.getDisplayData();
                if (data.equals(DB.NOMOREDATA)){
                    break;
                }else{
                    System.out.print(data);

                    availableCampers.appendText(data);
                }
            } while(true);






            System.out.println(insurance.isSelected());
            System.out.println(strCategory);
            System.out.println(intStartWeek);


        }

        if (event.getSource().equals(button3)){

           // finalPrice.getText(priceLabel.getText());








        }

    }


    public String depositDate(){

        LocalDate date = LocalDate.now().plusDays(14);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        String formattedString = date.format(formatter);

        return formattedString;
    }

    public String paymentDue(){
        LocalDate date = LocalDate.now().plusDays(56);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        String formattedString = date.format(formatter);

        return formattedString;
    }






}