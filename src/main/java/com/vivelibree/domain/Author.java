package com.vivelibree.domain;

public class Author {
  private String name;
  private String bio;

  public Author() {}

  public Author(String name, String bio) {
    this.name = name;
    this.bio = bio;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  @Override
  public String toString() {
    return "Author{" +
        "name='" + name + '\'' +
        ", bio='" + bio + '\'' +
        '}';
  }
}
