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

@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Entity
@Table(name = "eqp1trdet")
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

    private LocalDateTime createdDate;
    @CreatedBy
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
