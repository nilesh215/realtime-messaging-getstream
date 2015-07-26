package messaging.service;

import client.StreamClientImpl;
import io.getstream.client.StreamClient;
import io.getstream.client.config.ClientConfiguration;
import io.getstream.client.config.StreamRegion;
import io.getstream.client.exception.StreamClientException;
import io.getstream.client.model.activities.AggregatedActivity;
import io.getstream.client.model.beans.StreamResponse;
import io.getstream.client.model.feeds.Feed;
import io.getstream.client.service.AggregatedActivityServiceImpl;
import messaging.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

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

    /**
     * Sends a message to another user
     *
     * @param message
     * @return
     * @throws IOException
     * @throws StreamClientException
     */
    public Message sendMessage(Message message) throws IOException, StreamClientException {
        ArrayList ids = new ArrayList();
        ids.add(String.valueOf(message.getToUser()));
        message.setTo(ids);
        message.setSentDate(new Date());
        message.setActor(String.valueOf(message.getFromUser()));
        message.setVerb(message.getFromUser() > message.getToUser() ?
                "message" + message.getFromUser() + message.getToUser() :
                "message" + message.getToUser() + message.getFromUser());
        return postMessage(message);
    }

    /**
     * post Messages to GetStream
     *
     * @return
     * @throws IOException
     * @throws StreamClientException
     */
    private Message postMessage(Message message) throws IOException, StreamClientException {
        Feed feed = streamClient.newFeed("messages", String.valueOf(message.getFromUser()));
        AggregatedActivityServiceImpl<Message> aggregatedActivityService = feed.newAggregatedActivityService(Message.class);
        message = aggregatedActivityService.addActivity(message);
        return message;
    }

    /**
     * Get the list of all conversations
     *
     * @param userId
     * @return
     * @throws IOException
     * @throws StreamClientException
     */
    public StreamResponse<AggregatedActivity<Message>> getMessages(long userId) throws IOException, StreamClientException {
        Feed feed = streamClient.newFeed("messages", String.valueOf(userId));
        AggregatedActivityServiceImpl<Message> aggregatedActivityService = feed.newAggregatedActivityService(Message.class);
        return aggregatedActivityService.getActivities();
    }

    /**
     * Get all messages in a conversation
     *
     * @param userId
     * @param conversationId
     * @return
     * @throws IOException
     * @throws StreamClientException
     */
    public AggregatedActivity<Message> getConversation(long userId, String conversationId) throws IOException, StreamClientException {
        StreamResponse<AggregatedActivity<Message>> aggregatedActivityStreamResponse = getMessages(userId);
        for (AggregatedActivity<Message> messageAggregatedActivity : aggregatedActivityStreamResponse.getResults()) {
            messageAggregatedActivity.getId().equals(conversationId);
            return messageAggregatedActivity;
        }
        return null;
    }
}
