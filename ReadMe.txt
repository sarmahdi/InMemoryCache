Project Name:		InMemoryCache

Status:			Complete
Notes			Should auto run on deployment in Tomcat or Glassfish
Frameworks/API:		Junit.
IDE:			NetBeans IDE
Application Servers	
JDK:			Java 7

This is an simple implementation of an In Memory Cache using a ConcurrentMap. The project is built through maven.

There is a test harness to test the functionailty of the cache. The testcases are written in Junit 4.11

The project is built through maven 3.0.4, so when running it first time the dependencies might be downloaded. The only external dependencies needed are Junit 4.1.1 and Log4j 1.2.15.

to compile and run the tests, on the project root, just run

$PROJECT_ROOT>mvn clean install

