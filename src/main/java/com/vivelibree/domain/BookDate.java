package com.vivelibree.domain;

public class BookDate {
  private Book book;
  private String date;

  public BookDate() {}

  public BookDate(Book book, String date) {
    this.book = book;
    this.date = date;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return "BookDate{" +
        "book=" + book +
        ", date='" + date + '\'' +
        '}';
  }
}
