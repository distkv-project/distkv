package com.distkv.server;

import java.io.Closeable;

/**
 * Service LifeCycle.
 *
 */
public interface Service extends Closeable {

  enum ServiceStatus {
    UNINITED(0, "UNINITED"),
    INITED(1, "INITED"),
    RUNNING(2, "RUNNING"),
    STOPPED(3, "STOPPED");

    public final String stateName;
    public final int value;

    ServiceStatus(int value, String stateName) {
      this.stateName = stateName;
      this.value = value;
    }
  }

  /**
   * Get the service name.
   * @return service name
   */
  String getName();

  ServiceStatus getStatus();

  /**
   * apply the configuration and init service
   */
  void config();

  /**
   * run the service
   */
  void run();

  /**
   * stop the service.
   */
  void stop();
}
