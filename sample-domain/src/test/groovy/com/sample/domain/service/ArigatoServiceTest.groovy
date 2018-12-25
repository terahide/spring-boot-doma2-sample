package com.sample.domain.service

import com.sample.domain.dao.AuditInfoHolder
import com.sample.domain.dto.arigato.Arigato
import com.sample.domain.service.arigato.ArigatoService
import com.sample.domain.test.helper.Doma2TestHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class ArigatoServiceTest extends Specification{

    @Autowired
    ArigatoService sut

    @Autowired
    Doma2TestHelper h

    def setup(){
        AuditInfoHolder.set "tes", LocalDateTime.now()

        h.delete 'arigatos'
    }

    def "ありがとうを伝える"(){
        given:
        def arigato = new Arigato()
        arigato.fromId = 1
        arigato.toId = 1
        arigato.subject = 'subject'
        arigato.body = 'ありがと'
        expect:
        sut.say(arigato)
    }
}
