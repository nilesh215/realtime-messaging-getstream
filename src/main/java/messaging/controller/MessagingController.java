package messaging.controller;

import messaging.dao.UserDao;
import messaging.service.GetStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Nilesh Bhosale
 */
@RestController()
@RequestMapping("/messaging/v1/")
public class MessagingController {
    @Autowired
    private GetStream getStream;

    @Autowired
    private UserDao userDao;

}
