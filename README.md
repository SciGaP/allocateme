Setup
======

1. `git clone https://github.com/SciGaP/allocateme`

2. Install maven with `brew install maven` (for Mac) or 'sudo apt-get install maven' (for Debian/Ubuntu Linux)

3. `cd allocate/allocate`. (You must be in a directory with `pom.xml` to compile with maven).

4. `mvn package`

5. `java -cp target/allocate-1.0-SNAPSHOT.jar org.apache.airavata.App`
