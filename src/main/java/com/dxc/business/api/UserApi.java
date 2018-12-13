package com.dxc.business.api;

import com.dxc.business.api.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "UserApi", url = "${user.url}")
public interface UserApi {

    @PostMapping(value = "/v1/user", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String addUser(@RequestBody User user);

    @GetMapping(value = "/v1/user", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<User> readAllUser();

    @PutMapping(value = "/v1/{userId}/user/activate", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String activateUser(@PathVariable("userId") String userId);

    @PutMapping(value = "/v1/{userId}/user/deactivate", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String deActivateUser(@PathVariable("userId") String userId);

    @PutMapping(value = "/v1/user/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String updateUser(@PathVariable("id") String id,
                      @RequestBody User user);

    @GetMapping(value = "/v1/user/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    User getUserById(@PathVariable("id") String id);

    @DeleteMapping(value = "/v1/user/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String deleteUser(@PathVariable("id") String id);

    @GetMapping(value = "/v1/user/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<User> getUserByName(@RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName);

    @PutMapping(value = "/v1/{userId}/setLimited", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String setLimitMonthly(@PathVariable("userId") String userId,
                           @RequestParam("limit") String limit);
}
