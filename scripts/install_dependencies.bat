:: Install drpc.

SET DRPC_REPOSITORY_URL="https://github.com/dst-project/drpc.git"
SET DRPC_COMMIT_ID="dbc184dd8a892cc485bfc5aae5f6a21884350c9d"

git clone %DRPC_REPOSITORY_URL% drpc_tmp
pushd drpc_tmp
git checkout %DRPC_COMMIT_ID%
mvn clean install -DskipTests
popd

echo Drpc was installed successfully.
echo All dependencies were installed successfully.

pause
