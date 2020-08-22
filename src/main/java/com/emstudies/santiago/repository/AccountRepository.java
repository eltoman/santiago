package com.emstudies.santiago.repository;

import com.emstudies.santiago.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
