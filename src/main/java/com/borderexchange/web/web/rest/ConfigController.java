package com.borderexchange.web.web.rest;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConfigController {
    private final Logger log = LoggerFactory.getLogger(ConfigController.class);

    @RequestMapping(value = "/api/logLevel")
    public ResponseEntity<?> changeLogLevel(@RequestParam(name = "pkg", defaultValue = "com.borderexchange", required = false) String pkg, @RequestParam(name = "level", defaultValue = "ERROR", required = false) String level) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.getLogger(pkg).setLevel(Level.valueOf(level));
        log.error("Logging level for '" + pkg + "' changed to : " + level);
        return ResponseEntity.accepted().build();
    }
}
