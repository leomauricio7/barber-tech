package br.com.barbertech.repository;

import br.com.barbertech.entity.AddressEntity;
import br.com.barbertech.entity.BarberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
