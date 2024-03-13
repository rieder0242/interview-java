package hu.bca.library.controllers;

import hu.bca.library.models.Book;
import hu.bca.library.services.BookService;
import java.util.List;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RepositoryRestController("books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ResponseStatus(HttpStatus.CREATED)

    @RequestMapping("/{bookId}/add_author/{authorId}")
    @ResponseBody Book addAuthor(@PathVariable Long bookId, @PathVariable Long authorId) {
        return this.bookService.addAuthor(bookId, authorId);
    }

    @RequestMapping("/update-all-with-year")
    @ResponseBody int updateAllWithYear() {
        return this.bookService.updateAllWithYear();
    }
    
    @RequestMapping("/query/{country}")
    @ResponseBody List<Book> queryByAutorCountryAndYear(@PathVariable String country, @RequestParam(required = false) Integer from) {
        return this.bookService.queryByAutorsCountryAndYear(country, from);
    }
}
