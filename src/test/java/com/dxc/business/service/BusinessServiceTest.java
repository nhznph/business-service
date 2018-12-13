package com.dxc.business.service;

import com.dxc.business.api.InvoiceApi;
import com.dxc.business.api.UserApi;
import com.dxc.business.api.model.Invoice;
import com.dxc.business.api.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class BusinessServiceTest {
    private static final String ID = "001";
    private static final String FIRST_NAME = "Nhan";
    private static final String LAST_NAME = "Phan";
    private static final String EMAIL = "nhanzks165@gmail.com";
    private static final String ADDRESS = "lth";
    private static final boolean ACTIVATED = false;

    private final static String TYPE_OF_INVOICE = "Electric";
    private final static Integer AMOUNT_OF_MONEY = 300000;
    private final static String VAT = "0.5";
    private final static String PERIOD = "Monthly";
    private final static String INVOICE_NO = "819d15a3-9692-19ba-9974-180df0e334c1";
    private final static String USER_ID = "001";

    @InjectMocks
    BusinessService businessService;

    @Mock
    User user;

    @Mock
    Invoice invoice;

    @Mock
    UserApi userApi;

    @Mock
    InvoiceApi invoiceApi;

    @Test
    public  void addInvoiceActive(){
        when(userApi.getUserById(ID)).thenReturn(user);
        String actual = businessService.addInvoice(ID,invoice);
        Assert.assertEquals("userId does not activate",actual);
    }

    @Test
    public  void readInvoice(){
       Assert.assertEquals(invoiceApi.readInvoice(INVOICE_NO),businessService.readInvoice(INVOICE_NO));
    }

    @Test
    public  void readAllInvoice(){
        Assert.assertEquals(invoiceApi.readAllInvoice(USER_ID),businessService.readAllInvoice(USER_ID));
    }


}
