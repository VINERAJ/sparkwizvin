package com.nighthawk.spring_portfolio.mvc.person;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String message;
    private String email;
    private String fromEmail;
    private boolean readFlag;

    /*@ManyToOne
    @JoinColumn(name = "id")
    private Person person;*/

    public Chat (String email, String message, String fromEmail){
        this.email = email;
        this.message = message;
        this.fromEmail = fromEmail;
        this.readFlag = false;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMessage() {
        return message;
    }

    public String getEmail() {
        return email;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public boolean isReadFlag(){
        return readFlag;
    }


}
