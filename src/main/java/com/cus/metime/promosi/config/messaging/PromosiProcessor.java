package com.cus.metime.promosi.config.messaging;

import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

/**
 * Created by C-US on 9/25/2017.
 */
@Configuration
@MessageEndpoint
public class PromosiProcessor {

    @ServiceActivator(inputChannel = "indexInput")
    public void indexInputProcessor(String r){
        System.out.println(r);
    }

    @ServiceActivator(inputChannel = "fileInput")
    public void fileInputProcessor(String r){
        System.out.println(r);
    }

}
