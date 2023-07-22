package ru.skypro.web_library.testing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.web_library.testing.service.info.InfoService;

@RestController
public class InfoController {
    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/appInfo")
    String appInfo() {
        return infoService.appInfo();
    }
}
