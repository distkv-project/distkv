import subprocess
import sys
import os

# A helper method to locate the current dir.
def get_current_dir():
    this_dir = os.path.dirname(os.path.realpath(__file__))
    return this_dir

def main():
    command = [
        "java",
         "-classpath",
         os.path.join(get_current_dir(), "distkv/jars/distkv-server.jar"),
         "com.distkv.server.storeserver.StoreServer"]
    try:
        subprocess.check_call(command)
    except KeyboardInterrupt as e:
        sys.exit(1)

if __name__ == "__main__":
    main()
