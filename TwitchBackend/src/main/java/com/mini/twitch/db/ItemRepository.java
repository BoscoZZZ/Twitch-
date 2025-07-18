package com.mini.twitch.db;


import com.mini.twitch.db.entity.ItemEntity;
import org.springframework.data.repository.ListCrudRepository;


public interface ItemRepository extends ListCrudRepository <ItemEntity, Long> {


    ItemEntity findByTwitchId(String twitchId);
    //一个api
    //执行 select * from items where twitchID = ?
}
