package messaging.domain;

import io.getstream.client.model.activities.BaseActivity;
import io.getstream.client.model.activities.SimpleActivity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Nilesh Bhosale on 13/6/15.
 */
@Entity
@Table(name = "messages")
@Data
public class Message extends SimpleActivity {

    @Column(name = "to_user")
    private long toUser;
    @Column(name = "from_user")
    private long fromUser;
    @Column(name = "message")
    private String message;
    @Column(name = "sent_date")
    private Date sentDate;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "message_id")
    private long messageId;


}
