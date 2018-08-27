package com.rokid.skill.protocol.response.response.action.directive.media;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.rokid.skill.protocol.response.response.action.directive.ResDirective;
import com.rokid.skill.protocol.response.response.action.directive.media.mediaitem.ResMediaItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author wuyukai
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResMedia extends ResDirective {

    public ResMedia() {
        setType("media");
    }

    /**
     * 从头开始播放操作，最好带上MediaItem，否则一旦设备那边没有Item，则会出错
     */
    public static final String MEDIA_ACTION_PLAY = "PLAY";
    /**
     * 暂停播放操作，暂停当前的Media播放
     */
    public static final String MEDIA_ACTION_PAUSE = "PAUSE";
    /**
     * 继续播放操作，继续当前的播放，需要注意的是客户端那边必须要有MediaItem，否则会出错（可以校验一下当前的Media状态是否是PAUSE的状态）
     */
    public static final String MEDIA_ACTION_RESUME = "RESUME";
    /**
     * 停止播放操作，停止当前的Media播放
     */
    public static final String MEDIA_ACTION_STOP = "STOP";
    /**
     * 上一首播放操作
     */
    public static final String MEDIA_ACTION_previous = "PREVIOUS";
    /**
     * 下一首播放操作
     */
    public static final String MEDIA_ACTION_NEXT = "NEXT";
    private boolean disableEvent;
    private String action;
    private ResMediaItem item;

}
