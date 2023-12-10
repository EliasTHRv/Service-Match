
package com.ServiceMatch.SM.repository;

import com.ServiceMatch.SM.entities.ProviderUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderRepository extends JpaRepository<ProviderUser, Long> {
    ProviderUser findByName(String name);
}

    

