package com.example.ppab_reseporia

// Untuk Responsi 1 masih belum menerapkan database sehingga semua data resep akan diakses melalui file DataResep
data class DataResep(
    val name: String,
    val imageRes: Int,
    val rating: Double,
    val likes: Int,
    val time: String,
    val category: String,
    val about: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val videoId: String? = null  // Tambahkan ini
)

val allFoodList = listOf(
    DataResep(
        name = "Ayam Woku",
        imageRes = R.drawable.ayamwoku,
        rating = 4.7,
        likes = 57,
        time = "40m",
        category = "Main Course",
        about = "Ayam Woku adalah masakan ayam khas Manado yang kaya rempah dengan cita rasa pedas dan gurih. Menggunakan bumbu woku yang melimpah, masakan ini cocok disajikan dengan nasi hangat.",
        ingredients = listOf(
            "1/2 ekor ayam",
            "8 cabe merah keriting",
            "10 bawang merah",
            "5 bawang putih",
            "1 ruas kunyit",
            "2 butir kemiri sangrai",
            "1 batang sereh, memarkan",
            "1 ruas jahe, memarkan",
            "2 lembar daun jeruk",
            "Daun kemangi secukupnya",
            "Air secukupnya",
            "Minyak goreng secukupnya",
            "1 sdt garam",
            "1 bungkus masako ayam",
            "1 sdt gula"
        ),
        instructions = listOf(
            "Cuci bersih ayam, potong sesuai selera, kemudian rebus sampai matang. Tiriskan.",
            "Haluskan bumbu (cabe, bawang merah, bawang putih, kunyit, kemiri).",
            "Panaskan minyak, tumis bumbu halus, sereh, jahe, dan daun jeruk sampai harum dan matang.",
            "Masukkan potongan ayam, aduk rata. Tambahkan air secukupnya.",
            "Bumbui dengan gula, garam, dan masako ayam. Masak hingga bumbu meresap dan ayam empuk.",
            "Terakhir, masukkan daun kemangi, aduk sebentar hingga layu. Angkat dan sajikan selagi hangat."
        ),
        videoId = "H_8fVsFfxP0"
    ),
    DataResep(
        name = "Mango Sticky",
        imageRes = R.drawable.mangosticky,
        rating = 4.5,
        likes = 34,
        time = "30m",
        category = "Dessert",
        about = "Mango Sticky Rice adalah hidangan penutup klasik Thailand yang terdiri dari nasi ketan manis, potongan mangga segar, dan siraman santan kental. Perpaduan rasa manis, gurih, dan segar yang sempurna.",
        ingredients = listOf(
            "250 gr beras ketan, rendam semalaman",
            "200 ml santan kental",
            "50 gr gula pasir",
            "1/2 sdt garam",
            "1 lembar daun pandan",
            "2 buah mangga harum manis, potong-potong",
            "Wijen sangrai untuk taburan"
        ),
        instructions = listOf(
            "Kukus beras ketan yang sudah direndam selama 20 menit hingga setengah matang.",
            "Rebus santan kental, gula pasir, garam, dan daun pandan hingga mendidih sambil terus diaduk. Angkat.",
            "Campurkan ketan setengah matang dengan santan rebus. Aduk rata, biarkan santan meresap.",
            "Kukus kembali ketan selama 15 menit hingga matang.",
            "Sajikan ketan dengan potongan mangga segar dan siraman santan kental sisa (jika ada). Taburi dengan wijen sangrai."
        ),
        videoId = "u7QfzLYeBBY"
    ),
    DataResep(
        name = "Salad Buah",
        imageRes = R.drawable.saladbuah,
        rating = 4.9,
        likes = 45,
        time = "50m",
        category = "Appetizer",
        about = "Salad buah segar dengan saus creamy yang manis dan sedikit asam, cocok sebagai hidangan pembuka yang menyehatkan atau camilan ringan.",
        ingredients = listOf(
            "Aneka buah segar (melon, semangka, anggur, stroberi, kiwi)",
            "100 gr mayonaise",
            "50 gr yogurt plain",
            "2 sdm susu kental manis",
            "Keju cheddar parut untuk taburan"
        ),
        instructions = listOf(
            "Cuci bersih semua buah dan potong dadu sesuai selera.",
            "Campurkan mayonaise, yogurt plain, dan susu kental manis. Aduk rata hingga menjadi saus.",
            "Tuang saus ke atas potongan buah, aduk perlahan hingga tercampur rata.",
            "Masukkan ke dalam lemari es selama minimal 30 menit agar dingin dan saus lebih meresap.",
            "Sajikan dingin dengan taburan keju cheddar parut."
        ),
        videoId = "u7QfzLYeBBY"
    ),
    DataResep(
        name = "Tahu Bihun",
        imageRes = R.drawable.tahubihun,
        rating = 4.2,
        likes = 22,
        time = "25m",
        category = "Appetizer",
        about = "Tahu Bihun adalah hidangan sederhana namun lezat, perpaduan tahu goreng renyah dan bihun yang dibumbui dengan rempah-rempah pilihan. Cocok sebagai camilan atau lauk pendamping.",
        ingredients = listOf(
            "5 potong tahu goreng, potong dadu",
            "100 gr bihun, rendam air panas",
            "2 siung bawang putih, cincang halus",
            "3 siung bawang merah, iris tipis",
            "2 buah cabai rawit (sesuai selera), iris",
            "1 batang daun bawang, iris",
            "1 sdm kecap manis",
            "1/2 sdt garam",
            "1/4 sdt merica bubuk",
            "Minyak untuk menumis"
        ),
        instructions = listOf(
            "Tumis bawang putih, bawang merah, dan cabai rawit hingga harum.",
            "Masukkan tahu goreng, aduk rata.",
            "Masukkan bihun yang sudah ditiriskan, tambahkan kecap manis, garam, dan merica bubuk. Aduk rata.",
            "Masak hingga bumbu meresap dan bihun matang. Tambahkan daun bawang, aduk sebentar.",
            "Angkat dan sajikan selagi hangat."
        ),
        videoId = "0gmZsHZFDfQ"
    ),
    DataResep(
        name = "Wedang Jahe",
        imageRes = R.drawable.wedangjahe,
        rating = 4.8,
        likes = 78,
        time = "45m",
        category = "Drinks",
        about = "Minuman tradisional Indonesia yang terbuat dari jahe, gula merah, dan rempah-rempah lain. Sangat cocok untuk menghangatkan tubuh dan meredakan masuk angin.",
        ingredients = listOf(
            "2 ruas jahe, bakar dan memarkan",
            "1 liter air",
            "100 gr gula merah, sisir",
            "1 batang sereh, memarkan",
            "2 lembar daun pandan"
        ),
        instructions = listOf(
            "Bakar jahe sebentar, lalu memarkan.",
            "Rebus air bersama jahe, gula merah, sereh, dan daun pandan hingga mendidih dan gula larut.",
            "Kecilkan api, masak terus sekitar 15-20 menit agar sari jahe keluar sempurna.",
            "Saring dan sajikan wedang jahe selagi hangat."
        ),
        videoId = "3IiC8HD-ZDw"
    ),
    DataResep(
        name = "Pindang Serani",
        imageRes = R.drawable.pindangserani,
        rating = 4.1,
        likes = 15,
        time = "20m",
        category = "Main Course",
        about = "Pindang Serani adalah olahan ikan berkuah bening khas Jepara, Jawa Tengah. Rasanya segar, pedas, dan sedikit asam, cocok disajikan saat cuaca panas.",
        ingredients = listOf(
            "500 gr ikan kakap/bandeng, potong-potong",
            "1 liter air",
            "2 lembar daun salam",
            "2 ruas lengkuas, memarkan",
            "1 batang serai, memarkan",
            "5 buah belimbing wuluh, iris",
            "2 buah tomat hijau, potong-potong",
            "Garam dan gula secukupnya",
            "Bumbu halus:",
            "- 5 siung bawang merah",
            "- 3 siung bawang putih",
            "- 3 buah cabai merah",
            "- 1 ruas kunyit",
            "- 1 ruas jahe"
        ),
        instructions = listOf(
            "Bersihkan ikan, lumuri dengan sedikit garam dan air jeruk nipis. Diamkan sebentar.",
            "Tumis bumbu halus hingga harum. Masukkan daun salam, lengkuas, dan serai. Aduk rata.",
            "Masukkan air, masak hingga mendidih.",
            "Masukkan potongan ikan, belimbing wuluh, dan tomat hijau. Bumbui dengan garam dan gula.",
            "Masak hingga ikan matang dan bumbu meresap. Koreksi rasa.",
            "Angkat dan sajikan pindang serani selagi hangat."
        ),
        videoId = "iTTUfia_mdo"
    ),
    DataResep(
        name = "Tiramisu Dessert",
        imageRes = R.drawable.tiramisudessert,
        rating = 4.6,
        likes = 48,
        time = "35m",
        category = "Dessert",
        about = "Tiramisu adalah hidangan penutup klasik Italia yang terdiri dari lapisan ladyfinger yang direndam kopi, krim mascarpone lembut, dan taburan kakao.",
        ingredients = listOf(
            "250 gr mascarpone cheese",
            "2 butir telur, pisahkan kuning dan putihnya",
            "50 gr gula pasir",
            "200 ml kopi hitam dingin",
            "1 sdm rum/kahlua (opsional)",
            "1 bungkus ladyfinger/bolu kering",
            "Cokelat bubuk untuk taburan"
        ),
        instructions = listOf(
            "Kocok kuning telur dan gula pasir hingga kental dan pucat.",
            "Masukkan mascarpone, aduk rata hingga lembut.",
            "Di wadah terpisah, kocok putih telur hingga kaku. Campurkan perlahan ke dalam adonan mascarpone.",
            "Campurkan kopi hitam dingin dengan rum/kahlua.",
            "Celupkan ladyfinger satu per satu ke dalam campuran kopi, jangan terlalu lama.",
            "Tata ladyfinger di dasar wadah, lapisi dengan adonan mascarpone. Ulangi hingga adonan habis.",
            "Dinginkan di kulkas minimal 4 jam atau semalaman. Taburi cokelat bubuk sebelum disajikan."
        ),
        videoId = "7VTtenyKRg4"
    ),
    DataResep(
        name = "Strawberry Matcha",
        imageRes = R.drawable.strawberrymatcha,
        rating = 4.3,
        likes = 27,
        time = "28m",
        category = "Drinks",
        about = "Minuman segar perpaduan matcha yang pahit dengan manisnya stroberi, menciptakan sensasi rasa yang unik dan menyegarkan.",
        ingredients = listOf(
            "1 sdt bubuk matcha",
            "50 ml air panas",
            "100 gr stroberi segar, haluskan",
            "2 sdm sirup gula (sesuai selera)",
            "200 ml susu segar dingin",
            "Es batu secukupnya"
        ),
        instructions = listOf(
            "Larutkan bubuk matcha dengan air panas, aduk hingga tidak ada gumpalan.",
            "Dalam gelas saji, masukkan stroberi yang sudah dihaluskan dan sirup gula.",
            "Tambahkan es batu secukupnya.",
            "Tuang susu segar dingin.",
            "Perlahan tuang larutan matcha di atas susu. Jangan diaduk agar terbentuk gradasi warna.",
            "Sajikan segera."
        ),
        videoId = "y81HrOvgM9o"
    ),
    DataResep(
        name = "Es Kuwut",
        imageRes = R.drawable.eskuwut,
        rating = 4.7,
        likes = 56,
        time = "40m",
        category = "Drinks",
        about = "Es Kuwut adalah minuman khas Bali yang segar, terbuat dari kelapa muda, melon, biji selasih, dan air kelapa, disajikan dengan sirup dan es.",
        ingredients = listOf(
            "Daging kelapa muda dari 1 buah kelapa",
            "1/2 buah melon, serut",
            "1 sdm biji selasih, rendam air hangat hingga mengembang",
            "Air kelapa muda secukupnya",
            "Sirup coco pandan/gula cair secukupnya",
            "Es batu secukupnya"
        ),
        instructions = listOf(
            "Siapkan semua bahan.",
            "Dalam gelas saji atau mangkuk besar, masukkan daging kelapa muda, serutan melon, dan biji selasih.",
            "Tambahkan air kelapa muda dan sirup gula/coco pandan sesuai selera.",
            "Masukkan es batu secukupnya.",
            "Aduk rata dan sajikan dingin."
        ),
        videoId = "H_8fVsFfxP0"
    )
)