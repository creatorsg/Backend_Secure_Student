package com.basic.myspringboot.controller;

import com.basic.myspringboot.controller.dto.StudentDTO;
import com.basic.myspringboot.service.StudentDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * [ 권한 정책 ]
 *   조회        : ROLE_USER 또는 ROLE_ADMIN
 *   등록/수정/삭제 : ROLE_ADMIN
 */
@RestController
@RequestMapping("/api/students/detail")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
public class StudentDetailController {

    private final StudentDetailService studentService;

    @GetMapping
    public ResponseEntity<List<StudentDTO.Response>> getAllStudents() {
        List<StudentDTO.Response> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO.Response> getStudentById(@PathVariable Long id) {
        StudentDTO.Response student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/number/{studentNumber}")
    public ResponseEntity<StudentDTO.Response> getStudentByStudentNumber(@PathVariable String studentNumber) {
        StudentDTO.Response student = studentService.getStudentByStudentNumber(studentNumber);
        return ResponseEntity.ok(student);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<StudentDTO.Response> createStudent(@Valid @RequestBody StudentDTO.Request request) {
        StudentDTO.Response createdStudent = studentService.createStudent(request);
        //HttpStatus.CREATED - 201
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<StudentDTO.Response> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentDTO.Request request) {
        StudentDTO.Response updatedStudent = studentService.updateStudent(id, request);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}