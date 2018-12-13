package com.dxc.business.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sendEmail", url = "${mail.url}")
public interface MailApi {
    @RequestMapping(value = "/{userId}/sendEmail")
    String sendEmail(@PathVariable("userId") String userId,
                     @RequestParam("mailTo") String mailTo,
                     @RequestParam("mailSubject") String mailSubject,
                     @RequestParam("mailText") String mailText);
}
