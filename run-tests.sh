#!/bin/bash

echo
echo "Compiling the source code"
echo
javac *.java

if ! test -f HashtableExperiment.class; then
    echo
    echo "HashtableExperiment.class not found! Not able to test!"
    echo
    exit 1
fi

echo
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo "Running tests for word-list with varying load factors"
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo

# Set debug level
debugLevel=1

# Define load factors to test
load_factors=(0.5 0.6 0.7 0.8 0.9 0.95 0.99)

for load in "${load_factors[@]}"; do
    echo "Running java HashtableExperiment with dataSource=3, loadFactor=$load"
    java HashtableExperiment 3 $load $debugLevel

    # Ensure output files exist before proceeding
    if [[ ! -f linear-dump.txt || ! -f double-dump.txt ]]; then
        echo "Error: Dump files not generated for load=$load"
        continue
    fi

    dos2unix linear-dump.txt double-dump.txt >& /dev/null

    echo "Checking results for load factor $load..."

    # Test Linear Probing results
    if diff -w -B linear-dump.txt test-cases/word-list-$load-linear-dump.txt > diff-linear-$load.out; then
        echo " Test PASSED for Linear Probing (load=$load)"
        rm -f diff-linear-$load.out
    else
        echo " Test FAILED for Linear Probing (load=$load)!"
        echo "   Check the file diff-linear-$load.out for differences."
    fi

    # Test Double Hashing results
    if diff -w -B double-dump.txt test-cases/word-list-$load-double-dump.txt > diff-double-$load.out; then
        echo " Test PASSED for Double Hashing (load=$load)"
        rm -f diff-double-$load.out
    else
        echo " Test FAILED for Double Hashing (load=$load)!"
        echo "   Check the file diff-double-$load.out for differences."
    fi

    echo
done

echo "All tests completed!"
