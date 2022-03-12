.DEFAULT_GOAL := build-run

setup:
	gradle wrapper --gradle-version 7.4

clean:
	./gradlew clean

build:
	./gradlew clean build

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain checkstyleTest

check-updates:
	./gradlew dependencyUpdates

build-run: build run

.PHONY: build
