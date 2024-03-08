package com.mathematical_assistant.repositiries;

import com.mathematical_assistant.entity.Equation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquationRepository extends JpaRepository<Equation, Integer> {

}
