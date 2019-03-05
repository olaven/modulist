# modulist
A better way to manage checklists.


Kan en pakkeliste settes for antall dager -> sier hvor mange man trenger avhengig av antal dager
Kjøre all inn på pakking?
* Remind before date / calendar event
* when arriving, leaving

This project is in an early state of development.

## Funksjonalitet
- [X] enkel oversikt på home-fragment
- [X] klikk på home-liste gjør noe
- [X] Navigation bytter fragment
- [X] Sette opp lokal datbase med Room
- [X] Lagre data i database
- [ ] Fullfør databasearkitektur (entiteter)
- [ ] lag modell av databasearkitektur
- [ ] klikke på en et listenavn skal vise listen i egen activity
- [ ] Opprette liste
- [ ] bytte mellom themes
- [ ] lagre theme-preferanse i userpreferences
- [ ] Skikkelig håndtering av databasemigrering
## GUI
- [ ] Bytte til standard card view i Home
- [ ] lag settings-fragment
## Testing
- [ ] Sjekk testing
- [ ] Bruk V-modell
## Dokumentasjon
- [ ] Skriv om appens ide og formål
- [ ] Bruk statistikk forå vise at telefonen er et vitkig verktøy hvor denne appen passer
- [ ] Skriv om API-version. Hvorfor er valgt slik det er?
- [ ] Bruk statistikk fra statistia for å forsvare versjonvalg
- [ ] Skriv om fragment-arkitektur
- [ ] Lag modell av database-løsningen (klasser)
- [ ] Lag modell av database-løsningen (database)
- [ ] Snakke om at jeg ikke har overskrevet tabellnavn o.l. for lesbarehetens skyld (viser at jeg kjenner muligheten)
- [ ] Asynkronitet i databaseoperasjoner og hvorfor det er nødvendig
- [ ] Gå gjennom alle dependencies i gradle
- [ ] V-model for testing
- [ ] Black-box vs White-box testing (doc, men også før, vite hva som er viktig hvor) -> vis til eksempler
- [ ] litt om assertJ kontra ren Junit
- [ ] Sørg for at alle punkter i hele TODO er nevt på en eller annen måte
- [ ] Sørg for at absolutt alle deler av eksamensoppgaven dekkes
- [ ] Skriv om sketcher

## Øvring
- [X] fragments må ikke overlappe
- [X] legge ved sketch av home-page
- [ ] Rydde i Todos (dette dok)
- [ ] Gå over dokumentasajonsliste i tictactoe og agenda
- [ ] Lansering til Google Play


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
