package com.example.ecokids

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "EcoKids.db"
        const val DATABASE_VERSION = 11

        const val TABLE_ANIMALS = "animals"
        const val TABLE_FRUITS = "fruits"
        const val TABLE_QUIZ = "quiz"
        const val TABLE_USERS = "users"

        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_HABITAT = "habitat"
        const val COL_COLOR = "color"
        const val COL_DESC = "description"
        const val COL_IMAGE = "image_res"
        
        const val COL_QUESTION = "question"
        const val COL_OPT_A = "option_a"
        const val COL_OPT_B = "option_b"
        const val COL_OPT_C = "option_c"
        const val COL_OPT_D = "option_d"
        const val COL_ANSWER = "correct_answer"
        const val COL_LEVEL = "level"
        
        // User columns
        const val COL_USERNAME = "username"
        const val COL_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createAnimalsTable = ("CREATE TABLE " + TABLE_ANIMALS + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME + " TEXT,"
                + COL_HABITAT + " TEXT,"
                + COL_DESC + " TEXT,"
                + COL_IMAGE + " INTEGER" + ")")
        db.execSQL(createAnimalsTable)

        val createFruitsTable = ("CREATE TABLE " + TABLE_FRUITS + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME + " TEXT,"
                + COL_COLOR + " TEXT,"
                + COL_DESC + " TEXT,"
                + COL_IMAGE + " INTEGER" + ")")
        db.execSQL(createFruitsTable)


        val createQuizTable = ("CREATE TABLE " + TABLE_QUIZ + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_QUESTION + " TEXT,"
                + COL_OPT_A + " TEXT,"
                + COL_OPT_B + " TEXT,"
                + COL_OPT_C + " TEXT,"
                + COL_OPT_D + " TEXT,"
                + COL_ANSWER + " INTEGER,"
                + COL_LEVEL + " TEXT,"
                + COL_IMAGE + " INTEGER" + ")")
        db.execSQL(createQuizTable)
        
        // Users table for Parent/Teacher login
        val createUsersTable = ("CREATE TABLE " + TABLE_USERS + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_USERNAME + " TEXT UNIQUE,"
                + COL_PASSWORD + " TEXT" + ")")
        db.execSQL(createUsersTable)
        
        insertInitialData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ANIMALS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FRUITS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_QUIZ")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    private fun insertInitialData(db: SQLiteDatabase) {
        // Animals
        addAnimal(db, "Kucing", "Rumah", "Kucing adalah hewan berkaki empat yang memiliki bulu halus. Kucing sering dipelihara di rumah sebagai hewan peliharaan. Kucing suka bermain dan tidur. Kucing dapat mengeong untuk berkomunikasi dengan manusia.", R.drawable.img_kucing)
        addAnimal(db, "Anjing", "Rumah", "Anjing adalah hewan berkaki empat yang setia kepada manusia. Anjing sering dipelihara untuk menjaga rumah. Anjing memiliki indera penciuman yang sangat tajam dan suka bermain dengan manusia.", R.drawable.img_anjing)
        addAnimal(db, "Sapi", "Peternakan", "Sapi adalah hewan ternak berukuran besar. Sapi hidup di peternakan dan memakan rumput. Sapi menghasilkan susu yang bermanfaat bagi manusia. Susu sapi digunakan untuk minuman dan makanan.", R.drawable.img_sapi)
        addAnimal(db, "Kambing", "Peternakan", "Kambing adalah hewan ternak yang memakan rumput dan daun. Kambing hidup di peternakan. Kambing menghasilkan daging dan susu yang berguna bagi manusia.", R.drawable.img_kambing)
        addAnimal(db, "Ayam", "Peternakan", "Ayam adalah hewan ternak yang memiliki dua kaki dan sayap. Ayam hidup di peternakan atau di sekitar rumah. Ayam menghasilkan telur dan daging yang sering dikonsumsi manusia.", R.drawable.img_ayam)
        addAnimal(db, "Ikan", "Air", "Ikan adalah hewan yang hidup di air. Ikan bernapas menggunakan insang dan memiliki sirip untuk berenang. Ikan tidak memiliki kaki. Banyak ikan hidup di sungai, danau, dan laut.", R.drawable.img_ikan)
        addAnimal(db, "Burung", "Udara", "Burung adalah hewan yang memiliki sayap dan dapat terbang. Burung hidup di pepohonan dan udara. Burung memiliki paruh dan bulu. Burung bertelur untuk berkembang biak.", R.drawable.img_burung)
        addAnimal(db, "Kelinci", "Darat", "Kelinci adalah hewan berbulu halus dengan telinga panjang. Kelinci hidup di darat dan sering dipelihara oleh manusia. Kelinci memakan sayuran dan wortel. Kelinci bergerak dengan cara melompat.", R.drawable.img_kelinci)
        addAnimal(db, "Singa", "Hutan", "Singa adalah hewan buas yang hidup di hutan dan padang rumput. Singa memiliki tubuh besar dan gigi yang tajam. Singa disebut sebagai raja hutan. Singa memakan daging.", R.drawable.img_singa)
        addAnimal(db, "Gajah", "Hutan", "Gajah adalah hewan darat terbesar di dunia. Gajah hidup di hutan dan memiliki belalai yang panjang. Belalai digunakan untuk mengambil makanan dan minum. Gajah memakan tumbuhan.", R.drawable.img_gajah)
        
        // Fruits
        addFruit(db, "Apel", "Merah", "Apel adalah buah yang rasanya segar. Apel berwarna merah dan berbentuk bulat. Apel baik untuk kesehatan tubuh. Apel membantu menjaga tubuh tetap sehat.", R.drawable.img_apel)
        addFruit(db, "Pisang", "Kuning", "Pisang adalah buah berwarna kuning. Pisang rasanya manis dan lembut. Pisang memberi energi untuk tubuh. Pisang baik dimakan saat sarapan.", R.drawable.img_pisang)
        addFruit(db, "Jeruk", "Oranye", "Jeruk adalah buah berwarna oranye. Jeruk rasanya asam manis. Jeruk mengandung vitamin C. Vitamin C membantu menjaga daya tahan tubuh.", R.drawable.img_jeruk)
        addFruit(db, "Mangga", "Hijau / Kuning", "Mangga adalah buah yang rasanya manis. Mangga dapat berwarna hijau atau kuning. Mangga mengandung banyak vitamin. Mangga baik untuk kesehatan tubuh.", R.drawable.img_mangga)
        addFruit(db, "Anggur", "Ungu", "Anggur adalah buah kecil yang tumbuh bergerombol. Anggur berwarna ungu dan rasanya manis. Anggur mengandung vitamin. Anggur baik untuk tubuh.", R.drawable.img_anggur)
        addFruit(db, "Stroberi", "Merah", "Stroberi adalah buah berwarna merah. Stroberi memiliki rasa asam manis. Stroberi mengandung vitamin C. Stroberi baik untuk kesehatan.", R.drawable.img_stroberi)
        addFruit(db, "Semangka", "Hijau / Merah", "Semangka memiliki kulit hijau dan daging merah. Semangka mengandung banyak air. Semangka membantu menghilangkan rasa haus. Semangka menyegarkan tubuh.", R.drawable.img_semangka)
        addFruit(db, "Nanas", "Kuning", "Nanas adalah buah berwarna kuning. Nanas rasanya manis dan sedikit asam. Nanas mengandung vitamin. Nanas baik untuk pencernaan.", R.drawable.img_nanas)
        addFruit(db, "Pepaya", "Oranye", "Pepaya adalah buah berwarna oranye. Pepaya rasanya manis. Pepaya membantu pencernaan. Pepaya baik untuk kesehatan tubuh.", R.drawable.img_pepaya)
        addFruit(db, "Alpukat", "Hijau", "Alpukat adalah buah berwarna hijau. Alpukat memiliki daging yang lembut. Alpukat mengandung lemak baik. Lemak baik membantu menjaga kesehatan tubuh.", R.drawable.img_alpukat)

        // Quiz
        // Quiz Level A
        addQuiz(db, "Hewan apa ini?", "Anjing", "Kucing", "Ayam", "Kelinci", 1, "A", R.drawable.img_kucing) // 1=B (Kucing)
        addQuiz(db, "Hewan apa ini?", "Kucing", "Anjing", "Sapi", "Gajah", 1, "A", R.drawable.img_anjing) // 1=B (Anjing)
        addQuiz(db, "Buah apa ini?", "Apel", "Jeruk", "Mangga", "Anggur", 0, "A", R.drawable.img_apel) // 0=A (Apel)
        addQuiz(db, "Buah apa ini?", "Pepaya", "Pisang", "Nanas", "Alpukat", 1, "A", R.drawable.img_pisang) // 1=B (Pisang) - Updated to img_pisang
        addQuiz(db, "Hewan apa ini?", "Burung", "Ayam", "Ikan", "Kelinci", 2, "A", R.drawable.img_ikan) // 2=C (Ikan)

        // Quiz Level B
        addQuiz(db, "Ikan hidup di…", "Rumah", "Hutan", "Air", "Udara", 2, "B", R.drawable.img_ikan)
        addQuiz(db, "Sapi hidup di…", "Rumah", "Air", "Peternakan", "Udara", 2, "B", R.drawable.img_sapi)
        addQuiz(db, "Warna buah apel adalah…", "Kuning", "Ungu", "Merah", "Hijau", 2, "B", R.drawable.img_apel)
        addQuiz(db, "Warna buah pisang adalah…", "Merah", "Kuning", "Oranye", "Ungu", 1, "B", R.drawable.img_pisang) // Updated to img_pisang
        addQuiz(db, "Singa hidup di…", "Rumah", "Peternakan", "Air", "Hutan", 3, "B", R.drawable.img_singa)

        // Quiz Level C
        addQuiz(db, "Hewan yang bisa terbang adalah…", "Ikan", "Kucing", "Burung", "Sapi", 2, "C", R.drawable.img_burung)
        addQuiz(db, "Hewan yang memiliki sirip adalah…", "Ayam", "Ikan", "Burung", "Kelinci", 1, "C", R.drawable.img_ikan)
        addQuiz(db, "Buah yang banyak mengandung air adalah…", "Pisang", "Apel", "Semangka", "Alpukat", 2, "C", R.drawable.img_semangka) // Updated to img_semangka
        addQuiz(db, "Buah yang baik untuk pencernaan adalah…", "Jeruk", "Pepaya", "Anggur", "Stroberi", 1, "C", R.drawable.img_pepaya) // Updated to img_pepaya
        addQuiz(db, "Buah yang memberi energi adalah…", "Semangka", "Pisang", "Alpukat", "Mangga", 1, "C", R.drawable.img_pisang) // Updated to img_pisang

        // Quiz Level D
        addQuiz(db, "Hewan terbesar di darat adalah…", "Singa", "Sapi", "Gajah", "Kambing", 2, "D", R.drawable.img_gajah)
        addQuiz(db, "Hewan yang bertelur adalah…", "Kucing", "Ayam", "Kambing", "Sapi", 1, "D", R.drawable.img_ayam)
        addQuiz(db, "Buah berwarna ungu adalah…", "Apel", "Anggur", "Mangga", "Pisang", 1, "D", R.drawable.img_anggur) // Updated to img_anggur
        addQuiz(db, "Buah yang mengandung lemak baik adalah…", "Jeruk", "Apel", "Alpukat", "Semangka", 2, "D", R.drawable.img_alpukat) // Updated to img_alpukat
        addQuiz(db, "Buah yang rasanya asam manis dan kaya vitamin C adalah…", "Pisang", "Alpukat", "Jeruk", "Pepaya", 2, "D", R.drawable.img_jeruk) // Updated to img_jeruk
    }

    private fun addAnimal(db: SQLiteDatabase, name: String, habitat: String, desc: String, image: Int) {
        val values = ContentValues()
        values.put(COL_NAME, name)
        values.put(COL_HABITAT, habitat)
        values.put(COL_DESC, desc)
        values.put(COL_IMAGE, image)
        db.insert(TABLE_ANIMALS, null, values)
    }

    private fun addFruit(db: SQLiteDatabase, name: String, color: String, desc: String, image: Int) {
        val values = ContentValues()
        values.put(COL_NAME, name)
        values.put(COL_COLOR, color)
        values.put(COL_DESC, desc)
        values.put(COL_IMAGE, image)
        db.insert(TABLE_FRUITS, null, values)
    }

    private fun addQuiz(db: SQLiteDatabase, question: String, optA: String, optB: String, optC: String, optD: String, ans: Int, level: String, image: Int) {
        val values = ContentValues()
        values.put(COL_QUESTION, question)
        values.put(COL_OPT_A, optA)
        values.put(COL_OPT_B, optB)
        values.put(COL_OPT_C, optC)
        values.put(COL_OPT_D, optD)
        values.put(COL_ANSWER, ans)
        values.put(COL_LEVEL, level)
        values.put(COL_IMAGE, image)
        db.insert(TABLE_QUIZ, null, values)
    }

    fun getAllAnimals(): List<Animal> {
        val list = ArrayList<Animal>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_ANIMALS", null)
        if (cursor.moveToFirst()) {
            do {
                list.add(Animal(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4)
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun getAllFruits(): List<Fruit> {
        val list = ArrayList<Fruit>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_FRUITS", null)
        if (cursor.moveToFirst()) {
            do {
                list.add(Fruit(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4)
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun getAllQuizzes(): List<QuizQuestion> {
        val list = ArrayList<QuizQuestion>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_QUIZ", null)
        if (cursor.moveToFirst()) {
            do {
                list.add(QuizQuestion(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getString(7),
                    cursor.getInt(8)
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
      // CRUD Operations for Animals
    fun addAnimal(name: String, habitat: String, desc: String, imageResId: Int): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NAME, name)
        values.put(COL_HABITAT, habitat)
        values.put(COL_DESC, desc)
        values.put(COL_IMAGE, imageResId)
        return db.insert(TABLE_ANIMALS, null, values)
    }

    fun updateAnimal(id: Int, name: String, habitat: String, desc: String, imageResId: Int): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NAME, name)
        values.put(COL_HABITAT, habitat)
        values.put(COL_DESC, desc)
        values.put(COL_IMAGE, imageResId)
        return db.update(TABLE_ANIMALS, values, "$COL_ID=?", arrayOf(id.toString()))
    }

    fun deleteAnimal(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_ANIMALS, "$COL_ID=?", arrayOf(id.toString()))
    }

    // CRUD Operations for Fruits
    fun addFruit(name: String, color: String, desc: String, imageResId: Int): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NAME, name)
        values.put(COL_COLOR, color)
        values.put(COL_DESC, desc)
        values.put(COL_IMAGE, imageResId)
        return db.insert(TABLE_FRUITS, null, values)
    }

    fun updateFruit(id: Int, name: String, color: String, desc: String, imageResId: Int): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NAME, name)
        values.put(COL_COLOR, color)
        values.put(COL_DESC, desc)
        values.put(COL_IMAGE, imageResId)
        return db.update(TABLE_FRUITS, values, "$COL_ID=?", arrayOf(id.toString()))
    }    
    // ================= QUIZ LOGIC (DYNAMIC + MANUAL) =================

    fun getQuizzesByLevel(level: String): List<QuizQuestion> {
        val quizList = ArrayList<QuizQuestion>()
        
        // 1. Ambil Manual Quiz dari Database
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_QUIZ WHERE $COL_LEVEL = ?", arrayOf(level))
        if (cursor.moveToFirst()) {
            do {
                val q = QuizQuestion(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_QUESTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_OPT_A)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_OPT_B)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_OPT_C)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_OPT_D)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ANSWER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_LEVEL)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_IMAGE))
                )
                quizList.add(q)
            } while (cursor.moveToNext())
        }
        cursor.close()

        // 2. Generate Dynamic Quiz dari Materi (Hanya level A & B)
        // Level A: Tebak Nama Hewan/Buah dari Gambar
        if (level == "A" || level == "B") {
            val generatedQuizzes = generateDynamicQuizzes()
            quizList.addAll(generatedQuizzes)
        }

        return quizList.shuffled().take(10) // Ambil max 10 soal acak
    }

    private fun generateDynamicQuizzes(): List<QuizQuestion> {
        val list = ArrayList<QuizQuestion>()
        val animals = getAllAnimals()
        val fruits = getAllFruits()
        
        // Combine names for distractors
        val allNames = ArrayList<String>()
        animals.forEach { allNames.add(it.name) }
        fruits.forEach { allNames.add(it.name) }

        // Generate Animal Questions
        for (a in animals) {
            val q = createGuessNameQuestion(a.name, a.imageResId, allNames)
            list.add(q)
        }

        // Generate Fruit Questions
        for (f in fruits) {
            val q = createGuessNameQuestion(f.name, f.imageResId, allNames)
            list.add(q)
        }
        
        return list
    }

    private fun createGuessNameQuestion(correctName: String, imageRes: Int, allNames: List<String>): QuizQuestion {
        // Ambil 3 jawaban salah secara acak
        val distractors = allNames.filter { it != correctName }.shuffled().take(3)
        
        // Gabung dan acak posisi
        val options = (distractors + correctName).shuffled()
        val correctIndex = options.indexOf(correctName)
        
        return QuizQuestion(
            id = -1, // ID negatif penanda dynamic
            question = "Apakah nama gambar ini?",
            optionA = options[0],
            optionB = options[1],
            optionC = options[2],
            optionD = options[3],
            correctAnswer = correctIndex,
            level = "A", // Default level dynamic
            imageResId = imageRes
        )
    }

    // CRUD Manual Quiz
    fun addQuiz(q: QuizQuestion): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_QUESTION, q.question)
        values.put(COL_OPT_A, q.optionA)
        values.put(COL_OPT_B, q.optionB)
        values.put(COL_OPT_C, q.optionC)
        values.put(COL_OPT_D, q.optionD)
        values.put(COL_ANSWER, q.correctAnswer)
        values.put(COL_LEVEL, q.level)
        values.put(COL_IMAGE, q.imageResId)
        return db.insert(TABLE_QUIZ, null, values)
    }

    fun deleteQuiz(id: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_QUIZ, "$COL_ID = ?", arrayOf(id.toString()))
        return result > 0
    }

    fun updateQuiz(q: QuizQuestion): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_QUESTION, q.question)
        values.put(COL_OPT_A, q.optionA)
        values.put(COL_OPT_B, q.optionB)
        values.put(COL_OPT_C, q.optionC)
        values.put(COL_OPT_D, q.optionD)
        values.put(COL_ANSWER, q.correctAnswer)
        values.put(COL_LEVEL, q.level)
        values.put(COL_IMAGE, q.imageResId)
        val result = db.update(TABLE_QUIZ, values, "$COL_ID = ?", arrayOf(q.id.toString()))
        return result > 0
    }

    fun getAllManualQuizzes(): List<QuizQuestion> {
        val quizList = ArrayList<QuizQuestion>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_QUIZ ORDER BY $COL_LEVEL, $COL_ID DESC", null)
        if (cursor.moveToFirst()) {
            do {
                val q = QuizQuestion(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_QUESTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_OPT_A)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_OPT_B)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_OPT_C)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_OPT_D)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ANSWER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_LEVEL)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_IMAGE))
                )
                quizList.add(q)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return quizList
    }



    // ================= USER FUNCTIONS =================

    fun addUser(user: User): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_USERNAME, user.username)
        values.put(COL_PASSWORD, user.password)
        return db.insert(TABLE_USERS, null, values)
    }

    fun checkUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val columns = arrayOf(COL_ID)
        val selection = "$COL_USERNAME = ? AND $COL_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null)
        val count = cursor.count
        cursor.close()
        return count > 0
    }

    fun checkUsernameExists(username: String): Boolean {
        val db = this.readableDatabase
        val columns = arrayOf(COL_ID)
        val selection = "$COL_USERNAME = ?"
        val selectionArgs = arrayOf(username)
        val cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null)
        val count = cursor.count
        cursor.close()
        return count > 0
    }
}
