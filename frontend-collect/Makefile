NPM := npm
IMG_NAME := frontend-collect
CON_NAME := Frontend-Collect
BACK_CON := Backend-Collect

all: prepare clean build test deploy end

prepare:
	@echo "==Prepare=="
	ln -sf /usr/local/node/bin/node /usr/bin/node
	rm -f ./package-lock.json

clean:
	@echo "==Clean=="
# ifneq ($(shell docker ps | grep $(BACK_CON)),)
#	 docker stop $(BACK_CON)
# endif
ifneq ($(shell docker ps | grep $(CON_NAME)),)
	docker stop $(CON_NAME)
endif
ifneq ($(shell docker ps -a | grep $(CON_NAME)),)
	docker rm $(CON_NAME)
endif
ifneq ($(shell docker images | grep $(IMG_NAME)),)
	docker rmi $(IMG_NAME)
endif

build:
	@echo "==Build=="
	$(NPM) install
	$(NPM) run build

test:
	@echo "==Test=="

deploy:
	@echo "==Deploy=="
	docker build -t $(IMG_NAME) .
	docker run -p 8082:80  -v /export/nginx/nginx.conf:/etc/nginx/nginx.conf -d --name $(CON_NAME) $(IMG_NAME)

end:
# ifeq ($(shell docker ps | grep $(BACK_CON)),)
# 	docker restart $(BACK_CON)
# endif

