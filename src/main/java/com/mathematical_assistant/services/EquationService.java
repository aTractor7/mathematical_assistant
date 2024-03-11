package com.mathematical_assistant.services;

import com.mathematical_assistant.entity.Equation;
import com.mathematical_assistant.repositiries.EquationRepository;
import com.mathematical_assistant.util.math_polynomial.EquationRotsOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.mathematical_assistant.util.math_polynomial.EquationRotsOperation.checkRoots;
import static com.mathematical_assistant.util.math_polynomial.EquationRotsOperation.getRootsNum;

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
    public List<Equation> getByRoot(String root) {
        return equationRepository.findByRootsContains(root);
    }

    @Transactional(readOnly = true)
    public List<Equation> getByPower(int power) {
        return getAll().stream()
                .filter(e -> getRootsNum(e.getRoots()) + 1 == power)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Equation getOne(int id) {
        return equationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No equations with such id: " + id));
    }

    public void create(Equation equation) {
        if(equation.getRoots() != null) {
            checkRoots(equation, equation.getRoots());
        }

        equationRepository.save(equation);
    }

    public void update(int id, Equation equation) {
        if(equation.getRoots() != null) {
            checkRoots(equation, equation.getRoots());
        }

        equationRepository.findById(id).ifPresentOrElse(e -> {
            e.setPolynomial(equation.getPolynomial());
            e.setRoots(equation.getRoots());
        }, () -> {
            throw new NoSuchElementException("No equation with id: " + id);
        });
    }

    public void delete(int id) {
        equationRepository.deleteById(id);
    }
}
