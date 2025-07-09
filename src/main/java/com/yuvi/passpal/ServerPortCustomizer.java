package com.yuvi.passpal;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

@Component
public class ServerPortCustomizer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        String port = System.getenv("X_ZOHO_CATALYST_LISTEN_PORT");
        int listeningPort;
        if(port != null && !port.isEmpty()){
            listeningPort = Integer.parseInt(port);
        } else{
            listeningPort = 9000;
        }
        factory.setPort(listeningPort);
    }
}
