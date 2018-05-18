package nh.backend.books.model;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Repository
public class BookRepository {

  private final static Book[] BOOKS = {
      new Book("B1", "Oliver Zeigermann, Nils Hartmann",
          "React:Die praktische Einführung in React, React Router und Redux", "32,90"),
      new Book("B2", "Robin Wieruch", "The Road to learn React: Your journey to master plain yet pragmatic React.js",
          "20,75"),
      new Book("B3", "Eve Porcello, Alex Banks", "Learning GraphQL: Declarative Data Fetching for Modern Web Apps",
          "33,99"),
      new Book("B4", "Don Norman", "The Design of Everyday Things", "12,99"),

  };

  public List<Book> findAll() {
    return Arrays.asList(BOOKS);
  }

}
