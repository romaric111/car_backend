package com.packt.cardatabase.domain;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

@RepositoryRestResource(exported=false)
public interface AppUserRepository  extends CrudRepository<AppUser, Long>{
	
	Optional<AppUser> findByUsername(String username);

}
