package com.sample.web.front.controller.api.v1.arigato

import com.sample.domain.dao.AuditInfoHolder
import com.sample.domain.dto.arigato.Arigato
import com.sample.domain.dto.user.User
import com.sample.domain.service.arigato.ArigatoService
import com.sample.domain.service.users.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDateTime

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

@SpringBootTest()
@Transactional
class WhenFavToArigatoSpec extends Specification{
    @Autowired
    WebApplicationContext wac

    @Shared
    MockMvc mvc

    @Autowired
    ArigatoService arigatoService

    @Autowired
    UserService userService

    def arigato

    def setup(){
        AuditInfoHolder.set "tes", LocalDateTime.now()
        mvc = webAppContextSetup(wac).apply(springSecurity()).build()

        def sendTo = someone('someone@sample.com')
        userService.create sendTo

        arigato = new Arigato()
        arigato.fromId = 1
        arigato.toId = sendTo.id
        arigato.subject = "ありがと"
        arigato.body = "ありがと"

        arigatoService.say arigato
    }

    @WithMockUser(username = "test@sample.com", password = "passw0rd", authorities = ".*")
    def "test"(){
        when:
        def json = mvc.perform(put("/api/v1/arigato/${arigato.id}/fav")
                .param('check', 'true')
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andReturn().response.contentAsString
        then:
        json.contains '"fav_counts":1'
    }

    def someone(email, lastName='yamada', firstName='hanako'){
        def u = new User()
        u.email = email
        u.lastName = lastName
        u.firstName = firstName
        u.password = 'passw0rd'
        return u
    }
}
