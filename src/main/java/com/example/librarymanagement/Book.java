package com.example.librarymanagement;
import java.util.ArrayList;
public class Book {
    private  String title;
    private String subtitle;
    private ArrayList<String> authors;
    private ArrayList<String> translators;
    private String isbn;
    private  String publisher;
    private  String date;
    private  String edition;
    private  String cover;
    private String language;
    private String rating;
    private ArrayList<String> tags;


    public Book(String title,String subtitle,ArrayList<String>authors,ArrayList<String>translators, String isbn,String publisher,String date, String edition,String cover,String language,String rating,ArrayList<String>tags){
        this.title=title;
        this.subtitle=subtitle;
        this.authors=authors;
        this.translators=translators;
        this.isbn=isbn;
        this.publisher=publisher ;
        this.date=date;
        this.edition=edition;
        this.cover=cover;
        this.language=language;
        this.rating=rating;
        this.tags=tags;

    }

    public String getPublisher() {
        return publisher;
    }

    public String getEdition() {
        return edition;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }


    public String getSubtitle() {
        return subtitle;
    }

    public ArrayList<String> getTranslators() {
        return translators;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public String getLanguage() {
        return language;
    }

    public String getCover() {
        return cover;
    }

    public String getDate() {
        return date;
    }

    public String getRating() {
        return rating;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public void setTranslators(ArrayList<String> translators) {
        this.translators = translators;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }


    public void setTitle(String title) {
        this.title = title;
    }
}
