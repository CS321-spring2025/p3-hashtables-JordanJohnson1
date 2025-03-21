# Project #3: Experiment with Hashing

**Author:** Jordan Johnson  
**Class:** CS321 Section 002  
**Semester:** Spring 2025

## Overview
This program evaluates Hashtable performance using open addressing with both linear probing and double hashing. It compares how different load factors affect the average number of probes required for insertion. Three types of data sources were used in testing: random integers, date objects, and strings from a word list.

## Reflection
The development journey of this project involved deep dives into hashing mechanics, including how Java’s `hashCode()` works and how probe sequences evolve under load.

Implementing the base `Hashtable` class as abstract, and then extending it for `LinearProbing` and `DoubleHashing`, went smoothly. I paid close attention to proper modulus handling, avoiding negative indices using a `positiveMod` method.

The `HashObject` class was crafted to track frequency of duplicates and the number of probes, critical for evaluating the performance of the probing schemes.

The core challenge came in matching the output formatting exactly to test specifications. Despite successful logic and implementation, minor discrepancies (like metadata, or probe counts starting at 0 instead of 1) caused test diffs to fail. These were iteratively debugged by analyzing expected vs. actual dump files.

Despite thorough debugging and test run automation via `run-tests.sh`, final test success was not achieved due to subtle formatting or runtime differences in data ingestion. I believe I am on the right track but I am currently one hour away from the final extended due date so this is what I might have to settle with.

## Compiling and Using
To compile all source files:
```bash
$ javac *.java
```

To run the main experiment class with a specific data source and load factor:
```bash
$ java HashtableExperiment <dataSource> <loadFactor> [<debugLevel>]
```
Where:
- `<dataSource>`:
  - `1` for random integers
  - `2` for date objects
  - `3` for strings from word-list.txt
- `<loadFactor>`: decimal from 0 to 1 (e.g., 0.5)
- `<debugLevel>`:
  - `0` summary only
  - `1` write linear and double hashing tables to dump files
  - `2` print insert debug logs

Example:
```bash
$ java HashtableExperiment 3 0.5 1
```

To run all automated test cases:
```bash
$ ./run-tests.sh
```

## Results
Although the output files (`linear-dump.txt` and `double-dump.txt`) are generated as expected and show clustering behavior for linear probing vs. more uniform distribution for double hashing, they failed automated diffs due to small format mismatches.

Manual inspection shows expected performance behavior:
- Double hashing consistently resulted in fewer average probes
- Clustering became more evident in linear probing as load factor increased

## Final Notes
This project was a valuable learning experience in deep systems-level debugging, formatting precision, and probe sequence analysis. Given more time I am sure I could have figured this out.

## Sources Used
Project walkthrough video
Project guide google doc
StackEdit for markdown editing.

