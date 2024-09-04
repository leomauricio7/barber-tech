package br.com.barbertech.repository;

import br.com.barbertech.entity.BarberEntity;
import br.com.barbertech.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
}
