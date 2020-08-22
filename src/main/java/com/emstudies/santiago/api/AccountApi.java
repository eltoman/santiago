package com.emstudies.santiago.api;

import com.emstudies.santiago.entities.Account;
import io.swagger.annotations.Api;
import io.swagger.models.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/api/v1/account/")
@Api(value = "AccountsControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public interface AccountApi {

    @GetMapping(value = "/{id}")
    ResponseEntity<Account> findById(@PathVariable Long id);

    @PostMapping
    @ApiOperation(value="Add a new Account", notes="This is a public API with admin right", response= Account.class)
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_ACCEPTED, message = "A new account request accepted"),
            @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Invalid Consumer Key")
    })
    ResponseEntity<Void> insert(@RequestBody Account account);

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);

    @PutMapping(value = "/{id}")
    ResponseEntity<Account> update(@PathVariable Long id, @RequestBody Account account);
}
