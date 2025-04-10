package org.swisscom.service_app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @GetMapping("/hi")
    public String test() {
        return "Hello World";
    }
}
