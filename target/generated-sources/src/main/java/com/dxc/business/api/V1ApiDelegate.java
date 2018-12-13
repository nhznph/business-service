package com.dxc.business.api;

import com.dxc.business.api.model.Invoice;
import com.dxc.business.api.model.User;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * A delegate to be called by the {@link V1ApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */

public interface V1ApiDelegate {

    /**
     * @see V1Api#addInvoice
     */
    ResponseEntity<String> addInvoice(String userId,
        Invoice body);

    /**
     * @see V1Api#addUser
     */
    ResponseEntity<String> addUser(User body);

    /**
     * @see V1Api#deleteInvoice
     */
    ResponseEntity<String> deleteInvoice(String invoiceNo,
        String userId);

    /**
     * @see V1Api#deleteUser
     */
    ResponseEntity<String> deleteUser(String id);

    /**
     * @see V1Api#getAllUser
     */
    ResponseEntity<List<User>> getAllUser();

    /**
     * @see V1Api#readAllInvoice
     */
    ResponseEntity<List<Invoice>> readAllInvoice(String userId);

    /**
     * @see V1Api#readInvoice
     */
    ResponseEntity<Invoice> readInvoice(String invoiceNo);

    /**
     * @see V1Api#searchUserByID
     */
    ResponseEntity<User> searchUserByID(String id);

    /**
     * @see V1Api#searchUserByName
     */
    ResponseEntity<List<User>> searchUserByName(String firstName,
        String lastName);

    /**
     * @see V1Api#sendMail
     */
    ResponseEntity<String> sendMail(String userId,
        String mailTo,
        String mailSubject,
        String mailText);

    /**
     * @see V1Api#setLimitedMonthly
     */
    ResponseEntity<String> setLimitedMonthly(String userId,
        String limit);

    /**
     * @see V1Api#updateActivateUser
     */
    ResponseEntity<String> updateActivateUser(String userId);

    /**
     * @see V1Api#updateDeactivateUser
     */
    ResponseEntity<String> updateDeactivateUser(String userId);

    /**
     * @see V1Api#updateInvoice
     */
    ResponseEntity<String> updateInvoice(String invoiceNo,
        String userId,
        Invoice body);

    /**
     * @see V1Api#updateUser
     */
    ResponseEntity<String> updateUser(String id,
        User body);

    /**
     * @see V1Api#viewReportInvoice
     */
    ResponseEntity<List<Invoice>> viewReportInvoice(String userId,
        String period,
        String monthly);

}
