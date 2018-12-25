package com.sample.web.admin.geb.page

import geb.Page

class UserListPage extends Page {
    static at = {
        title == 'ユーザ一覧 | Sample'
    }

    static content = {
        "操作をクリックする" {
            find('#dropdownMenu1').click()
        }
        "ユーザ登録をクリックする"{
            find('#toUserForm').click()
        }
    }
}