#!/usr/bin/env bash
set -ex

# Use maven to build the jar
cd /code/neural-parscit-evaluation
mvn clean compile assembly:single

# Run the jar
java -jar target/neural-parscit-evaluation-1.0-SNAPSHOT-jar-with-dependencies.jar


# Run python
cd /code
# python -u generate_metrics.py "$@"
jupyter nbconvert \
  --ExecutePreprocessor.allow_errors=True \
  --ExecutePreprocessor.timeout=-1 \
  --FilesWriter.build_directory=../results \
  --execute generate_metrics.ipynb
