package com.distkv.supplier;

import com.distkv.server.storeserver.StoreServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestWrapper {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestWrapper.class);

  private static final int KILL_PROCESS_WAIT_TIMEOUT_SECONDS = 1;

  private static Process process = null;

  private static int serverPort = -1;

  public static void startRpcServer(int port) {
    try {
      serverPort = port;
      Method method = StoreServer.class.getMethod("main", String[].class);
      method.invoke(null, (Object) new String[]{String.valueOf(serverPort)});
    } catch (Exception e) {
      LOGGER.error("Failed to start StoreServer. {1} ", e);
    }
  }

  public static void stopProcess() {
    if (OSinfo.isMacOS()) {
      killForMac(serverPort);
    } else if (OSinfo.isLinux()) {
      killForLinux(serverPort);
    } else if (OSinfo.isWindows()) {
      killForWin(serverPort);
    } else {
      LOGGER.warn("Failed to stop rpc server.");
      System.exit(-1);
    }
    int numAttempts = 0;
    while (process.isAlive()) {
      if (numAttempts > 0) {
        LOGGER.warn("Attempting to kill store server, numAttempts={}.", numAttempts);
      }
      if (numAttempts == 0) {
        process.destroy();
      } else {
        process.destroyForcibly();
      }
      ++numAttempts;
      try {
        process.waitFor(KILL_PROCESS_WAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
        LOGGER.error("Failed to stop store server. This process is exiting.");
        System.exit(-1);
      }
    }
  }

  public static void killForMac(int port) {
    String pid = null;
    try {
      process = Runtime.getRuntime().exec("lsof -i :" + port);
      try (BufferedReader reader = new BufferedReader(
          new InputStreamReader(process.getInputStream()))) {
        String line = null;
        while ((line = reader.readLine()) != null) {
          String[] strs = line.split("\\s+");
          if ("java".equals(strs[0])) {
            pid = strs[1];
          }
        }
      }
      process = Runtime.getRuntime().exec("kill -9  " + pid);
    } catch (IOException e) {
      LOGGER.error("Failed to kill process of StoreServer. ");
    }
  }

  public static void killForWin(int port) {
    String pid = null;
    try {
      process = Runtime.getRuntime().exec("cmd /c netstat -ano |findstr  \"" + port + "\"");
      try (BufferedReader reader = new BufferedReader(
          new InputStreamReader(process.getInputStream()))) {
        String line = null;
        while ((line = reader.readLine()) != null) {
          String[] strs = line.split("\\s+");
          pid = strs[5];
        }
      }
      process = Runtime.getRuntime().exec("taskkill /F /pid " + pid + "");
    } catch (IOException e) {
      LOGGER.error("Failed to kill process of StoreServer. ");
    }
  }

  public static void killForLinux(int port) {
    String pid = null;
    try {
      process = Runtime.getRuntime().exec("netstat -apn | grep :" + port);
      try (BufferedReader reader = new BufferedReader(
          new InputStreamReader(process.getInputStream()))) {
        String line = null;
        while ((line = reader.readLine()) != null) {
          String[] strs = line.split("\\s+");
          pid = strs[5].replace("/java", "");
        }
      }
      process = Runtime.getRuntime().exec("kill -9  " + pid);
    } catch (IOException e) {
      LOGGER.error("Failed to kill process of StoreServer. ");
    }
  }

  public static class OSinfo {

    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isLinux() {
      return OS.contains("linux");
    }

    public static boolean isMacOS() {
      return OS.contains("mac");
    }

    public static boolean isWindows() {
      return OS.contains("windows");
    }
  }
}
