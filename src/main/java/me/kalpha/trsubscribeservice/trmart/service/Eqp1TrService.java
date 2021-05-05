package me.kalpha.trsubscribeservice.trmart.service;

import lombok.extern.slf4j.Slf4j;
import me.kalpha.trsubscribeservice.trmart.entity.Eqp1Tr;
import me.kalpha.trsubscribeservice.trmart.entity.Eqp1TrDet;
import me.kalpha.trsubscribeservice.trmart.repository.Eqp1TrDetRepository;
import me.kalpha.trsubscribeservice.trmart.repository.Eqp1TrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class Eqp1TrService {
    @Autowired
    Eqp1TrRepository trRepository;
    @Autowired
    Eqp1TrDetRepository trDetRepository;
//    @Autowired
//    ModelMapper eqp1TrMapper;
//    @Autowired
//    ModelMapper eqp1TrDetMapper;

    @Transactional
// ModelMapper사용 - 특정값 설정    
//    public Eqp1Tr createTr(Eqp1Tr receivedEqp1Tr) {
//        eqp1TrMapper.addMappings(new PropertyMap<Eqp1Tr, Eqp1Tr>() {
//            @Override
//            protected void configure() {
//                map().setCreatedDate(null);
//            }
//        });
//        eqp1TrDetMapper.addMappings(new PropertyMap<Eqp1TrDet, Eqp1TrDet>() {
//            @Override
//            protected void configure() {
//                map().setCreatedDate(null);
//            }
//        });
//
//        Eqp1Tr eqp1Tr = eqp1TrMapper.map(receivedEqp1Tr, Eqp1Tr.class);
//        List<Eqp1TrDet> eqp1TrDets = receivedEqp1Tr.getEqp1TrDets().stream()
//                .map(o -> eqp1TrDetMapper.map(o, Eqp1TrDet.class))
//                .collect(Collectors.toList());

    public Eqp1Tr createTr(Eqp1Tr eqp1Tr) {
        eqp1Tr.setCreatedDate(null);
        List<Eqp1TrDet> eqp1TrDets = eqp1Tr.getEqp1TrDets();
        eqp1TrDets.forEach(o -> o.setCreatedDate(null));
        // Save at Eqp1Tr, Eqp1TrDet
        // CascadeType이 지정되어 있지 않기 때문에 따로 저장해야 한다.
        try {
            trRepository.save(eqp1Tr);
            trDetRepository.saveAll(eqp1TrDets);
        } catch (DataIntegrityViolationException e) { // 이전 offset에서 다시 시작하는 경우 (기존 데이터 존재)
            eqp1Tr.setCreatedDate(LocalDateTime.now());
            eqp1TrDets.forEach(o -> o.setCreatedDate(LocalDateTime.now()));
            trRepository.save(eqp1Tr);
            trDetRepository.saveAll(eqp1TrDets);
        } catch (Exception e) {
            log.debug("{} : createTr({} ({}))", this.getClass(), eqp1Tr, eqp1Tr.getEqp1TrDets().size());
            throw e;
        }

        log.info("eqp1Tr : {} ({})", eqp1Tr, eqp1Tr.getEqp1TrDets().size());
        eqp1TrDets.forEach(o ->
                log.info("eqp1TrDets : {}", o)
        );

        return eqp1Tr;
    }
}
