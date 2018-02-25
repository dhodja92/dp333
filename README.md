# dp333 - Daily Programmer 333 challenge
[Daily Programmer #333 challenge](https://www.reddit.com/r/dailyprogrammer/comments/739j8c/20170929_challenge_333_hard_build_a_web_apidriven/) solution

## Intent
To import and display the data as required in the challenge. It should be possible to filter voter entries by county, month, party affiliation and active status. Also, it should be possible to limit the number of entries displayed.

## Usage
Firstly, the JSON file containing the voters data should be downloaded from [here](https://data.iowa.gov/api/views/cp55-uurs/rows.json?accessType=DOWNLOAD) and then added to the classpath (specifically, the `src/main/resources` folder).

Build the project with Maven using `mvn clean install`, and then run the executable .jar file through the command line using the `java -jar path-to-jar` command.

There are two ways to access the data: `get_voters_where` (manual query building), and `get_voters_where_querydsl` (QueryDsl implementation).
Both URLs support these query parameters:
* county - `county` query parameter
* month  - `month` query parameter
* party affiliation - `party` query parameter
* active status - `active` query parameter
* limit entries - `limit` query parameter.

## Tools used
* Spring Boot
* Spring Data JPA
* QueryDsl
* Hibernate
* Thymeleaf template engine
* Postgres RDBMS
* Maven