package com.emstudies.santiago.controller;

import com.emstudies.santiago.entities.Account;
import com.emstudies.santiago.service.AccountService;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/account/")
@Api(value = "AccountsControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Account> findById(@PathVariable Long id){
        Account account = accountService.findById(id);
        return ResponseEntity.ok().body(account);
    }

    @PostMapping
    public ResponseEntity<Account> insert(@RequestBody Account account){
        account = accountService.insert(account);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(account.getId()).toUri();
        return ResponseEntity.created(uri).body(account);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Account> update(@PathVariable Long id, @RequestBody Account account) {
        account = accountService.update(id, account);
        return ResponseEntity.ok().body(account);
    }
}
