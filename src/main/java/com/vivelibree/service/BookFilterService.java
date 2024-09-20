package com.vivelibree.service;

import com.vivelibree.domain.Book;
import com.vivelibree.domain.BookDate;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public class BookFilterService {

  /**
   * Performs the following three actions from a given list of books and a string:
   * 1. Show in the console those books from the list that do not have a publication date
   * 2. Get the most recently published book from the list that contains a given string
   * 3. Sort the given list firstly by its publication date and then
   * by the author's biography length
   *
   * @param filter string to look for in the tittle, summary or author's biography of a book
   * @param books  list on which to perform the mentioned actions
   * @return the most recently published book that contains the given string if found
   */
  private Optional<BookDate> filter(String filter, List<Book> books) {
    if (Objects.isNull(books) || books.isEmpty()) {
      return Optional.empty();
    }
    // Show in the console those books that do not have a publication date
    books.forEach(book -> {
      if (Objects.isNull(book.getPublicationTimestamp())) {
        System.out.println(book);
      }
    });

    // Get the most recently published book that contains the given string
    Comparator<Book> mostRecentDateComparator = Comparator.comparing(
        Book::getPublicationTimestamp,
        Comparator.nullsFirst(String::compareTo).reversed()
    );
    Optional<BookDate> mostRecentFilteredBook = books.stream()
        .filter(book -> bookContainsText(filter, book))
        .min(mostRecentDateComparator)
        .map(book -> {
          BookDate bookDate = new BookDate();
          bookDate.setBook(book);
          bookDate.setDate(timestampDateToStringDate(book.getPublicationTimestamp()));
          return bookDate;
        });

    // Sort the list by the publication date and author's biography length
    Comparator<Book> shortestAuthorBioComparator = Comparator.comparing(
        book -> Objects.nonNull(book.getAuthor()) ? book.getAuthor().getBio().length()
            : Integer.MAX_VALUE,
        Comparator.nullsLast(Integer::compareTo)
    );
    Comparator<Book> dateAndAuthorBioComparator = mostRecentDateComparator.thenComparing(
        shortestAuthorBioComparator);
    books.sort(dateAndAuthorBioComparator);

    return mostRecentFilteredBook;
  }

  /**
   * Checks if the book contains a given string in its title, summary or
   * author's biography
   *
   * @param text string to check if it is contained in the book
   * @param book book to check if it contains the string
   * @return true if the book contains the string, false otherwise
   */
  public boolean bookContainsText(String text, Book book) {
    if (Objects.isNull(book) || StringUtils.isBlank(text)) {
      return false;
    }
    
    return StringUtils.containsIgnoreCase(book.getTitle(), text)
        || StringUtils.containsIgnoreCase(book.getSummary(), text)
        || (Objects.nonNull(book.getAuthor()) && StringUtils.containsIgnoreCase(
        book.getAuthor().getBio(),
        text));
  }

  /**
   * Gets a date as a string with the format "MM-dd-yyyy" from a timestamp string
   *
   * @param timestamp string from which to extract the date
   * @return date as a string
   */
  public String timestampDateToStringDate(String timestamp) {
    if (Objects.isNull(timestamp) || !StringUtils.isNumeric(timestamp)) {
      return null;
    }
    Instant instantDate = Instant.ofEpochMilli(Long.parseLong(timestamp));
    LocalDate date = LocalDate.ofInstant(instantDate, ZoneId.systemDefault());

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    return date.format(formatter);
  }
}
