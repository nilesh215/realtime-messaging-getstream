package messaging.controller;

import io.getstream.client.exception.StreamClientException;
import messaging.dao.MessageDao;
import messaging.dao.UserDao;
import messaging.domain.Message;
import messaging.service.GetStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/send",method = RequestMethod.POST)
    public Message sendMessage(@RequestBody Message message) throws IOException, StreamClientException {
        message=getStream.sendMessage(message);
        message=messageDao.save(message);
        return message;
    }




}
