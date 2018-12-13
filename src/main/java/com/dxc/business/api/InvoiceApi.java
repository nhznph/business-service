package com.dxc.business.api;

import com.dxc.business.api.model.Invoice;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "InvoiceApi", url = "${invoice.url}")
public interface InvoiceApi {
    @PostMapping(value = "/v1/invoice/{userId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String addInvoice(@PathVariable("userId") String userId,
                      @RequestBody Invoice invoice);

    @DeleteMapping(value = "/v1/{userId}/invoices/{invoiceNo}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String deleteInvoice(@PathVariable("invoiceNo") String invoiceNo,
                         @PathVariable("userId") String userId);

    @PutMapping(value = "/v1/{userId}/invoices/{invoiceNo}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String updateInvoice(@PathVariable("invoiceNo") String invoiceNo,
                         @PathVariable("userId") String userId,
                         @RequestBody Invoice invoice);

    @GetMapping(value = "/v1/{userId}/invoice/lists", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Invoice> readAllInvoice(@PathVariable("userId") String userId);

    @GetMapping(value = "/v1/{userId}/invoice/viewReport", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Invoice> viewReport(@PathVariable("userId") String userId,
                             @RequestParam("period") String period,
                             @RequestParam("monthly") String monthly);

    @GetMapping(value = "/v1/invoices/{invoiceNo}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Invoice readInvoice(@PathVariable("invoiceNo") String invoiceNo);
}
