
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StudentRegistration extends Application {

    Label lID, lFirstName, lLastName, lDate, noOfStudents, EUFCalculation;
    TextField tID, tFirstName, tLastName, tNoOfStudents, tEUFCalculation;
    DatePicker date;
    Text academicInfoText;
    Label ltopicCovered, QOD, userName, password,wrongCredentialLbl;
    RadioButton theoretical, numerical;
    ToggleGroup type = new ToggleGroup();
    TextField tuserName, topicCovered;
    PasswordField pPassword;
    ChoiceBox<String> choiceBox = new ChoiceBox();
    ChoiceBox<String> qualityOfDelivery = new ChoiceBox();
    CheckBox previousLectureReview = new CheckBox("Previous Lecture Review");
    Button loginBtn, saveBtn, loadBtn, updateBtn, deleteBtn;

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    Scene scene1, scene2;
    BorderPane layout, borderPane;
    private String radioButtonLabel, message, qod;
    TableView<Student> table = new TableView<>();
    ObservableList data = FXCollections.observableArrayList();
    String subject;
    ObservableList option = FXCollections.observableArrayList();
    Image titleIcon = new Image("school.png");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("Login Window");
        window.getIcons().add(titleIcon);
        databaseConnector();

        GridPane grid = new GridPane();
        grid.setVgap(20);
        grid.setHgap(20);
        grid.setPadding(new Insets(10));

        Text loginText = new Text("Student Registration System");
        loginText.setFont(Font.font("SanSerif", 20));
        BorderPane borderPane1 = new BorderPane();
        borderPane1.getChildren().add(loginText);
        grid.add(borderPane1, 0, 0);

        userName = new Label("User Name ");
        grid.add(userName, 0, 1);
        tuserName = new TextField();
        tuserName.setPromptText("User Name");
        grid.add(tuserName, 1, 1);

        password = new Label("Password");
        grid.add(password, 0, 2);
        pPassword = new PasswordField();
        pPassword.setPromptText("password");
        grid.add(pPassword, 1, 2);
        pPassword.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                loginBtn.fire();
            }
        });
        
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Admin", "Student");
        comboBox.setValue("Admin");
        comboBox.setOnAction(e -> {
            if ("Admin".equals(comboBox.getValue())) {
                System.out.println("Admin");
            } else {
                System.out.println("Student");
            }
        });
        grid.add(comboBox, 1, 3);

        loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> {
            try {
                String query = "select * from Admin where username = ? and password=?";
                pst = conn.prepareStatement(query);
                pst.setString(1, tuserName.getText());
                pst.setString(2, pPassword.getText());
                rs = pst.executeQuery();
                if (rs.next()) {
                    System.out.println("Login Successfull");
                    tuserName.clear();
                    pPassword.clear();
                    window.setScene(scene2);
                    wrongCredentialLbl.setText("");
                } else {
                    wrongCredentialLbl.setText("Wrong Credentials Entered !!");
                    System.out.println("Login failed Check username or Password");
                    tuserName.clear();
                    pPassword.clear();
                }
                pst.close();
                rs.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
          
        });
        grid.add(loginBtn, 1, 4);
        grid.setAlignment(Pos.CENTER);
       
        wrongCredentialLbl=new Label(); //for Wrong Credential Entered
        layout = new BorderPane();
        layout.setCenter(grid);
        layout.setBottom(wrongCredentialLbl);
        scene1 = new Scene(layout, 400, 400);
        //for Scene2'
        borderPane = new BorderPane();
        Button logout = new Button("Logout");
        logout.setOnAction(e -> {
            window.setScene(scene1);
        });

        TableColumn idColumn = new TableColumn("ID");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn firstNameColumn = new TableColumn("First Name");
        firstNameColumn.setMinWidth(100);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn lastNameColumn = new TableColumn("Last Name");
        lastNameColumn.setMinWidth(100);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn nameOfSubjectColumn = new TableColumn("Subject Name");
        nameOfSubjectColumn.setMinWidth(100);
        nameOfSubjectColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfSubject"));
        //nameOfSubject,TheoreticalOrNumerical,previousLectureReview
        //   ,noOfStudents,EUFCalculation,date,QOD,username,password;
        TableColumn tonColumn = new TableColumn("Theory or Numerical");
        tonColumn.setMinWidth(100);
        tonColumn.setCellValueFactory(new PropertyValueFactory<>("TheoreticalOrNumerical"));
        TableColumn previousLectureColumn = new TableColumn("P R V");
        previousLectureColumn.setMinWidth(100);
        previousLectureColumn.setCellValueFactory(new PropertyValueFactory<>("previousLectureReview"));
        TableColumn noOfStudentsColumn = new TableColumn("No of Students ");
        noOfStudentsColumn.setMinWidth(100);
        noOfStudentsColumn.setCellValueFactory(new PropertyValueFactory<>("noOfStudents"));
        TableColumn EUFColumn = new TableColumn("EUF");
        EUFColumn.setMinWidth(50);
        EUFColumn.setCellValueFactory(new PropertyValueFactory<>("EUFCalculation"));
        TableColumn dateColumn = new TableColumn("Date");
        dateColumn.setMinWidth(100);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        TableColumn QODColumn = new TableColumn("QOD");
        QODColumn.setMinWidth(50);
        QODColumn.setCellValueFactory(new PropertyValueFactory<>("QOD"));

        table.getColumns().addAll(idColumn, firstNameColumn, lastNameColumn, nameOfSubjectColumn, tonColumn, previousLectureColumn,
                noOfStudentsColumn, dateColumn, EUFColumn, QODColumn);
        ScrollPane sp = new ScrollPane();
        sp.setContent(table);
        //sp.setPrefSize(850, 500);
        sp.setPrefViewportHeight(500);
        sp.setPrefViewportWidth(800);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setFitToHeight(true);
        table.setOnMouseClicked(e -> {
            tableMouseAction();
        });
        table.setOnKeyPressed(e -> {
            tableMouseAction();
        });
        //logout.
        borderPane.setTop(logout);
        borderPane.setPadding(new Insets(10));
        borderPane.setRight(sp);
        BorderPane.setMargin(sp, new Insets(0, 0, 90, 10));
        BorderPane.setAlignment(logout, Pos.TOP_RIGHT);
        borderPane.setCenter(createUser());
        scene2 = new Scene(borderPane, 1000, 700);

        //for Creating user
        window.setScene(scene1);
        window.show();
    }

    public void databaseConnector() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:AdminDatabase");
            System.out.println("Connection Established");
        } catch (ClassNotFoundException | SQLException exp) {
            System.out.println(exp);
        }
    }

    public VBox createUser() {
        Text createUserText = new Text("Create Student ");
        createUserText.setFont(Font.font("Sanserif", 20));
        Text studentInfoText = new Text("Student Info");
        studentInfoText.setFont(Font.font("Sanserif", 20));

        /* lID=new Label("ID");
        lFirstName=new Label("First Name");
        lLastName=new Label("Last Name");
        lDate=new Label("Date of Submission");
         */
        tID = new TextField();
        tID.setPromptText("Student ID");
        tID.setMaxWidth(250);
        tFirstName = new TextField();
        tFirstName.setPromptText("First Name");
        tFirstName.setMaxWidth(250);
        tLastName = new TextField();
        tLastName.setPromptText("Last Name");
        tLastName.setMaxWidth(250);
        date = new DatePicker();
        date.setPromptText("Date of Submission");
        date.setMaxWidth(250);

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(15);
        // grid.setPadding(new Insets(0,10,0,0));
        academicInfoText = new Text("Academic Information");
        academicInfoText.setFont(Font.font("Arial", 20));
        Label subjectName = new Label("Subject Name");
        grid.add(subjectName, 0, 0);

        choiceBox.getItems().addAll("OS", "Compiler Desing", "Java");
        choiceBox.setTooltip(new Tooltip("Select the subject"));
        choiceBox.setValue("OS");
        choiceBox.setOnAction(e -> {
            subject = choiceBox.getValue();

        });
        grid.add(choiceBox, 1, 0);

        ltopicCovered = new Label("Topic Covered");
        grid.add(ltopicCovered, 0, 1);
        topicCovered = new TextField();
        topicCovered.setPromptText("Topic here");
        grid.add(topicCovered, 1, 1);

        theoretical = new RadioButton("Theoretical");
        numerical = new RadioButton("Numerical");
        theoretical.setOnAction(e -> {
            radioButtonLabel = theoretical.getText();
        });
        numerical.setOnAction(e -> {
            radioButtonLabel = numerical.getText();
        });
        grid.add(theoretical, 0, 2);
        grid.add(numerical, 1, 2);
        type.getToggles().add(theoretical);
        type.getToggles().add(numerical);

        previousLectureReview.setOnAction(e -> {
            if (previousLectureReview.isSelected()) {
                message = "yes";
            } else {
                message = "No";
            }
        });
        grid.add(previousLectureReview, 0, 3);

        noOfStudents = new Label("No Of Students");
        grid.add(noOfStudents, 0, 4);
        tNoOfStudents = new TextField();
        grid.add(tNoOfStudents, 1, 4);

        EUFCalculation = new Label("EUF Calculation");
        grid.add(EUFCalculation, 0, 5);
        tEUFCalculation = new TextField();
        grid.add(tEUFCalculation, 1, 5);

        QOD = new Label("Quality Of Delivery");
        grid.add(QOD, 0, 6);
        qualityOfDelivery.getItems().addAll("Good", "Well", "Satisfactory");
        qualityOfDelivery.setValue("Well");
        qualityOfDelivery.setOnAction(e -> {
            qod = qualityOfDelivery.getValue();
        });
        grid.add(qualityOfDelivery, 1, 6);
        saveBtn = new Button("Save");
        saveBtn.setOnAction(e -> {
            saveAction();
            tableLoadAction();
        });
        loadBtn = new Button("Load");
        loadBtn.setOnAction(e -> {
            tableLoadAction();
        });
        updateBtn = new Button("Update");
        updateBtn.setOnAction(e -> {
            updateAction();
            tableLoadAction();
        });

        deleteBtn = new Button("Delete");
        deleteBtn.setOnAction(e -> {
            deleteAction();
            tableLoadAction();
        });
        Button clearFieldBtn =new Button("New ");
        clearFieldBtn.setOnAction(e->{
            clearFields();
        });
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(0, 0, 0, 20));
        vBox.getChildren().addAll(createUserText, studentInfoText, tID, tFirstName, tLastName, date,
                academicInfoText, grid, saveBtn, loadBtn, updateBtn, deleteBtn,clearFieldBtn);
        return vBox;
    }

    public void saveAction() {
        // int id=); 
        try {//TextField tID,tFirstName,tLastName,tNoOfStudents,tEUFCalculation;
            String query = "Insert into Admin  (StudentId,FirstName,LastName,NameOfSubject,TheoreticalOrNumerical,"
                    + "previousLectureReview,NoOfStudents,EUFCalculation,date,QualityOfDelivery,username) Values (?,?,?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(query);
            pst.setInt(1, Integer.parseInt(tID.getText()));
            pst.setString(2, tFirstName.getText());
            pst.setString(3, tLastName.getText());
            pst.setString(4, subject);
            pst.setString(5, radioButtonLabel);
            pst.setString(6, message);
            pst.setInt(7, Integer.parseInt(tNoOfStudents.getText()));
            pst.setDouble(8, Double.parseDouble(tEUFCalculation.getText()));
            pst.setString(9, date.getEditor().getText());
            pst.setString(10, qod);
            pst.execute();
            pst.close();
            rs.close();
            System.out.println("Saved");
            tableLoadAction();
            clearFields();
        } catch (SQLException | NumberFormatException ex) {
            System.err.println(ex);
        }
    }

    public void clearFields() {
        //TextField tID,tFirstName,tLastName,tNoOfStudents,tEUFCalculatio
        tID.clear();
        tFirstName.clear();
        tLastName.clear();
        tNoOfStudents.clear();
        topicCovered.clear();
        tEUFCalculation.clear();
        date.getEditor().setText(null);
        qualityOfDelivery.setValue(null);
        previousLectureReview.setSelected(false);
        theoretical.setSelected(false);
        numerical.setSelected(false);
        choiceBox.setValue(null);
    }

    public void tableLoadAction() {
        data.clear();
        try {
            String query = "select * from Admin";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {//StudentId,FirstName,LastName,TheoreticalOrNumerical,"
                //+ "previousLectureReview,NoOfStudents,EUFCalculation,date,QualityOfDelivery,usernam
                data.add(new Student(rs.getString("StudentId"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("NameOfSubject"),
                        rs.getString("TheoreticalOrNumerical"),
                        rs.getString("previousLectureReview"),
                        rs.getString("NoOfStudents"),
                        rs.getString("EUFCalculation"),
                        rs.getString("date"),
                        rs.getString("QualityOfDelivery"),
                        rs.getString("username"),
                        rs.getString("password")
                ));
                table.setItems(data);
            }
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(StudentRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void tableMouseAction() {
        try {
            clearFields();
            //StudentId,FirstName,LastName,TheoreticalOrNumerical,"
            //+ "previousLectureReview,NoOfStudents,EUFCalculation,date,QualityOfDelivery,username

            Student student = (Student) table.getSelectionModel().getSelectedItem();
            String query = "Select * from Admin where StudentId=?";
            pst = conn.prepareStatement(query);
            pst.setString(1, student.getId());
            rs = pst.executeQuery();
            while (rs.next()) {
                tID.setText(rs.getString("StudentId"));
                tFirstName.setText(rs.getString("FirstName"));
                tLastName.setText(rs.getString("LastName"));
                tNoOfStudents.setText(rs.getString("NoOfStudents"));
                tEUFCalculation.setText(rs.getString("EUFCalculation"));
                date.getEditor().setText(rs.getString("date"));
                tuserName.setText(rs.getString("username"));
                qualityOfDelivery.setValue(rs.getString("QualityOfDelivery"));
                if (null != rs.getString("TheoreticalOrNumerical")) {
                    switch (rs.getString("TheoreticalOrNumerical")) {
                        case "Theoretical":
                            theoretical.setSelected(true);
                            break;
                        case "Numerical":
                            numerical.setSelected(true);
                            break;
                        default:
                            theoretical.setSelected(false);
                            numerical.setSelected(false);
                            break;
                    }
                } else {
                    theoretical.setSelected(false);
                    numerical.setSelected(false);
                }
                if (null != rs.getString("PreviousLectureReview")) {
                    switch (rs.getString("PreviousLectureReview")) {
                        case "yes":
                            previousLectureReview.setSelected(true);
                            break;
                        case "no":
                            previousLectureReview.setSelected(false);
                            break;
                    }
                } else {
                    previousLectureReview.setSelected(false);
                }
                choiceBox.setValue(rs.getString("NameOfSubject"));
            }

            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(StudentRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateAction() {
        try {//
            //StudentId,FirstName,LastName,TheoreticalOrNumerical,"
            //+ "previousLectureReview,NoOfStudents,EUFCalculation,date,QualityOfDelivery,usernam
            String query = "update Admin set StudentId=?, FirstName=?, LastName=?,NameOfSubject=?, TheoreticalOrNumerical=?,"
                    + "previousLectureReview=?, NoOfStudents=?, EUFCalculation=?, date=?, QualityOfDelivery=?,"
                    + "username=?, password=? where StudentID= '" + tID.getText() + "'";
            pst = conn.prepareStatement(query);
            pst.setString(1, tID.getText());
            pst.setString(2, tFirstName.getText());
            pst.setString(3, tLastName.getText());
            pst.setString(4, choiceBox.getValue());
            pst.setString(5, radioButtonLabel);
            pst.setString(6, message);
            pst.setString(7, tNoOfStudents.getText());
            pst.setString(8, tEUFCalculation.getText());
            pst.setString(9, date.getEditor().getText());
            pst.setString(10, qualityOfDelivery.getValue());
            // pst.setString(9, );
            clearFields();
            pst.execute();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Updated");
            alert.setContentText("User Updated Successfully");
            alert.showAndWait();
            tableLoadAction();

        } catch (SQLException ex) {
            Logger.getLogger(StudentRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteAction() {
        String query = "Delete from Admin Where StudentId=?";
        try {
            pst = conn.prepareStatement(query);
            pst.setString(1, tID.getText());
            pst.executeUpdate();
            System.out.println("Delete Successful");
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
