MVN  := mvn
MVN_ARG := -P prod
MVN_TEST_ARG := -P test
IMG_NAME := backend-collect
CON_NAME := Backend-Collect

all: clean build test deploy

clean:
	@echo "==Clean=="
ifneq ($(shell docker ps | grep $(CON_NAME)),)
	docker stop $(CON_NAME)
endif
ifneq ($(shell docker ps -a | grep $(CON_NAME)),)
	docker rm $(CON_NAME)
endif
ifneq ($(shell docker images | grep $(IMG_NAME)),)
	docker rmi $(IMG_NAME)
endif
	$(MVN) clean $(MVN_ARG)

build:
	@echo "==Build=="
	$(MVN) package $(MVN_ARG) -DskipTests

test:
	@echo "==Test=="
	$(MVN) test $(MVN_TEST_ARG)

deploy:
	@echo "==Deploy=="
	docker build -t $(IMG_NAME) .
	docker run -d -p 8080:8080 -v /etc/localtime:/etc/localtime --name $(CON_NAME) $(IMG_NAME)
