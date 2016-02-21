Setup
======

1. `git clone https://github.com/SciGaP/allocateme`

2. Install maven with `brew install maven` (for Mac) or 'sudo apt-get install maven' (for Debian/Ubuntu Linux)

3. `cd allocate/allocate`. (You must be in a directory with `pom.xml` to compile with maven).

4. `mvn package`

5. `java -cp target/allocate-1.0-SNAPSHOT.jar org.apache.airavata.App`


Sample
======

```
{ "_id" : ObjectId("56c92f16b72c1e16ac1c9842"), "user" : { "name" : "Matthew Caesar", "primaryEmail" : "sameet.sapra@gmail.com" }, "publications" : [ { "num_citations" : 420, "name" : "Virtual ring routing: network routing inspired by DHTs" }, { "num_citations" : 406, "name" : "Design and implementation of a routing control platform" }, { "num_citations" : 368, "name" : "ROFL: routing on flat labels" }, { "num_citations" : 316, "name" : "Floodless in seattle: a scalable ethernet architecture for large enterprises" }, { "num_citations" : 276, "name" : "Veriflow: Verifying network-wide invariants in real time" }, { "num_citations" : 250, "name" : "BGP routing policies in ISP networks" }, { "num_citations" : 216, "name" : "[BOOK][B] HLP: a next generation inter-domain routing protocol" }, { "num_citations" : 174, "name" : "Debugging the data plane with anteater" }, { "num_citations" : 168, "name" : "Finishing flows quickly with preemptive scheduling" }, { "num_citations" : 157, "name" : "[PDF][PDF] BotGrep: Finding P2P Bots with Structured Graph Analysis." }, { "num_citations" : 151, "name" : "Achieving convergence-free routing using failure-carrying packets" }, { "num_citations" : 115, "name" : "The SAHARA model for service composition across multiple providers" }, { "num_citations" : 69, "name" : "Cirripede: circumvention infrastructure using router redirection with plausible deniability" }, { "num_citations" : 65, "name" : "Revisiting route caching: The world should be flat" }, { "num_citations" : 56, "name" : "[CITATION][C] The Tangled Web of Password Reuse." }, { "num_citations" : 55, "name" : "[BOOK][B] Towards localizing root causes of BGP dynamics" }, { "num_citations" : 51, "name" : "Walk the line: consistent network updates with bandwidth guarantees" }, { "num_citations" : 43, "name" : "Stealthy traffic analysis of low-latency anonymous communication using throughput fingerprinting" }, { "num_citations" : 40, "name" : "Dynamic route recomputation considered harmful" }, { "num_citations" : 5332, "name" : "Genome-wide association study of 14,000 cases of seven common diseases and 3,000 shared controls" } ], "institution" : { "verified" : false }, "tier" : 1, "funding" : null }
```
