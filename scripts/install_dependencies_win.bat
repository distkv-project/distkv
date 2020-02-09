:: Install dousi RPC.

SET DOUSI_REPOSITORY_URL="https://github.com/jovany-wang/dousi.git"
SET DOUSI_COMMIT_ID="aa414eda5b6f9a4dafc2c76a7bc999866f3d9d5f"

git clone %DOUSI_REPOSITORY_URL% dousi_tmp
pushd dousi_tmp
git checkout %DOUSI_COMMIT_ID%
mvn clean install -DskipTests
popd
rd /S /Q dousi_tmp

echo Dousi RPC was installed successfully.
echo All dependencies were installed successfully.

pause
