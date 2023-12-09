package com.ServiceMatch.SM.repository;

import com.ServiceMatch.SM.entities.ClientUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientUser, Long> {

}
