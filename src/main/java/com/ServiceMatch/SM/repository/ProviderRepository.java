
package com.ServiceMatch.SM.repository;

import com.ServiceMatch.SM.entities.Provider;
import com.ServiceMatch.SM.enums.RolEnum;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {
    Provider findByName(String name);

//  public List<Provider> findByRol(RolEnum rol);
  
//  @query(SELECT p FROM )
//public List<Provider> findCalificacion(@Param("idProvider") Long id));
//    
//}

    public List<Provider> findByRol(RolEnum rol);

}

    

