all: build run

build:
	docker-compose build motivator

start: run

run:
	docker-compose -p portal up -d motivator

stop:
	docker-compose -p portal kill

destroy:
	docker-compose -p portal down

logs:
	docker-compose -p portal logs motivator

root:
	docker-compose -p portal exec motivator bash

status:
	docker-compose -p portal ps

