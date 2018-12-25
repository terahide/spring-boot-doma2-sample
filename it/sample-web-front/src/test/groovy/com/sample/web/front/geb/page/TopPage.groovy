package com.sample.web.front.geb.page

import geb.Page

class TopPage extends Page{
    static url = "http://localhost:18080/admin/"
    static at = {
        title == 'Home | Sample'
    }

    static content = {
        "ありがとをクリックする"{
            find('#toArigato').click()
        }
        "一番上のありがとの件名"{
            find('.arigato', 0).find('.subject').text()
        }
    }
}
