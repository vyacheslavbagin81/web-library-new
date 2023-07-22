package ru.skypro.web_library.testing.service.info;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InfoServiceImpl implements InfoService {
    @Value("app.env")
    private String appEnv;
    @Override
    public String appInfo() {
        return appEnv;
    }
}
