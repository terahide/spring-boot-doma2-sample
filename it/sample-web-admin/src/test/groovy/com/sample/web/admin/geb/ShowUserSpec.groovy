package com.sample.web.admin.geb

import com.sample.web.admin.geb.page.*
import geb.spock.GebSpec

class ShowUserSpec extends GebSpec{
    def "トップページを表示するシナリオ"(){
        when: "ログインする"
        to LoginPage
        ログインする()
        then:
        at TopPage
        when: "ユーザ一覧を表示する"
        メニューのユーザ一覧をクリックする()
        then: "ユーザ一覧を表示する"
        at UserListPage
        when: "ユーザフォームを開く"
        操作をクリックする()
        ユーザ登録をクリックする()
        then:
        at UserFormPage
        when: "ユーザを登録する"
        ユーザ情報を入力する('someuser@sample.com', 'yamada', 'taro', 'passw0rd')
        登録をクリックする()
        then: "ユーザ一覧を表示する"
        at UserDetailPage

    }
}
