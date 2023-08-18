IMG_NAME := python-collect
CON_NAME := Python-Collect

all: clean deploy

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

deploy:
	@echo "==Deploy=="
	docker build -t $(IMG_NAME) .
	docker run -p 5000:5000 -v /pythonProject:/pythonProject -d --name $(CON_NAME) $(IMG_NAME)
