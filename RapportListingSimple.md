# DominoTracker
<center>
Ce projet n'a rien à voir avec les dominos.
  
Etienne Kita 203
</center>

## Qu'est ce que Domino tracker ?
Domino Tracker a pour but de remplacer les post-it que moi et ma famille utilisons tous les jours pour noter l'activité de notre chat Domino, sorti à telle heure, mangé, dormi, etc...
On y peut se connecter, voir les activités du chat, changer la date et rentrer un évenement parmis une liste de types d'évenement.

## Organisation du projet

Le projet respecte une architecture MVC, et se connecte directement à une base de donnée MySQL hébergée localement.
Certaines actions simples sont directement implémentées dans la base de données sous forme de procédures et fonctions. Les autres sont gérées dans les classes faisant partie du dossier "model".
### Fonctions implémentées dans la base de donnée:
<ul>
  <li>connexion(username, password) <i>utilisation de la fonction de hachage SHA256</i></li>
  <li>add_event(user_id, type)</li>
</ul>
 
### Fonctions gérées dans les classes Java:
Calendrier:
<ul>
  <li>ArrayList<Event> getEvents()</li>
  <li>int getCatId(String name)</li>
</ul>
User:
<ul>
  <li>void inscription(String pseudo, String mdp, String email)</li>
</ul>

## Diagramme de cas d'utilisation

<img src="https://user-images.githubusercontent.com/95452323/228650373-86b13f0b-5b57-4750-b238-cf6e9c1db7a6.png">

## Diagramme de classes

<img src="https://cdn.discordapp.com/attachments/924421151504097321/1090730324381675661/image.png">

## Points d'amélioration

Ayant réalisé ce projet seul, il comporte de nombreux points d'amélioration sur lesquels j'aimerais travailler plus tard:
<ul>
  <li>Pouvoir ajouter des éléments aux évenements: commentaires, évenements..</li>
  <li>Ajouter des statistisques</li>
  <li>Possibilité d'avoir plusieurs chats, à prendre en compte dans l'affichage, pouvoir sélectionner un chat en particulier ou tous</li>
  <li>Meilleure interface graphique: Elle est actuellement très basique, je n'ai pas eu le temps de découvrir en détails comment me servir des outils</li>
  <li>Plusieurs foyers auxquels seraient attribués chaques chats et personnes</li>
 </ul>
 
 
 
 
 
 
 ## Listing simple du code JAVA
 ### activities.user.ConnexionActivity
```java
public class ConnexionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {...}
    private void gotoInscription() {...}
    // Lorsque l'utilisateur rentre un mauvais MDP
    private void popupMDP(int code_erreur) {...}
    public void NextActivity(int userid) {...}
}
```

### activities.user.InscriptionActivity

```java
public class InscriptionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {...}
    private void gotoConnexion() {...}
    private void popup(String message) {...}
    public void NextActivity(int userid) {...}
}
```

### activities.events.EventsActivity

```java
public class EventsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {...}
    private void addEventActivity() {...}
    private void updateSelectedDateText() {...}
    private void filterAndUpdate(EventAdapter adapt, LocalDate selectedDate){...}
}
```

### activities.events.AddEventActivity

```java
public class AddEventActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {...}
    @Override
    public void onBackPressed(){
        finishAct();
    }
    private void finishAct() {...}
    private void addSelectedEvent(){...}
    private class AddEventTask extends AsyncTask<Void, Void, Void> {
        ProgressBar progressBar;
        Context context;
        AddEventTask(Context c){...}
        @Override
        protected void onPreExecute() {...}
        @Override
        protected Void doInBackground(Void... params) {...}
        @Override
        protected void onPostExecute(Void result) {...}
    }
}
```

### activities.events.EventAdapter

```java
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private List<Event> eventList;
    public EventAdapter(List<Event> eventList) {...}
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {...}
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {...}
    @Override
    public int getItemCount() {...}
    public void setEventList(List<Event> eventList) {...}
    public class ViewHolder extends RecyclerView.ViewHolder {...}
}
```

### model.connexion.ConnexionBD

```java
public class ConnexionBD {
    private void connectToBD() throws ClassNotFoundException, SQLException {...}
    public PreparedStatement getPreparedStatement(String sql) {...}
    public int connectUser(String pseudo, String password) {...}
    public void addEvent(int user, int type) {...}
}

```

### model.user.User

```java
public class User {
    public int connexion(String pseudo, String mdp) {...}
    public void inscription(String pseudo, String mdp, String email) throws InscriptionException {...}
}
```

### model.events.Calendrier

```java
public class Calendrier {
    public ArrayList<String> getCategories(){...}
    public void addEvent(String cat, int user){...}
    public ArrayList<Event> filterToDate(ArrayList<Event> events, LocalDate date){...}
    public ArrayList<Event> getEvents(){...}
    private int getCatId(String name){...}
}
```

### model.events.Event

```java
public class Event {
    private String name;
    private String startTime;
    private String description;
    private LocalDate date;
    ...
}
```

### model.user.InscriptionException

Exception Basique
