package com.andikas.burogu.utils

import com.andikas.burogu.data.model.Article

object DataDummy {

    fun generateDummyArticles(): List<Article> {
        val articles = ArrayList<Article>()

        articles.add(
            Article(
                id = null,
                imagePath = "https://i.ibb.co/0Jr4fg4/Business-people-studying-list-of-rules-reading-guidance-making-checklist-Vector-illustration-for-com.jpg",
                title = "Petunjuk Penggunaan Burogu",
                content = "Anda dapat menjelajahi artikel buatan anda pada halaman Home, anda juga dapat melihat detail dari artikel dengan meng-klik artikel yang terdapat di list.\n\nSelanjutnya dalam halaman Detail, anda dapat memilih opsi untuk menandai, mengedit, atau menghapus artikel yang telah anda buat. Untuk kembali ke halaman Home anda dapat menekan tombol back pada Navigasi Ponsel anda atau pada tombol di sisi kiri atas.\n\nPada halaman Home, anda juga bisa masuk ke halaman Profil dengan menekan tombol pada sisi kanan atas. Di halaman Profil, anda dapat melihat nama anda dan navigasi untuk melihat artikel yang telah anda tandai.",
                createdDate = "Feb 23, 2022",
                author = "Andikas",
                isBookmarked = false,
                isDummy = true
            )
        )

        articles.add(
            Article(
                id = null,
                imagePath = null,
                title = "Contoh Artikel",
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\nSed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?",
                createdDate = "Feb 23, 2022",
                author = "Andikas",
                isBookmarked = false,
                isDummy = true
            )
        )

        return articles
    }

}