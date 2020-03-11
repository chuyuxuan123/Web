package com.example.demo.config;

import com.example.demo.service.RemoteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

import java.rmi.RemoteException;

@Configuration
public class RmiConfig {
    @Autowired
    private RemoteBookService remoteBookService;

    @Bean
    public RmiServiceExporter rmiServiceExporter() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("bookinfo");
        rmiServiceExporter.setService(remoteBookService);
        rmiServiceExporter.setServiceInterface(RemoteBookService.class);
        try {
            rmiServiceExporter.afterPropertiesSet();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return rmiServiceExporter;
    }
}
