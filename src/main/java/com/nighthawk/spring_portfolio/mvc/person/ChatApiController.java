package com.nighthawk.spring_portfolio.mvc.person;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatApiController {
    @Autowired
    private ChatJpaRepository chatJpaRepository; 
    
    @PostMapping("")
    public String sendMessage(@RequestParam String email, @RequestParam String message, @RequestParam String fromEmail){
        Chat chat = new Chat(email, message, fromEmail);
        Chat savedChat = chatJpaRepository.save(chat);

        // store this message somewhere and deliver to user
        return "Message Sent Successfully. ID: " + savedChat.getId();
    }
}
