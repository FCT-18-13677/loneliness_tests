mvn clean install -Dmaven-skip.test=true

scp -r target/DialogFlowTests-0.0.1-SNAPSHOT.jar senior@inti.init.uji.es:~/dialogflow_test
