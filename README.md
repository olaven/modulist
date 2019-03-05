# modulist
A better way to manage checklists.


Kan en pakkeliste settes for antall dager -> sier hvor mange man trenger avhengig av antal dager
Kjøre all inn på pakking?
* Remind before date / calendar event
* when arriving, leaving

This project is in an early state of development.

## TODO: 
- [X] Navigation bytter fragment
- [X] legge ved sketch av home-page
- [X] enkel oversikt på home-fragment
- [X] fragments må ikke overlappe
- [X] klikk på home-liste gjør noe
- [ ] Strategi på migrering av db
- [ ] Rydde i Todos (dette dok)
- [ ] dokumentasjon -> navngiving av SQL-tabeller og fields , vise at jeg vet at man kan oversrkrive, men ikke har gjort det
- [ ] klikke på en et listenavn skal vise listen i egen activity
- [ ] Sette opp lokal datbase med Room
- [X] Lagre data i database
- [ ] V-modell i dokumentasjon
- [ ] modell for databaselogikk i dokumentasjon
- [ ] Black-box vs White-box testing (doc, men også før, vite hva som er viktig hvor)
- [ ] Bytte til standard card view i Home
- [ ] vise en vilkårlig liste i et generisk view (activity eller fragment?)
- [ ] Gå over dokumentasajonsliste i tictactoe og agenda
- [ ] bytte mellom themes
- [ ] om multidexing eksplisitt for API < 21
- [ ] snakk om alle dependecies i dokumentasjon (og vis kilde)
- [ ] lagre theme-preferanse i userpreferences


## Konsept og formål
Jeg ønsker å lage en app for lister. Mange - undertegnede inkludert - bruker lister til å huske ting, og
til å organisere hverdagen. Det være seg handlelister, pakkelister, generelle huskelister, osv. osv.
Etter hvert som det blir mange lister, blir det også mange ting som gjentar seg.

### Eksempel:

Se for deg disse listene:
#### Pakke til vinterfeire
* tannbørste
* telefon + lader
* minst to gode bøker
* badebukse
* t-skjorter

#### Pakke til vinterferie
* tannbørste
* telefon + lader
* laptop + lader
* minst to gode bøker
* vinterjakke
* ulgenser

Dette ser fint ut når listene er såpass korte som de er nå, men etter hvert som man bruker listene aktivt,
vokser de; hver gang man glememr noe, legger man det til, hver gang noe endrer seg, oppdater man osv. Listene
blir både lengre og flere. Se for eksempel for deg at du kjøper en Kindle. Behovet for å drasse med seg to
tunge bøker er borte. Det må oppdateres i begge listene. Om man ser for seg at man har en påske- og høstfereliste
i tillegg, har man plutselig fire lister som må holdes synkronisert og oppdatert. Det blir fort kaos.
Her skal Modulist hjelpe.

Modulist skal la brukeren lage lister som baserer seg på tidligere lister.
Litt som arv i programmering. Det skal være brukervennlig og lett for ikke-tekniske brukere å få orden i
listene sine.


#### Pakke til ferie
* tannbørste
* telefon + lader
* minst to gode bøker

#### Pakke til vinterfeire
* __Pakke til ferie__
* badebukse
* t-skjorter

#### Pakke til vinterferie
* __Pakke til ferie__
* minst to gode bøker
* vinterjakke
* ulgenser


## Dokumentasjon
Har fragments i en framelayout pga .https://stackoverflow.com/questions/43858926/set-androidname-of-fragment-programmatically
Navngitt parametere i annotations


Lever(alive) over tid -> appen må også gjøre det
