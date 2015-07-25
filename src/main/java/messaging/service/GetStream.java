package messaging.service;

import client.StreamClientImpl;
import io.getstream.client.StreamClient;
import io.getstream.client.config.ClientConfiguration;
import io.getstream.client.config.StreamRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Nilesh Bhosale
 */
@Component("getStream")
public class GetStream {


    private StreamClient streamClient;

    /**
     * GetStream Constructor
     * Set the APIKEY, APISECRET and StreamRegion
     * Create a new client for Stream using the configuration
     */
    @Autowired
    public GetStream(@Value("${getstream.key}") String apiKey,
                     @Value("${getstream.secret}") String apiSecret) {
        ClientConfiguration streamConfig = new ClientConfiguration();
        streamConfig.setRegion(StreamRegion.AP_SOUTH_EAST);
        streamClient = new StreamClientImpl(streamConfig, apiKey, apiSecret);
    }

}
