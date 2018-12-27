package com.sample.domain.service

import com.sample.domain.dao.AuditInfoHolder
import com.sample.domain.dto.arigato.Arigato
import com.sample.domain.dto.common.BZip2Data
import com.sample.domain.dto.common.Pageable
import com.sample.domain.dto.system.UploadFile
import com.sample.domain.service.arigato.ArigatoService
import com.sample.domain.test.helper.Doma2TestHelper
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class ArigatoServiceTest extends Specification {

    @Autowired
    ArigatoService sut

    @Autowired
    Doma2TestHelper h

    final static long mineId = 1

    def setup() {
        AuditInfoHolder.set "tes", LocalDateTime.now()

        h.delete 'arigatos'
    }

    def "画像なしでありがとうを伝える"() {
        when: "ありがとを伝える"
        sut.say arigato()
        and: "ありがとを取得する"
        def page = sut.search(Pageable.NO_LIMIT)
        then:
        page.count == 1
        and:
        page.data[0].from.name != null
    }

    def "画像付きでありがとうを伝える"() {
        given:
        def arigato = arigato()
        arigato.uploadFile.add toUploadFile('./src/test/resources/test.jpg')
        when: "ありがとを伝える"
        sut.say arigato
        and: "ありがとを取得する"
        def page = sut.search(Pageable.NO_LIMIT)
        then:
        page.data[0].uploadFileId[0] != null
        when: "画像を取得する"
        def uploadFile = sut.getImage(page.data[0].uploadFileId[0])
        then:
        uploadFile.content.toBase64() != null
    }

    def "ありがとうにいいねをする"(){
        given: "ありがとを伝える"
        def arigato = arigato()
        sut.say arigato
        when:
        sut.fav mineId, arigato.id
        and: "ありがとを取得する"
        def count = sut.countFav arigato.id
        then:
        count == 1
    }

    def toUploadFile(path){
        def file = new File(path)
        def content = BZip2Data.of(IOUtils.readFully(new FileInputStream(file), (int)file.length()))
        def uploadFile = new UploadFile()
        uploadFile.originalFilename = file.name
        uploadFile.filename = file.name
        uploadFile.content = content
        uploadFile.contentType = MediaType.IMAGE_JPEG.toString()
        return uploadFile
    }

    def arigato(){
        def arigato = new Arigato()
        arigato.fromId = mineId
        arigato.toId = 1
        arigato.subject = 'subject'
        arigato.body = 'ありがと'
        return arigato
    }
}