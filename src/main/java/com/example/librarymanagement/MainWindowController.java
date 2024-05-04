package com.example.librarymanagement;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.google.gson.Gson;
import javafx.scene.control.TableView;
import java.io.*;
import java.io.InputStream;

import javafx.application.HostServices;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.*;
import java.util.List;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;


public class MainWindowController extends Application {

    @FXML
    private TableColumn<Book, String> titleCol;
    @FXML
    private TableColumn<Book, String> subtitleCol;
    @FXML
    private TableColumn<Book, String> authorsCol;
    @FXML
    private TableColumn<Book, String> translatorsCol;
    @FXML
    private TableColumn<Book, String> isbnCol;
    @FXML
    private TableColumn<Book, String> publisherCol;
    @FXML
    private TableColumn<Book, LocalDate> dateCol;
    @FXML
    private TableColumn<Book, String> editionCol;
    @FXML
    private TableColumn<Book, String> coverCol;
    @FXML
    private TableColumn<Book, String> languageCol;
    @FXML
    private TableColumn<Book, String> ratingCol;
    @FXML
    private TableView<Book> tableView;


    @FXML
    private TableColumn<Book, String> tagsCol;
    @FXML
    private TableColumn<Book, String> numberOfPagesCol;
    @FXML
    private TableColumn<Book, String> coverTypeCol;
    @FXML
    private ArrayList<Book> books = new ArrayList<>(); // Initialize the ArrayList

    @FXML
    private TableView<Book> bookTableView;
    @FXML
    private ChoiceBox<String> tagChoose;

    @FXML
    private ListView<String> translatorsListView;

    private ObservableList<String> authorsList = FXCollections.observableArrayList();
    private ObservableList<String> translatorsList = FXCollections.observableArrayList();
    private ObservableList<String> tagsList = FXCollections.observableArrayList();
    private ObservableList<Book> allBooks = FXCollections.observableArrayList();
    private ObservableList<Book> filteredBooks = FXCollections.observableArrayList();
    private Set<String> uniqueTags = new HashSet<>();
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;

    private HostServices hostServices;
    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowController.class.getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Library Management");
        scene.getStylesheets().add(getClass().getResource("/com/example/librarymanagement/styles.css").toExternalForm());
        stage.setMaximized(true);
        stage.setScene(scene);
        MainWindowController controller = fxmlLoader.getController();
        controller.initialize(books); //AddController from passing the books in MainWindow
        controller.setHostServices(getHostServices()); // Pass HostServices to the controller
        controller.loadBooksFromFile();
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

    @FXML
    public void initialize(ArrayList<Book> books) //This method, set the columns correctly and populate the TableView with data from your book list.
    {
        this.books = books;
        //bookTableView.setItems(FXCollections.observableArrayList(books));1
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        subtitleCol.setCellValueFactory(new PropertyValueFactory<>("subtitle"));
        authorsCol.setCellValueFactory(new PropertyValueFactory<>("authors"));
        translatorsCol.setCellValueFactory(new PropertyValueFactory<>("translators"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        editionCol.setCellValueFactory(new PropertyValueFactory<>("edition"));
        coverCol.setCellValueFactory(new PropertyValueFactory<>("cover"));
        languageCol.setCellValueFactory(new PropertyValueFactory<>("language"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        tagsCol.setCellValueFactory(new PropertyValueFactory<>("tags"));
        numberOfPagesCol.setCellValueFactory(new PropertyValueFactory<>("numberofPages"));
        coverTypeCol.setCellValueFactory(new PropertyValueFactory<>("coverType"));
    }

    private void loadBooksFromFile() {  // Method to load books from a JSON file.
        String folderPath = "Books";
        String jsonFilePath = folderPath + File.separator + "library.json";
        File jsonFile = new File(jsonFilePath);
        if (jsonFile.exists()) {
            importFromJson(jsonFilePath);
        } else {
            updateBookListView();
        }
    }

    @FXML
    private void search() {
        String query = searchField.getText().toLowerCase().trim();
        ObservableList<Book> filteredBooks = FXCollections.observableArrayList();

        if (query.isEmpty()) {
            filteredBooks.addAll(books); // Return all books if search query is empty
        } else {
            for (Book book : books) {
                if (book.getTitle().toLowerCase().contains(query) ||
                        book.getSubtitle().toLowerCase().contains(query) ||
                        containsIgnoreCase(book.getAuthors(), query) ||
                        containsIgnoreCase(book.getTranslators(), query) ||
                        (book.getIsbn() != null && book.getIsbn().toLowerCase().contains(query)) ||
                        (book.getPublisher() != null && book.getPublisher().toLowerCase().contains(query)) ||
                        (book.getDate() != null && book.getDate().toString().toLowerCase().contains(query)) ||
                        (book.getEdition() != null && book.getEdition().toLowerCase().contains(query)) ||
                        (book.getLanguage() != null && book.getLanguage().toLowerCase().contains(query)) ||
                        (book.getRating() != null && book.getRating().toLowerCase().contains(query)) ||
                        containsIgnoreCase(book.getTags(), query) ||
                        String.valueOf(book.getNumberofPages()).toLowerCase().contains(query) ||
                        (book.getCoverType() != null && book.getCoverType().toLowerCase().contains(query))) {
                    filteredBooks.add(book);
                }
            }
        }

        bookTableView.setItems(filteredBooks); // Update bookTableView
    }

    private boolean containsIgnoreCase(ArrayList<String> list, String query) {
        if( list == null ){
            return false;
        }
        for (String item : list) {
            if (item != null && item.toLowerCase().contains(query)) {
                return true;
            }
        }
        return false;
    }

    @FXML
    public void handleSearch(ActionEvent event) {
        // Call the search() method to update the ListView with the filtered results
        search();
    }



    @FXML
    public void refreshTableView(ActionEvent event) {
        // Clear the current items in the TableView
        bookTableView.getItems().clear();

        // Add all the books back to the TableView
        bookTableView.getItems().addAll(books);
        searchField.clear();
    }


    @FXML
    public void addButtonClick(ActionEvent event) throws IOException
    {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowController.class.getResource("add.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Add Screen");
        stage.setMaximized(true);
        stage.setScene(scene);
        AddController controller = fxmlLoader.getController();
        controller.initialize(bookTableView,books); //
        stage.showAndWait();
        exportToJson("library.json");

    }

    @FXML
    public void importFromJson(String filePath) {   // Method to read data from a JSON file
        try {
            Gson gson = new Gson();
            try (Reader reader = new FileReader(filePath)) {
                books = gson.fromJson(reader, new TypeToken<ArrayList<Book>>() {
                }.getType());  // Add data read from JSON file to the books list
                updateBookListView();
                System.out.println("Books imported from JSON .");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void importButton(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select JSON File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            importFromJson(file.getAbsolutePath());  // Read data from the file
        }
    }

    @FXML
    public void exportToJson(String path) {
        initialize(books);
        String folderPath = "Books";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String baseFileName = "library";
        String filePath = folderPath + File.separator + baseFileName + ".json";
        File file = new File(filePath);


        try (FileWriter fileWriter = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString = gson.toJson(books);
            fileWriter.write(jsonString);
            System.out.println("JSON is created");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Gson gson = new Gson();
            String jsonFormat = gson.toJson(books);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
                writer.write(jsonFormat);
            } catch (IOException e) {
                System.err.println("An error occurred while writing to the file: " + e.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private String userPath;

    @FXML
    private void exportButton(ActionEvent event) {
        Gson gson = new Gson();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser file = new FileChooser();
        file.setInitialFileName("library.json");
        FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter("File", "*.json");
        file.getExtensionFilters().add(fileExtensions);
        file.setTitle("Choose to save!");
        File f = file.showSaveDialog(stage);
        exportToJson(f.getAbsolutePath());   // Write data in JSON format to the selected file
        userPath=f.getAbsolutePath();
    }

    @FXML
    public void addBook(String title, String subtitle, ArrayList<String> authors, ArrayList<String> translators, String isbn, String publisher, String date, String edition, String cover, String language, String rating, ArrayList<String> tags, String pageNumber, String coverType) {
        Book newBook = new Book(title, subtitle, authors, translators, isbn, publisher, date, edition, cover, language, rating, tags, pageNumber, coverType);
        books.add(newBook);
    }



    @FXML
    public void editButtonClick(ActionEvent event) throws IOException {
        // Get the selected book from the TableView
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindowController.class.getResource("edit.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            stage.setTitle("Edit Screen");
            stage.setMaximized(true);
            stage.setScene(scene);
            EditController editController = fxmlLoader.getController();
            editController.initialize(selectedBook,bookTableView,books);
            stage.showAndWait();
            exportToJson("library.json");
        }
    }

    private void updateBookListView() {

        bookTableView.getItems().setAll(books);
    }















    @FXML
    public void deleteBook() {

        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Book");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this book?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                books.remove(selectedBook);
                bookTableView.getItems().remove(selectedBook);
                updateBookListView();

                // Update JSON files
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String folderPath = "Books";
                String filePath = folderPath + File.separator + "library.json";
                try (Writer writer = new FileWriter(filePath)) {
                    gson.toJson(books, writer);
                    System.out.println("Selected book is deleted from JSON file.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (userPath != null) {
                    gson = new Gson();
                    try (Writer writer = new FileWriter(userPath)) {
                        gson.toJson(books, writer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    System.out.println("userPath is not set. Cannot write to user-specified file.");
                }

            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Book Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a book to delete.");
            alert.showAndWait();
        }

    }

    @FXML
    public void openListController(MouseEvent event) throws IOException {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2) { // Check if it's a double click

            if (selectedBook != null) {
                Stage stage = new Stage(); // Create a new stage
                FXMLLoader fxmlLoader = new FXMLLoader(MainWindowController.class.getResource("list.fxml")); // Load the FXML file

                Scene scene = new Scene(fxmlLoader.load(), 320, 240); // Create the scene
                stage.setTitle("List Screen"); // Set the stage title
                stage.setMaximized(true); // Maximize the stage
                stage.setScene(scene); // Set the scene to the stage
                // Get the controller instance
                ListController listController = fxmlLoader.getController();

                // Initialize the controller with the selected book and other necessary data
                listController.initialize(selectedBook, bookTableView, books,event);


                // Show the stage
                stage.showAndWait();
            }
        }
    }
    @FXML
    public void helpButton(ActionEvent event) throws IOException {
        File userManual = new File("src/main/resources/UserManual.pdf");
        if (userManual.exists()) {
            hostServices.showDocument(userManual.toURI().toString());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("User Manual not found!");
            alert.showAndWait();
        }
    }
    public void filterButtonClick(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowController.class.getResource("tagfilter.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),300, 500);
        stage.setTitle("Filters by Tags");
        stage.setScene(scene);
        com.example.librarymanagement.FilterController controller = fxmlLoader.getController();
        controller.initialize(bookTableView, books); //
        stage.showAndWait();


    }

    /*private void showUserManual() {

        try {
            File userGuide = new File("src/resources/UserManual.pdf");
            if (userGuide.exists()) {
                Desktop.getDesktop().open(userGuide);
            } else {

    }

     */

}
