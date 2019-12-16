:: Install drpc.

SET DRPC_REPOSITORY_URL="https://github.com/dst-project/drpc.git"
SET DRPC_COMMIT_ID="c9bfaa54db11af01df0645beaca560f74fbf93d2"

git clone %DRPC_REPOSITORY_URL% drpc_tmp
pushd drpc_tmp
git checkout %DRPC_COMMIT_ID%
mvn clean install -DskipTests
popd

echo Drpc was installed successfully.
echo All dependencies were installed successfully.

pause
