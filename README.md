Run :
> docker-compose up
- This will start Minio and postgres db

>Test File upload
 - curl --location --request POST 'http://localhost:8081/api/v1/upload' \
--form 'file=@"/home/amit/voice.mp4"'

>Couldnt use GCP, as my card was not allowing recurring deduction. Instead, using one mock service, getting called as hystrix command.