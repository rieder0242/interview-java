package hu.bca.library.services;

import hu.bca.library.models.Book;

import java.util.List;

public interface BookService {

    Book addAuthor(Long bookId, Long authorId);

    /**
     *
     * @return the number of the updated book
     *
     */
    /*
        Az itt hagyott "import java.util.List;" arra utal, hogy valaki a konyvek
        listajaval tert vissza (vagy csak az id-kkal), de az tobb ezer konyv 
        eseton (feltehetoleg) folosleges lenne. Mindenesetre a specifikaciobol 
        nem tuik ki.
     */
    int updateAllWithYear();
}
