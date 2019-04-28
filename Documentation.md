
# Dokumentasjon - Modulist  
Dette er dokumentet som beskrives i krav 2 i [oppgaveteksten](./oppgavetekst.pdf).

- [Dokumentasjon - Modulist](#dokumentasjon---modulist)
  - [Om oppgaven](#om-oppgaven)
    - [Ide og formaal](#ide-og-formaal)
  - [Beskrivelse av min loesning](#beskrivelse-av-min-loesning)
    - [Eksempel:](#eksempel)
      - [Pakke til ferie](#pakke-til-ferie)
      - [Pakke til sommerferie](#pakke-til-sommerferie)
      - [Pakke til vinterferie](#pakke-til-vinterferie)
    - [Utvikling av konseptet](#utvikling-av-konseptet)
  - [Appens muligheter](#appens-muligheter)
    - [Lister](#lister)
    - [Naviagasjon](#naviagasjon)
    - [Alle lister - skjermbilde](#alle-lister---skjermbilde)
    - [Se paa en spesifik liste - skjermbilde](#se-paa-en-spesifik-liste---skjermbilde)
      - [Meny](#meny)
    - [Vaerplanlegging - skjermbilde](#vaerplanlegging---skjermbilde)
    - [Settings - skjermbilde](#settings---skjermbilde)
    - [Tutorials](#tutorials)
  - [Tidlige skisser](#tidlige-skisser)
  - [Arkitektur](#arkitektur)
    - [Fragment](#fragment)
    - [Actvities](#actvities)
    - [App-klassen](#app-klassen)
      - [Konstanter](#konstanter)
      - [Notifications](#notifications)
    - [Permissions](#permissions)
    - [Database](#database)
    - [Lokal lagring generelt](#lokal-lagring-generelt)
    - [Multithreading](#multithreading)
  - [Support-biblioteker](#support-biblioteker)
  - [Intents](#intents)
  - [Services og notifications](#services-og-notifications)
  - [Brukertest](#brukertest)
  - [Visuelt](#visuelt)
  - [Versjoner](#versjoner)
  - [Navngivning](#navngivning)
  - [Publisering](#publisering)
  - [Kildeliste](#kildeliste)
  - [Vedlegg](#vedlegg)
    - [Tidlig skisse](#tidlig-skisse)
    - [Skisse - lister](#skisse---lister)
    - [Skisse - listeflyt](#skisse---listeflyt)
    - [Skjermbilde - alle lister](#skjermbilde---alle-lister)
    - [Skjermbilde - en liste](#skjermbilde---en-liste)
    - [Skjermbilde - meny paa liste](#skjermbilde---meny-paa-liste)
    - [Skjermbilde - redigeringsmeny paa liste](#skjermbilde---redigeringsmeny-paa-liste)
    - [Skjermbilde - vaer-planlegging](#skjermbilde---vaer-planlegging)
    - [Skjermbilde - innstillinger](#skjermbilde---innstillinger)
    - [Skjermbilde - instruksjonsvideoer](#skjermbilde---instruksjonsvideoer)

## Om oppgaven 
Oppgaven her var å skrive en appliksasjon som implementerte _Tic Tac Toe_ ("bondesjakke"). I tillegg var det krav om følgende: 
1. Applikasjonen skal ha et tydelig definert konsept og formål
2. Applikasjonen skal ha en Fragment-arkitektur.
3. Applikasjonen skal gjøre bruk av et eksternt API. 
4. Applikasjonen skal gjøre bruk av en lokal database. 
5. Applikasjonen skal ha multimedia innhold.
6. Applikasjonen skal ha lokasjonsdata og/eller kart.
7. Applikasjonen skal gjøre bruk av sensordata.


### Ide og formaal 
Dersom man lager en del pakkelister, merker man fort at det oppstaar noen problemer: 
1. Selv om de er praktiske, er blir de fort tidkrevende aa lage
2. De blir vanskelige aa vedlikeholde og oppdatere over tid. 
3. Det er ofte kloenete aa kommunisere hvor mye man skal pakke avhengig av lengden paa turen

Utfordring 1 og 2 har nemlig samme opphav: duplisering av innhold paa tvers av lister. Duplisering foerer til at man maa bruke mer tid paa la lage dem. Mengden jobb ved oppddatering av punkter vil skalere proposjonalt med antall som har punktet (se [eksempel](#eksempel)). 

For aa loese disse problemene, har jeg latt meg inspirere av et annet domene med noen av de samme utfordringene - programvareutvikling. Spesielt har jeg latt meg inspirere av arv-mekanismen i objektorientert programmering. 

Utfordring nummer 3 kan loeses veldig greit med noe datamaskiner er veldig gode til: enkle regnestykker. 

Oppsummert, er formaalet altsaa aa loese de nevnte utfordringene, samtidig som jeg dekker kravene som oppgaven stiller paa en god maate. 

## Beskrivelse av min loesning 
Resultatet har blitt en app hvor man lager "modulaere pakkelister" - Module-lists - Modulist.
_Modulist_ lar brukeren lage pakkelister. Pakkelistene kan saa "utvides" av andre pakkelister. Utvidningen bestaar i at alle elementer i den foerste listen blir med til den nye. 


### Eksempel:
(__notis__: demo-lister kan legges til fra [innstillinger](#skjermbilde---innstillinger)]

#### Pakke til ferie
* tannbørste
* telefon + lader
* minst to gode bøker

#### Pakke til sommerferie
* __Pakke til ferie__
* badebukse
* t-skjorter

#### Pakke til vinterferie
* __Pakke til ferie__
* minst to gode bøker
* vinterjakke
* ulgenser

Dette ser fint ut når listene er såpass korte som de er nå, men etter hvert som man bruker listene aktivt, vokser de; hver gang man glemmer noe, legger man det til, hver gang noe endrer seg, oppdater man osv. Listene blir både lengre og flere. Se for eksempel for deg at du kjøper en Kindle. Behovet for å drasse med seg to
tunge bøker er borte. Det må oppdateres i begge listene. Om man ser for seg at man har en påske- og høstfereliste
i tillegg, har man plutselig fire lister som må holdes synkronisert og oppdatert. Det blir fort kaos.
Her skal Modulist hjelpe.

### Utvikling av konseptet
Da jeg startet arbeidet ved semesterstart, var tanken å bygge en app som kunne dekke alle slags lister. Det være seg handlelister, pakkelister, generelle huskelister, osv.

Kjappe soek paa app-butikker, viser at liste-apper ikke akkurat er nytt territorium[<sup>1</sup>](#1)[<sup>2</sup>](#2). Markedet for generelle liste-apper er rett og slett et veldig vanskelig marked aa vaere i. Derfor skjoente jeg fort at min strategi maatte vaere aa spisse konseptet saa mye som mulig - satse paa et nisje-marked fremfor aa gaa for massene. 

Etter samtaler med venner og bekjente, kom jeg frem til at det spesielt var en type liste hvor flere så nytten av "liste-arv"-konseptet mitt:
pakkelister

Jeg reiser hyppig mellom ulike byer, og har derfor ogsaa mange pakkelister. Prosjektet ble derfor fort et lite "scratch my own itch"-prosjekt. Paa en side har dette vaert en fordel. Jeg har kjent min brukergruppe godt (jeg er den jo!). En potensiell ulempe er at jeg blir saa last i mine egne behov at jeg ikke forstaar markedet som helhet. Denne ulempen er hoeyst reell, men jeg hadde nok vurdert den som mer avgjoerende hvis jeg hadde gaatt for masse-markedet. 

I og med at jeg gaar for et nisje-marked uansett, har jeg landet paa at fordelene med aa vaere i brukergruppen veier opp for ulempene. 

En stund etter at oppgaven ble utdelt, tegnet jeg noen enkle skisser for aa forstaa hva jeg ville bygge foer jeg bygget dem. Disse omtales naermere [her](#tidlige-skisser).

## Appens muligheter 
(__notis__: demo-lister kan legges til fra [innstillinger](#skjermbilde---innstillinger)]
### Lister

### Naviagasjon   
TODO skriv om navigasjonen 

### Alle lister - [skjermbilde](#skjermbilde---alle-lister)
Denne skjermen er det foerste som moeter brukeren naar det starter appen. 
Sidne gir enkel tilgang til selve listene. 

For aa legge til lister, trykker man paa en "Floating Action Button" (FAB) som vises i hjoernet. Dette er standard i Material Design, og er forholdsvis kjent for de fleste Android-brukere[<sup>3</sup>](#3). Naar man lager en liste, faar brukeren tre valg. Legge til et navn, legge til en farge, og legge de listene man vil arve elementer fra. Jeg fikk veldig positiv tilbakemelding paa at man hadde mulighet for aa legge til sine egne farger.

Muligheten for aa legge til de man arver fra, var litt mer forvirrende. Allikevel har jeg valgt aa ha det inne fordi det er sapass sentralt til hensiktene med appen. Denne forvirringen har allikevel blit imoetegaatt med [instruksjonsvideoer](#skjermbilde---instruksjonsvideoer) som skal gjoere det lettere aa laere seg hvordan appen fungerer. 

### Se paa en spesifik liste - [skjermbilde](#skjermbilde--en-liste)
Paa mange maater er dette den viktigste siden i appen; det er her brukeren jobber med selve pakkelistene. 

Her vises b.la. et "Seekbar"-element. Den brukes til aa juster til hvor mange dager man oensker aa pakke for. Naar den endres, oppdateres listene fortoepende. Til aa begynne med hadde jeg denne paa 0 som default. Da var tanken at brukeren selv skulle legge inn dager hver gang. I [brukertestene](#brukertest) oppdaget jeg at de mindre app-vante brukerene ikke oppfattet muligheten for aa justere antall dager i det hele tatt. Derfor har jeg satt den som litt "scrollet" (til 7) som default. Da forsvant problemet. 

Under dem har man selve elementene. De gir ganske enkelt mulighet for aa huke av- og slette elementer. Hvert element er kun knyttet til en liste, men dersom man forsoeker aa slette dem, vil de fjernes i "barne-listene" ogsaa. Det faar man en varsling om naar man sletter. 

Nederst i hoeyre gjoerne har man en "Floating Action Button" for aa legge til nye liste-elementer. Prosessen tilsvarer aa legge til nye liste. Nye elementer legges ogsaa automatisk til "barne-lister". Naar man legger til et element faar man mulighet til aa legge til navn og _hyppighet_. Hyppighet handler om hvor mange dager som maa gaa foer man trenger aa pakke flere av elementet. 

Foerste gangen de fikk valget om hyppighet, synes de fleste brukerene at det var litt forvirrende. Derimot forsto saa godt som alle brukerene hva det var etter at elementet var lagt til. Terskelen for aa fjerne/legge til elemeenter er veldig lav, og derfor har jeg ikke gjort noen store endringer her. 

#### Meny
I app-baren ligger en [standard-meny](https://developer.android.com/guide/topics/ui/menus) (skjermbilde [her](#skjermbilde---meny-paa-liste)) som gir brukeren en rekke muligheter: 
* _Toggle attic mode_
    
    "Attic Mode" bruker telefonens sensordata for aa gi ekstrafunksjonalitet. Tanken er at man vil befinne seg paa loftet fra tid til annen, naar man pakker. Ekstrafunksjonliteten bestaar av at telefonens lommelykt skrur seg paa nar det er moerkt rundt, og man vil faa en varsel om aa ta tak i en genser om det er kaldt. Det er viktig aa ta vare paa brukerens helse <3 
* _Add location reminder_
  
    Her gis det mulighet til aa legge til paaminnelser naar man ankommer et omraade. En typisk use-case kan vaere "minne meg paa a pakke naar jeg naermer meg hjem-adresssen min". For a aapnaa dette, bruker jeg [geofencing](https://developer.android.com/training/location/geofencing).
* _Share list_

    Denne muligheten gir brukeren mulighet til aa dele listene i tekstform, f.eks. via SMS eller mail. Alle apper som aapner for deling, kan koble seg paa dette. 
* _Add to calendar_

    Lar brukeren legge til et kalender-event i kalenderen sin, basert paa listen. Akkurat som med delingen, kan brukeren velge sin egen app for haandtering her. Som default (og i min testing, primaert) har det vaert Google Calendar. 
* _Edit list_ - [skjermbilde](#skjermbilde---redigeringsmeny-paa-liste)

    Listene skal leve over tid, og man skal kunne endre dem. Da kommer denne under-menyen inn! Her kan man oppdatere listens navn, "foreldre-lister" og farge, samt sletting av lister. 

### Vaerplanlegging - [skjermbilde](#skjermbilde---vaer-planlegging)
Pakking foregaar i all hovedsak foer man skal dra til et sted. Derfor har jeg lagt inn en "Weather-Planner" i appen min. Den henter data fra [Weatherbit](api.weatherbit.io). Data visualiseres i grafen. 

### Settings - [skjermbilde](#skjermbilde---innstillinger)
Det er tre instillinger tilgjengelig. 
* legge til demo-data -> fin naar man skal laere seg aa bruke appen 
* endre tema -> fargetemaer som lar brukeren tilpasse appen 
* slette data -> gjoer akkurat det den sier 

### Tutorials 
Som nevnt tidligere i dette dokumentet, har appen en viss laeringskurve. Derfor har jeg lagt til instruksjonsvideoer som brukeren kan benytte seg av. Alle hovedfunkjsonliteter demonstreres i videoene.  

## Tidlige skisser 
Jeg begynte aa skisse til oppgaven ganske tidlig. Da de ble tegnet, hadde jeg [som nevnt](#utvikling-av-konseptet) tenkt til aa lage mulighet for mer generelle lister. Allikevel har hovedtrekkne i skissene holdt seg igjen i det endelige resultatet. 

Den [tidligeste skssen](#tidlig-skisse) viser noen smaa skisser og en haug med stikkord. Her hadde jeg en tett kopling mellom en liste og en lokasjon. Notatene viser ogsaa at jeg hadde delt inn listene i kategoriene. Dette har jeg (med unntak av location reminders) gaatt litt bort fra, for aa unnga unoedvendig kompleksitet. 

[Neste skisse](#skisse---lister) viser en sketsj for en hovedskjerm, og utviding av lister. Selve liste-siden er ganske enkel i denne skissen, omtrent som i sluttlisten. "Seekbar"-menyen for antall dager dukker ikke opp her, fordi dette ble tegnet foer konseptet var spisset mot pakking. 

Hjem-skjermen som vises i denne skissen, er ganske annerledes enn den som er i [resultatet](#skjermbilde---alle-lister). Her har man stoette for aa lege til lister som "favoritter" og som "aktuelle". Listene vises ogsaa i horisontale views. Dette var lenge en de lav appen, men jeg gikk etter hvert bort fra det. Ogsaa her handlet det om aa unngaa unoedvendig kompleksitet. 

Oeverst i venstre hjoerene ser man at en basis for det som i dag ligger i [menyen](#meny) er til stede. ideene om aa dele lister og legge dem til i kalenderen har kommet inn, sammen med geofence-reminders (HER: "Add location at"). 

## Arkitektur 

### Fragment 
Programmet bruker en fragment-arkitektur, slik som 
oppgaven spesifiserer. Fragments gir en fordel over activities her fordi de er mindre ressurskrevende å starte opp enn activities. 
Fragments er mer fleksible enn activities var tenkt til å være. De kan gjenbrukes og, dersom man ønsker, kan man ha flere i samme skjerm (f.eks endring ved rotasjon). Det er ikke anbefalt at de skal være så store. De skal dele opp ganske store bolker. 

Alle skjermer som kan naaes fra [navigasjonsmenyen](#navgasjonsmeny), bytter ut innholdet i en hoved-activity, `MainActivity.kt`.

Jeg bruker kun ett fragment om gangen, og jeg har laast appen i portraint-rotasjon. Derfor er det først og fremst kostnaden ved å starte en fragment som jeg sparer. Det er fint her ved kjapp navigering mellom fragments. 

Rotasjons-laasingen er slik som den er fordi hovedfunksjonaliteten i appen ikke er av en type hvor det er naturlig med landscape-rotasjon. Dersom appens fokus hadde vaert aa lese lange artikler, se paa bilder, film eller lignende, ville det derimot vaert mer naturlig. Denne appen skal vise lange, verttikale lister. Da ville landscape-modus skapt mer scrolling og unoedvendig tap av brukervennlighet. Det er ogsaa faa mennesker som faktisk bruker telefoner i landscape, saerlig paa Android[<sup>4</sup>](#4).

![Activity to framgents](photos/diagrams/activity-to-fragment.png)

Aa bytte fragmens innebaerer bare aa sjekke hvilket element i navigasjonsmenyen som klikkes, og saa bytte gjennom en `FragmentManager`. 
```kotlin

class MainActivity : BaseActivity() {

    private fun setupDrawerItemListeners() {
            
        activity_main_navigation_view.bringToFront()
        activity_main_navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_my_lists -> replaceMainFragment(MyListsFragment())
                R.id.nav_weather_planner -> replaceMainFragment(WeatherPlannerFragment())
                R.id.nav_settings -> replaceMainFragment(SettingsFragment())
                R.id.nav_tutorial_videos -> replaceMainFragment(TutorialVideoFragment())
            }
            activity_main_drawer.closeDrawer(Gravity.START)
            true
        }
    }

    fun replaceMainFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.activity_base_frame_layout, fragment)
            .commit()
    }
}
// NOTE: kode som ikke er relevant for eksempel er fjernet 
```

### Actvities
Activities er et eget GUI-vindu i Android, som kan startes i Android. En stor del av Android sin filosofi er at ting kan startes fra forskjellige steder: en app kan starte en annen app, osv[<sup>5</sup>](#5). Dette gjoeres gjerne gjennom intents ([mer senere](#intents)) som starter activities. 

![Bilde av activity lifecycle](photos/diagrams/actvity-lifecycle.png)
(bilde[<sup>6</sup>](#6))

Activities har sin egen livssyklus, med tilhoerende metoder, som figuren over viser. Disse brukes til initialisering og "teardown"-hendelser i applikasjonen. Her bruker jeg saerlig oppstartsmetodene til aa intiere det som skal skje, sette opp lyttere o.l.
```kotlin 
//MainActivity.kt
override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_main)
    replaceMainFragment(MyListsFragment())
    setUpActionBarWithDrawer()
    setupDrawerItemListeners()
}
```

Metodene kan ogsaa brukes for aa lagre states fra input o.l. Da kan man lagre dette i et bundle-objekt, som sendes i `savedInstanceState: Bundle?`. Det haandteres imidlertid ganske godt av Android fra foer, og jeg har ikke opplevt problemer ved bruk av appen. Derfor har jeg heller ikke haandtert dette manuelt. 

Jeg har to activities som vises til brukeren, som begge er subklasser av en felles `BaseActivity.kt`: [Klassediagram for Actvity-klassene](photos/diagrams/activity-diagram-class.png)

Jeg kunne valgt aa vise en liste i et fragment, sann som med de andre delene av appen min. Dette ville vaert hakket kjapperet, av [nevnte](#fragment) aarsaker. Allikevel har jeg oensket aa ha det i en fragment, av to aarsaker: 
1. en activity spretter opp som et eget vindu. Naar jeg apner en liste i appen min, _foeles det riktig_, rett og slett; jeg er ferdig med aa navigere og bruke appens mange ekstrafunksjonaliteter. Naa skal jeg fokusere paa aa pakke. 
2. For aa integrere bedre med andre apper paa telefonen, hadde det vaert stilig dersom andre apper kunne starte aktiviteten til en gitt liste. Dette kan man gjoere med intent-fitlers. Per i dag ligger ikke den type funksjonalitet i appen, men det er et hav av muligheter som kunne vaere veldig spennende. Derfor har jeg oensket aa holde doeren aapen. 

I Android kan man som nevnt forvente at activities kan startes av andre applikasjoner. Bruksflyten er mer sporadisk, og jeg maa forvente at begge activities er det foerste en bruker moeter. Derfor maa jeg haandtere noen ting begge steder. I mitt tilfelle: aa legge paa fargetema, og aa hente inn tillatelser til aa bruke lokasjonstjenester(geofencing) og kamera(lys). Kodebiten som gjoer det, er vist nedenfor. 
``` kotlin 
override fun onCreate(savedInstanceState: Bundle?) {

    applyTheme()
    checkPermissionForLocation()
    checkPermissionForCamera()
    super.onCreate(savedInstanceState)
}
```

### App-klassen 
Jeg har laget en Applikasjonsklasse som extender `android.app.Application`. Den er definert i `AndroidManifest.xml`, slik at den kjoerer som hoved-applikasjonsklasse for programmet. Jeg bruker denne klassen til to ting: Konstanter og oppretting av en "notification channels". 

#### Konstanter
Appen noen konstanter som trengs i loepet av appens liv. Det er API-noekler og diverse ID'er for requestkoder. De ligger tilgjengelig som statiske klassevariable `App.kt`. 

Det er ikke optimalt aa legge API-noekler paa denne maaten. Allikevel har jeg valgt aa gjoere det i dette tilfellet. Delvis fordi det er enklere, men foerst og fremst av grunnene som staar i kommentarene under: 

#### Notifications 
I nyere versjoner av Android, er man noedt til aa opprette "Notification Channels" for aa sende Notificaiotions til brukeren. Formaalet med dette er at brukeren skal faa mer kontroll over notifikasjone som sendes fra ulike apper. Hvis en app registrerer forskjellige "Channels", kan en bruker legge ulike innstillinger paa hver av dem[<sup>7</sup>](#7)

En "Channel" har en ID, et nan og et vitkighetsnivaa. Jeg har valgt aa sette viktighetsnivaaet til `NotificaionManager.IMPORTANCE_DEFAULT`. Mine varsler (for aa pakke) er ikke noe jeg anser som veldig viktig. Det er heller ikke uviktig. Med "default"-viktighetsnivaaet vil en notification spille lyd og dukke opp i status bar[<sup>8</sup>](#8). 

```kotlin
private fun createNotificationChannels() {

    // ta hoeyede for endringer i notifikasjoner i android O
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "Main channel for modulist notificaions"

        val manager = getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}
```

### Permissions 
TODO ME 

### Database  
Å lese fra en database er en forholdsvis tidkrevende prosess. Derfor er det viktig at dette ikke gjøres på samme tråd som kjører brukergrensesnittet ("UI-tråden"). Da vil man blokkere alt annet som skjer, grensesnittet mot brukeren vil henge dersom ting tar tid. Det senker brukeropplevelsen. På databaser er det faktisk så nøye at Android i utgangspuntket ikke lar deg kjøre database kall på UI-tråden i det hele tatt. 

For å håndtere dette, har jeg brukt arkitekturen som har blitt brukt i forelesning, og som Google har på sine eksempelsider<sup>9</sup>](#9). Den bygger på [Kotlins "coroutines"](https://kotlinlang.org/docs/reference/coroutines/basics.html) og [@WorkerThread](https://developer.android.com/reference/android/support/annotation/WorkerThread) for å oppnå multithreading. 
Den har vært oversiktlig og fin. En ulempe med den er at den er litt vel omfattende; det er ganske mye kode for ganske lite, sammenlignet med å kjøre spørringer på separate tråder, med litt færre abstraksjonslag mellom "funksjonskalleren" og databasen. Jeg har holdt meg til den allikevel, først og fremst fordi den faste strukturen var lett å jobbe med. 

Jeg har flere entiteter: `Item`, `ListRelation` og `ModuleList`. For aa foelge arkitekturen, blir det ogsaa ganske mange klasser. De skal ha en DAO, et Repository og en Model i tillegg til selve entitet-klassen. Noen ting er felles blant disse. Jeg har derfor bygget videre paa arkitekturen fra forelesningen, og endt opp med dette: 
![Klassediagram over mine databaseklasser](photos/diagrams/database-class-diagram-modulelist.png)

Jeg har lagt noe felles funksjonalitet i de generiske Common-klassene. Da slapp jeg aa definere metoder for aa legge til, oppdatere og slette elementer for hvert av modellene manuelt. Godene med dette hadde vaert tydeligere dersom jeg hadde hatt fere entiteter, men jeg mene at jeg fikk igjen for generaliseringen i mitt prosjekt ogsaa. 

![Databasediagram over mine entiteter](photos/diagrams/database-entity-diagram.png)
Selve database-arkitekturen er forholdsvis enkel, men den fungerte mer enn godt nok for det jeg trengte. 

### Lokal lagring generelt 

I min oppgave bruekr jeg hovedsaklig SQL-databaser til aa lagre data. Det finnes andre lagringsmetoder i Android: ekstern/intern fil-lagring og "SharedPreferences". SharedPreferences egner foerst og fremst godt til lagring av enklere datatyper, og opererer paa "key-value"-parr<sup>10</sup>](#10). Ikke til data om listene.

Internt og eksternt storage er enda mulighet som jeg kunne brukt. Det egner seg til litt stoerre megnder data, men jeg konkluderte med at en relasjonsdatabase passer enda bedre fordi dataen er strukturert<sup>11</sup>](#11). Med en relasjonsdatabase kan jeg dessuten gjoere spoerringer paa dataen (f.eks. hente ut etter X kritereie). SQL-databaser er godt optimalisert for akkurat denne oppgaven, saerlig sammenlignet med de andre alternativene.

Videre er "SharedPreferences" i ment for å lagre enklere data som `String`, `Int` osv. 
Man kunne konvertert objektene frem og tilbake til et format som JSON-strings, men det ville forkludret koden unødvendig mye i forhold til gevinsten, slik jeg vurderte det.  

Shared preferences egner seg derimot godt til klassiske "key-value"-scenarier. Det går også kjappere å lese fra "SharedPreferences" enn fra en SQL-database. Derfor har jeg valgt å bruke "SharedPreferences" for å lagre fargetemaene. 

### Multithreading 
TODO ME 

## Support-biblioteker 
Da jeg startet paa Modulist, brukte jeg de samme support-bibliotekene som ble brukt i undervisningen. Support-bibliotekene gir bakoverkompatibilitet med tidligere versjoner av Android. Etter hvert byttet jeg til AndroidX, som er erstatningen paa de gamle support-bibliotekene<sup>12</sup>](#12). 

Migreringen ble veldig enklel. Android Studio hadde en egen knapp som mer eller mindre gjorde alt for meg. 

Utgangspuntet for at jeg oensket aa bytte, var at jeg oppdaget at noen bibliotek ikke fungerte like bra (eller ikke i det hele tatt) med mindre man hadde AndroidX. Saerlig stoette jeg paa problemer med [biblioteket jeg bruker til Youtube-spillere](https://github.com/PierfrancescoSoffritti/Android-YouTube-Player).

## Intents 
TODO ME 

## Services og notifications 
TODO ME 

## Brukertest
Jeg har gjennomfoert brukertester med venner og bekjente. Jeg har passet på å la både "tekniske" og "ikke-tekniske" kjente. Det vil si at jeg også testet folk som ikke er vant til å bruke mange apper og som sjelden lærer seg å bruke nye programmer. 

Jeg fikk flere tilbakemeldinger: 
* Ikke alle skjoente at de kunne redigere Seekbaren paa item-skjermen. Dette var noe saerlig de ikke-tekniske brukerene slet med. Derfor satte jeg den som default til 7. Da ligger den litt under hallveis mot midten. Min hypotese var at fargen ville skjaere litt i resten av bildet, og at det ville gjeore at folk la merke til den. Jeg later til aa ha hatt noe rett i det, for folk saa den mye tidligere enn foer etter denne endringen .
* Da jeg startet testene, var jeg litt usikker paa om fargene var mer i veien enn til hjelp. De tar tross alt litt tid fra brukeren under oppsett av en liste, og de har ikke noedvendigvis saa mye funksjonlitet. Jeg fikk derimot beskjed av testobjektene om at dette var noe de likte. Derfor beholdt jeg det videre. 
* De aller fleste som testet synes at det var litt vanskelig aa faa tak paa konseptet i appen til aa begynne med. De synes det var lettest var (ikke saa overraskende) de som hadde kjennskap til objektorietert programmering. Mange av de mindre tekniske testobjektene, trengte lenger tid. Dette foerte til to ting: 
  1. Instruksjonsvideoer som en veldig lett tilgjengelig del av appen 
  2. Enkelhet rundt konseptet. Jeg har skrevet mer om dette [tidligere i dokumentet](#tidlige-skisser).

Appen har først og fremst blitt kjørt på min egen [Moto E Play](https://www.motorola.com/us/products/moto-e-play-gen-5). 
seekbar  
farger 
ting de likte 
noe de var kritiske til 
Vanskelige konsepter -> strippet ned funksjonalitet til minimum.

## Visuelt
Jeg har holdt meg til Material Design, og Google sine standard-komponenter. Disse er kjente for brukeren. Det er lagt opp til at brukeren skal kunne endre fargetema gjennom instillinger-skjermen. 

Jeg har også laget et ikon til appen.
![startskjerm](./photos/icon.png)

## Versjoner
TODO ME 


## Navngivning 
(samme som i _Tic Tac Toe_)

Mange av navngivningskonvensjonene jeg har fulgt er veldig vanlige, standard-konvenserjoner. 
B.la. bruker jeg [Camel Case](https://en.wikipedia.org/wiki/Camel_case) på de aller fleste variablelnavn. Kosntanter har store bokstaver. 

Hva navngivning av XML-id'er har jeg ikke vært like tradisjonell. Her har det vært ekstra viktig å ha en navngivningskonvensjon som faktisk er _helt_ identifiserende. Dette er fordi at ID'ene er tilgjengelige i globalt scope. Konvensjonen jeg landet på har følgende sturktur: "parent, type"\_"parent, navn"\_"element, type"_"element, navn". Under er et eksempel i en activity som heter `DemoActivity`. ID er satt på `TextView`-tagen: 
```XML
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        tools:context=".gui.DemoActivity">

     <TextView
        <!-- ID med min konvensenjon: -->
        android:id="@+id/activity_demo_text_welcome_message"
        android:text="Hei, velkommen til min demo!"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop-toBottomOf="parent"
      />

</android.support.constraint.ConstraintLayout>
```

Sammensatte ord separeres også med understrek ("activity_demo_text_welcome_message", ikke _activity_demo_text_welcomeMessage"). Det kan være litt forvirrende, men mitt personlige inntrykk var at dette var bedre å forholde seg til det ørlille usikkerhetsmomentet enn å ha blandingen av store og små bokstaver der jeg allerde separerte med "_". 

Når jeg refererer til XML-elementer i Kotlin-kode, har jeg brukt [Kotlin View Binding](https://kotlinlang.org/docs/tutorials/android-plugin.html#view-binding). Der har spart meg for masse unødvendig "boilerplate"-kode. Det har også gjort at de lange navnkonvensjonene mine ikke har vært  så til hinder som man kanskje kunne frykte 
```kotlin

// uten view binding 
val textPlayer1 = findViewById(R.id.fragment_game_text_player1) as TextView; 
textPlayer1.setTextColor(activePlayerColor)

// med view binding 
fragment_game_text_player1.setTextColor(activePlayerColor)
```


## Publisering 
TODO: playstore 
Prosjektet ligger også på et [github-repo](https://github.com/olaven/modulist)


## Kildeliste 
__note__: Der tilstrekkelig informasjon ikke er oppgitt, kommer det frem i kildehenvisningen.
* <span id="1">1:</span> https://play.google.com/store/search?q=todo&c=apps
* <span id="2">2:</span> https://itunes.apple.com/us/app/wunderlist-to-do-list-tasks/id406644151?mt=8#see-all/customers-also-bought-apps
* <span id="3">3:</span> Uspesifiert forfatter, Google. NA. “Floating Action Buttons”. https://material.io/develop/android/components/floating-action-button/ (lastet ned 27. April 2019)
* <span id="4">4:</span> Rahul Reddy. 28 Juli 2017. “Smartphone vs Tablet Orientation: Who’s Using What?”. https://www.scientiamobile.com/smartphone-vs-tablet-orientation-whos-using-what/ (lastet ned 28. April 2019)
* <span id="5">5:</span> Uspesifisert forfatter, Google. NA. “Introduction to Activities”. https://developer.android.com/guide/components/activities/intro-activities (lastet ned 28. April 2019)
* <span id="6">6:</span> Uspesifisert forfatter, Google. NA. “Understand the Activity Lifecycle”. https://developer.android.com/guide/components/activities/activity-lifecycle (lastet ned 28. April 2019)
* <span id="7">7:</span> Uspesifisert forfatter, Google. "Create and Manage Notification Channels”. https://developer.android.com/training/notify-user/channels (lastet ned 28. April 2019)
* <span id="8">8:</span> Uspesifisert forfatter, Google. NA. "Set the importance level". https://developer.android.com/training/notify-user/channels#importance (lastet ned 28. April 2019)
* <span id="9">9:</span> Uspesifisert forfatter, Google. NA. "Save data in a local database using Room". https://developer.android.com/training/data-storage/room/ (lastet ned 28. April 2019)
* <span id="10">10:</span> Obaro Ogbo. 21 September 2016. "How to store data locally in an Android app". https://www.androidauthority.com/how-to-store-data-locally-in-android-app-717190/ (lastet ned 28. April 2019)
* <span id="11">11:</span> Uspesifisert forfatter, Google. NA. "Data and file storage overview". https://developer.android.com/guide/topics/data/data-storage (lastet ned 28. April 2019)
* <span id="12">12:</span> Uspesifisert forfatter, Google. NA. "AndroidX Overview". https://developer.android.com/jetpack/androidx/#using_androidx (lastet ned 28. April 2019)





## Vedlegg
### Tidlig skisse 
![Tidlig skisse/idemyldring](photos/sketches/early_sketch.png) 
### Skisse - lister 
![Skisse for sammenheng mellom lister](photos/sketches/list_flow.png) 
### Skisse - listeflyt 
![Skisse for visning av lister](photos/sketches/list_view.png) 
### Skjermbilde - alle lister 
![Skjermbilde av skjermen med alle lister](photos/screenshots/lists.png)
### Skjermbilde - en liste
![Skjermbilde av skjermed med en liste](photos/screenshots/list.png) 
### Skjermbilde - meny paa liste 
![Skjermbilde av skjermed med en liste](photos/screenshots/list-options.png) 
### Skjermbilde - redigeringsmeny paa liste 
![Skjermbilde av skjermed med en liste](photos/screenshots/list-edit.png) 
### Skjermbilde - vaer-planlegging 
![Skjermbilde vaerskjermen](photos/screenshots/weather.png) 
### Skjermbilde - innstillinger 
![Skjermbilde instillings-skjermen](photos/screenshots/settings.png) 
### Skjermbilde - instruksjonsvideoer
![Skjermbilde instruksjonsskjermen](photos/screenshots/tutorials.png) 