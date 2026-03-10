set -eu

mvn package
java -jar target/tim-challenge-1.0-SNAPSHOT.jar
