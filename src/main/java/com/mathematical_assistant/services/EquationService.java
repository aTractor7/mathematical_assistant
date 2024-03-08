package com.mathematical_assistant.services;

import com.mathematical_assistant.entity.Equation;
import com.mathematical_assistant.repositiries.EquationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Equation getOne(int id) {
        return equationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No equations with such id: " + id));
    }
}
