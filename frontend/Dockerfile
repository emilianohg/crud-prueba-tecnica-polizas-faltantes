# syntax=docker/dockerfile:1.4

FROM node:18-bullseye-slim as builder

RUN <<EOF
apt-get update
apt-get install -y --no-install-recommends git
EOF

RUN <<EOF
useradd -s /bin/bash -m vscode
groupadd docker
usermod -aG docker vscode
EOF

RUN mkdir /project
WORKDIR /project

RUN npm install -g @angular/cli@15


# install Docker tools (cli, buildx, compose)
COPY --from=gloursdocker/docker / /