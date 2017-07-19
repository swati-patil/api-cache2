# api-cache2

1. There are 2 parts to the projects and code base - reading git api data and caching a subset of apis and generating view on cached data. All the urls directed to git api use app context gitapi/netflixgit and all the view urls use repoviews/cache.
2. Enviornment variable used - GITHUB_API_TOKEN
3. It is a maven project. To generate a war, use command - mvn clean install.
4. pom.xml are located - gitapi/pom.xml and repoviews/pom.xml
5. Generated war's are locataed at - gitapi/target/gitapi-0.0.1-SNAPSHOT.war and repoviews/target/repoview-0.0.1-SNAPSHOT.war
6. Sample urls - 
http://localhost:8080/gitapi/netflixgit/healthcheck
http://localhost:8080/gitapi/netflixgit/orgs/Netflix/members
http://localhost:8080/repoview/cache/view/top/10/forks


Things I could have done better - 
1. memcahced is used as standard installation. Code doesn't deal with settings at all. It uses TTL of 3600. Memcached is assumed to be running on default port 11211.
2. Code could have been structured better.
3. I could have all the rest apis as a single service.
4. The code handles very basic error handling. IT could have been better.

The code has been developed using java+jersey+memcached. For memcache, spymemcached.jar has been used. 
The code has been tested on tomcat. 
