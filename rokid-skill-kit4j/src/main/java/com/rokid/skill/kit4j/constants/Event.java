package com.rokid.skill.kit4j.constants;


/**
 * @author wuyukai on 2018/6/16.
 */
public interface Event {

    /**
     * tts结束事件
     */
    String VOICE_STARTED = "Voice.STARTED";
    /**
     * tts失败事件
     */
    String VOICE_FAILED = "Voice.FAILED";
    /**
     * tts完成事件
     */
    String VOICE_FINISHED = "Voice.FINISHED";

    /**
     * 媒体开始事件
     */
    String MEDIA_STARTED = "Media.STARTED";
    /**
     * 媒体失败事件
     */
    String MEDIA_FAILED = "Media.FAILED";
    /**
     * 媒体暂停事件
     */
    String MEDIA_PAUSED = "Media.PAUSED";
    /**
     * 媒体完成事件
     */
    String MEDIA_FINISHED = "Media.FINISHED";
    /**
     * 媒体超时事件
     */
    String MEDIA_TIMEOUT = "Media.TIMEOUT";

    /**
     * 技能退出事件
     */
    String SKILL_EXIT = "Skill.EXIT";
    /**
     * 技能挂起事件
     */
    String SKILL_SUSPEND = "Skill.SUSPEND";
    /**
     * 会话结束事件
     */
    String SESSION_ENDED = "Session.ENDED";

    /**
     * 技能打开意图
     */
    String WELECOME_INTENT = "ROKID.INTENT.WELCOME";
    /**
     * 未知意图
     */
    String UNKONWN_INTENT = "ROKID.INTENT.UNKNOWN";
    /**
     * 技能退出意图
     */
    String EXIT_INTENT = "ROKID.INTENT.EXIT";
}
