package com.example.librarymanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.application.Platform;

public class ListController {

    private TableView<Book> table;

    @FXML
    private Label titleLbl;
    @FXML
    private Label subtitleLbl;
    @FXML
    private Label authorsLbl;
    @FXML
    private Label translatorsLbl;
    @FXML
    private Label isbnLbl;
    @FXML
    private Label publisherLbl;
    @FXML
    private Label dateLbl;
    @FXML
    private Label editionLbl;
    @FXML
    private Label languageLbl;
    @FXML
    private Label ratingLbl;
    @FXML
    private Label tagsLbl;
    @FXML
    private Label numberOfPagesLbl;
    @FXML
    private Label coverTypeLbl;
    @FXML
    private ImageView coverImage;
    @FXML
    private ArrayList<Book> books = new ArrayList<>(); // Initialize the ArrayList

    @FXML
    public void listTableRowDoubleClicked(Book selectedBook,MouseEvent event) {
        if (event.getClickCount() == 2) {
            if (selectedBook != null) {
                Platform.runLater(() -> {
                    titleLbl.setText(selectedBook.getTitle());
                    subtitleLbl.setText(selectedBook.getSubtitle());
                    String authors = selectedBook.getAuthors() != null ? String.join(", ", selectedBook.getAuthors()) : "";
                    authorsLbl.setText(authors);
                    String translators = selectedBook.getTranslators() != null ? String.join(", ", selectedBook.getTranslators()) : "";
                    translatorsLbl.setText(translators);
                    isbnLbl.setText(selectedBook.getIsbn());
                    publisherLbl.setText(selectedBook.getPublisher());
                    dateLbl.setText(selectedBook.getDate());
                    editionLbl.setText(selectedBook.getEdition());
                    languageLbl.setText(selectedBook.getLanguage());
                    ratingLbl.setText(selectedBook.getRating());
                    String tags = selectedBook.getTags() != null ? String.join(", ", selectedBook.getTags()) : "";
                    tagsLbl.setText(tags);
                    numberOfPagesLbl.setText(selectedBook.getNumberofPages());
                    coverTypeLbl.setText(selectedBook.getCoverType());

                });
                String imageUrl = selectedBook.getCover();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    try {
                        File file = new File(imageUrl);
                        if (file.exists()) {
                            Image image = new Image(file.toURI().toString());
                            coverImage.setImage(image);
                            coverImage.setFitWidth(200);
                            coverImage.setFitHeight(300);
                        } else {
                            System.out.println("File not found: " + imageUrl);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    private void openDetailsScreen(Book selectedBook) {

    }

    private void closeCurrentWindow(MouseEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }


    @FXML
    public void initialize(Book selectedBook, TableView<Book> bookTableView, ArrayList<Book> books,MouseEvent event) {
        // Initialize the controller with the provided data
        this.table = bookTableView;
        this.books = books;
        listTableRowDoubleClicked(selectedBook,event);
    }



    public String getTitle()
    {
        String title = titleLbl.getText();
        if(title.isBlank()) return null;
        return title;
    }
    public String getSubtitle()
    {
        String subtitle = subtitleLbl.getText();
        if(subtitle.isBlank()) return null;
        return subtitle;
    }



    public String getISBN()
    {
        String isbn = isbnLbl.getText();
        if(isbn.isBlank()) return null;
        return isbn;
    }
    public String getPublisher()
    {
        String publisher =  publisherLbl.getText();
        if(publisher.isBlank()) return null;
        return publisher;
    }

    public String getEdition()
    {
        String edition= editionLbl.getText();
        if(edition.isBlank()) return null;
        return edition;
    }

    public String getLanguage()
    {
        String language = languageLbl.getText();
        if(language.isBlank()) return null;
        return language;
    }
    public String getRating()
    {
        String rating = ratingLbl.getText();
        if(rating.isBlank()) return null;
        return rating;
    }

    public String getPageNumber()
    {
        String pageNumber = numberOfPagesLbl.getText();
        if(pageNumber.isBlank()) return null;
        return pageNumber;
    }
    public String getCoverType()
    {
        String coverType = coverTypeLbl.getText();
        if(coverType.isBlank()) return null;
        return coverType;
    }


    // Dosya yolunu almak için bir String alanı
    /*private String coverImagePath;

    // ImageView'e resim eklemek için bir metot
    public void setCoverImage(String imagePath) {
        try {
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                coverImage.setImage(image);
                coverImage.setFitWidth(200); // Genişliği sabit tut
                coverImage.setFitHeight(300); // Yüksekliği sabit tut
            } else {
                // Dosya bulunamadı hatası
                System.out.println("Dosya bulunamadı: " + imagePath);
            }
        } catch (Exception e) {
            // Resim yüklenirken bir hata oluştu
            e.printStackTrace();
        }
    }

    // Dosya yolunu ayarlamak için bir setter
    public void setCoverImagePath(String coverImagePath) {
        this.coverImagePath = coverImagePath;
    }

    // ImageView'daki resmi temizlemek için bir metot
    public void clearCoverImage() {
        coverImage.setImage(null);
    }*/


}