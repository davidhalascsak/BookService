package com.david.book_service.services;

import com.david.book_service.exceptions.BookNotFoundException;
import com.david.book_service.mappers.BookMapper;
import com.david.book_service.models.Book;
import com.david.book_service.models.data.BookDTO;
import com.david.book_service.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {

    private final BookMapper mapper;
    private final MongoTemplate mongoTemplate;
    private final BookRepository bookRepository;

    @Cacheable(value = "books")
    public BookDTO findBookByIsbn(String isbn) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findByIsbn(isbn);

        if(book.isPresent()) {
            return mapper.bookToBookDTO(book.get());
        } else {
            throw new BookNotFoundException("The book is not found!");
        }
    }

    @CachePut(value = "books")
    public BookDTO addToCache(BookDTO bookDTO) {
        return bookDTO;
    }

    public List<BookDTO> getTopRatedBooks() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, "rating"));
        query.with(PageRequest.of(0, 30));

        List<Book> books = mongoTemplate.find(query, Book.class);

        return mapper.booksToBookDTOs(books);
    }

    public List<BookDTO> getAllBooks() {
        return mapper.booksToBookDTOs(bookRepository.findAll());
    }

    public List<BookDTO> getAllBooksByIds(List<String> ids) {
        return mapper.booksToBookDTOs(bookRepository.findAllByIdIn(ids));
    }

    public List<BookDTO> getAllBooksByTitle(String title) {
        String regex = ".*" + title + ".*";
        Query query = new Query(Criteria.where("title").regex(regex, "i"));

        List<Book> books = mongoTemplate.find(query, Book.class);

        return mapper.booksToBookDTOs(books);
    }

    public List<BookDTO> getAllBooksByGenre(String genre) {
        return mapper.booksToBookDTOs(bookRepository.findAllByGenre(genre.replaceAll("-", " ")));
    }

    @CachePut(value = "books")
    public BookDTO saveBook(BookDTO bookDTO) {
        Book book = mapper.bookDTOToBook(bookDTO);

        return mapper.bookToBookDTO(bookRepository.save(book));
    }

    @CacheEvict(value = "books")
    public void deleteBook(String id) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()) {
            bookRepository.deleteById(id);
        }
    }
}
