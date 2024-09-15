package com.vivelibree.domain;

public class Book {
  private Long id;
  private String title;
  private String publicationTimestamp;
  private Integer pages;
  private String summary;
  private Author author;

  public Book() {}

  public Book(Long id, String title, String publicationTimestamp, Integer pages, String summary,
      Author author) {
    this.id = id;
    this.title = title;
    this.publicationTimestamp = publicationTimestamp;
    this.pages = pages;
    this.summary = summary;
    this.author = author;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPublicationTimestamp() {
    return publicationTimestamp;
  }

  public void setPublicationTimestamp(String publicationTimestamp) {
    this.publicationTimestamp = publicationTimestamp;
  }

  public Integer getPages() {
    return pages;
  }

  public void setPages(Integer pages) {
    this.pages = pages;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  @Override
  public String toString() {
    return "Book{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", publicationTimestamp=" + publicationTimestamp +
        ", pages=" + pages +
        ", summary='" + summary + '\'' +
        ", author=" + author +
        '}';
  }
}
