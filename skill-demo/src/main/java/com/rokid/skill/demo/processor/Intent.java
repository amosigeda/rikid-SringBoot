package com.rokid.skill.demo.processor;


/**
 * @author wuyukai on 2018/6/16. 所有的intent定义在注解的类中
 * 大小写切换  ctrl+shift+u
 */
public interface Intent {

    String FOOD = "food";
    String EAT = "eat";


     //4G 通话
    String CALL= "call";
    String CALLNAME = "callname";
    String YES = "yes";
    String NO = "no";
    String CALLPHONE ="callphone";

    //声音控制
    String TURNDOWN="turndown";
    String TURNUP="turnup";
    String ANJING="anjing";
    String SHUTUP_SOFT="shutup_soft";
    String SHUTUP_ANGER="shutup_anger";

    //音乐
    String PLAY_TAG="play_tag";
    String PLAY_SINGER="play_singer";
    String PLAY_SONG="play_song";
    String PLAY="play";
    String PLAY_FAVORITE="play_favorite";
    String PLAY_HOTLIST="play_hotlist";
    String PLAY_ALBUM="play_album";
    String PLAY_FAV_SONGLIST="play_fav_songlist";
    String PLAY_RANDOM="play_random";
    String PLAY_LANGUAGE="play_language";
    String PLAY_SINGER_CATEGORY="play_singer_category";
    String NEXT="next";
    String PREVIOUS="previous";
    String RESUME="resume";
    String PAUSE="pause";
    String CANCEL_LOOP="cancel_loop";
    String LOOP="loop";
    String LIKE="like";
    String LIKE_SINGER="like_singer";
    String DISLIKE="dislike";
    String DISLIKE_SINGER="dislike_singer";
    String DISLIKE_LANGUAGE="dislike_language";
    String SONG_INFO="song_info";
    String SINGER_INFO="singer_info";
    String REPLAY="replay";
    String HELP="help";
    String STOP="stop";


    //闹钟
    String _SETUP_ALARM="_setup_alarm";
    String _QUERY_ALARM="_query_alarm";
    String _UPDATE_ALARM="_update_alarm";
    String _CANCEL_ALARM="_cancel_alarm";
    String _SELECT_THEME="_select_theme";
    String _CLOSE_ALARM="_close_alarm";
    String _SETUP_REMIND="_setup_remind";




}
