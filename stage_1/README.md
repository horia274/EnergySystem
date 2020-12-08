# EnergySystem
## Etapa 1

### Input / Output
Am creat multiple *clase de input* pentru parsarea fisierelor json si incarcarea
lor in obiecte. Acest obiect format va fi o instanta a clasei **InputData**.

*Clasele de output* sunt folosite pentru a forma obiectul de output, de tipul
**OutputData** si pentru a-l scrie in fisierul json.
in fisierul json corespunzator.

### Design pattern-ul Factory
Am folosit acest design pattern in combinatie cu **Singleton**, intrucat este
nevoie de o singura instanta de *factory*, pentru a creea obiecte noi.

Ideea mea s-a bazat pe urmatoarea clasificare, prezenta in enuntul proiectului:
**consumatori (casnici sau industriali)**. Asa ca am considerat util sa creez
un factory pe *tipuri de consumator*, in ideea ca, in cea de-a doua etapa, se vor
diferentia in aceste 2 tipuri.

Momentan nu este foarte util, intrucat nu se face vreo diferentiere in acest sens.
In schimb, utilitatea va fi foarte mare daca s-ar imparti pe categorii, intrucat,
in acest caz, va trebui sa:

* creez o noua clasa pentru o categorie noua de consumatori, care va trebuie sa
implementeze **interfata Consumer**;

* adaug noul tip in **clasa ConsumerFactory**;

* las restul codului asa cum este, ideea de **incapsulare**.

### Clasele Concrete
Sunt cele care definesc si implementeaza conceptul de distibuitor, **Distributor**,
de consumator, **ConcreteConsumer** si de contract, **Contract**.

Astfel, **clasa ConcreteConsumer** implementeaza metodele din **interfata Consumer**,
cum ar fi: *primirea salariului*, *alegerea unui contract, pe baza unei liste de 
distribuitori*, *platirea contractului* etc.

Am considerat util sa tin minte la fiecare pas doua contracte, si anume, contractul
curent, *contract* si contractul pe luna trecuta, *oldContract*, in cazul in care
consumatorul are de platit facturi restante.

Distribuitorul implementeaza metode de tipul: *calculul contractului curent*,
*adaugarea unui contract in lista de contracte*, *primirea banilor de la clienti*,
*plata facturii* etc.

Am considerat suficiente comentariile din cod pentru a explica functionalitatile lor.

### Motorul actiunii
Este reprezentat de **clasa Simulation**, care contine trei metode principale:

* **firstTurn** are rolul de a realiza toate interactiunile din **luna 0**, dintre
**clasele concrete**, intr-o ordine gandita. Practic, fiecare **entitate** (consumer,
distributer, contract) va apela metodele necesare;

* **simulateTurn** va face update la lista curenta de consumatori si la atributele
curente ale distribuitorilor, iar ulterior va apela **firstTurn**, pentru a incheia
runda;

* **simulateAllTurns** apeleaza **firsTurn** si **simulateTurn** la fiecare update.

### Main-ul
Are rolul de a lega toate clasele intre ele, deoarece rolul lui este acela de a
forma obiectul de **input**, de a apela **simulateAllTurns**, prin prisma unui obiect
**Simulate** si de a forma **output-ul** pe baza rezultatului obtinut.

