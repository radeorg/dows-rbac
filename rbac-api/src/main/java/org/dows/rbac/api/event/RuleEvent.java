package org.dows.rbac.api.event;

import org.springframework.context.ApplicationEvent;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/2/2024 7:19 PM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
public class RuleEvent extends ApplicationEvent {
    public RuleEvent(Object source) {
        super(source);
    }
}

