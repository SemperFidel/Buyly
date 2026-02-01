CERT_NAME = ktorapp.local
CERT_KEY = $(CERTS_DIR)/$(CERT_NAME).key
CERT_CRT = $(CERTS_DIR)/$(CERT_NAME).crt
DOCKER_COMPOSE_FILE = docker-compose.yml

.PHONY: all prepare-project build-docker-compose clean

all: prepare-project build-docker- update-repo

prepare-project: $(CERT_KEY) $(CERT_CRT)

$(CERT_KEY):
	@echo "Generating private key..."
	openssl genpkey -algorithm RSA -out $(CERT_KEY) -pkeyopt rsa_keygen_bits:2048

$(CERT_CRT): $(CERT_KEY)
	@echo "Generating self-signed certificate..."
	openssl req -x509 -new -key $(CERT_KEY) -out $(CERT_CRT) -days 365 \
		-subj "/C=US/ST=State/L=City/O=Company/OU=IT/CN=ktorapp.local"

build-docker-:
	@echo "Building and starting containers with Docker Compose..."
	docker-compose -f $(DOCKER_COMPOSE_FILE) up --build -d

.PHONY: update-repo

update-repo:
	@echo "Введите сообщение для коммита:"
	@read COMMIT_MESSAGE; \
	echo "Введите название ветки:"; \
	read BRANCH_NAME; \
	git add --all; \
	git commit -m "$$COMMIT_MESSAGE"; \
	git push origin $$BRANCH_NAME

