package com.polovyi.ivan.springpageableresponseproducer.repository;

import com.polovyi.ivan.springpageableresponseproducer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {

}
