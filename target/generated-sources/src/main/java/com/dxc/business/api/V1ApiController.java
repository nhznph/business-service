package com.dxc.business.api;

import com.dxc.business.api.model.Invoice;
import com.dxc.business.api.model.User;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import java.util.List;

@Controller
public class V1ApiController implements V1Api {

    private final V1ApiDelegate delegate;

    @org.springframework.beans.factory.annotation.Autowired
    public V1ApiController(V1ApiDelegate delegate) {
        this.delegate = delegate;
    }
    public ResponseEntity<String> addInvoice(@ApiParam(value = "Id of user",required=true) @PathVariable("userId") String userId,@ApiParam(value = "Invoice object need to be add in Invoice" ,required=true )  @Valid @RequestBody Invoice body) {
        return delegate.addInvoice(userId, body);
    }

    public ResponseEntity<String> addUser(@ApiParam(value = "user object need to be add in database" ,required=true )  @Valid @RequestBody User body) {
        return delegate.addUser(body);
    }

    public ResponseEntity<String> deleteInvoice(@ApiParam(value = "ID of invoice to delete",required=true) @PathVariable("invoiceNo") String invoiceNo,@ApiParam(value = "ID of user exist",required=true) @PathVariable("userId") String userId) {
        return delegate.deleteInvoice(invoiceNo, userId);
    }

    public ResponseEntity<String> deleteUser(@ApiParam(value = "ID of user to delete",required=true) @PathVariable("id") String id) {
        return delegate.deleteUser(id);
    }

    public ResponseEntity<List<User>> getAllUser() {
        return delegate.getAllUser();
    }

    public ResponseEntity<List<Invoice>> readAllInvoice(@ApiParam(value = "user Id",required=true) @PathVariable("userId") String userId) {
        return delegate.readAllInvoice(userId);
    }

    public ResponseEntity<Invoice> readInvoice(@ApiParam(value = "invoice code",required=true) @PathVariable("invoiceNo") String invoiceNo) {
        return delegate.readInvoice(invoiceNo);
    }

    public ResponseEntity<User> searchUserByID(@ApiParam(value = "ID of user to get",required=true) @PathVariable("id") String id) {
        return delegate.searchUserByID(id);
    }

    public ResponseEntity<List<User>> searchUserByName(@NotNull @ApiParam(value = "firstname of user", required = true) @Valid @RequestParam(value = "firstName", required = true) String firstName,@NotNull @ApiParam(value = "last name of user", required = true) @Valid @RequestParam(value = "lastName", required = true) String lastName) {
        return delegate.searchUserByName(firstName, lastName);
    }

    public ResponseEntity<String> sendMail(@ApiParam(value = "user Id",required=true) @PathVariable("userId") String userId,@ApiParam(value = "Mail To .") @Valid @RequestParam(value = "mailTo", required = false) String mailTo,@ApiParam(value = "Mail Subject.") @Valid @RequestParam(value = "mailSubject", required = false) String mailSubject,@ApiParam(value = "Mail Text.") @Valid @RequestParam(value = "mailText", required = false) String mailText) {
        return delegate.sendMail(userId, mailTo, mailSubject, mailText);
    }

    public ResponseEntity<User> setLimitedMonthly(@ApiParam(value = "user Id",required=true) @PathVariable("userId") String userId,@NotNull @ApiParam(value = "monthly limited", required = true) @Valid @RequestParam(value = "limit", required = true) Integer limit) {
        return delegate.setLimitedMonthly(userId, limit);
    }

    public ResponseEntity<String> updateActivateUser(@ApiParam(value = "user Id",required=true) @PathVariable("userId") String userId) {
        return delegate.updateActivateUser(userId);
    }

    public ResponseEntity<String> updateDeactivateUser(@ApiParam(value = "user Id",required=true) @PathVariable("userId") String userId) {
        return delegate.updateDeactivateUser(userId);
    }

    public ResponseEntity<String> updateInvoice(@ApiParam(value = "ID of invoice to update",required=true) @PathVariable("invoiceNo") String invoiceNo,@ApiParam(value = "ID of user exist",required=true) @PathVariable("userId") String userId,@ApiParam(value = "invoice object need to be updated" ,required=true )  @Valid @RequestBody Invoice body) {
        return delegate.updateInvoice(invoiceNo, userId, body);
    }

    public ResponseEntity<String> updateUser(@ApiParam(value = "ID of user to update",required=true) @PathVariable("id") String id,@ApiParam(value = "user object need to be updated" ,required=true )  @Valid @RequestBody User body) {
        return delegate.updateUser(id, body);
    }

    public ResponseEntity<List<Invoice>> viewReportInvoice(@ApiParam(value = "user Id",required=true) @PathVariable("userId") String userId,@NotNull @ApiParam(value = "changed period .", required = true) @Valid @RequestParam(value = "period", required = true) String period,@NotNull @ApiParam(value = "input month or year need view report", required = true) @Valid @RequestParam(value = "monthly", required = true) String monthly) {
        return delegate.viewReportInvoice(userId, period, monthly);
    }

}
