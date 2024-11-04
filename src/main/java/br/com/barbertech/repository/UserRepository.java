package br.com.barbertech.repository;

import br.com.barbertech.entity.CompanyEntity;
import br.com.barbertech.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
