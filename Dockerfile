FROM registry.codeocean.com/codeocean/miniconda3:4.8.2-python3.8-ubuntu18.04

ARG DEBIAN_FRONTEND=noninteractive

RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        default-jdk \
        maven \
    && rm -rf /var/lib/apt/lists/*

RUN conda install -y \
        scikit-learn \
    && conda clean -ya

COPY pom.xml /tmp
RUN cd /tmp && mvn package && rm -rf /tmp/target
