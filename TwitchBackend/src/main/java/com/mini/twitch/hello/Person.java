package com.mini.twitch.hello;


import com.fasterxml.jackson.annotation.JsonProperty;

public record Person(
        String name,
        String company,
//        Address homeAddress,
//        Book favoriteBook
        @JsonProperty("home_address") Address homeAddress,
        //让下划线消失 统一格式
        @JsonProperty("favorite_book") Book favoriteBook

) {
}
