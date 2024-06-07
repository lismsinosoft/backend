package com.gfk.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AvailabilityListener {
  /**
   * 基于 spring 的事件监听机制，监听系统的消息
   * 当监听到 AvailabilityChangeEvent 事件会触发此方法的调用
   * 这里使用日志记录事件的状态
   * @param event
   */
  @EventListener
  public void onStateChange(AvailabilityChangeEvent<? extends AvailabilityState> event) {
    log.info(event.getState().getClass().getSimpleName() + ": " + event.getState());
  }

}
