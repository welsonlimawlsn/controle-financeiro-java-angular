-- FUNCIONALIDADES
insert into fcn (fcnid, fcnatcogr, fcndcc, fcnnme, fcnptdtds)
values (1, false, 'Permite realizar o login do usuário', 'Fazer login', null);

insert into fcn (fcnid, fcnatcogr, fcndcc, fcnnme, fcnptdtds)
values (2, false, 'Permite realizar o cadastro de usuarios', 'Criar usuário', null);

insert into fcn (fcnid, fcnatcogr, fcndcc, fcnnme, fcnptdtds)
values (4, true, 'Permite realizar a criação de novas contas', 'Criar conta', false);

insert into fcn (fcnid, fcnatcogr, fcndcc, fcnnme, fcnptdtds)
values (5, true, 'Permite consultar as contas do usuário logado', 'Consultar contas usuário', false);

insert into fcn (fcnid, fcnatcogr, fcndcc, fcnnme, fcnptdtds)
values (6, false, 'Permite recuperar a senha esquecida', 'Recuperar senha', null);

insert into fcn (fcnid, fcnatcogr, fcndcc, fcnnme, fcnptdtds)
values (7, false, 'Permite registrar um novo dispositivo', 'Novo Dispositivo', null);

insert into fcn (fcnid, fcnatcogr, fcndcc, fcnnme, fcnptdtds)
values (8, true, 'Permite remover uma conta', 'Remove conta', false);

-- GRUPOS

insert into gpo(gpoid, gponme)
values (1, 'Administrador');

insert into gpo(gpoid, gponme)
values (2, 'Usuário padrão');

-- PARAMETROS
insert into pmt (pmtid, pmtdcc, pmtvlr)
VALUES (2, 'Minutos para expiração do token', '60');

insert into pmt (pmtid, pmtdcc, pmtvlr)
VALUES (3, 'Access Control Origin', '*');


-- GRUPO X FUNCIONALIDADE

insert into gpofcn (gpoid, fcnid)
VALUES (2, 4);

insert into gpofcn (gpoid, fcnid)
VALUES (2, 5);


insert into gpofcn (gpoid, fcnid)
VALUES (2, 8);
