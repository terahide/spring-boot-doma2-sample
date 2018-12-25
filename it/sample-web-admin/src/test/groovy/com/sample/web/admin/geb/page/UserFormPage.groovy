package com.sample.web.admin.geb.page

import geb.Page

class UserFormPage extends Page{
    static at = {
        title == 'ユーザ登録 | Sample'
    }

    static content = {
        "ユーザ情報を入力する"{email='someuser@sample.com', lastName='yamada', firstName='taro', password='passw0rd' ->
            def f = find('#form1')
            f.firstName() << firstName
            f.lastName() << lastName
            f.password() << password
            f.passwordConfirm() << password
            f.email() << email
        }
        "登録をクリックする"{
            find('button[type="submit"]').click()
        }
    }
}
