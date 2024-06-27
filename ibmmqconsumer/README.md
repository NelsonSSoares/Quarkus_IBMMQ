# IBM CONSUMER

## Iniciando IBM MQ Consumer
Para iniciar este projeto corretamente, execute o container da mensageria IBM MQ,
e o serviço produtor de mensagens IBM Producer, para que tenha mensagens publicadas
na fila.

### 1. Baixe a imagem do IBM MQ:
```shell script
docker pull ibmcom/mq:latest
```
1.2 Rode o container docker para iniciar o IBM MQ:
```shell script
docker run --env LICENSE=accept --env MQ_QMGR_NAME=QM1 --volume qm1data:/mnt/mqm --publish 1414:1414 --publish 9443:9443 --detach --env MQ_APP_PASSWORD=passw0rd ibmcom/mq:latest
```
> **_NOTA:_** Estas variáveis são inseridas por padrão pela documentação IBM MQ, seus valores podem ser alterados, se desejar.

### 2. Verifique se o gerenciador do IBM MQ iniciou corretamente.

2.1 Abra seu navegador e acesse: https://localhost:9443/ibmmq/console/login.html

2.2 Ira aparecer um alerta, vá em avançado e depois, aceitar o risco e continuar.

2.3 utilize as credenciais default para acessar o gerenciador IBM MQ:
```
Login: admin
Senha: passw0rd
```
Site Documentação IBM MQ: https://www.ibm.com/docs/pt-br/ibm-mq

### 3. Inicie a Aplicação IBM Producer que esta no mesmo repositório desta aplicação.

3.1 Acompanhe o Readme da Aplicação IBM Producer e publique mensagens na fila.

### 4. Iniciando esta Aplicação.
```shell script
./mvnw compile quarkus:dev
```
4.1 Acompanhe o log das mensagens sendo imprimidas no terminal.
