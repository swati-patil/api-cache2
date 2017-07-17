# api-cache2

1. There are 2 parts to the projects and code base - reading git api data and caching a subset of apis and generating view on cached data. All the urls directed to git api use app context NetflixCacheGitAPI/netflixgit and all the view urls use RepositoryViews/repoviews.
2. It is a maven project. To generate a war, use command - mvn clean install.
3. pom.xml are located - NetflixCacheGitAPI/pom.xml and RepositoryViews/pom.xml
4. Generated war's are locataed at - NetflixCacheGitAPI/target/NetflixCacheGitAPI-0.0.1-SNAPSHOT.war and RepositoyViews/target/RepositoryViews-0.0.1-SNAPSHOT.war
2. Sample urls - 
http://localhost:8080/NetflixCacheGitAPI/netflixgit/healthcheck
http://localhost:8080/NetflixCacheGitAPI/netflixgit/orgs/Netflix/members
http://localhost:8080/RepositoryViews/repoviews/view/top/10/forks


Things I could have done better - 
1. memcahced is used as standard installation. Code doesn't deal with settings at all. It uses TTL of 3600.
2. Code could have been structured better.
3. I could have all the rest apis as a single service.
4. The code handles very basic error handling. IT could have been better.

The code has been developed using java+jersey+memcached. For memcache, java jar spymemcached.jar has been used. 
The code has been tested on tomcat. 
