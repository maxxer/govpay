.. _govpay_scenari_dovuto3:

Pagamento di un dovuto ad iniziativa PSP
----------------------------------------

In questo scenario al cittadino viene fornito un Avviso di Pagamento AgID relativo ad una pendenza. Egli si reca presso le strutture del PSP (sportello, ATM, Home banking, Mobile APP, etc...) per l’esecuzione del versamento.

Il flusso di questo scenario è il seguente:

1. L’Ente Creditore, alla predisposizione di una nuova pendenza, stampa
   l’Avviso di Pagamento pagoPA ad essa associata e la consegna al
   Soggetto Debitore, in formato digitale o cartaceo, secondo le
   modalità previte dell'Ente.
2. Munito dell'avviso, il Soggetto Debitore interagisce con il PSP che
   acquisisce gli estremi dell'Avviso, tramite scansione dei glifi
   grafici o trascrizione manuale dei codici di riferimento.
3. Il PSP verifica gli estremi di pagamento della pendenza,
   eventualmente interagendo con il Gestionale Pendenze, e li prospetta
   al Soggetto Debitore.
4. Il Soggetto Debitore perfeziona il pagamento e GovPay lo notifica al
   Gestionale Pendenze.

Nell’ambito di questa tipologia di pagamento individuiamo i seguenti
casi:

-  Consegna dell’Avviso di Pagamento

L’ente creditore, alla predisposizione di una nuova pendenza, stampa
l’Avviso di Pagamento pagoPA ad essa associata e la consegna al
cittadino.

-  Verifica della pendenza collegata all'Avviso di Pagamento

Il cittadino si reca presso il PSP per pagare tramite l'avviso Avviso di
Pagamento. Il sistema verifica gli estremi della pendenza associata
prima di autorizzare le operazioni di riscossione dell'importo dovuto.

-  Notifica del pagamento di un Avviso di Pagamento

Al termine delle operazioni di riscossione, il gestionale viene
notificato dell'esito del pagamento per aggiornare lo stato della
pendenza.


.. toctree::
   :hidden:

   realizzazione
   configurazione
   esecuzione

