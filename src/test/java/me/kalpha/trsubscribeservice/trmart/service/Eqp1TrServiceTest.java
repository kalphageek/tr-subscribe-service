package me.kalpha.trsubscribeservice.trmart.service;

import me.kalpha.trsubscribeservice.trmart.entity.Eqp1Tr;
import me.kalpha.trsubscribeservice.trmart.entity.Eqp1TrDet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class Eqp1TrServiceTest {
    @Autowired
    Eqp1TrService eqp1TrService;

    @Transactional
    @Test
    public void createTr() {
        String trName = "lot1";
        Eqp1Tr eqp1TrDto = generateEqp1Tr(trName);

        Eqp1Tr eqp1Tr = eqp1TrService.createTr(eqp1TrDto);

        assertTrue(eqp1Tr.getEqp1TrDets().stream().count() == 2);
    }

    private Eqp1Tr generateEqp1Tr(String trName) {
        Eqp1Tr eqp1Tr = new Eqp1Tr();
        eqp1Tr.setId(1l);
        eqp1Tr.setValue(1233l);
        eqp1Tr.setName(trName);
        eqp1Tr.setCreatedBy("2043738");
        eqp1Tr.setCreatedDate(LocalDateTime.now());
        eqp1Tr.setEventTime(LocalDateTime.now());;

        Eqp1TrDet eqp1TrDet = new Eqp1TrDet();
        eqp1TrDet.setId(2l);
        eqp1TrDet.setCol1("col1");
        eqp1TrDet.setCol2(234235l);
        eqp1TrDet.setCreatedBy("2043738");
        eqp1TrDet.setCreatedDate(LocalDateTime.now());
        eqp1TrDet.assignEqp1Tr(eqp1Tr);

        Eqp1TrDet eqp1TrDet2 = new Eqp1TrDet();
        eqp1TrDet2.setId(3l);
        eqp1TrDet2.setCol1("col1");
        eqp1TrDet2.setCol2(234235l);
        eqp1TrDet2.setCreatedBy("2043738");
        eqp1TrDet2.setCreatedDate(LocalDateTime.now());
        eqp1TrDet2.assignEqp1Tr(eqp1Tr);

        return eqp1Tr;
    }
}