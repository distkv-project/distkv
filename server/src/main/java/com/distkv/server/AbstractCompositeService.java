package com.distkv.server;

import java.util.ArrayList;
import java.util.List;

/**
 *　Composition of services.
 */
public abstract class AbstractCompositeService extends AbstractService {

  private static final List<Service> serviceList = new ArrayList<>();

  public AbstractCompositeService(String serviceName) {
    super(serviceName);
  }
  
  public void addService(Service service) {
    serviceList.add(service);
  }

  public void removeService(Service service) {
    serviceList.remove(service);
  }

  @Override
  public void config() {
    for (Service service : serviceList) {
      service.config();
    }
    super.config();
  }

  @Override
  public void run() {
    for (Service service : serviceList) {
      service.run();
    }
    super.run();
  }

  @Override
  public void stop() {
    for (Service service : serviceList) {
      service.stop();
    }
    super.stop();
  }
}
