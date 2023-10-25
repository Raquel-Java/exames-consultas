--Garantindo privilégios ao super user
grant all privileges on database db_empresa to web_api;

--Criando usuário user_api
create user user_api with sysid 402866 nocreatedb password 'sevenpay#10';

----------------------------------------------

-- ## TABELA exame
create table if not exists exame (

    id bigserial NOT NULL,
    nome text NULL,
    identificador_exame text NULL,
    dt_realizacao_exame TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    crm_medico_examinador text NULL,
    parte_corpo text NULL,
    indicacao text NULL,
    cpf_paciente text NULL,
    dt_criacao_registro TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    dt_alteracao_registro TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    -- Criação de constraints
    constraint pk_exame primary key (id)
);

-- Criação dos indices
create index idx_identificador_exame on exame (identificador_exame asc);

-- Select para uso do user_api
grant select on table exame to user_api;


----------------------------------------------