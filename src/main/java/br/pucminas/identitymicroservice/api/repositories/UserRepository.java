package br.pucminas.identitymicroservice.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.pucminas.identitymicroservice.api.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
