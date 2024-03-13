package hu.bca.library.services.impl;

import hu.bca.library.models.Author;
import hu.bca.library.models.Book;
import hu.bca.library.repositories.AuthorRepository;
import hu.bca.library.repositories.BookRepository;
import hu.bca.library.services.BookService;
import hu.bca.library.services.OpenLibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    protected BookRepository bookRepository;
    @Autowired
    protected AuthorRepository authorRepository;
    @Autowired
    protected OpenLibraryService openLibraryService;

    @Override
    public Book addAuthor(Long bookId, Long authorId) {
        Optional<Book> book = this.bookRepository.findById(bookId);
        if (book.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Book with id %s not found", bookId));
        }
        Optional<Author> author = this.authorRepository.findById(authorId);
        if (author.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Author with id %s not found", authorId));
        }

        List<Author> authors = book.get().getAuthors();
        authors.add(author.get());

        book.get().setAuthors(authors);
        return this.bookRepository.save(book.get());
    }

    @Override
    public int updateAllWithYear() {
        final Iterable<Book> books = bookRepository.findAll();
        int count = 0;
        for (Book book : books) {
            final Integer bookPublisYear = openLibraryService.getBookPublisYear(book.getWorkId());
            if (!Objects.equals(bookPublisYear, book.getYear())) {
                book.setYear(bookPublisYear);
                bookRepository.save(book);
                count++;
            }
        }
        return count;
    }

    @Override
    public List<Book> queryByAutorsCountryAndYear(String country, Integer from) {
        if (from == null) {
            return bookRepository.findByAuthorsCountryOrderByYearAsc(country);
        } else {
            return bookRepository.findByAuthorsCountryAndYearGreaterThanOrderByYearAsc(country, from);
        }
    }

}
