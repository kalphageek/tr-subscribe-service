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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Entity
@Getter @Setter
@NoArgsConstructor
@ToString(of = {"id","col1","col2"})
public class Eqp1TrDet implements Persistable<Long> {
    @Id
    private Long id;
    private String col1;
    private Long col2;

    @ManyToOne
    @JoinColumn(name = "tr_id")
    private Eqp1Tr eqp1Tr;

    @CreatedDate
    private LocalDateTime createdDate;
    private String createdBy;

    public void assignEqp1Tr(Eqp1Tr eqp1Tr) {
        this.eqp1Tr = eqp1Tr;
        this.eqp1Tr.getEqp1TrDets().add(this);
    }

    @Override
    public boolean isNew() {
        if (createdDate == null) {
            createdDate = LocalDateTime.now();
            return true;
        } else return false;
    }
}
