package com.gfk.business.common.actuator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//metrics: http://localhost:21013/api/actuator/prometheus
// 健康检测：http://localhost:21013/api/actuator/health/readiness、http://localhost:21013/api/actuator/health/liveness

@RestController
@RequestMapping("/actuator")
public class StateController {

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  private ConfigRead configRead;

  /**
   * 将存活状态改为 BROKEN
   * 这会导致 k8s 杀死 pod，并根据重启策略重启 pod
   *
   * @return
   */
  @GetMapping("broken")
  public String broken() {
    AvailabilityChangeEvent.publish(applicationEventPublisher, this, LivenessState.BROKEN);
    return "success broken, " + new Date();
  }

  /**
   * 将存活状态修改为 correct
   * @return
   */
  @GetMapping("correct")
  public String correct() {
    AvailabilityChangeEvent.publish(applicationEventPublisher, this, LivenessState.CORRECT);
    return "success correct, " + new Date();
  }

  /**
   * 将就绪状态修改为 ACCEPTING_TRAFFIC (接受流量)
   * k8s 会将外部请求转发到此 pod
   * @return
   */
  @GetMapping("accept")
  public String accept() {
    AvailabilityChangeEvent.publish(applicationEventPublisher, this, ReadinessState.ACCEPTING_TRAFFIC);
    return "success accept, " + new Date();
  }

  /**
   * 将就绪状态修改为 REFUSING_TRAFFIC
   * k8s 通过将 service 对应的后端 endpoint 中此 pod 的ip移除来拒绝外部请求
   * @return
   */
  @GetMapping("refuse")
  public String refuse() {
    AvailabilityChangeEvent.publish(applicationEventPublisher, this, ReadinessState.REFUSING_TRAFFIC);
    return "success refuse, " + new Date();
  }

  @GetMapping("configmap")
  public Map<String, Object> getConfig() {
    Map<String, Object> map = new HashMap<>();
    map.put("StringValue:", configRead.getStringValue());
    map.put("NumberValue:", configRead.getNumberValue());
    map.put("BooleanValue:", configRead.isBooleanValue());
    return map;
  }


}
