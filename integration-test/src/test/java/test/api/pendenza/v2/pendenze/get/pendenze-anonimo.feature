Feature: Ricerca pagamenti

Background:

* callonce read('classpath:utils/common-utils.feature')

Scenario: Ricerca pendenze utente anonimo

* def pendenzeBaseurl = getGovPayApiBaseUrl({api: 'pendenze', versione: 'v2', autenticazione: 'public'})

Given url pendenzeBaseurl
And path '/pendenze'
When method get
Then status 401 

Scenario: Ricerca pendenze utente anonimo

* def pendenzeBaseurl = getGovPayApiBaseUrl({api: 'pendenze', versione: 'v2', autenticazione: 'public'})

Given url pendenzeBaseurl
And path '/pendenze', 'test', 'test'
When method get
Then status 401 