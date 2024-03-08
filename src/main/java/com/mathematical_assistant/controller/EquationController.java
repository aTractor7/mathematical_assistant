package com.mathematical_assistant.controller;

import com.mathematical_assistant.dto.EquationDTO;
import com.mathematical_assistant.entity.Equation;
import com.mathematical_assistant.services.EquationService;
import com.mathematical_assistant.util.ErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/equations")
public class EquationController {

    private final EquationService equationService;
    private final ModelMapper modelMapper;

    @Autowired
    public EquationController(EquationService equationService, ModelMapper modelMapper) {
        this.equationService = equationService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<EquationDTO> getAll() {
        return equationService.getAll().stream().map(this::convertToEquationDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EquationDTO getOne(@PathVariable int id) {
        return convertToEquationDTO(equationService.getOne(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody EquationDTO equationDTO) {

        Equation equation = convertToEquation(equationDTO);
        equationService.create(equation);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable int id, @RequestBody EquationDTO equationDTO) {

        Equation equation = convertToEquation(equationDTO);
        equationService.update(id, equation);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable int id) {
        equationService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NoSuchElementException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private Equation convertToEquation(EquationDTO equationDTO) {
        return modelMapper.map(equationDTO, Equation.class);
    }

    private EquationDTO convertToEquationDTO(Equation equation) {
        return modelMapper.map(equation, EquationDTO.class);
    }
 }
