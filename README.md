# project-spring
## Endpoint de Serviços

Este endpoint oferece uma série de serviços relacionados ao gerenciamento de status. Abaixo estão listados os serviços disponíveis e suas respectivas funcionalidades:

### 1. Obter Todos os Status

**Método:** GET  
**Endpoint:** `/v1/api/status`  
**Descrição:** Retorna todos os status disponíveis.  
**Retorno:** Uma lista de objetos JSON contendo o estado e o status correspondente.

### 2. Obter Status por Estado

**Método:** GET  
**Endpoint:** `/v1/api/{estado}`  
**Descrição:** Retorna todos os status relacionados a um estado específico.  
**Parâmetros de URL:**
- `{estado}`: O estado pelo qual se deseja filtrar os status.  
  **Retorno:** Uma lista de objetos contendo o estado e o status correspondente.

### 3. Obter Status por Data

**Método:** GET  
**Endpoint:** `/v1/api/data`  
**Descrição:** Retorna todos os status registrados em uma data específica.  
**Parâmetros de Consulta:**
- `data`: A data pela qual se deseja filtrar os status.  
  **Retorno:** Uma lista de objetos contendo o estado e o status correspondente.

### 4. Obter Estado com Mais Alertas e Negativos

**Método:** GET  
**Endpoint:** `/v1/api/most-alerts-and-negatives`  
**Descrição:** Retorna o estado que possui o maior número de alertas e status negativos.  
**Retorno:** Um objeto JSON contendo o estado e a contagem correspondente.

### 5. Criar Novos Status

**Método:** POST  
**Endpoint:** `/v1/api/create`  
**Descrição:** Cria novos registros de status de 5 em 5 minutos com base nos valores obtidos de uma fonte externa.  
**Retorno:**
- Código de status HTTP 201 (Criado) se os registros forem criados com sucesso.
- Código de status HTTP 500 (Erro do Servidor Interno) se ocorrer um erro ao processar a criação dos registros.

### Observações

- Todos os serviços retornam dados em formato JSON.
- Os dados de entrada e saída estão no formato especificado na descrição de cada serviço.
- Certifique-se de fornecer os parâmetros necessários para os serviços que os exigem, como data e estado.

Esse arquivo README oferece uma visão geral dos serviços disponíveis neste endpoint e deve ser consultado para entender melhor como usar cada serviço.