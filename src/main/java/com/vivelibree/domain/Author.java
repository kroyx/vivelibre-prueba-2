package com.vivelibree.domain;

import java.util.Objects;

public class Author {
  private String name;
  private String firstSurname;
  private String bio;

  public Author() {}

  public Author(String name, String firstSurname, String bio) {
    this.name = name;
    this.firstSurname = firstSurname;
    this.bio = bio;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFirstSurname() {
    return firstSurname;
  }

  public void setFirstSurname(String firstSurname) {
    this.firstSurname = firstSurname;
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
        ", firstSurname='" + firstSurname + '\'' +
        ", bio='" + bio + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Author author = (Author) o;
    return Objects.equals(getName(), author.getName()) && Objects.equals(
        getFirstSurname(), author.getFirstSurname()) && Objects.equals(getBio(),
        author.getBio());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getFirstSurname(), getBio());
  }
}
