package com.lab2.laboratoriska.web;


import com.lab2.laboratoriska.model.Book;
import com.lab2.laboratoriska.model.dto.AddBookDto;
import com.lab2.laboratoriska.model.dto.EditBookDto;
import com.lab2.laboratoriska.model.dto.GetAllBooksDto;
import com.lab2.laboratoriska.model.dto.MarkBookAsTakenDto;
import com.lab2.laboratoriska.model.enums.ECategory;
import com.lab2.laboratoriska.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/book", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/user")
    public ResponseEntity<Page<GetAllBooksDto>> getAllBooks(@PageableDefault(sort = {"id"}, value = 5)Pageable pageable){
        return ResponseEntity.ok(this.bookService.findAll(pageable));
    }

    @GetMapping
    public List<GetAllBooksDto> listAll(){
        return this.bookService.findAll();
    }

    @PatchMapping("/librarian")
    public ResponseEntity<Book> editBook(@RequestBody EditBookDto editBookDto){
        return ResponseEntity.ok(this.bookService.edit(editBookDto));
    }

    @DeleteMapping("/librarian/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id){
        return ResponseEntity.ok(this.bookService.deleteById(id));
    }

    @PatchMapping("/librarian/takeBook")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Book takeBook(@RequestBody MarkBookAsTakenDto markBookAsTakenDto){
        return this.bookService.markBookAsTaken(markBookAsTakenDto);
    }

    @PostMapping("/librarian")
    public ResponseEntity<Book> addBook(@RequestBody AddBookDto addBookDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.bookService.add(addBookDto));
    }

    @GetMapping("/user/categories")
    public ResponseEntity<List<ECategory>> getCategories(){
        return ResponseEntity.ok(this.bookService.findAllCategories());
    }


}
