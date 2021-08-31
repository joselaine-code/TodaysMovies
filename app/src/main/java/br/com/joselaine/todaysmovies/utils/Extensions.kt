package br.com.joselaine.todaysmovies.utils

fun String?.getFullImageUrl() = "https://image.tmdb.org/t/p/w500$this"

fun String?.dateFormat(): String {
    val dateList = this?.split("-")
    return if (dateList?.size == 3){
        "${dateList[2]}/${dateList[1]}/${dateList[0]}"
    } else {
        "Sem data"
    }
}
