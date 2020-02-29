:: Install dousi RPC.

SET DOUSI_REPOSITORY_URL="https://github.com/jovany-wang/dousi.git"
SET DOUSI_COMMIT_ID="28a386f3c40f7ab06a38e8cac84193c14dfeef8d"

git clone %DOUSI_REPOSITORY_URL% dousi_tmp
pushd dousi_tmp
git checkout %DOUSI_COMMIT_ID%
mvn clean install -DskipTests
popd
rd /S /Q dousi_tmp

echo Dousi RPC was installed successfully.
echo All dependencies were installed successfully.

pause
