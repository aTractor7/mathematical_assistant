package com.mathematical_assistant.services;

import com.mathematical_assistant.dto.EquationDTO;
import com.mathematical_assistant.entity.Equation;
import com.mathematical_assistant.repositiries.EquationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class EquationService {

    private final EquationRepository equationRepository;

    @Autowired
    public EquationService(EquationRepository equationRepository) {
        this.equationRepository = equationRepository;
    }

    @Transactional(readOnly = true)
    public List<Equation> getAll() {
        return equationRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Equation getOne(int id) {
        return equationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No equations with such id: " + id));
    }
    
    public void create(Equation equation) {
        equation.setPower(0);
        //TODO: add roots counter
        equationRepository.save(equation);
    }

    public void update(int id, Equation equation) {
        equation.setPower(0);
        //TODO: add roots counter
        equationRepository.findById(id).ifPresentOrElse(e -> {
            e.setPolynomial(equation.getPolynomial());
            e.setPower(equation.getPower());
            e.setRoots(equation.getRoots());
        }, () -> {
            throw new NoSuchElementException("No equation with id: " + id);
        });
    }

    public void delete(int id) {
        equationRepository.deleteById(id);
    }
}
