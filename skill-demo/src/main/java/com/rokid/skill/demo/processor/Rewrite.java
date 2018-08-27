package com.rokid.skill.demo.processor;

import com.rokid.skill.kit4j.core.AbstractRewrite;
import com.rokid.skill.protocol.request.request.content.slot.Slot;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * intent 及 slot 改写功能，高级特性，不使用可忽略
 *
 * @author wuyukai on 2018/7/8.
 */
@Component
public class Rewrite extends AbstractRewrite {


    @Override
    public String rewriteAction(String originalActionName) {
        return null;
    }

    @Override
    public Map<String, Slot> rewriteSlots(Map<String, Slot> originalSlots) {
        return null;
    }

    @Override
    public boolean support() {
        return false;
    }

   /* @Override
    public String rewriteAction(String originalActionName) {
        if ("hello".equals(originalActionName)) {
            return "eat";
        }
        return originalActionName;
    }


    @Override
    public Map<String, Slot> rewriteSlots(Map<String, Slot> originalSlots) {
        Slot slot = originalSlots.get("hello");
        slot.setValue("嘿嘿");
        originalSlots.put("hello", slot);
        return originalSlots;
    }

    @Override
    public boolean support() {
        return true;
    }*/
}
