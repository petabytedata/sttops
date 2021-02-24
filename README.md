# Setting up development environment

####Steps:
- Navigate to project home directory.
- cd deployment
- Run "docker-compose up -d"

Above steps will setup local minio server and postgres db locally. 

Now navigate back to project home directory and run "mvn clean install". Then just run the command below:
- "java -jar target/stt-ops-0.0.1-SNAPSHOT.jar" 
This will start sttops service

You will also need to build and run mock service in order to get positive response("Hi Siri") from upstream service, otherwise
you will only get to see fallback method's response in the DB.

To run the mock service:
- Navigate to home directory of mock service
- Run "mvn clean install"
- Run java -jar target/mock-0.0.1-SNAPSHOT.jar  
  


>Test File upload
 - curl --location --request POST 'http://localhost:8081/api/v1/upload' \
--form 'file=@"/home/amit/voice.mp4"'

Open link http://172.21.0.5:9000/minio , with credentials
- minio/minio123
and check the uploaded file in bucket.

Also open DB table stt_results to see the response details being persisted.