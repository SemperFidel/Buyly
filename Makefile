.PHONY: update-repo docker-deploy

update-repo:
	@echo "Введите сообщение для коммита:"
	@read COMMIT_MESSAGE; \
	echo "Введите название ветки:"; \
	read BRANCH_NAME; \
	git add --all; \
	git commit -m "$$COMMIT_MESSAGE"; \
	git push origin $$BRANCH_NAME

docker-deploy:
	docker compose up -d