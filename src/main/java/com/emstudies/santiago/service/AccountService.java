package com.emstudies.santiago.service;

import com.emstudies.santiago.entities.Account;
import com.emstudies.santiago.repository.AccountRepository;
import com.emstudies.santiago.service.exceptions.DatabaseException;
import com.emstudies.santiago.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Account findById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Account insert(Account account){
        return accountRepository.save(account);
    }

    public void delete(Long id) {
        try {
            accountRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Account update(Long id, Account account) {
        try{
            Account accountToUpdate = accountRepository.getOne(id);
            accountToUpdate.updateData(account);
            return accountRepository.save(accountToUpdate);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }
}
