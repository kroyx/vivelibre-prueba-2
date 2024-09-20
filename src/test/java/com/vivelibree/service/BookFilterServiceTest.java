package com.vivelibree.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivelibree.domain.Book;
import com.vivelibree.domain.BookDate;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import junit.framework.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.support.ReflectionSupport;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.Mockito;

public class BookFilterServiceTest {

  private static BookFilterService bookFilterService;
  private static Book book;
  private static List<Book> booksList;

  public static String TEST_RESOURCES_PATH = "src/test/resources";

  private static List<Book> loadBooksList(String pathname) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(new File(pathname),
          new TypeReference<List<Book>>() {
          });
    } catch (IOException e) {
      return new ArrayList<>();
    }
  }

  private static Book loadBook(String pathname) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(new File(pathname), Book.class);
    } catch (IOException e) {
      return null;
    }
  }

  @BeforeAll
  static void beforeAll() {
    bookFilterService = new BookFilterService();
    book = loadBook(TEST_RESOURCES_PATH + "/book.json");
    booksList = loadBooksList(TEST_RESOURCES_PATH + "/books_list.json");
  }

  @Test
  @DisplayName("TimestampDateToString: Timestamp with correct format")
  public void testTimestampDateToStringDateFunctionWithCorrectFormatTimestamp() {
    String timestamp = "867308140";
    String expectedDate = "01-11-1970";
    Assertions.assertEquals(expectedDate, bookFilterService.timestampDateToStringDate(timestamp),
        "The result must be the date with MM-dd-yyyy format");
  }

  @Test
  @DisplayName("TimestampDateToString: Null timestamp")
  void testTimestampDateToStringDateFunctionWithNullTimestamp() {
    Assertions.assertNull(bookFilterService.timestampDateToStringDate(null),
        "The result must be null");
  }

  @Test
  @DisplayName("TimestampDateToString: Timestamp with not valid format")
  void testTimestampDateToStringDateFunctionWithNotValidTimestampFormat() {
    String timestamp = "867ab81j0";
    Assertions.assertNull(bookFilterService.timestampDateToStringDate(timestamp),
        "The result must be null");
  }

  @Test
  @DisplayName("BookContainsText: Text in book title")
  void testBookContainsTextFunctionWithTextInTitle() {
    Assertions.assertTrue(bookFilterService.bookContainsText("hunger", book));
  }

  @Test
  @DisplayName("BookContainsText: Text in book summary")
  void testBookContainsTextFunctionWithTextInSummary() {
    Assertions.assertTrue(bookFilterService.bookContainsText("katniss", book));
  }

  @Test
  @DisplayName("BookContainsText: Text in author's bio")
  void testBookContainsTextFunctionWithTextInAuthorBio() {
    Assertions.assertTrue(bookFilterService.bookContainsText("Suzanne", book));
  }

  @Test
  @DisplayName("BookContainsText: Text not contained")
  void testBookContainsTextFunctionWithTextNotContainedAnywhere() {
    Assertions.assertFalse(bookFilterService.bookContainsText("Microservices", book));
  }

  @Test
  @DisplayName("BookContainsText: Null book")
  void testBookContainsTextFunctionWithNullBook() {
    Assertions.assertFalse(bookFilterService.bookContainsText("Microservices", null));
  }

  @Test
  @DisplayName("BookContainsText: Null text")
  void testBookContainsTextFunctionWithNullText() {
    Assertions.assertFalse(bookFilterService.bookContainsText(null, book));
  }

  @Test()
  @DisplayName("Filter: Prints books without publication date")
  void testFilterFunctionPrintsBooksWithoutPublicationDate() throws NoSuchMethodException {
    PrintStream mockPrintStream = Mockito.mock(PrintStream.class);
    System.setOut(mockPrintStream);

    String filter = "harry";
    Method filterMethod = BookFilterService.class.getDeclaredMethod("filter", String.class,
        List.class);
    ReflectionSupport.invokeMethod(filterMethod, bookFilterService, filter, booksList);

    Mockito.verify(mockPrintStream, Mockito.times(2)).println(Mockito.any(Book.class));
    System.setOut(System.out);
  }

  @Test
  @DisplayName("Filter: Returns most recent book that contains filter")
  void testFilterFunctionReturnsMostRecentBookThatContainsFilter() throws NoSuchMethodException {
    String filter = "harry";
    Method filterMethod = BookFilterService.class.getDeclaredMethod("filter", String.class,
        List.class);
    Optional<BookDate> bookDate = (Optional<BookDate>) ReflectionSupport.invokeMethod(filterMethod,
        bookFilterService, filter, booksList);

    Assertions.assertTrue(bookDate.isPresent());
    Assertions.assertEquals("Harry Potter and the Sorcerer's Stone",
        bookDate.get().getBook().getTitle());
    Assertions.assertEquals("01-11-1970", bookDate.get().getDate());
  }

  @Test
  @DisplayName("Filter: Returns nothing when filter is not contained in any book")
  void testFilterFunctionReturnsEmptyOptionalWhenFilterIsNotContainedByAnyBook()
      throws NoSuchMethodException {
    String filter = "Microservices";
    Method filterMethod = BookFilterService.class.getDeclaredMethod("filter", String.class,
        List.class);
    Optional<BookDate> bookDate = (Optional<BookDate>) ReflectionSupport.invokeMethod(filterMethod,
        bookFilterService, filter, booksList);

    Assertions.assertFalse(bookDate.isPresent());
  }

  @Test
  @DisplayName("Filter: Returns nothing when list is null")
  void testFilterFunctionReturnsEmptyOptionalWhenListIsNull() throws NoSuchMethodException {
    String filter = "harry";
    Method filterMethod = BookFilterService.class.getDeclaredMethod("filter", String.class,
        List.class);
    Optional<BookDate> bookDate = (Optional<BookDate>) ReflectionSupport.invokeMethod(filterMethod,
        bookFilterService, filter, null);

    Assertions.assertFalse(bookDate.isPresent());
  }

  @Test
  @DisplayName("Filter: Returns nothing when list is null")
  void testFilterFunctionReturnsEmptyOptionalWhenFilterIsNull() throws NoSuchMethodException {
    String filter = "harry";
    Method filterMethod = BookFilterService.class.getDeclaredMethod("filter", String.class,
        List.class);
    Optional<BookDate> bookDate = (Optional<BookDate>) ReflectionSupport.invokeMethod(filterMethod,
        bookFilterService, null, booksList);

    Assertions.assertFalse(bookDate.isPresent());
  }

  @Test
  void testFilterFunctionSortsList() throws NoSuchMethodException {
    String filter = "Harry";
    Method filterMethod = BookFilterService.class.getDeclaredMethod("filter", String.class,
        List.class);
    ReflectionSupport.invokeMethod(filterMethod, bookFilterService, filter, booksList);

    List<Book> booksListSorted = loadBooksList("src/test/resources/books_list_sorted.json");
    Assertions.assertIterableEquals(booksListSorted, booksList);
  }
}
