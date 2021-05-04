FROM bitriseio/docker-android

RUN mkdir android
COPY . /bitrise/src/android

WORKDIR /bitrise/src/android
