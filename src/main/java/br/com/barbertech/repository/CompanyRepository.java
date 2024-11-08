package br.com.barbertech.repository;

import br.com.barbertech.entity.BarberEntity;
import br.com.barbertech.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    // Método para buscar barbeiros pelo nome usando LIKE
    List<CompanyEntity> findByNameContainingIgnoreCase(String name);
}
