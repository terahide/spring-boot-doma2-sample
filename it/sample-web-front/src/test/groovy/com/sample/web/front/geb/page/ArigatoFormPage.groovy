package com.sample.web.front.geb.page

import geb.Page

class ArigatoFormPage extends Page{
    static at = {
        title == 'ありがと | Sample'
    }

    static content = {
        "ありがとを入力する"{subject='ありがと',body='いつもありがと',to='1' ->
            def f = find('#arigatoForm')
            f.toId() << to
            f.subject() << subject
            f.body() << body
        }
        "登録をクリックする"{
            find('button[type="submit"]').click()
        }
    }
}
