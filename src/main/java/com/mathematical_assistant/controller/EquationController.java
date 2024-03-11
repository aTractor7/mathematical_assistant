package com.mathematical_assistant.controller;

import com.mathematical_assistant.dto.EquationDTO;
import com.mathematical_assistant.entity.Equation;
import com.mathematical_assistant.services.EquationService;
import com.mathematical_assistant.util.validator.EquationValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.mathematical_assistant.util.error.ErrorsUtil.generateErrorMessage;

@RestController
@RequestMapping("/equations")
public class EquationController {

    private final EquationService equationService;
    private final EquationValidator equationValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public EquationController(EquationService equationService, EquationValidator equationValidator, ModelMapper modelMapper) {
        this.equationService = equationService;
        this.equationValidator = equationValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<EquationDTO> getAll() {
        return equationService.getAll().stream().map(this::convertToEquationDTO).collect(Collectors.toList());
    }

    @GetMapping("/getByRoot")
    public List<EquationDTO> getAll(@RequestParam(name = "root") String root) {
        return equationService.getByRoot(root).stream().map(this::convertToEquationDTO).collect(Collectors.toList());
    }

    @GetMapping("/getByPower")
    public List<EquationDTO> getAll(@RequestParam(name = "power") int power) {
        return equationService.getByPower(power).stream().map(this::convertToEquationDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EquationDTO getOne(@PathVariable int id) {
        return convertToEquationDTO(equationService.getOne(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid EquationDTO equationDTO, BindingResult bindingResult) {

        Equation equation = convertToEquation(equationDTO);
        equationValidator.validate(equation, bindingResult);

        if(bindingResult.hasErrors()) {
            throw new IllegalArgumentException(generateErrorMessage(bindingResult.getFieldErrors()));
        }

        equationService.create(equation);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable int id, @RequestBody @Valid EquationDTO equationDTO,
                                             BindingResult bindingResult) {
        Equation equation = convertToEquation(equationDTO);
        equationValidator.validate(equation, bindingResult);

        if(bindingResult.hasErrors()) {
            throw new IllegalArgumentException(generateErrorMessage(bindingResult.getFieldErrors()));
        }

        equationService.update(id, equation);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable int id) {
        equationService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private Equation convertToEquation(EquationDTO equationDTO) {
        return modelMapper.map(equationDTO, Equation.class);
    }

    private EquationDTO convertToEquationDTO(Equation equation) {
        return modelMapper.map(equation, EquationDTO.class);
    }
 }
