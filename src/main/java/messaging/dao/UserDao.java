package messaging.dao;


import messaging.domain.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Nilesh Bhosale
 */
@Transactional
public interface UserDao extends CrudRepository<User, Long> {
}
