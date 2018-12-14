package com.dxc.business.service;

import com.dxc.business.TypeOfInvoice;
import com.dxc.business.api.InvoiceApi;
import com.dxc.business.api.MailApi;
import com.dxc.business.api.UserApi;
import com.dxc.business.api.model.Invoice;
import com.dxc.business.api.model.User;
import com.dxc.business.schedule.ScheduledTasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class BusinessService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    @Autowired
    private MailApi mailApi;

    @Autowired
    private InvoiceApi invoiceApi;

    @Autowired
    private UserApi userApi;

    //chay lap sd fixedDelay and fixedRate

//    @Scheduled(fixedRate = 2000)
//    public void scheduleTaskWithFixedRate() {
//        // call send email method here
//        logger.info("Send email to producers to inform quantity sold items");
//        // 2s se co message
//    }

//    @Scheduled(fixedDelay = 10000)
//    public void scheduleTaskWithFixedDelay() {
//        // Call send email method here
//        // Pretend it takes 1000ms
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        logger.info("Send email to producers to inform quantity sold items");
//    }
//    fixedDelay = 10000, nhưng khoảng cách giữa các lần log là khoảng 11s
//            . Lý do việc thực thi hàm này hết 1s và phải chờ 10s sau nó mới lại dc gọi lại


    //. Chạy lặp với khoảng thời gian fixedRate sau khi đi deploy initialDelay
    // (Sử dụng kết hợp fixedRate và initialDelay)
//    bạn chạy tác vụ gửi mail ngay khi deploy xong, thì initialDelay cho phép bạn thực hiện
//    việc này sau 1 khoảng thời gian là initialDelay(miliseconds)

//    @Scheduled(fixedRate = 2000, initialDelay = 10000)
//    public void scheduleTaskWithInitialDelay() {
//        logger.info("Send email to producers to inform quantity sold items");
//    }


    //Hẹn giờ với cron (Sử dụng cron)
    //Cũng vẫn yêu cầu gửi mail, nhưng bạn muốn gửi vào 12h thứ 6 hàng tuần, hoặc 23h59 ngày cuối tháng .v.v.v. Những thứ bên trên kia là ko đủ.
    // Vậy bạn hãy nghĩ tới cron. Mình ví dụ, log ra màn hình vào giây 15 của mỗi phút
    @Scheduled(cron = "0 */2 * ? * *")
    public void scheduleTaskWithCronExpression() {
        logger.info("Send email to producers to inform quantity sold items");
    }
    // link: https://www.freeformatter.com/cron-expression-generator-quartz.html

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
        if (getTotalMoneyMonth(checkListAmountOfInvoice) > checkUser.getLimited()) {
            mailSubject = "Expense limit";
            mailText = "Total of monthly expense limit";
            return mailApi.sendEmail(userId, mailTo, mailSubject, mailText);
        } else {
            return "the total amount is not greater than the limit";
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
