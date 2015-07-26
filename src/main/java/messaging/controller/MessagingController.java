package messaging.controller;

import io.getstream.client.exception.StreamClientException;
import io.getstream.client.model.activities.AggregatedActivity;
import io.getstream.client.model.beans.StreamResponse;
import messaging.dao.MessageDao;
import messaging.dao.UserDao;
import messaging.domain.Message;
import messaging.service.GetStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by Nilesh Bhosale
 */
@RestController()
@RequestMapping("/v1")
public class MessagingController {
    @Autowired
    private GetStream getStream;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageDao messageDao;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public Message sendMessage(@RequestBody Message message) throws IOException, StreamClientException {
        message = getStream.sendMessage(message);
        message = messageDao.save(message);
        return message;
    }

    @RequestMapping(value = "/messages/{userId}", method = RequestMethod.POST)
    public StreamResponse<AggregatedActivity<Message>> sendMessage(@RequestParam long userId) throws IOException, StreamClientException {
        return getStream.getMessages(userId);
    }

    @RequestMapping(value = "/conversation/{userId}/{conversationId}", method = RequestMethod.DELETE)
    public AggregatedActivity<Message> getConversation(
            @RequestParam long userId,
            @RequestParam String conversationId) throws IOException, StreamClientException {
        return getStream.getConversation(userId, conversationId);
    }


}
