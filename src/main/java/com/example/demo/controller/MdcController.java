package com.example.demo.controller;

import com.example.demo.service.AsyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author meow
 */
@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class MdcController {

    private final AsyncService asyncService;

    @GetMapping("mdc")
    public String hello() {
        log.info("Hello World in MdcController ");
        asyncService.asyncMethod();
        asyncService.mdcAsyncMethod();
        return "mdc";
    }
}
