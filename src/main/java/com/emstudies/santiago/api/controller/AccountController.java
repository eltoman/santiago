package com.emstudies.santiago.api.controller;

import com.emstudies.santiago.api.AccountApi;
import com.emstudies.santiago.entities.Account;
import com.emstudies.santiago.service.AccountService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Component
public class AccountController implements AccountApi {

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;

    @Override
    public ResponseEntity<Account> findById(@PathVariable Long id){
        logger.info("findById: ", id);
        Account account = accountService.findById(id);
        return ResponseEntity.ok().body(account);
    }

    @Override
    public ResponseEntity<Void> insert(@RequestBody Account account){
        account = accountService.insert(account);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(account.getId()).toUri();
        return ResponseEntity.accepted().body(null);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Account> update(@PathVariable Long id, @RequestBody Account account) {
        account = accountService.update(id, account);
        return ResponseEntity.ok().body(account);
    }
}
