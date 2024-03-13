package hu.bca.library.repositories;

import hu.bca.library.models.Book;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByAuthorsCountryOrderByYearAsc(String country);

    List<Book> findByAuthorsCountryAndYearGreaterThanOrderByYearAsc(String country, Integer from);
}
