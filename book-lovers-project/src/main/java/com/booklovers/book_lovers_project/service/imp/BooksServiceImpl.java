//package com.booklovers.book_lovers_project.service.imp;
//
//import com.booklovers.book_lovers_project.dto.BookDto;
//import com.booklovers.book_lovers_project.entity.BookEntity;
//import com.booklovers.book_lovers_project.exception.ResourceNotFoundException;
//import com.booklovers.book_lovers_project.repository.BookRepository;
//import com.booklovers.book_lovers_project.service.BookService;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.*;
//import org.springframework.stereotype.Service;
//
//@Service
//public class BookServiceImpl implements BookService {
//
//    private final BookRepository bookRepository;
//    private final ModelMapper modelMapper;
//
//    @Autowired
//    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper) {
//        this.bookRepository = bookRepository;
//        this.modelMapper = modelMapper;
//    }
//
//    @Override
//    public BookDto create(BookDto bookDto) {
//        Book book = modelMapper.map(bookDto, Book.class);
//        Book saved = bookRepository.save(book);
//        return modelMapper.map(saved, BookDto.class);
//    }
//
//    @Override
//    public BookDto update(Long id, BookDto bookDto) {
//        Book existing = bookRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
//        existing.setTitle(bookDto.getTitle());
//        existing.setDescription(bookDto.getDescription());
//        existing.setAuthor(bookDto.getAuthor());
//        existing.setIsbn(bookDto.getIsbn());
//        existing.setPages(bookDto.getPages());
//        existing.setPublishedDate(bookDto.getPublishedDate());
//        existing.setCoverImagePath(bookDto.getCoverImagePath());
//        Book updated = bookRepository.save(existing);
//        return modelMapper.map(updated, BookDto.class);
//    }
//
//    @Override
//    public BookDto getById(Long id) {
//        Book book = bookRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
//        return modelMapper.map(book, BookDto.class);
//    }
//
//    @Override
//    public void delete(Long id) {
//        Book book = bookRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
//        bookRepository.delete(book);
//    }
//
//    @Override
//    public Page<BookDto> getAll(String search, Pageable pageable) {
//        Page<Book> page;
//        if (search == null || search.isBlank()) {
//            page = bookRepository.findAll(pageable);
//        } else {
//            page = bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(search, search, pageable);
//        }
//        return page.map(book -> modelMapper.map(book, BookDto.class));
//    }
//}