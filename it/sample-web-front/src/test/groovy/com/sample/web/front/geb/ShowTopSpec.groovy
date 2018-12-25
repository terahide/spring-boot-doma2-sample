package com.sample.web.front.geb

import com.sample.web.front.geb.page.*
import geb.spock.GebSpec

class ShowTop extends GebSpec{
    def "トップページを表示するシナリオ"(){
        given: "ログインする"
        to LoginPage
        ログインする()
        at TopPage
        when: "ありがとフォームを開く"
        ありがとをクリックする()
        then: "ありがとフォームが表示される"
        at ArigatoFormPage
        when: "ありがとを登録する"
        ありがとを入力する('ありがと', 'いつもありがと')
        登録をクリックする()
        then: "トップページが表示される"
        at TopPage
        and: "登録したありがとが表示される"
        一番上のありがとの件名() == 'ありがと'
    }
}
