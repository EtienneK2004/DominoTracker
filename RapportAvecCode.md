# DominoTracker
Ce projet n'a rien à voir avec les dominos.

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
 
 
 
 
 
 
 ## Listing du code JAVA
 ### activities.user.ConnexionActivity
```java
package com.example.dominotracker.activities.user;


import androidx.appcompat.app.AppCompatActivity;

import ...


import com.example.dominotracker.R;
import com.example.dominotracker.activities.events.EventsActivity;
import com.example.dominotracker.model.user.User;

public class ConnexionActivity extends AppCompatActivity {

    private static Button btn_send, btn_inscription;
    private static EditText pseudo, mdp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        User userBD = new User();
        pseudo = (EditText) findViewById(R.id.pseudo);
        mdp = (EditText) findViewById(R.id.mdp);

        btn_send = (Button) findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int result = userBD.connexion(pseudo.getText().toString(), mdp.getText().toString());
                if(result<=0){
                    popupMDP(result);
                }
                else{
                    NextActivity(result);
                }
            }
        });


        btn_inscription = (Button) findViewById(R.id.btn_inscription);

        btn_inscription.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                gotoInscription();
            }
        });

        if(Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    private void gotoInscription() {
        Intent intent = new Intent(ConnexionActivity.this, InscriptionActivity.class);
        startActivity(intent);
        finish();
    }

    // Lorsque l'utilisateur rentre un mauvais MDP
    private void popupMDP(int code_erreur) {
        // AlertDialog pour informer l'utilisateur
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Erreur de connexion");
        switch(code_erreur){
            case(1):
                builder.setMessage("Erreur avec la base de donnée");
            default:
                builder.setMessage("Nom d'utilisateur ou mot de passe incorrect.");
        }

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



    public void NextActivity(int userid) {
        Intent intent = new Intent(ConnexionActivity.this, EventsActivity.class);
        intent.putExtra("EXTRA_USER", userid);
        startActivity(intent);
        finish();
    }
}
```

### activities.user.InscriptionActivity

```java
package com.example.dominotracker.activities.user;

import androidx.appcompat.app.AppCompatActivity;

import ...

import com.example.dominotracker.R;
import com.example.dominotracker.activities.events.EventsActivity;
import com.example.dominotracker.model.user.InscriptionException;
import com.example.dominotracker.model.user.User;

public class InscriptionActivity extends AppCompatActivity {

    private static Button btn_send, btn_connexion;
    private static EditText pseudo, mdp, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        User userBD = new User();
        pseudo = (EditText) findViewById(R.id.pseudo);
        mdp = (EditText) findViewById(R.id.mdp);
        email = (EditText) findViewById(R.id.email);

        btn_send = (Button) findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    Log.i("pseudo", pseudo.getText().toString());
                    userBD.inscription(pseudo.getText().toString(), mdp.getText().toString(), email.getText().toString());
                    int result = userBD.connexion(pseudo.getText().toString(), mdp.getText().toString());
                    NextActivity(result);
                }catch(InscriptionException e){
                    popup(e.getMessage());
                }

            }
        });

        btn_connexion = (Button) findViewById(R.id.btn_connexion);

        btn_connexion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                gotoConnexion();
            }
        });


        if(Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }

    private void gotoConnexion() {
        Intent intent = new Intent(InscriptionActivity.this, ConnexionActivity.class);
        startActivity(intent);
        finish();
    }

    private void popup(String message) {
        // AlertDialog pour informer l'utilisateur
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Erreur d'inscription");
        builder.setMessage(message);


        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



    public void NextActivity(int userid) {
        Intent intent = new Intent(InscriptionActivity.this, EventsActivity.class);
        intent.putExtra("EXTRA_USER", userid);
        startActivity(intent);
        finish();
    }
}
```

### activities.events.EventsActivity

```java
package com.example.dominotracker.activities.events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ...


import com.example.dominotracker.model.events.Calendrier;
import com.example.dominotracker.model.events.Event;
import com.example.dominotracker.R;

public class EventsActivity extends AppCompatActivity {
    private ArrayList<Event> eventList = new ArrayList<>();
    private DatePickerDialog datePickerDialog;
    private LocalDate selectedDate = LocalDate.now();

    private Calendrier calendrier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.calendrier = new Calendrier();
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.event_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EventAdapter adapter = new EventAdapter(eventList);
        recyclerView.setAdapter(adapter);

        // Ajouter des événements à la liste des événements :
        eventList = calendrier.getEvents();



        // Créer un DatePickerDialog pour permettre à l'utilisateur de sélectionner une date
        datePickerDialog = new DatePickerDialog(EventsActivity.this, (view, year, monthOfYear, dayOfMonth) -> {



            //monthOfYear est entre 0 et 11, or il faut entre 1 et 12
            selectedDate = LocalDate.of(year, monthOfYear+1, dayOfMonth);


            // Afficher la liste des événements filtrés
            filterAndUpdate(adapter, selectedDate);

        }, selectedDate.getYear(), selectedDate.getMonthValue()+1, selectedDate.getDayOfMonth());

        filterAndUpdate(adapter, selectedDate);

        Button btnSelectDate = findViewById(R.id.btn_select_date);
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ouvrir le DatePickerDialog
                datePickerDialog.show();
            }
        });

        Button previousDayButton = findViewById(R.id.previous_day_button);
        previousDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDate = selectedDate.minusDays(1); // Reculer d'un jour
                filterAndUpdate(adapter, selectedDate);
            }
        });

        Button nextDayButton = findViewById(R.id.next_day_button);
        nextDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDate = selectedDate.plusDays(1); // Reculer d'un jour
                filterAndUpdate(adapter, selectedDate);
            }
        });
        updateSelectedDateText();

        Button ajoutButton = findViewById(R.id.btn_insert);
        ajoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEventActivity();
                filterAndUpdate(adapter, selectedDate);

            }
        });



    }

    private void addEventActivity() {
        Intent intent = new Intent(this, AddEventActivity.class);
        intent.putExtra("EXTRA_USER", getIntent().getIntExtra("EXTRA_USER", 0));

        // Lancement de l'activité
        startActivity(intent);
        finish();
    }

    private void updateSelectedDateText() {
        Button dateSelect = findViewById(R.id.btn_select_date);
        dateSelect.setText(selectedDate.toString());
        datePickerDialog.updateDate(selectedDate.getYear(), selectedDate.getMonthValue()-1, selectedDate.getDayOfMonth());
    }

    private void filterAndUpdate(EventAdapter adapt, LocalDate selectedDate){
        updateSelectedDateText();
        adapt.setEventList(calendrier.filterToDate(eventList, selectedDate));
    }

}
```

### activities.events.AddEventActivity

```java
package com.example.dominotracker.activities.events;

import androidx.appcompat.app.AppCompatActivity;

import ...

import com.example.dominotracker.model.events.Calendrier;
import com.example.dominotracker.R;

public class AddEventActivity extends AppCompatActivity {

    private String selectedCategory;
    private Spinner spinner;
    private Button btnValider;
    private Calendrier calendrier;

    private int user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        this.calendrier = new Calendrier();

        Intent intent = getIntent();
        user = intent.getIntExtra("EXTRA_USER", 0);

        //Remplissage du spinner
        spinner = findViewById(R.id.spinner_choices);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, calendrier.getCategories());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnValider = findViewById(R.id.btn_valider);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSelectedEvent();

            }
        });

        Button btnAnnuler = findViewById(R.id.btn_annuler);
        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAct();

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                btnValider.setVisibility(View.VISIBLE);
                String selectedOption = adapterView.getItemAtPosition(position).toString();
                selectedCategory = selectedOption;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                btnValider.setVisibility(View.GONE);
                selectedCategory = "";
            }
        });


    }

    @Override
    public void onBackPressed(){
        finishAct();
    }

    private void finishAct() {
        Intent intent = new Intent(this, EventsActivity.class);
        intent.putExtra("EXTRA_USER", getIntent().getIntExtra("EXTRA_USER", 0));

        // Lancement de l'activité
        startActivity(intent);
        finish();
    }

    private void addSelectedEvent(){
        AddEventTask task = new AddEventTask(this);
        task.execute();
    }

    private class AddEventTask extends AsyncTask<Void, Void, Void> {

        ProgressBar progressBar;
        Context context;

        AddEventTask(Context c){
            this.context = c;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            ((Activity) context).addContentView(progressBar, params);
        }

        @Override
        protected Void doInBackground(Void... params) {
            calendrier.addEvent(selectedCategory, user);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);
            finishAct();
        }
    }





}
```

### activities.events.EventAdapter

```java
package com.example.dominotracker.activities.events;

import ...

import androidx.recyclerview.widget.RecyclerView;

import com.example.dominotracker.R;
import com.example.dominotracker.model.events.Event;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private List<Event> eventList;

    public EventAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.eventName.setText(event.getName());
        holder.eventTime.setText(event.getEventTime());
        holder.eventDescription.setText(event.getDescription());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView eventName;
        public TextView eventTime;
        public TextView eventDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.event_name);
            eventTime = itemView.findViewById(R.id.event_time);
            eventDescription = itemView.findViewById(R.id.event_description);
        }
    }
}
```

### model.connexion.ConnexionBD

```java
package com.example.dominotracker.model.connexion;


import ...

public class ConnexionBD {

    private String ip = "....";

    private String port = "3306";

    private String nomBD = "domino_tracker";

    private String url = "jdbc:mysql://"+ ip + ":" + port + "/"+ nomBD;

    private static final String user = "....";

    private static final String pass = "....";

    private Connection conn;


    private void connectToBD() throws ClassNotFoundException, SQLException {
        if(conn == null){
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(url, user, pass);
        }
    }


    public Statement getStatement() {

        try {

            connectToBD();

            Statement st = conn.createStatement();



            return st;

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();

            return null;

        }

    }

    public PreparedStatement getPreparedStatement(String sql) {

        try {

            connectToBD();

            PreparedStatement ps = conn.prepareStatement(sql);



            return ps;

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();

            return null;

        }


    }

    public int connectUser(String pseudo, String password) {

        try {

            connectToBD();
            if(pseudo.length()>16 || password.length()>100) return 0;

            String functionName = "connexion";
            String query = "SELECT " + functionName + "('" + pseudo + "','" + password + "')";

            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();

            return -1;

        }


    }

    public void addEvent(int user, int type) {

        try {

            connectToBD();
            String call = "{call Ajout_Evenement(?, ?)}";
            CallableStatement statement = conn.prepareCall(call);
            statement.setInt(1, user);
            statement.setInt(2, type);
            statement.execute();

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();

        }
    }
}

```

### model.user.User

```java
package com.example.dominotracker.model.user;

import com.example.dominotracker.model.connexion.ConnexionBD;

import ...

public class User {

    public int connexion(String pseudo, String mdp) {
        return new ConnexionBD().connectUser(pseudo, mdp);
    }

    public void inscription(String pseudo, String mdp, String email) throws InscriptionException {
        if(pseudo.equals("") || mdp.equals("") || email.equals("") || pseudo == null || mdp == null || email == null)
            throw new InscriptionException("Veuillez remplir tous les champs");

        try {
            String sql = "SELECT userid from user where ?=?;";

            PreparedStatement testStatement = new ConnexionBD().getPreparedStatement(sql);
            testStatement.setString(1, "username");
            testStatement.setString(2, pseudo);
            //Test username déjà pris

            ResultSet resultUsername = testStatement.executeQuery();
            if (resultUsername.next()) {
                throw new InscriptionException("Nom d'utilisateur déjà pris");
            }

            //Test email déjà pris
            testStatement.setString(1, "email");
            testStatement.setString(2, email);
            ResultSet resultEmail = testStatement.executeQuery();
            if (resultEmail.next()) {
                throw new InscriptionException("Email déjà utilisé");
            }

            //Insertion
            String sqlInsert = "INSERT INTO user(username, password, email) VALUES(?, SHA2(?, 256), ?);";
            PreparedStatement prepInsert = new ConnexionBD().getPreparedStatement(sqlInsert);
            prepInsert.setString(1, pseudo);
            prepInsert.setString(2, mdp);
            prepInsert.setString(3, email);
            prepInsert.executeUpdate();

        }
        catch(SQLException e){
            e.printStackTrace();
            throw new InscriptionException("Erreur avec la base de donnée");
        }
    }
}
```

### model.events.Calendrier

```java
package com.example.dominotracker.model.events;

import com.example.dominotracker.model.connexion.ConnexionBD;

import ...

public class Calendrier {

    public ArrayList<String> getCategories(){
        ArrayList<String> list = new ArrayList<>();


        try {
            ConnexionBD fct = new ConnexionBD();
            Statement st = fct.getStatement();

            String sqlQuery = "SELECT name FROM actioncat;";


            ResultSet rs = st.executeQuery(sqlQuery);
            while(rs.next()){
                String cat = rs.getString(1);
                list.add(cat);
            }


        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addEvent(String cat, int user){
        ConnexionBD fct = new ConnexionBD();

        synchronized (Calendrier.class) {
            fct.addEvent(user, getCatId(cat));
        }
    }

    public ArrayList<Event> filterToDate(ArrayList<Event> events, LocalDate date){
        ArrayList<Event> filteredEvents = new ArrayList<>();
        for (Event event : events) {
            if (event.getDate().isEqual(date)) {
                filteredEvents.add(event);
            }
        }
        return filteredEvents;
    }

    public ArrayList<Event> getEvents(){
        ArrayList<Event> eventList = new ArrayList<>();


        try {
            ConnexionBD fct = new ConnexionBD();
            Statement st = fct.getStatement();

            String sqlQuery = "SELECT H.time, U.username, C.name FROM historique H, user U, actioncat C WHERE H.user=U.userid AND H.category=C.category_id";

            synchronized (Calendrier.class) {
                ResultSet rs = st.executeQuery(sqlQuery);

                SimpleDateFormat heure = new SimpleDateFormat("HH:mm");
                SimpleDateFormat jour = new SimpleDateFormat("yyyy-MM-dd");
                while (rs.next()) {
                    Timestamp time = rs.getTimestamp(1);
                    String user = rs.getString(2);
                    String cat = rs.getString(3);
                    eventList.add(new Event(
                            cat,
                            heure.format(time),
                            "Par " + user,
                            LocalDate.parse(jour.format(time)))
                    );
                }
            }


        } catch(Exception e) {
            e.printStackTrace();
        }

        EventTimeComparator comparator = new EventTimeComparator();
        Collections.sort(eventList, comparator);
        return eventList;
    }

    private class EventTimeComparator implements Comparator<Event> {
        @Override
        public int compare(Event event1, Event event2) {
            String time1 = event1.getEventTime();
            String time2 = event2.getEventTime();
            return time1.compareTo(time2);
        }
    }

    private int getCatId(String name){
        int i = 0;
        try {
            ConnexionBD fct = new ConnexionBD();
            Statement st = fct.getStatement();

            String sqlQuery = "SELECT category_id FROM actioncat WHERE name='"+name+"';";

            synchronized (Calendrier.class) {
                ResultSet rs = st.executeQuery(sqlQuery);
                rs.next();
                i = rs.getInt(1);
            }


        } catch(Exception e) {
            e.printStackTrace();
        }
        return i;
    }
}
```

### model.events.Event

```java
package com.example.dominotracker.model.events;

import java.time.LocalDate;

public class Event {
    private String name;
    private String startTime;
    private String description;
    private LocalDate date;

    public Event(String name, String startTime, String description, LocalDate date) {
        this.name = name;
        this.startTime = startTime;
        this.description = description;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventTime() {
        return startTime;
    }

    public void setEventTime(String startTime) {
        this.startTime = startTime;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
```

### model.user.InscriptionException

```java
package com.example.dominotracker.model.user;
public class InscriptionException extends Throwable {
    public InscriptionException(String s) {
        super(s);
    }
}
```
