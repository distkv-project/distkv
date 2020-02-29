package com.distkv.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractService implements Service {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);

  private static final boolean[][] stateMap =
      {
          //                uninited inited running stopped
          /* uninited  */    {false, true,  false,  true},
          /* inited    */    {false, false,  true,   true},
          /* running   */    {false, false, true,   true},
          /* stopped   */    {false, false, false,  true},
      };

  private boolean isInStates(ServiceStatus status) {
    return this.status.equals(status);
  }

  private boolean canEnterStatus(ServiceStatus status) {
    return stateMap[this.status.value][status.value];
  }

  private final String serviceName;
  private ServiceStatus status = ServiceStatus.UNINITED;
  private final byte[] statusLock = new byte[0];




  public AbstractService(String serviceName) {
    this.serviceName = serviceName;
  }

  @Override
  public String getName() {
    return serviceName;
  }

  @Override
  public ServiceStatus getStatus() {
    return this.status;
  }

  @Override
  public void config() {
    if (canEnterStatus(ServiceStatus.INITED)) {
      synchronized (statusLock) {
        LOGGER.debug("init service {}", getName());
        serviceInit();
        this.status = ServiceStatus.INITED;
      }
    }
  }

  public abstract void serviceInit();

  @Override
  public void run() {
    if (canEnterStatus(ServiceStatus.RUNNING)) {
      synchronized (statusLock) {
        LOGGER.debug("run service {}", getName());
        serviceRun();
        this.status = ServiceStatus.RUNNING;
      }
    }
  }

  protected abstract void serviceRun();

  @Override
  public void stop() {
    if (canEnterStatus(ServiceStatus.STOPPED)) {
      synchronized (statusLock) {
        LOGGER.debug("stop service {}", getName());
        serviceStop();
        this.status = ServiceStatus.STOPPED;
      }
    }
  }

  protected abstract void serviceStop();

  @Override
  public void close() {
    // do nothing now.
  }

}
