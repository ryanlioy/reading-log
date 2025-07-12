package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.dto.AuthorDto;
import dev.ryanlioy.bookloger.dto.BookDto;
import dev.ryanlioy.bookloger.dto.CreateAuthorDto;
import dev.ryanlioy.bookloger.entity.AuthorEntity;
import dev.ryanlioy.bookloger.mapper.AuthorMapper;
import dev.ryanlioy.bookloger.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final BookService bookService;

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper, BookService bookService) {
        this.authorMapper = authorMapper;
        this.authorRepository = authorRepository;
        this.bookService = bookService;
    }

    /**
     * Find an author by ID
     * @param id the ID to find
     * @return the author, null if not found
     */
    public AuthorDto findAuthorById(Long id) {
        Optional<AuthorEntity> author = authorRepository.findById(id);
        AuthorDto dto = null;
        if (author.isPresent()) {
            dto = authorMapper.entityToDto(author.get());
        }
        return dto;
    }

    /**
     * Creates and author
     * @param createAuthorDto the author to create
     * @return the created author
     */
    public AuthorDto createAuthor(CreateAuthorDto createAuthorDto) {
        List<BookDto> books = bookService.getAllBooksById(createAuthorDto.getBookIds());
        return authorMapper.entityToDto(authorRepository.save(authorMapper.createDtoToEntity(createAuthorDto, books)));
    }

    /**
     * Delete an author by ID
     * @param id the ID to delete
     */
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }

    /**
     * Get authors of a book
     * @param bookId the book ID
     * @return {@link List} of authors, empty list if no authors are found
     */
    public List<AuthorDto> getAuthorsByBookId(Long bookId) {
        return authorRepository.findAllAuthorsByBookId(bookId).stream().map(authorMapper::entityToDto).toList();
    }

    public boolean doesAuthorExist(String name) {
        Optional<AuthorEntity> author = authorRepository.findByName(name);
        return author.isPresent();
    }
}
