package com.rokid.skill.demo.processor;

import com.rokid.skill.demo.common.ProtocolUtils;
import com.rokid.skill.kit4j.aop.Action;
import com.rokid.skill.kit4j.aop.Processor;
import com.rokid.skill.kit4j.constants.Event;
import com.rokid.skill.kit4j.core.BaseProcessor;
import com.rokid.skill.protocol.exception.ProtocolException;
import com.rokid.skill.protocol.request.ReqRequest;
import com.rokid.skill.protocol.request.request.content.slot.Slot;
import com.rokid.skill.protocol.response.ResResponse;
import com.rokid.skill.protocol.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 外卖技能 demo，confirm 和 pickUp 用法 demo
 *
 * @author wuyukai on 2018/5/6.
 */
@Slf4j
@Processor
public class HelloProcessor extends BaseProcessor {

    private static final Logger log = LoggerFactory.getLogger(HelloProcessor.class);
    String douh=",";

    /**
     *
     * 技能打开意图
     * @return 进入直接confirm
     */
    @Action(intent = Event.WELECOME_INTENT)
    public ResResponse welcome() {
        log.info("enter pick up ...");
        return ProtocolUtils.pickUp("你好");
    }

    /**
     * 退出时间忽略，confirm可生效
     *
     * @return ignore响应
     */
    @Action(event = Event.SKILL_EXIT)
    public ResResponse skillExit() {
        log.info("skill exit ignore ...");
        return ProtocolUtils.ignore();
    }

    @Action(intent = Event.SESSION_ENDED)
    public ResResponse sessionEnd() {
        log.info("skill exit ignore ...");
        return ProtocolUtils.ignore();
    }

    @Action(intent = Event.VOICE_STARTED)
    public ResResponse voiceStart() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter VOICE_START confirm intent ..."+sentence);
        return ProtocolUtils.ignore();
    }
    @Action(intent = Event.VOICE_FINISHED)
    public ResResponse voiceFinshed() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter VOICE_FINISHED confirm intent ..."+sentence);
        return ProtocolUtils.ignore();
    }

    /**
     * eat 意图
     *
     * @return 响应
     */
    @Action(intent = Intent.EAT)
    public ResResponse eat() {
        log.info("eat intent, open confirm ...");
        return ProtocolUtils.confirm("你想吃什么", "food", "food", null);
    }

    /**
     * confirm 意图
     *
     * @return 响应
     */
    @Action(intent = Intent.FOOD)
    public ResResponse food() {
        log.info("enter food confirm intent ...");
        Map<String, Slot> slotMap = super.getSlotMap();
        Slot foodSlot = slotMap.get("food");
        if (foodSlot != null) {
            String food = foodSlot.getValue();
            return ProtocolUtils.playTts("好的，帮你预订了" + food);
        }else {
            return ProtocolUtils.playTts("未找到你需要的东西。");
        }
    }

    //4G通话技能
    @Action(intent = Intent.CALL)
    public ResResponse call() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter call confirm intent ..."+sentence);
       return ProtocolUtils.playTts("请问你要打电话给谁");
    }

    @Action(intent = Intent.CALLPHONE)
    public ResResponse callphone() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter callphone confirm intent ..."+sentence);
        Map<String, Slot> slotMap = super.getSlotMap();

        Slot wantSlot = slotMap.get("want");
        Slot phoneSlot = slotMap.get("phone");
        String want=wantSlot.getValue();
        String phone=phoneSlot.getValue();

            String name = sentence.replace(want,"").replace(phone,"").replace("给","");

            List<String> op = new ArrayList();
            op.add(name);
            String content="确定打电话给"+name+"嘛";
            return ProtocolUtils.confirm(content, "yes", "yes",op);

    }

    @Action(intent = Intent.YES)
    public ResResponse yes() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter yes confirm intent ..."+sentence);
        return ProtocolUtils.playTts("正在拨打");
    }

    @Action(intent = Intent.NO)
    public ResResponse no() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter no confirm intent ..."+sentence);
        return ProtocolUtils.playTts("好的");
    }

    @Action(intent = Intent.CALLNAME)
    public ResResponse callName() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter callName confirm intent ..."+sentence);
        Map<String, Slot> slotMap = super.getSlotMap();
        Slot nameSlot = slotMap.get("name");
            String name = nameSlot.getValue();
        String content="确定打电话给"+name+"嘛";
            return ProtocolUtils.playTts(content);
    }

    //声音控制
    @Action(intent = Intent.TURNDOWN)
    public ResResponse turndown() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter turndown confirm intent ..."+sentence);
        return ProtocolUtils.playTts("好的");
    }

    @Action(intent = Intent.TURNUP)
    public ResResponse turnup() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter turnup confirm intent ..."+sentence);
        return ProtocolUtils.playTts("好的");
    }

    @Action(intent = Intent.ANJING)
    public ResResponse anjing() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter anjing confirm intent ..."+sentence);
        return ProtocolUtils.playTts("好的");
    }

    @Action(intent = Intent.SHUTUP_SOFT)
    public ResResponse shutupSoft() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter shutup_soft confirm intent ..."+sentence);
        return ProtocolUtils.playTts("好的");
    }

    @Action(intent = Intent.SHUTUP_ANGER)
    public ResResponse shutupAnger() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter shutup_anger confirm intent ..."+sentence);
        return ProtocolUtils.playTts("好的");
    }
    //音乐技能
    @Action(intent = Intent.PLAY_SINGER)
    public ResResponse play_singer() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter play_singer confirm intent ..."+sentence);
        Map<String, Slot> slotMap = super.getSlotMap();
        Slot nameSlot = slotMap.get("singer");
        if (nameSlot != null) {
            String singer = nameSlot.getValue();
            String content="好的，请您欣赏"+singer+"的歌曲";
          return ProtocolUtils.media(singer,content);
        }else {
            return ProtocolUtils.playTts("未找到");
        }
    }
    @Action(intent = Intent.PLAY_SONG)
    public ResResponse play_song() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter play_song confirm intent ..."+sentence);
        Map<String, Slot> slotMap = super.getSlotMap();
        Slot nameSlot = slotMap.get("song");
        if (nameSlot != null) {
            String song = nameSlot.getValue();
            String content="好的，请您欣赏"+song;
            return ProtocolUtils.media(song,content);
        }else {
            return ProtocolUtils.playTts("未找到");
        }
    }
    @Action(intent = Intent.PLAY)
    public ResResponse play() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter play confirm intent ..."+sentence);
        Map<String, Slot> slotMap = super.getSlotMap();
        Slot songSlot = slotMap.get("song");
        Slot singerSlot = slotMap.get("singer");
        if (singerSlot != null && songSlot !=null) {
            String song = songSlot.getValue();
            String singer =singerSlot.getValue();
            String content="好的，请您欣赏"+singer+"的"+song;
            String iteamid=singer+douh+song;
            return ProtocolUtils.media(iteamid,content);
        }else {
            return ProtocolUtils.playTts("未找到");
        }
    }
    @Action(intent = Intent.PLAY_FAVORITE)
    public ResResponse play_favorite() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter play_favorite intent ..."+sentence);
        Map<String, Slot> slotMap = super.getSlotMap();
        Slot favlistSlot = slotMap.get("favlist");
        if (favlistSlot !=null) {
            String favlist = favlistSlot.getValue();
            String content="好的，请您欣赏";
            return ProtocolUtils.media(favlist,content);
        }else {
            return ProtocolUtils.playTts("未找到");
        }
    }

    @Action(intent = Intent.PLAY_HOTLIST)
    public ResResponse play_hotlist() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter play_hotlist confirm intent ..."+sentence);
        return ProtocolUtils.playTts("请您欣赏");
    }
    @Action(intent = Intent.STOP)
    public ResResponse stopmusic() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter stopmusic confirm intent ..."+sentence);
        return ProtocolUtils.stopMusic();
    }



    @Action(intent = Intent.PLAY_ALBUM)
    public ResResponse play_album() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter play_album intent ..."+sentence);
        Map<String, Slot> slotMap = super.getSlotMap();
        Slot albumSlot = slotMap.get("album");
        if (albumSlot !=null) {
            String album = albumSlot.getValue();
            String content="好的，请您欣赏!";
            return ProtocolUtils.media(album,content);
        }else {
            return ProtocolUtils.playTts("未找到");
        }
    }
    @Action(intent = Intent.PLAY_FAV_SONGLIST)
    public ResResponse play_fav_songlist() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter play_fav_songlist intent ..."+sentence);
        Map<String, Slot> slotMap = super.getSlotMap();
        Slot albumSlot = slotMap.get("songlist_any");
        if (albumSlot !=null) {
            String album = albumSlot.getValue();
            String content="好的，请您欣赏.";
            return ProtocolUtils.media(album,content);
        }else {
            return ProtocolUtils.playTts("未找到");
        }
    }
    @Action(intent = Intent.PLAY_RANDOM)
    public ResResponse play_random() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter play_random confirm intent ..."+sentence);
        return ProtocolUtils.playTts("请您欣赏");
    }

    @Action(intent = Intent.REPLAY)
    public ResResponse replay() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter replay confirm intent ..."+sentence);
        return ProtocolUtils.playTts("好的");
    }
    @Action(intent = Intent.HELP)
    public ResResponse help() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter HELP confirm intent ..."+sentence);
        return ProtocolUtils.playTts("好的");
    }
    @Action(intent = Intent.PLAY_LANGUAGE)
    public ResResponse play_language() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter play_language intent ..."+sentence);
        Map<String, Slot> slotMap = super.getSlotMap();
        Slot languageSlot = slotMap.get("language");
        if (languageSlot !=null) {
            String language = languageSlot.getValue();
            String content="好的，请您欣赏.!!!";
            return ProtocolUtils.media(language,content);
        }else {
            return ProtocolUtils.playTts("未找到");
        }
    }

    @Action(intent = Intent.PLAY_TAG)
    public ResResponse play_tag() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter PLAY_TAG intent ..."+sentence);
        Map<String, Slot> slotMap = super.getSlotMap();
        Slot tagSlot = slotMap.get("tag");
        if (tagSlot !=null) {
            String tag = tagSlot.getValue();
            String content="好的，请您欣赏!!!";
            return ProtocolUtils.media(tag,content);
        }else {
            return ProtocolUtils.playTts("未找到");
        }
    }
    @Action(intent = Intent.PLAY_SINGER_CATEGORY)
    public ResResponse play_singer_category() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter play_singer_category intent ..."+sentence);
        Map<String, Slot> slotMap = super.getSlotMap();
        Slot sexSlot = slotMap.get("sex");
        if (sexSlot !=null) {
            String sex = sexSlot.getValue();
            String content="好的，请您欣赏..";
            return ProtocolUtils.media(sex,content);
        }else {
            return ProtocolUtils.playTts("未找到");
        }
    }
    @Action(intent = Intent.NEXT)
    public ResResponse next() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter next confirm intent ..."+sentence);
        return ProtocolUtils.media("下一首","好的");
    }
    @Action(intent = Intent.PREVIOUS)
    public ResResponse previous() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter previous confirm intent ..."+sentence);
        return ProtocolUtils.media("上一首","好的");
    }
    @Action(intent = Intent.RESUME)
    public ResResponse resume() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter resumeMusic confirm intent ..."+sentence);
        return ProtocolUtils.resumeMusic();
    }
    @Action(intent = Intent.PAUSE)
    public ResResponse pause() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter pause confirm intent ..."+sentence);
        return ProtocolUtils.pauseMusic();
    }

    @Action(intent = Intent.CANCEL_LOOP)
    public ResResponse cancel_loop() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter cancel_loop confirm intent ..."+sentence);
        return ProtocolUtils.playTts("好的");
    }
    @Action(intent = Intent.LOOP)
    public ResResponse loop() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter loop confirm intent ..."+sentence);
        return ProtocolUtils.playTts("好的");
    }
    @Action(intent = Intent.LIKE)
    public ResResponse like() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter like confirm intent ..."+sentence);
        return ProtocolUtils.playTts("好的");
    }
    @Action(intent = Intent.LIKE_SINGER)
    public ResResponse like_singer() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter like_singer confirm intent ..."+sentence);
        return ProtocolUtils.playTts("好的,已收藏");
    }
    @Action(intent = Intent.DISLIKE)
    public ResResponse dislike() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter dislike confirm intent ..."+sentence);
        return ProtocolUtils.playTts("'好的,已加入歌曲黑名单");
    }
    @Action(intent = Intent.DISLIKE_SINGER)
    public ResResponse dislike_singer() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter dislike_singer confirm intent ..."+sentence);
        return ProtocolUtils.playTts("'好的,已加入歌手黑名单");
    }
    @Action(intent = Intent.DISLIKE_LANGUAGE)
    public ResResponse dislike_language() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter dislike_language confirm intent ..."+sentence);
        return ProtocolUtils.playTts("'好的,已加入语言黑名单");
    }
    @Action(intent = Intent.SONG_INFO)
    public ResResponse song_info() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter song_info confirm intent ..."+sentence);
        return ProtocolUtils.playTts("让我看一下");
    }
    @Action(intent = Intent.SINGER_INFO)
    public ResResponse singer_info() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter singer_info confirm intent ..."+sentence);
        return ProtocolUtils.playTts("让我看一下");
    }
    //闹钟
    @Action(intent = Intent._SETUP_ALARM)
    public ResResponse _setup_alarm() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter _setup_alarm intent ..."+sentence);
        Map<String, Slot> slotMap = super.getSlotMap();
        String time = slotMap.get("time").getValue();
        String date = slotMap.get("date").getValue();
       /* String WhatTime = slotMap.get("WhatTime").getValue();
        String WakeMeUp = slotMap.get("WakeMeUp").getValue();
        String Alarm = slotMap.get("Alarm").getValue();
        String HowMany = slotMap.get("HowMany").getValue();*/
//String iteamid=time+douh+date+douh+WhatTime+douh+WakeMeUp+douh+Alarm+douh+HowMany;
        String iteamid=time+douh+date;
            String content="好的正在设置";
        return ProtocolUtils.media(iteamid,content);
    }
    @Action(intent = Intent._QUERY_ALARM)
    public ResResponse _query_alarm() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter play_singer_category intent ..."+sentence);
        Map<String, Slot> slotMap = super.getSlotMap();
        String time = slotMap.get("time").getValue();
        String date = slotMap.get("date").getValue();
        String WhatTime = slotMap.get("WhatTime").getValue();
        String WakeMeUp = slotMap.get("WakeMeUp").getValue();
        String Alarm = slotMap.get("Alarm").getValue();
        String HowMany = slotMap.get("HowMany").getValue();
        String iteamid=time+douh+date+douh+WhatTime+douh+WakeMeUp+douh+Alarm+douh+HowMany;
        String content="好的";
        return ProtocolUtils.media(iteamid,content);
    }
    @Action(intent = Intent._UPDATE_ALARM)
    public ResResponse _update_alarm() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter _update_alarm intent ..."+sentence);
        Map<String, Slot> slotMap = super.getSlotMap();
        String time = slotMap.get("time").getValue();
        String date = slotMap.get("date").getValue();
        String WhatTime = slotMap.get("WhatTime").getValue();
        String WakeMeUp = slotMap.get("WakeMeUp").getValue();
        String Alarm = slotMap.get("Alarm").getValue();
        String HowMany = slotMap.get("HowMany").getValue();
        String SpecificTime = slotMap.get("SpecificTime").getValue();
        String iteamid=time+douh+date+douh+WhatTime+douh+WakeMeUp+douh+Alarm+douh+HowMany+douh+SpecificTime;
        String content="好的";
        return ProtocolUtils.media(iteamid,content);
    }
    @Action(intent = Intent._SELECT_THEME)
    public ResResponse _select_theme() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter _select_theme intent ..."+sentence);
        Map<String, Slot> slotMap = super.getSlotMap();
        String previous = slotMap.get("previous").getValue();
        String next = slotMap.get("next").getValue();
        String theme = slotMap.get("theme").getValue();
        String iteamid=previous+douh+next+douh+theme;
        String content="好的";
        return ProtocolUtils.media(iteamid,content);
    }
    @Action(intent = Intent._CLOSE_ALARM)
    public ResResponse _close_alarm() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter _close_alarm confirm intent ..."+sentence);
        return ProtocolUtils.playTts("好的");
    }
    @Action(intent = Intent._SETUP_REMIND)
    public ResResponse _setup_remind() {
        ReqRequest request =super.getRequest();
        String sentence=request.getRequest().getContent().getSentence();
        log.info("enter _setup_remind intent ..."+sentence);
        Map<String, Slot> slotMap = super.getSlotMap();
        String remindContent = slotMap.get("remindContent").getValue();
        String shortPeriod = slotMap.get("shortPeriod").getValue();
        String date = slotMap.get("date").getValue();
        String iteamid=remindContent+douh+shortPeriod+douh+date;
        String content="好的";
        return ProtocolUtils.media(iteamid,content);
    }

    public static void main(String[] args) throws ProtocolException {
        ResponseUtils.buildIngoreEventResponse();
    }
}
