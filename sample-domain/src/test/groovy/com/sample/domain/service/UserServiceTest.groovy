package com.sample.domain.service

import com.sample.domain.dao.AuditInfoHolder
import com.sample.domain.dto.common.Pageable
import com.sample.domain.dto.user.User
import com.sample.domain.dto.user.UserCriteria
import com.sample.domain.service.users.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.time.LocalDateTime

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@SpringBootTest(webEnvironment = NONE)
@Transactional
// テスト後にロールバックする
class UserServiceTest extends Specification {

    @Autowired
    UserService userService

    def setup(){
        AuditInfoHolder.set "tes", LocalDateTime.now()
    }

    def "存在しないメールアドレスで絞り込んだ場合、0件が返ること"() {
        when:
        def criteria = new UserCriteria()
        criteria.setEmail("aaaa")

        def pages = userService.findAll(criteria, Pageable.DEFAULT)

        then:
        pages.getCount() == 0
    }

    def "住所をIS NULLで絞り込んだ場合、0件が返ること"() {
        when:
        def criteria = new UserCriteria()
        criteria.setOnlyNullAddress(true)

        def pages = userService.findAll(criteria, Pageable.DEFAULT)

        then:
        pages.getCount() == 0
    }

    def "ユーザを登録してそのユーザを検索するとそのユーザが返ること"() {
        given: "ユーザを登録する"
        def user = new User()
        user.email = 'someuser@sample.com'
        user.lastName = 'yamada'
        user.firstName = 'taro'
        userService.create user

        and: "検索条件の設定"
        def criteria = new UserCriteria()
        criteria.email = user.email

        when: "ユーザを検索"
        def page = userService.findAll(criteria, Pageable.DEFAULT)

        then:
        page.data[0].email == user.email
    }

}
