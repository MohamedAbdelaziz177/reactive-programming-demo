package com.abdelaziz26.springreactive;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService  studentService;

    @GetMapping("/{id}")
    public Mono<Student> getStudent(@PathVariable Long id) {
        return studentService.findById(id);
    }

    @GetMapping(path = "/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Student> getStudents() {
        return studentService.findAll();
    }

    @PostMapping
    public Mono<Student> saveStudent(@RequestBody StudentRequest request)
    {
        return studentService.save(request);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
    }
}
