# telikos-redis-library

Step1: Import this as a maven jar dependency in any of the applications where caching is required
Step2: Create a class which extends CacheImpl from telikos-redis-library similar to Repository
Step3: Autowire the class created by extending CacheImpl in the consumer service
Step4: Call get and put methods of CacheImpl class from telikos-redis-library

 
## The library was created to expose 3 mothods ... Those are

Get method to read the data from redis cache based on the key provided from consumer service

Two overloaded Put methods for writing the data into redis cache

## TTL configuration

Method1: If TTL is not provided in consumer application.yml file, default TTL will be considered from library

Method2: If TTL is provided in consumer application.yml file, then default TTL will not be considered from library that will be overridden

Method3: Entry level TTL can be configured through overloaded method
