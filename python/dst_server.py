import subprocess

import os

# A helper method to locate the current dir.
def get_current_dir():
    this_dir = os.path.dirname(os.path.realpath(__file__))
    return this_dir

def main():
    command = [
        "java",
         "-classpath",
         os.path.join(get_current_dir(), "dst/jars/dst-server.jar"),
         "com.distkv.dst.server.DstServer"]
    subprocess.check_call(command)

if __name__ == "__main__":
    main()
