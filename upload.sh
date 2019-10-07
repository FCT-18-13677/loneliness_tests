mvn clean install -Dmaven.test.skip=true

scp -r target/DialogFlowTests-0.0.1-SNAPSHOT.jar senior@inti.init.uji.es:~/dialogflow_test
