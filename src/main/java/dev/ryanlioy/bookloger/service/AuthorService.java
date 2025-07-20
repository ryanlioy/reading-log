package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.constants.Errors;
import dev.ryanlioy.bookloger.dto.AuthorDto;
import dev.ryanlioy.bookloger.dto.BookDto;
import dev.ryanlioy.bookloger.dto.CreateAuthorDto;
import dev.ryanlioy.bookloger.dto.meta.ErrorDto;
import dev.ryanlioy.bookloger.entity.AuthorEntity;
import dev.ryanlioy.bookloger.mapper.AuthorMapper;
import dev.ryanlioy.bookloger.repository.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private static final Logger LOG = LoggerFactory.getLogger(AuthorService.class);
    private static final String CLASS_LOG = "AuthorService::";
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
        LOG.info("{}findAuthorById() Author with ID={} " + (author.isPresent() ? "found" : "not found"), CLASS_LOG, author);
        return author.map(authorMapper::entityToDto).orElse(null);
    }

    /**
     * Creates and author
     * @param createAuthorDto the author to create
     * @return the created author
     */
    public AuthorDto createAuthor(CreateAuthorDto createAuthorDto, List<ErrorDto> errors) {
        AuthorDto authorDto = getAuthorByName(createAuthorDto.getName());
        if (authorDto != null) {
            errors.add(new ErrorDto(Errors.AUTHOR_ALREADY_EXISTS));
            LOG.error("{}createAuthor() Author with name {} already exists", CLASS_LOG, authorDto.getName());
            return null;
        }

        List<BookDto> books = bookService.getAllBooksById(createAuthorDto.getBookIds());
        AuthorEntity entity = authorRepository.save(authorMapper.createDtoToEntity(createAuthorDto, books));
        LOG.info("{}createAuthor() Author with ID={} created", CLASS_LOG, entity.getId());
        return authorMapper.entityToDto(entity);
    }

    /**
     * Delete an author by ID
     * @param id the ID to delete
     */
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
        LOG.info("{}deleteAuthorById() Author with ID={} deleted", CLASS_LOG, id);
    }

    /**
     * Get authors of a book
     * @param bookId the book ID
     * @return {@link List} of authors, empty list if no authors are found
     */
    public List<AuthorDto> getAuthorsByBookId(Long bookId) {
        List<AuthorEntity> entities = authorRepository.findAllAuthorsByBookId(bookId);
        LOG.info("{}getAuthorsByBookId() Found {} authors for book with ID={}", CLASS_LOG, entities.size(), bookId);
        return entities.stream().map(authorMapper::entityToDto).toList();
    }

    public boolean doesAuthorExist(String name) {
        Optional<AuthorEntity> author = authorRepository.findByName(name);
        LOG.info("{}doesAuthorExist() author with name {}" +  (author.isPresent() ? "exists" : "does not exist"), CLASS_LOG, author);
        return author.isPresent();
    }

    public AuthorDto getAuthorByName(String name) {
        Optional<AuthorEntity> author = authorRepository.findByName(name);
        LOG.info("{}getAuthorByName() Author with name {}",CLASS_LOG, author);
        return author.map(authorMapper::entityToDto).orElse(null);
    }
}
