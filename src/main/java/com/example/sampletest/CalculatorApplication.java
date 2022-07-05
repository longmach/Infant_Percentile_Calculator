package com.example.sampletest;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.DateStringConverter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class CalculatorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, ParseException {
        // Intro lines
        VBox intro = new VBox();
        intro.setPadding(new Insets(10, 10, 10, 10));
        intro.setSpacing(5);
        Text intro1 = new Text("Our growth percentile calculator reveals how your child's size compares with other boys or girls the same age.");
        Text intro2 = new Text("Just enter your child's weight, height (aka length), and head circumference, and we'll calculate a percentile");
        Text intro3 = new Text("for each. That's a number reflecting what percentage of kids is larger or smaller. Doctors watch these numbers");
        Text intro4 = new Text("over time to make sure your child is growing in a healthy way.");
        intro.getChildren().add(intro1);
        intro.getChildren().add(intro2);
        intro.getChildren().add(intro3);
        intro.getChildren().add(intro4);

        // Date of Birth line
        Label dobLabel = new Label("Date of birth *");
        TextField dobText = new TextField("dd/mm/yyyy");

        // Date of measurement line
        Label domLabel = new Label("Date of measurement *");
        TextField domText = new TextField("dd/mm/yyyy");

        // Gender line
        Label genderLabel = new Label("Gender *");
        /*String[] genderString = {"Female", "Male"};*/
        ComboBox genderBox = new ComboBox();

        genderBox.getItems().addAll(
                "Female",
                "Male"
        );
        // Set gender default as Female
        genderBox.getSelectionModel().selectFirst();

        // Weight line
        Label weightLabel = new Label("Weight (kg.)");
        TextField weightText = new TextField("0.0");

        // Length line
        Label lengthLabel = new Label("Length (cm.)");
        TextField lengthText = new TextField("0.0");

        // Head circumference line
        Label headLabel = new Label("Head Circumference (cm.)");
        TextField headText = new TextField("0.0");

        // Result
        Label age = new Label();
        Label gender =  new Label();
        Label weight = new Label();
        Label length = new Label();
        Label head = new Label();
        Label weightPercentile = new Label();
        Label lengthPercentile = new Label();
        Label headPercentile = new Label();


        // Calculate button
        Button calculateButton = new Button("Calculate");
        calculateButton.setOnAction((event)-> {
            // get age in months
            String date1 = dobText.getText();
            String date2 = domText.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
            double daysBetween = 0;
            try {
                daysBetween = ChronoUnit.DAYS.between(
                        LocalDate.parse(date1, formatter),
                        LocalDate.parse(date2, formatter));
            } catch (DateTimeParseException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong date format, must be dd/mm/yyyy.", ButtonType.OK);
                alert.show();
            }

            double monthsBetween = daysBetween / 30.42;
            monthsBetween = Math.round(monthsBetween * 100.00)/100.00;
            age.setText(String.valueOf(monthsBetween));


            // get gender input
            String genderVal = genderBox.getValue().toString();
            int genderInt = 0;
            if (genderVal.equals("Male")) {
                genderInt = 1;
            } else if (genderVal.equals("Female")) {
                genderInt = 2;
            }

            // get weight, length, head circum input if available
            double weightVal = Double.parseDouble(weightText.getText());
            double lengthVal = Double.parseDouble(lengthText.getText());
            double headVal = Double.parseDouble(headText.getText());
            gender.setText(genderVal);
            weight.setText(weightText.getText());
            length.setText(lengthText.getText());
            head.setText(headText.getText());

            Calculator calculate = new Calculator();
            double percentile;
            if (weightVal > 0) {
                percentile = calculate.returnPercentile(1, genderInt, monthsBetween, weightVal);
                weightPercentile.setText(String.valueOf(percentile));
            } else if (weightVal < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Weight must be positive", ButtonType.OK);
                alert.show();
            }
            if (lengthVal > 0) {
                percentile = calculate.returnPercentile(2, genderInt, monthsBetween, lengthVal);
                lengthPercentile.setText(String.valueOf(percentile));
                //String.format("%.2f", lengthPercentile);
            } else if (lengthVal < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Length must be positive", ButtonType.OK);
                alert.show();
            }
            if (headVal > 0) {
                percentile = calculate.returnPercentile(3, genderInt, monthsBetween, headVal);
                headPercentile.setText(String.valueOf(percentile));
                //String.format("%.2f", headPercentile);
            } else if (headVal < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Head circumference must be positive", ButtonType.OK);
                alert.show();
            }
        });



        // Form for input
        GridPane form = new GridPane();
        form.setPadding(new Insets(10, 10, 10, 10));
        form.setVgap(5);
        form.setHgap(5);

        // add input lines and button to form
        form.add(dobLabel, 0, 0);
        form.add(dobText, 1, 0);
        form.add(domLabel, 0, 1);
        form.add(domText, 1, 1);
        form.add(genderLabel, 0, 2);
        form.add(genderBox, 1, 2);
        form.add(weightLabel, 0, 3);
        form.add(weightText, 1, 3);
        form.add(lengthLabel, 0, 4);
        form.add(lengthText, 1, 4);
        form.add(headLabel, 0, 5);
        form.add(headText, 1, 5);
        form.add(calculateButton, 0, 6);

        // add buffer between input and result
        for (int i = 0; i < 6; i++) {
            form.add(new Label("    "), 2, i);
        }

        // add results' title to form
        form.add(new Label("Result"), 3, 0);
        form.add(new Label("Age (months)"), 3, 1);
        form.add(new Label("Gender"), 3, 2);
        form.add(new Label("Weight (kg.)"), 3, 3);
        form.add(new Label("Length (cm.)"), 3, 4);
        form.add(new Label("Head Circum. (cm)"), 3, 5);

        // add buffer between results' labels and input
        for (int i = 0; i < 6; i++) {
            form.add(new Label("    "), 4, i);
        }

        // add inputs
        form.add(new Label("Input"), 5, 0);
        form.add(age, 5, 1);
        form.add(gender, 5, 2);
        form.add(weight, 5, 3);
        form.add(length, 5, 4);
        form.add(head, 5, 5);

        // add buffer between input and percentile
        for (int i = 0; i < 6; i++) {
            form.add(new Label("    "), 6, i);
        }

        // add percentiles
        form.add(new Label("Percentile (%)"), 7, 0);
        form.add(weightPercentile, 7, 3);
        form.add(lengthPercentile, 7, 4);
        form.add(headPercentile, 7, 5);



        // add all lines to Vbox
        VBox group =  new VBox();
        group.setAlignment(Pos.TOP_CENTER);
        group.setPadding(new Insets(10, 10, 10, 10));
        group.getChildren().add(intro);
        group.getChildren().add(form);


        Scene scene = new Scene(group, 640, 360);
        stage.setTitle("Percentile Calculator for Infant (0-36 months)");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}