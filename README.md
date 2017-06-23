# **RepairLog**

Πρόκειται για μια Andoird εφαρμογή που υλοποιήθηκε στα πλαίσια εργασίας για το Μεταπτυχιακό μου.

Το αντικείμενο της εφαρμογής είναι να μπορεί χρήστης να καταχωρείτα οχήματα τα οποία έχει καθώς και τις επισκευές που έχει κάνει σε αυτά.  

### **ΕΡΓΑΛΕΙΑ ΑΝΑΠΤΥΞΗΣ**
Για την ανάπτυξη της εφαρμογής χρησιμοποιήθηκε Android Studio που είναι και το επίσημο εργαλείο ανάπτυξης που προτείνει και η Google για την ανάπτυξη εφαρμογών σε περιβάλλον Android. 

### **ΠΑΡΟΥΣΙΑΣΗ ΤΗΣ ΕΦΑΡΜΟΓΗΣ**

Το λογότυπο της εφαρμογής καθώς και το όνομά της όταν κάποιος την  κάνει εγκατάσταση στη φορητή του συσκευή  φαίνονται στη παρακάτω φωτογραφία.

![enter image description here](https://raw.githubusercontent.com/tsotzolas/Photos/master/RepairLog/1.png)
-
Με το που την ανοίγει  ο χρήστης την εφαρμογή τον μεταφέρει στην σελίδα να κάνει Google Sign In . 


![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/2.png?raw=true)

Και στη συνέχεια μόλις ολοκληρώσει το Google Sign In θα του εμφανίσει τις επιλογές να  μεταβεί στην αρχική σελίδα της εφαρμογής 

![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/3.png?raw=true)



Εδώ βλέπουμε ότι του δίνεται και η δυνατότητα να κάνει κάνει και Google Sign Out καθώς και Disconnect. 
<p/>


Με το που πατήσει να μεταβεί ο χρήστης στην αρχική σελίδα γίνεται και ο συγχρονισμός των δεδομένων της εφαρμογής που βρίσκονται  στην συσκευή του χρήστη με τα δεδομένα που είναι αποθηκευμένα στο cloud. 
![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/4.png?raw=true)
<p/>
Στη συνέχεια εμφανίζεται η αρχική σελίδα της εφαρμογής

![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/5.png?raw=true)

<p/>
Να σημειωθεί σε αυτό το σημείο ότι η εφαρμογή είναι δίγλωσση , και αναγνωρίσει αν ο χρήστης έχει επιλεγμένη γλώσσα στο κινητό του τα Αγγλικά ή τα Ελληνικά και του εμφανίζει αντίστοιχα την γλώσσα. Φυσικά ο χρήστης μπορεί να αλλάξει και αυτός την γλώσσα της εφαρμογής επιλέγοντας πάνω δεξιά τις

![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/6.png?raw=true) 

και στη συνέχεια επιλέγοντας της επιλογή 

![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/7.png?raw=true)

Αρχικά ο χρήστης, από την αρχική οθόνη, θα πρέπει να επιλέξει 

![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/8.png?raw=true) 

ώστε να μπορέσει να προσθέσει κάποιο όχημα. Πατώντας την επιλογή αυτή θα πρέπει να επιλέξει αν θέλει να καταχωρήσει κάποιο αυτοκίνητο ή κάποια μηχανή 

![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/9.png?raw=true) 

Έστω ότι θέλει να προσθέσει ένα αυτοκίνητο. Τότε θα του εμφανίσει να συμπληρώσει τα στοιχεία του οχήματος. 

![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/10.png?raw=true) 

Έστω ότι θέλει να προσθέσει ένα αυτοκίνητο. Τότε θα του εμφανίσει να συμπληρώσει τα στοιχεία του οχήματος. 

![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/11.png?raw=true) 

Θα πρέπει να επιλέξει πρώτα την χρονολογία, μετά τη μάρκα του αυτοκινήτου , στη συνέχεια το μοντέλο και τέλος τα κυβικά του αυτοκινήτου. Ακόμα προαιρετικά μπορεί ο χρήστης να βάλε και μια φωτογραφία του αυτοκινήτου του , ειδάλλως θα μπει μια προεπιλεγμένη φωτογραφία. Να σημειωθεί ότι τα δεδομένα των αυτοκινήτων δεν ορίζονται από την εφαρμογή αλλά από εξωτερικό Rest API . Οπότε αν κάποιες καταχωρήσεις λείπουν δεν είναι από δική μου υπαιτιότητα.  
Στην παρακάτω εικόνα φαίνεται πώς θα είναι όταν θα έχει συμπληρώσει όλα τα στοιχεία. 

![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/12.png?raw=true) 

Να σημειωθεί ότι η εφαρμογή δεν αφήνει τον χρήστη να αφήσει κάποια από τα πεδία κενά και τον προτρέπει να τα συμπληρώσει. 
Στη συνέχεια πρέπει να επιλέξει να αποθηκεύσει την καταχώρηση. 
Μετά την αποθήκευση ο χρήστης μεταφέρεται στην αρχική σελίδα της εφαρμογής. 
Επιλέγοντας την επιλογή να δει τα οχήματά του 
![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/13.png?raw=true)

Επιλέγοντας πάνω σε κάποιο από αυτά βλέπει την λίστα με τις εργασίες που έχουν γίνει σε αυτό. 
![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/14.png?raw=true)
 

Εκεί υπάρχουν και οι επιλογές άμα θέλει ο χρήστης να διαγράψει το όχημα ή να προσθέσει κάποια ακόμα επισκευή.
Στην περίπτωση που θέλει  να διαγράψει το όχημα θα ερωτηθεί άμα θέλει να διαγράψει και θα προχωρήσει στην διαγραφή του οχήματος.
Στην περίπτωση που θέλει να προσθέσει μια νέα εργασία θα μεταβεί στην οθόνη της προσθήκης νέας εργασίας.
Στην περίπτωση που θέλει να δει την επισκευή την επιλέγει και μπορεί να την δει ολόκληρη.
![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/15.png?raw=true)
 

Στην περίπτωση όπου θέλει ο χρήστης να διαγράψει μια εργασία θα πρέπει να πατήσει παρατεταμένα πάνω στην εργασία και τότε θα ερωτηθεί άμα θέλει να διαγράψει την συγκεκριμένη καταχώρηση.
![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/16.png?raw=true)

Η σελίδα εισαγωγής μιας νέας εργασίας είναι όπως παρακάτω.
![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/17.png?raw=true)

Θα πρέπει ο χρήστης να επιλέξει την ημερομηνία που έγινε η καταχώρηση , τα χιλιόμετρα του οχήματος καθώς και το κόστος και την περιγραφή της εργασίας.
![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/18.png?raw=true)

![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/19.png?raw=true)
 

Και στο τέλος να αποθηκεύσει την καταχώρηση.

![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/20.png?raw=true)

Η ίδια διαδικασία γίνεται αν θέλει ο χρήστης να κάνει εισαγωγή κάποια μηχανή απλά στην οθόνη που πρέπει να επιλέξει αν θέλει να δημιουργήσει μια εισαγωγή για αυτοκίνητο ή μηχανή πρέπει να επιλέξει τη μηχανή.

![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/21.png?raw=true)

![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/22.png?raw=true) 

Μια διαφορά σε σχέση με το αμάξι είναι ότι ο χρήστης θα πρέπει να πληκτρολογήσει όλα τα πεδία για την καταχώρηση. Δεν βρήκα κάποιο αντίστοιχο REST API για της μηχανές. 
Aπό την αρχική οθόνη ο χρήστης μπορεί να δει την τοποθεσία του στο χάρτη , αφού έχει ενεργοποιήσει πριν το GPS της φορητής του συσκευής , πατώντας στην επιλογή 

![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/23.png?raw=true) 

![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/24.png?raw=true) 

Τέλος για να μπορέσει ο χρήστης να βγει από την εφαρμογή αρκεί από την αρχική σελίδα να πατήσει το Back Button της φορητής του συσκευής  όπου θα ερωτηθεί άμα θέλει να εξέλθει της εφαρμογής

![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/25.png?raw=true) 


## **ΣΗΜΕΙΑ ΙΔΙΑΙΤΕΡΟΥ ΠΡΟΓΡΑΜΜΑΤΙΣΤΙΚΟΥ ΕΝΔΙΑΦΕΡΟΝΤΟΣ**

### **ΑΠΟΘΗΚΕΥΣΗ ΤΩΝ ΔΕΔΟΜΕΝΩΝ**

Για την αποθήκευση των δεδομένων έχει χρησιμοποιηθεί για βάση το [Realm](https://realm.io/). Η βάση είναι τοπικά και μπορεί να λειτουργήσει και χωρίς τη χρήση του διαδικτύου. Αυτό όμως που έχει ιδιαίτερο ενδιαφέρων είναι ότι έχουμε υλοποιήσει συγχρονισμό των δεδομένων μας με τη χρήση του [Realm Object Server.](https://realm.io/docs/realm-object-server/)  


![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/26.png?raw=true) 

Το σχήμα δείχνει ακριβώς πώς λειτουργεί ή όλη διαδικασία και είναι μια Real Time Database. Ο Realm Object Server που έχει στηθεί, βρίσκεται σε ένα VM στον [Okeanos](https://okeanos.grnet.gr/home/)  σε ένα  Ubuntu 16.04 λειτουργικό σύστημα . 

![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/27.png?raw=true) 

Το διαχειριστικό UI του Real Object Server  φαίνεται όπως παρακάτω

![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/28.png?raw=true) 

Για την υλοποίηση αυτό χρειάζεται να δημιουργηθεί ένας χρήστης στον Realm Object Server. 
Αυτό γίνεται στον κώδικα αμέσως μετά το Google Sign Login του χρήστη . Αφού γίνει το Google Sign Login , χρησιμοποιούμε το Google email του χρήστη σας username  και το Google Id για password και φτιάχνουμε τον χρήστη 

   

    private void handleSignInResult(GoogleSignInResult result) { 
        Log.d(TAG, "handleSignInResult:" + result.isSuccess()); 
        if (result.isSuccess()) { 
            // Signed in successfully, show authenticated UI. 
            acct = result.getSignInAccount(); 
            String username = ""; 
            String password = ""; 
            //Κάνουμε έναν έλεγχο άμα ο χρήστης έχει το συγκεκριμένο email να του το αλλάξουμε 
            // γιατί με το email αυτό είναι ο λογαρισμός του Database Administrator στο Realm Object Server 
            if (acct != null) { 
                if ("tsotzolas@gmail.com".equals(acct.getEmail())) { 
                    username = "tsotzo1@gmail.com"; 
                } else { 
                    //σαν username στο Realm βάζουμε το email του χρήστη απο το Google Sign In 
                    username = acct.getEmail(); 
                } 
                //Σαν password στο Realm βάζουμε το google id 
                password = acct.getId(); 
            } 
 
            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName())); 
            Realm.init(getApplicationContext()); 
            //Αφού ο χρήστης κάνει google Sign In μετα φτιάχνουμε χρήστη στο Realm Object Server 
            SyncUser.loginAsync(SyncCredentials.usernamePassword(username, password, true), AUTH_URL, new SyncUser.Callback() { 
                @Override 
                public void onSuccess(SyncUser user) { 

                    Toast.makeText(SignInActivity.this, "Create User in Realm Object Server", Toast.LENGTH_LONG).show(); 
                } 
 
                @Override 
                public void onError(ObjectServerError error) { 
                    String errorMsg; 
                    switch (error.getErrorCode()) { 
                        case EXISTING_ACCOUNT: 
                            errorMsg = "Account already exists"; 
                            break; 
                        default: 
                            errorMsg = error.toString(); 
                    } 
                } 
            }); 
            updateUI(true); 
 Στη συνέχεια όταν ο χρήστης πατήσει να πάει στην αρχική οθόνη κάνουμε login του χρηστη στον Realm Object Server. Σε αυτό το σημείο έχουμε βάλει μια χρονοκαθηστέρηση των 5 sec ώστε να προλάβει η βάση του Realm να συγχρονίσει με τον Realm Object Server. 

    public void gotoMain(View view) { 
    showProgressDialog1(); 
 
    //Κάνουμε Συγχρονισμό του Realm με τον Realm Object Server 
    SyncRealm.realmSync(); 
 
    //Καθηστερούμε την όλη διαδικασία για να προλαβει να κάνει συγχρονισμο το Realm 
    Handler handler = new Handler(); 
    handler.postDelayed(new Runnable() { 
 
        @Override 
        public void run() { 
            hideProgressDialog1(); 
            Intent ki = new Intent(SignInActivity.this, MainActivity.class); 
            startActivity(ki); 
 
        } 
 
    }, 5000); // 5000ms delay

### **ΔΕΔΟΜΕΝΑ ΑΥΤΟΚΙΝΗΤΩΝ**
Για να μπορέσει να συμπληρώσει ο χρήστης τα δεδομένα των αυτοκινήτων του χρησιμοποίησα ένα REST API που βρήκα στο διαδίκτυο και βρίσκεται στην παρακάτω διεύθυνση [https://www.carqueryapi.com/](https://www.carqueryapi.com/)   
Για να μπορέσουμε να πάρουμε τα δεδομένα χρησιμοποιήσαμε το Retrofit για να μπορέσουμε να κάνουμε επικοινωνήσουμε με το API. Ενδεικτικά παραθέτω τον κώδικα όπου καλούμε και παίρνουμε τα δεδομένα από τις μάρκες των αυτοκινήτων. 

    CarService carService = retrofit.create(CarService.class);
    carService.getMake("getMakes", year, "0").enqueue(new Callback<Make>() { 
    @Override 
    public void onResponse(Call<Make> call, Response<Make> response) { 
        if (response.isSuccessful()) { 
            makeList = response.body(); 
            makeListString1 = new ArrayList<String>(); 
            //Βάζουμε αυτό στην αρχή για να ξέρουμε αν έχει επιλέξει κάτι ο χρήστης ή όχι 
            if (!makeListString1.contains(".......")) { 
                makeListString1.add("......."); 
            } 
            //Γεμίζουμε τη list με τα makes 
            for (int i = 0; i < makeList.getMakes().size(); i++) { 
                makeListString1.add(makeList.getMakes().get(i).getMakeDisplay()); 
            } 
            //Καλούμε για να γεμίσουμε τις makes 
            fillListMakes(); 
            hideProgressDialog(); 
        } else { 
            Log.e(TAG, "Failed. Status: " + response.code()); 
            Log.i(TAG, "-------->" + call.request().url()); 
        } 
    }  

### **ΔΟΚΙΜΕΣ ΕΦΑΡΜΟΓΗΣ**

Η εφαρμογή δόθηκε για δοκιμή σε άλλους προγραμματιστές αλλά και σε απλούς χρήστες οι οποίοι μας επισήμαναν διάφορα λειτουργικά προβλήματα καθώς και λεκτικά σφάλματα που είχα. Ακόμα διαπιστώθηκαν διάφορες δυσλειτουργίες που είχε η εφαρμογή σε διαφορετικές συσκευές. Τα σφάλματα που προέκυπταν έρχονται και στο crash report του Firebase  
![enter image description here](https://github.com/tsotzolas/Photos/blob/master/RepairLog/29.png?raw=true)

