
# Room Persistence Library

Room je del Android Jetpack in predstavlja abstrakcijski sloj nad SQLite, ki omogoča boljšo in bolj varno integracijo lokalne podatkovne baze v Android aplikacije. 

## Zakaj Uporabiti Room?


### Prednosti
- **Preverjanje napak**: Zmanjša možnost napak, saj Room preveri SQL poizvedbe in sheme podatkovnih baz že med prevajanjem aplikacije.
- **Manj Kodiranja**: Room uporablja anotacije, ki zmanjšajo potrebo po ročnem pisanju boilerplate kode.
- **Podpora ORM**: Olajša shranjevanje in pridobivanje podatkovnih objektov iz podatkovne baze.
- **LiveData Integracija**: Omogoča avtomatsko posodabljanje uporabniškega vmesnika ob spremembah v podatkovni bazi.

### Slabosti
- **Omejena Agilnost**: Manj fleksibilen za kompleksne poizvedbe v primerjavi z neposredno uporabo SQLite.
- **Učna Krivulja**: Zahteva čas za učenje in razumevanje njegovih anotacij in API-jev.
- **Omejena Podpora**: Podpira samo SQLite, kar omejuje možnosti uporabe drugih podatkovnih baz.
- **Manjša zmogljivost**: Zaradi dodatne plasti abstrakcije je Room manj učinkovit kot neposredna uporaba SQLite.

## Uporaba

### Definiranje Entitet

 ```kotlin

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BussinessCollection(
    val collectionName: String,
    val colorHex : String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)


 ```
 ### DAO

 ```kotlin
 import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BussinessCollectionDao {
    @Insert
     fun insertCollection(collection: BussinessCollection)

    @Query("SELECT * FROM BussinessCollection")
     fun getAllCollections(): List<BussinessCollection>


     @Delete 
     fun deleteCollection(businessCollection: BusinessCollection)
     

}
 ```



### Uporaba baze    
```kotlin
@Database(entities = [Bussiness::class, BussinessNote::class, BussinessCollection::class, BussinessCollectionCrossRef::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bussinessDao(): BussinessDao
    abstract fun businessNoteDao(): BussinessNoteDao
    abstract fun businessCollectionDao(): BussinessCollectionDao
    abstract  fun bussinessCollectionCrossRefDao(): BussinessCollectionCrossRefDao


    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).
                fallbackToDestructiveMigration()
                    .
                        allowMainThreadQueries().
                build()
                INSTANCE = instance
                return instance
            }
        }
    }

```


### Primer pridobivanja podatkov iz baze
```kotlin

private fun loadCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            val collectionsList = MyApplication.database.businessCollectionDao().getAllCollections()
            _collections.emit(collectionsList)
        }
    }
    
```

### Database Inspector
![Database Inspector](https://i.imgur.com/Ir59raO.png)
 



### Licenca
Room je del Android Jetpacka in je na voljo pod [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0), ki je ena izmed najbolj liberalnih in odprtih licenc.

### Število Uporabnikov
Kot del Android Jetpacka ima Room veliko uporabnikov a natančno število ni znano.

### Vzdrževanje Projekta
- **Število Razvijalcev**: Vzdržuje ga ekipa razvijalcev pri Googlu kot del Android ekosistema.
- **Zadnji Popravek**: : 29. november 2023, verzija 2.6.1, Za najnovejše informacije o popravkih lahko obiščemo: [uradno stran projekta](https://developer.android.com/jetpack/androidx/releases/room).


