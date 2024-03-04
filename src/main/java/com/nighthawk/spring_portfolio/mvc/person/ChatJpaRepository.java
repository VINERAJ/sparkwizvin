package com.nighthawk.spring_portfolio.mvc.person;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
public interface ChatJpaRepository extends CrudRepository<Chat, Long>{

    List<Chat> findByEmail(String email);
    //List<Chat> findByEmailUnread(String email);

}
