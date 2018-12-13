package com.dxc.business.service;

import com.dxc.business.TypeOfInvoice;
import com.dxc.business.api.InvoiceApi;
import com.dxc.business.api.MailApi;
import com.dxc.business.api.UserApi;
import com.dxc.business.api.model.Invoice;
import com.dxc.business.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class BusinessService {
    @Autowired
    private MailApi mailApi;

    @Autowired
    private InvoiceApi invoiceApi;

    @Autowired
    private UserApi userApi;

    // mail
    public String sendMail(String userId, String mailTo, String mailSubject, String mailText) {
        User checkUser = userApi.getUserById(userId);
        if (checkUser != null) {
            if (checkUser.getEmail().endsWith("@gmail.com")) {
                mailTo = checkUser.getEmail();
            } else {
                return " email error !!";
            }
        }
        Date a = new Date();
        List<Invoice> checkListAmountOfInvoice = invoiceApi.viewReport(userId, "monthly", String.valueOf(getMonth(a)));
        if (checkListAmountOfInvoice.size() == 4) {
            if (getTotalMoneyMonth(checkListAmountOfInvoice) > checkUser.getLimited()) {
                mailSubject = "Expense limit";
                mailText = "Total of monthly expense limit";
                return mailApi.sendEmail(userId, mailTo, mailSubject, mailText);
            } else {
                return "the total amount is not greater than the limit";
            }

        } else {
            return "month does not have enough invoices ";
        }
    }

    //invoice
    public String addInvoice(String userId, Invoice invoice) {
        User checkUser = userApi.getUserById(userId);
        if (checkUser.isActivated()) {
            return invoiceApi.addInvoice(userId, invoice);
        } else {
            return "userId does not activate";
        }

    }

    public String deleteInvoice(String invoiceNo, String userId) {
        Invoice invoice = invoiceApi.readInvoice(invoiceNo);
        User checkUser = userApi.getUserById(userId);
        if (checkUser.isActivated()) {
            if (userId.equals(invoice.getUserId())) {
                return invoiceApi.deleteInvoice(invoiceNo, userId);
            } else {
                return "userId have no invoice";
            }
        } else {
            return "userId does not activate";
        }
    }

    public Invoice readInvoice(String invoiceNo) {
        return invoiceApi.readInvoice(invoiceNo);
    }

    public String updateInvoice(String invoiceNo, String userId, Invoice invoice) {
        User checkUser = userApi.getUserById(userId);
        if (checkUser.isActivated()) {
            return invoiceApi.updateInvoice(invoiceNo, userId, invoice);
        } else {
            return "userId does not activate";
        }
    }

    public List<Invoice> readAllInvoice(String userId) {
        return invoiceApi.readAllInvoice(userId);
    }

    public List<Invoice> viewReport(String userId, String period, String monthly) {
        return invoiceApi.viewReport(userId, period, monthly);
    }

    //user
    public String addUser(User user) {
        return userApi.addUser(user);
    }

    public List<User> readAllUser() {
        return userApi.readAllUser();
    }

    public String updateUser(String userId, User user) {
        return userApi.updateUser(userId, user);
    }

    public User getUserById(String userId) {
        return userApi.getUserById(userId);
    }

    public String deleteUser(String userId) {
        return userApi.deleteUser(userId);
    }

    public List<User> getUserByName(String firstName, String lastName) {
        return userApi.getUserByName(firstName, lastName);
    }

    public String setLimitMonthly(String userId, String limit) {
        return userApi.setLimitMonthly(userId, limit);
    }

    //admin
    public String activateUser(String userId) {
        return userApi.activateUser(userId);
    }

    public String deActivateUser(String userId) {
        return userApi.deActivateUser(userId);
    }
    //helper
    private int getMonth(Date a) {
        LocalDate localDate = a.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        return month;
    }

    private double getTotalMoneyMonth(List<Invoice> invoiceList) {
        double total = 0.0;
        for (Invoice invoice : invoiceList) {
            if (invoice.getTypeOfInvoice().equals(TypeOfInvoice.Water.name())) {
                double money = invoice.getAmountOfMoney() * Double.valueOf(invoice.getVat()) + invoice.getAmountOfMoney();
                total += money;
            }
            if (invoice.getTypeOfInvoice().equals(TypeOfInvoice.Electric.name())) {
                double money = invoice.getAmountOfMoney() * Double.valueOf(invoice.getVat()) + invoice.getAmountOfMoney();
                total += money;
            }
            if (invoice.getTypeOfInvoice().equals(TypeOfInvoice.Internet.name())) {
                double money = invoice.getAmountOfMoney() * Double.valueOf(invoice.getVat()) + invoice.getAmountOfMoney();
                total += money;
            }
            if (invoice.getTypeOfInvoice().equals(TypeOfInvoice.Telephone.name())) {
                double money = invoice.getAmountOfMoney() * Double.valueOf(invoice.getVat()) + invoice.getAmountOfMoney();
                total += money;
            }
        }
        return total;
    }
}
