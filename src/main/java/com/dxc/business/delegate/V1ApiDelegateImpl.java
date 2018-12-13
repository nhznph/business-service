package com.dxc.business.delegate;

import com.dxc.business.api.V1ApiDelegate;
import com.dxc.business.api.model.Invoice;
import com.dxc.business.api.model.User;
import com.dxc.business.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class V1ApiDelegateImpl implements V1ApiDelegate {
    @Autowired
    private BusinessService businessService;

    @Override
    public ResponseEntity<String> addInvoice(String userId, Invoice body) {
        return ResponseEntity.ok(businessService.addInvoice(userId, body));
    }

    @Override
    public ResponseEntity<String> addUser(User body) {
        return ResponseEntity.ok(businessService.addUser(body));
    }

    @Override
    public ResponseEntity<String> deleteInvoice(String invoiceNo, String userId) {
        return ResponseEntity.ok(businessService.deleteInvoice(invoiceNo, userId));
    }

    @Override
    public ResponseEntity<String> deleteUser(String id) {
        return ResponseEntity.ok(businessService.deleteUser(id));
    }

    @Override
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok(businessService.readAllUser());
    }

    @Override
    public ResponseEntity<List<Invoice>> readAllInvoice(String userId) {
        return ResponseEntity.ok(businessService.readAllInvoice(userId));
    }

    @Override
    public ResponseEntity<Invoice> readInvoice(String invoiceNo) {
        return ResponseEntity.ok(businessService.readInvoice(invoiceNo));
    }

    @Override
    public ResponseEntity<User> searchUserByID(String id) {
        return ResponseEntity.ok(businessService.getUserById(id));
    }

    @Override
    public ResponseEntity<List<User>> searchUserByName(String firstName, String lastName) {
        return ResponseEntity.ok(businessService.getUserByName(firstName, lastName));
    }

    @Override
    public ResponseEntity<String> sendMail(String userId, String mailTo, String mailSubject, String mailText) {
        return ResponseEntity.ok(businessService.sendMail(userId, mailTo, mailSubject, mailText));
    }

    @Override
    public ResponseEntity<String> setLimitedMonthly(String userId, String limit) {
        return ResponseEntity.ok(businessService.setLimitMonthly(userId, limit));
    }

    @Override
    public ResponseEntity<String> updateActivateUser(String userId) {
        return ResponseEntity.ok(businessService.activateUser(userId));
    }

    @Override
    public ResponseEntity<String> updateDeactivateUser(String userId) {
        return ResponseEntity.ok(businessService.deActivateUser(userId));
    }

    @Override
    public ResponseEntity<String> updateInvoice(String invoiceNo, String userId, Invoice body) {
        return ResponseEntity.ok(businessService.updateInvoice(invoiceNo, userId, body));
    }

    @Override
    public ResponseEntity<String> updateUser(String id, User body) {
        return ResponseEntity.ok(businessService.updateUser(id, body));
    }

    @Override
    public ResponseEntity<List<Invoice>> viewReportInvoice(String userId, String period, String monthly) {
        return ResponseEntity.ok(businessService.viewReport(userId, period, monthly));
    }
}
