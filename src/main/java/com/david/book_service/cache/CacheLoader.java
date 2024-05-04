package com.david.book_service.cache;

import com.david.book_service.models.Book;
import com.david.book_service.services.BookService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Slf4j
@Component
@EnableScheduling
@AllArgsConstructor
public class CacheLoader {

    private BookService service;

    @PostConstruct
    public void loadCache() {
        List<Book> topRatedBooks = service.getTopRatedBooks();
        for(Book book: topRatedBooks) {
            service.addToCache(book);
        }
        log.info("Top rated books are cached");
    }

    @Scheduled(cron ="0 0 0 * * *")
    @CacheEvict(value = "books", allEntries = true)
    public void updateCache()
    {
        log.info("Clear book cache");
        loadCache();
    }
}
