set -eu

mvn package
java -jar target/tim-challenge-1.0-SNAPSHOT.jar < "seeds/seed_-974398609313948000.txt"
