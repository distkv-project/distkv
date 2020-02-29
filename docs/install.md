## 1. Environment
The environment requirements of Distkv are:

- JDK >= 1.8.0
- Maven >= 3.5.0
- Python >= 3.5

## 2. Build From Source
The following steps guide you to build a full version Distkv with `dkv-server` and `dkv-cli`.

1. Clone the Distkv repository to your local.

        git clone https://github.com/distkv-project/distkv.git
    
2. Enter the root directory of Distkv.

        cd distkv

3. Install the dependencies of Distkv.
        
        bash scripts/install_dependencies.sh
        
        
4. Install Distkv.

        pip install -e deploy/python -v
        

After the 4 steps, the Distkv should be installed successfully on you local. Type `dkv-server --version` to have a check.


## 3. Run Unit Tests
If you'd like to run the unit tests of Distkv, you needn't build the full version Distkv. Just follow the following steps to build a simple version of Distkv and run the unit tests.

1. Install the dependencies of Distkv.
        
        bash scripts/install_dependencies.sh
        
2. Build with skiping tests.

        mvn clean install -DskipTests

3. If you'd like to run the tests, use this command.

        mvn test

If there is no error appearing, congratulations, you have succeeded to build Distkv and pass all tests.
