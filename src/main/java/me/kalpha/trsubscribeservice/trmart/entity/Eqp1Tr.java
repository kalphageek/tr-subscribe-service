package me.kalpha.trsubscribeservice.trmart.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Entity
@NoArgsConstructor
@Getter @Setter
@ToString(of = {"id","name","value","eventTime"})
public class Eqp1Tr implements Persistable<Long> {
    @Id
    private Long id;

    private String name;
    private Long value;
    private LocalDateTime eventTime;
    @CreatedDate
    private LocalDateTime createdDate;
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
