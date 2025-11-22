package com.imprenta.personas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.imprenta.personas.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    boolean existsByRfc(String rfc);
    boolean existsByEmail(String email);
    Cliente findByEmail(String email);


}
