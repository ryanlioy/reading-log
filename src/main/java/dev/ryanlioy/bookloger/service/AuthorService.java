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

    public AuthorDto findAuthorById(Long id) {
        Optional<AuthorEntity> author = authorRepository.findById(id);
        AuthorDto dto = null;
        if (author.isPresent()) {
            dto = authorMapper.entityToDto(author.get());
        }
        return dto;
    }

    public AuthorDto createAuthor(CreateAuthorDto createAuthorDto) {
        List<BookDto> books = bookService.getAllBooksById(createAuthorDto.getBookIds());
        return authorMapper.entityToDto(authorRepository.save(authorMapper.createDtoToEntity(createAuthorDto, books)));
    }

    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
}
