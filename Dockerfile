FROM registry.codeocean.com/codeocean/miniconda3:4.8.2-python3.8-ubuntu18.04

ARG DEBIAN_FRONTEND=noninteractive

RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        default-jdk=2:1.11-68ubuntu1~18.04.1 \
        maven=3.6.0-1~18.04.1 \
    && rm -rf /var/lib/apt/lists/*

RUN conda install -y \
        jupyter==1.0.0 \
        scikit-learn==0.23.1 \
    && conda clean -ya

COPY postInstall /
RUN /postInstall