package com.mini.twitch.model;


import com.mini.twitch.db.entity.ItemEntity;


public record FavoriteRequestBody(
        ItemEntity favorite
) {

}
