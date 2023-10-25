
# API Exames Consultas {api-exames-consultas}

Esta aplicação tem como objetivo o gerenciamento dos exames dos pacientes, os recursos se destinam a manutenção do prontuário no que se refere a gravação de novos exames, alterações, atualizações e listagens.


## Documentação da API

#### Faz download de um exame

```http
  GET /api-exames-consultas/v1/exames/download/${filename}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `filename`      | `string` | **Obrigatório**. Deve ser informado o nome gerado para o arquivo seguido da extensão ex: 00221-teste.PDF |

Recebe o nome do arquivo pega e devolve para download.

#### Lista todos os exames do paciente

```http
  GET /api-exames-consultas/v1/exames/prontuario/${cpf}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `cpf`      | `string` | **Obrigatório**. Deve ser informado somente o cpf sem digitos ou traçoes ex: 00022211156 |

Recebe um cpf e retorna lista com todos os exames vinculados ao cpf.

#### Lista todos os exames salvos no servidor

```http
  GET /api-exames-consultas/v1/exames/list
```

 **Sem necessidade de parametros**. Não necessita passar nenhum parâmetro.

Retorna uma lista com o nome de todos os exames salvos no servidor.


#### Grava um exame ou documento no prontuário do paciente.

```http
  POST /api-exames-consultas/v1/upload
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `File`      | `form` | **Obrigatório**. 	Deve ser informado o caminho local onde está o arquivo que deverá ser gravado no prontuário do paciente. |
| `C.P.F`      | `string` | **Obrigatório**. Deve ser informado somente o cpf sem digitos ou traçoes ex: 00022211156|
| `CRM`      | `string` |Deve ser informado o CRM do medico que realizou o exame. |
| `Data Exame`      | `string` |	Deve ser a data em que o exame foi realizado. |
| `Indicação`      | `string` |	Deve ser informado o motivo da indicação do exame ao paciente. |
| `Parte-Corpo`      | `string` |		Deve ser informado em qual parte do corpo o paciente foi submetido ao exame. |
| `Usuario`      | `string` |	Deve ser informado o nome do usuário que está enviando essas informações ao prontuário. |

Salva o arquivo enviado no prontuário do paciente.

## Autores

- [@raquel-java](https://github.com/Raquel-Java)

