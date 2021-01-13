# EnergySystem
## Etapa 2

#### Ignat Andrei-Horia 324CA

### Rulare teste

Clasa Test#main
  * ruleaza solutia pe testele din checker/, comparand rezultatele cu cele de referinta
  * ruleaza checkstyle

Detalii despre teste: checker/README

Biblioteci necesare pentru implementare:
* Jackson Core 
* Jackson Databind 
* Jackson Annotations

Tutorial Jackson JSON: 
<https://ocw.cs.pub.ro/courses/poo-ca-cd/laboratoare/tutorial-json-jackson>

### Entitati

#### Clase de input / output

InputData si OutputData sunt principalele clase de input si de output folosite,
pentru a putea intai citi si ulterior scrie rezultatele in fisierul json. Acestea
se folosesc de clase mai mici, cum ar fi *ConsumerInputData*, *ProducerInputdata*
etc, care extrag informatiile despre consumator respectectiv producator.

#### Simulation

Este clasa care realizeaza intreaga simulare, ce contine metode care dau update,
care simuleaza prima luna, *luna 0* si ulterior fiecare luna urmatoare.

#### Consumer

Clasa **Consumer** de data aceasta modeleaza direct un consumator concret, fara a mai
face diferentierea intre tipuri de consumatori, cum implementasem la etapa1.
Functionalitatea in schimb, nu difera cu absolut nimic, relatia dintre consumator
si distribuitor functionand identic.

#### Distributor

Aceasta clasa modeleaza un distribuitor real, folosindu-se de clasele de tipul
"strategie" pentru a-si alege producatorii. Aceasta clasa **implementeaza
interfata DistributorObserver**, caci un distribuitor este vazut ca un **observer**
in relatia sa cu producatorii. Se pastreaza vechile funcionalitati care stabilesc
legatura din consumator si distribuitor si se adauga noile functionalitati petru
relatia distribuitor - producator.

#### Producer

Clasa implementeaza metodele necasare unui producator si **interfata
ProducerObservable**. Voi explica in sectiunea de design pattern-uri functionarea
acestuia.

#### Strategy

Impreuna cu cele 3 clase mostenitoare, **GreenStrategy**, **PriceStrategy** si
**QuantityStrategy**, acestea sorteaza dupa criteriul specificat o lista de
producatori, data in constructor si adauga contracte de tip *production* intre
distribuitorul primit tot in constructor si producatorii selectati.

#### Contractele

Exista doua tipuri de contracte, ambele realizeaza legaturi intre entitati.

##### DistributionContract

Se refera la contract de distribuire a energiei si este intre un consumator si
un distribuitor, fiind exact cel din etapa 1.

##### ProductionContract

Stabileste legatura intre un producator si un distribuitor, calculand pretul
in functie de energia transmisa.

### Flow

#### Luna 0

Se parcurg distribuitorii si isi aleg contract, apeland o metoda specifica
distribuitorilor, *chooseContract*, care aplica strategia specifica, folosind
clasele de tip *Strategy*.

Apoi se executa interactiunea dintre distribuitori si consumatori, ca la etapa1

#### Luna oarecare

Se efectueaza update-uri, folosind o metoda *update*, ce primeste schimbarile
din luna curenta. Apoi interactioneaza distribuitorii cu consumatorii ca mai sus,
iar partea noua consta in *interactiunea distribuitorilor cu producatorii*.

Astfel, se parcurg producatorii in ordine si se apeleaza o metoda de notificare,
prin care anunta distribuitorii cu care producatorul curent are contract, daca
acesta a primit update.

Acum se iau distribuitorii, iar cei notificati, isi apeleaza din nou strategia,
stergand lista anterioara de contracte de productie. In acelasi timp, fiecare
producator isi pierde contractul vechi.

In final, se pastreaza toate contractele sortate crescator, ale fiecarui
producator, pentru a putea scrie aceste informatii in fisierul de output.

### Elemente de design OOP

Am impartit clasele in pachete cu nume sugestive, de exemplu, *contractele*
se afla in pachetul *contracts* etc.

Se pot remarca mostenirea intre strategiile specifice si clasa **Strategy**,
implementarea interfetelor **DistributorObserver** si **ProducerObservable**
si abstractizarea strategiei prin clasa abstracta **Strategy**.

### Design patterns

Ca design pattern-uri am folosit:

#### Observer

In momentul in care un **producator (observable)**, primeste update, acesta isi
modifica campul *isUpdated* la statusul de *true*. Apoi este parcursa lista de 
**distribuitori (observers)** cu care acesta are contract si se seteaza campul
*hasUpdatedProducer* pe *true*. In final, se apeleaza **computeStrategy**,care
foloseste metode **chooseProducers** din clasa **Strategy**.

#### Strategy

Evident este folosit pentru a implementa cele 3 strategii. Astfel, clasa abstracta
**Strategy** are metoda comuna de alegere a producatorilor, iar cele mostenite,
implementeaza algoritmul de sortare specific.

#### Factory

Am folosit acest design pattern pentru *a construi instante ale strategiilor*, caci
acestea sunt de mai multe tipuri. Este folosit de catre *distribuitor* in momentul
in care isi alege din nou producatorii cu care semneaza contract.

#### Singleton

Intrucat am nevoie de o singura instanta de **factory**, care sa genereze strategii
variate, am considerat util imbinarea acestor doua design pattern-uri.


