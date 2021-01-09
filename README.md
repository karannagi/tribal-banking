# tribal-banking-service
Steps to run in Docker
git clone https://github.com/karannagi/tribal-banking-service.git
cd tribal-banking-service
mvn clean install
docker build -t tribal/banking .
docker run -d -p 8080:8080 tribal/banking
