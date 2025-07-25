package com.mini.twitch.db.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.mini.twitch.external.model.Clip;
import com.mini.twitch.external.model.Stream;
import com.mini.twitch.external.model.Video;
import com.mini.twitch.model.ItemType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Table("items")
//找数据 @ sign
public record ItemEntity(
        @Id Long id,
        //@id 代表 对应于primary key
        @JsonProperty("twitch_id") String twitchId,
        //按下划线的来
        String title,
        String url,
        @JsonProperty("thumbnail_url") String thumbnailUrl,
        @JsonProperty("broadcaster_name") String broadcasterName,
        @JsonProperty("game_id") String gameId,
        @JsonProperty("item_type") ItemType type
) {


    public ItemEntity(String gameId, Video video) {
        this(null, video.id(), video.title(), video.url(), video.thumbnailUrl(), video.userName(), gameId, ItemType.VIDEO);
    }


    public ItemEntity(Clip clip) {
        this(null, clip.id(), clip.title(), clip.url(), clip.thumbnailUrl(), clip.broadcasterName(), clip.gameId(), ItemType.CLIP);
    }


    public ItemEntity(Stream stream) {
        this(null, stream.id(), stream.title(), null, stream.thumbnailUrl(), stream.userName(), stream.gameId(), ItemType.STREAM);
    }
}
