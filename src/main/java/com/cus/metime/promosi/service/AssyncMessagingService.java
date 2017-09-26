package com.cus.metime.promosi.service;

import com.cus.metime.promosi.config.messaging.Promosi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * Created by C-US on 9/25/2017.
 */
@Service
public class AssyncMessagingService {

    private final Promosi outputChannelSource;
    private Integer integer = 0;
    private Integer integer2 = 0;

    @Autowired
    public AssyncMessagingService(Promosi outputChannelSource){
        this.outputChannelSource = outputChannelSource;
    }

    public void sendIndexMessage(){
        integer++;
        MessageChannel messageChannel = outputChannelSource.indexOutput();
        messageChannel.send(
            MessageBuilder.withPayload("Tes Kirim Index : "+integer.toString()).build()
        );
    }

    public void sendFileMessage(){
        integer2++;
        MessageChannel messageChannel = outputChannelSource.indexOutput();
        messageChannel.send(
            MessageBuilder.withPayload("Tes Kirim File : "+integer2.toString()).build()
        );
    }

}
