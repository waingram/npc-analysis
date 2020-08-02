# NPC Analysis

[![License](https://img.shields.io/badge/License-BSD%203--Clause-blue.svg)](https://github.com/waingram/npc-analysis/raw/master/LICENSE)

* William A. Ingram (Virginia Tech Univeristy Libraries)

Simple scripts to analyize the performace of [Neural ParsCit](https://github.com/WING-NUS/Neural-ParsCit) (NPC), a citation string parser developed by the [Web Information Retrieval / Natural Language Processing Group (WING)](https://wing.comp.nus.edu.sg/) at the National University of Singapore. 

## Dependencies

The analysis code is divided into two parts: the first is a Java program which compares the output of NPC with our gold standard data and generates a report. The second piece is a Python script to generate metrics based on the output of the comparison. 

To run the code you'll need:
- Java JDK > v.8
- Maven
- Python > v.3
- scikit-learn.metrics 

## Data

The `data` director contains a set of 100 gold standard annotated citation strings, along with the output of (an early version of) NPC applied to the 100 citation strings. 

## LICENSE

**The material in this repository is shared under multiple licenses.** The Java and Python code is shared under a BSD 3-Clause License.
The data used for analysis is shared under a Creative Commons Attribution (CC-BY) license.
