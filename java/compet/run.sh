set -eu

echo "##########################################################"
ls -alh src/main/java/com/timit/compet/Player.java | awk '{print "##  " $5 "   " $9 "   ##"}'
echo "##########################################################"

mvn clean spotless:apply package
java -jar target/tim-challenge-1.0-SNAPSHOT.jar < "seeds/seed_-974398609313948000.txt"
