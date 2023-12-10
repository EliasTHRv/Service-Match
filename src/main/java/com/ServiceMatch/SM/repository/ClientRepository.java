package com.ServiceMatch.SM.repository;

import com.ServiceMatch.SM.entities.ClientUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientUser, Long> {
    @Query("SELECT COUNT(a) FROM AppUser a WHERE a.rol = :rol")
    long countByRol(@Param("rol") String rol);

}
