package org.example.youth_be;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/v1/health-check")
    public String healthCheck() {
        return "ok 123";
    }
}
