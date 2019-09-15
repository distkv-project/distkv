#include "launcher.h"

#include <unistd.h>
#include <vector>
#include <string>

static std::string GetThisBinaryPath() {
  char buffer[500];
  if (nullptr == getcwd(buffer, 500)) {
    return std::string();
  }

  return std::string(buffer);
}

int main() {
  std::vector<std::string> server_command{
      "java",
      "-classpath",
      GetThisBinaryPath() + "/dst-client-0.1.0-SNAPSHOT-jar-with-dependencies.jar",
      "org.dst.client.cmd.DstCmdStarter"};

  dst::Launcher launcher(server_command);
  launcher.execute();

  return 0;
}
