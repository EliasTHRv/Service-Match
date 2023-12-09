package com.ServiceMatch.SM.entities;

import com.ServiceMatch.SM.enums.JobStatusEnum;
import javax.persistence.*;



import java.io.Serializable;
// import java.time.LocalDate;
import java.util.Date;

import lombok.*;

@Data
@Entity
@Table(name = "job")
public class Job implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(nullable = true)

    Long callification;

    @Column(nullable = true)
    String comment;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    Date startDate = new Date();


    @Column(name = "end_date", nullable = true)

    @Temporal(TemporalType.DATE)
    Date endDate;   

    @Column(nullable = false)
    Double cost;

    @Column(nullable = false)
    String description;

    @Column(name = "job_status", nullable = false)
    @Enumerated(EnumType.STRING)
    JobStatusEnum jobStatus;
//TODO FIXME
//    @OneToOne
//    Skill skill;

    @OneToOne
    ClientUser clientUser;

    @OneToOne
    Provider provider;


}

    
    
    
    






    



