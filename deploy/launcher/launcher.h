#ifndef LAUNCHER_LAUNCHER_H
#define LAUNCHER_LAUNCHER_H

#include <sys/wait.h>
#include <unistd.h>

#include <vector>
#include <string>
#include <iostream>

namespace dst {

  class Launcher {
   public:
   explicit Launcher(std::vector<std::string> command): command_(std::move(command)){ }

   void execute() {
     pid_t pid = fork();

     if (pid != 0) {
       return;
     }

     signal(SIGCHLD, SIG_DFL);
     // Try to execute the command.
     std::vector<const char *> command_args_str;
     for (const auto &arg : command_) {
       command_args_str.push_back(arg.c_str());
     }
     command_args_str.push_back(nullptr);
     int rv = execvp(command_args_str[0],
                     const_cast<char *const *>(command_args_str.data()));

     // The process failed to start. This is a fatal error.
     std::cout << "Failed to run command." << std::endl;
     exit(2);
   }

   private:
    std::vector<std::string> command_;

  };

}  // namespace dst

#endif //LAUNCHER_LAUNCHER_H
