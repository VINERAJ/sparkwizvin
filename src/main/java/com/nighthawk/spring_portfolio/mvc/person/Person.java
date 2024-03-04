package com.nighthawk.spring_portfolio.mvc.person;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Convert;
import static jakarta.persistence.FetchType.EAGER;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import org.codehaus.groovy.util.ListHashMap;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.mapping.Set;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;

import com.vladmihalcea.hibernate.type.json.JsonType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/*
Person is a POJO, Plain Old Java Object.
First set of annotations add functionality to POJO
--- @Setter @Getter @ToString @NoArgsConstructor @RequiredArgsConstructor
The last annotation connect to database
--- @Entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Convert(attributeName ="person", converter = JsonType.class)
public class Person {

    // automatic unique identifier for Person record
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // email, password, roles are key attributes to login and authentication
    @NotEmpty
    @Size(min=5)
    @Column(unique=true)
    @Email
    private String email;

    @NotEmpty
    private String password;

    // @NonNull, etc placed in params of constructor: "@NonNull @Size(min = 2, max = 30, message = "Name (2 to 30 chars)") String name"
    @NonNull
    @Size(min = 2, max = 30, message = "Name (2 to 30 chars)")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    // To be implemented
    @ManyToMany(fetch = EAGER)
    private Collection<PersonRole> roles = new ArrayList<>();

    /* HashMap is used to store JSON for daily "stats"
    "stats": {
        "2022-11-13": {
            "class 1": csa,
            "class 2": csp,
            "class 3": csse,
            "class 4": calcab,
            "class 5": phys
        }
    }
    */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String,Map<String, Object>> stats = new HashMap<>(); 

    /* Shaurya - need to add one to many relation between person and chat
    not working right now
    @OneToMany(mappedBy = "chat")
    private java.util.Set<Chat> recordings = new HashSet<>();*/
    

    // Constructor used when building object from an API
    public Person(String email, String password, String name, Date dob, String stats) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.stats = new HashMap<>();
       
    }

    // A custom getter to return age from dob attribute
    public int getAge() {
        if (this.dob != null) {
            LocalDate birthDay = this.dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return Period.between(birthDay, LocalDate.now()).getYears(); }
        return -1;
    }

    // Initialize static test data 
    public static Person[] init() {

        // basics of class construction
        Person p1 = new Person();
        p1.setName("Thomas Edison");
        p1.setEmail("toby@gmail.com");
        p1.setPassword("123Toby!");

        // adding Note to notes collection
        try {  // All data that converts formats could fail
            Date d = new SimpleDateFormat("MM-dd-yyyy").parse("01-01-1840");
            p1.setDob(d);
        } catch (Exception e) {
            // no actions as dob default is good enough
        }

         // Adding stats data
        Map<String, Object> stats1 = new HashMap<>();
        stats1.put("class 1", "csa");
        stats1.put("class 2", "csp");
        stats1.put("class 3", "csse");
        stats1.put("class 4", "calcab");
        stats1.put("class 5", "phys");
        p1.getStats().put("01-01-1840", stats1);
        String dobKey1 = new SimpleDateFormat("MM-dd-yyyy").format(p1.getDob());
        p1.getStats().put(dobKey1, stats1);

        Person p5 = new Person();
        p5.setName("Spark Admin");
        p5.setEmail("spk@gmail.com");
        p5.setPassword("spark");
        try {
            Date d = new SimpleDateFormat("MM-dd-yyyy").parse("09-11-2001");
            p5.setDob(d);
        } catch (Exception e) {
        }
           // Adding stats data
           Map<String, Object> stats5 = new HashMap<>();
           stats5.put("class 1", "csa");
           stats5.put("class 2", "csp");
           stats5.put("class 3", "csse");
           stats5.put("class 4", "calcab");
           stats5.put("class 5", "phys");
           p5.getStats().put("09-11-2001", stats5);
           String dobKey5 = new SimpleDateFormat("MM-dd-yyyy").format(p5.getDob());
           p5.getStats().put(dobKey5, stats1);
   

        

        Person p6 = new Person();
        p6.setName("John Mortensen");
        p6.setEmail("jm1021@gmail.com");
        p6.setPassword("123Qwerty!");
        try {
            Date d = new SimpleDateFormat("MM-dd-yyyy").parse("09-14-2001");
            p6.setDob(d);
        } catch (Exception e) {
        }

           // Adding stats data
           Map<String, Object> stats6 = new HashMap<>();
           stats6.put("class 1", "csa");
           stats6.put("class 2", "csp");
           stats6.put("class 3", "csse");
           stats6.put("class 4", "calcab");
           stats6.put("class 5", "phys");
           p6.getStats().put("09-14-2001", stats6);
           String dobKey6 = new SimpleDateFormat("MM-dd-yyyy").format(p6.getDob());
           p6.getStats().put(dobKey6, stats1);
   

        // Array definition and data initialization
        Person persons[] = {p1,p5, p6};
        return(persons);
    }

    public static void main(String[] args) {
        // obtain Person from initializer
        Person persons[] = init();

        // iterate using "enhanced for loop"
        for( Person person : persons) {
            System.out.println(person);  // print object
        }
    }

}
/*{
    "id": "19",
    "date": "2024-02-06",
    "period1": "stats",
    "period2": "csa",
    "period3": "calcab",
    "period4": "apes",
    "period5": "offroll"
    
  } */ 

  // test data for how to implement this POST (http://localhost:8098/api/person/setStats?)