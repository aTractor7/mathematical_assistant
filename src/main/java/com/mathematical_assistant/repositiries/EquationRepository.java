package com.mathematical_assistant.repositiries;

import com.mathematical_assistant.entity.Equation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquationRepository extends JpaRepository<Equation, Integer> {

    List<Equation> findByRootsContains(String root);
}
