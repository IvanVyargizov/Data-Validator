.DEFAULT_GOAL := build-run

setup:
	gradle wrapper --gradle-version 7.0.1

clean:
	./gradlew clean

build:
	./gradlew clean build

install:
	./gradlew clean install

test:
	./gradlew test

lint:
	./gradlew checkstyleMain checkstyleTest

check-updates:
	./gradlew dependencyUpdates

build-run: build run

.PHONY: build
