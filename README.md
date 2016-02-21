Purpose
======
The purpose of this project was to develop a backend system that could handle a user's requests for CPU cores by intelligently querying online sources to assess a user's credibility.

Future Goals
======

- Convert JSONObjects to Thrift models
- Transfer main algorithm to Thrift endpoints

Setup
======

1. `git clone https://github.com/SciGaP/allocateme`

2. Install maven with `brew install maven` (for Mac) or `sudo apt-get install maven` (for Debian/Ubuntu Linux)

3. `cd allocate/allocate`. (You must be in a directory with `pom.xml` to compile with maven).

4. `mvn package`

5. `java -cp target/allocate-1.0-SNAPSHOT.jar org.apache.airavata.App`


Sample Data
======
<code>
{ "_id" : ObjectId("56c9c5b80eec3c068d68a94c"), "user" : { "name" : "Albert Einstein", "primaryEmail" : "alberteinstein@princeton.edu" }, "publications" : [ { "num_citations" : 14144, "name" : "Can quantum-mechanical description of physical reality be considered complete?" }, { "num_citations" : 3679, "name" : "Investigations on the Theory of the Brownian Movement" }, { "num_citations" : 1993, "name" : "The evolution of physics: The growth of ideas from early concepts to relativity and quanta" }, { "num_citations" : 2665, "name" : "The meaning of relativity: Including the relativistic theory of the non-symmetric field" }, { "num_citations" : 2187, "name" : "Relativity: The special and general theory" }, { "num_citations" : 1483, "name" : "Ideas and opinions" }, { "num_citations" : 1284, "name" : "The theory of the brownian movement" }, { "num_citations" : 2103, "name" : "The bed-load function for sediment transportation in open channel flows" }, { "num_citations" : 1523, "name" : "Erklarung der Perihelionbewegung der Merkur aus der allgemeinen Relativitatstheorie" }, { "num_citations" : 551, "name" : "The collected papers of Albert Einstein" }, { "num_citations" : 1111, "name" : "The principle of relativity" }, { "num_citations" : 909, "name" : "On the movement of small particles suspended in a stationary liquid demanded by the molecular-kinetic theory of heart" }, { "num_citations" : 766, "name" : "Ist die Trägheit eines Körpers von seinem Energieinhalt abhängig?" }, { "num_citations" : 826, "name" : "The gravitational equations and the problem of motion" }, { "num_citations" : 822, "name" : "On the electrodynamics of moving bodie" }, { "num_citations" : 300, "name" : "Albert Einstein, creator and rebel" }, { "num_citations" : 650, "name" : "Lens-like action of a star by the deviation of light in the gravitational field" }, { "num_citations" : 569, "name" : "Why war" }, { "num_citations" : 505, "name" : "On the method of theoretical physics" }, { "num_citations" : 616, "name" : "The world as I see it" } ], "institution" : { "verified" : true }, "tier" : 3, "funding" : { "award" : [ { "id" : "1052893", "piLastName" : "Kukuk", "title" : "Indigenous Women in Science Network (IWSN) Third Annual Meeting", "awardeeName" : "University of Montana", "piFirstName" : "Penelope", "awardeeStateCode" : "MT", "agency" : "NSF", "date" : "09/20/2010", "fundsObligatedAmt" : "99911", "publicAccessMandate" : "0", "awardeeCity" : "Missoula" } ] } }
</code>
