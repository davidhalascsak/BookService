package com.david.book_service.mappers;

import com.david.book_service.models.Book;
import com.david.book_service.models.data.BookDTO;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO bookToBookDTO(Book book);

    Book bookDTOToBook(BookDTO bookDTO);

    List<BookDTO> booksToBookDTOs(List<Book> books);

    List<Book> bookDTOsToBooks(List<BookDTO> bookDTOs);
}
