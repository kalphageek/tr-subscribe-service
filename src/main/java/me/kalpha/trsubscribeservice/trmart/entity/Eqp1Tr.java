package me.kalpha.trsubscribeservice.trmart.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Entity
@Table(name = "eqp1tr")
@NoArgsConstructor
@Getter @Setter
@ToString(of = {"id","name","value","eventTime", "createdDate", "createdBy"})
public class Eqp1Tr implements Persistable<Long> {
    @Id
    private Long id;

    private String name;
    private Long value;
    private LocalDateTime eventTime;
    private LocalDateTime createdDate;
    @CreatedBy
    private String createdBy;

    public List<Eqp1TrDet> getEqp1TrDets() {
        if (eqp1TrDets == null)
            eqp1TrDets = new ArrayList<>();
        return eqp1TrDets;
    }

    @OneToMany(mappedBy = "eqp1Tr")
    List<Eqp1TrDet> eqp1TrDets;

    @Override
    public boolean isNew() {
        if (createdDate == null) {
            createdDate = LocalDateTime.now();
            return true;
        } else return false;
    }
}
